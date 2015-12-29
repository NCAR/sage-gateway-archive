package sgf.gateway.search.provider.solr;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.util.NamedList;
import sgf.gateway.search.api.*;
import sgf.gateway.search.core.FacetsImpl;
import sgf.gateway.search.core.ResultImpl;
import sgf.gateway.search.core.ResultsImpl;
import sgf.gateway.search.core.SearchResultImpl;
import sgf.gateway.search.provider.solr.facets.SolrFacetFactory;

import java.util.List;
import java.util.Set;

public class SolrResultsFactoryImpl implements SolrResultsFactory {

    protected static final Log LOG = LogFactory.getLog(SolrResultsFactory.class);

    private SolrFacetFactory facetFactory;
    private String idField;
    private String typeField;
    private String nameField;
    private String descriptionField;
    private String shortNameField;
    private String authoritativeSourceURIField;
    private String detailsURIField;
    private String remoteIndexableType;
    private Set<String> nonDownloadableDocTypes;

    public SolrResultsFactoryImpl(SolrFacetFactory facetFactory, String idField, String typeField, String nameField, String descriptionField, String shortNameField,
                                  String authoritativeSourceURIField, String detailsURIField, String remoteIndexableType, Set<String> nonDownloadableDocTypes) {
        this.facetFactory = facetFactory;
        this.idField = idField;
        this.typeField = typeField;
        this.nameField = nameField;
        this.descriptionField = descriptionField;
        this.shortNameField = shortNameField;
        this.authoritativeSourceURIField = authoritativeSourceURIField;
        this.detailsURIField = detailsURIField;
        this.remoteIndexableType = remoteIndexableType;
        this.nonDownloadableDocTypes = nonDownloadableDocTypes;
    }

    @Override
    public SearchResult getSearchResult(Criteria searchCriteria, QueryResponse queryResponse) {

        Long resultCount = getResultCount(queryResponse);
        Facets facets = getFacets(queryResponse);
        Results results = getResults(queryResponse);
        Results moreLikeThisResults = getMoreLikeThisResults(queryResponse);

        SearchResultImpl searchResult = new SearchResultImpl(searchCriteria, resultCount, facets, results);

        searchResult.setMoreLikeThisResults(moreLikeThisResults);

        return searchResult;
    }

    protected Long getResultCount(QueryResponse queryResponse) {
        SolrDocumentList solrResults = queryResponse.getResults();
        return solrResults.getNumFound();
    }

    protected Facets getFacets(QueryResponse queryResponse) {

        Facets facets = new FacetsImpl();

        List<FacetField> facetFields = queryResponse.getLimitingFacets();

        if (facetFields != null) {
            for (FacetField field : facetFields) {
                Facet facet = createFacet(field);
                facets.add(facet);
            }
        }

        return facets;
    }

    protected Facet createFacet(FacetField facetField) {

        Facet facet = facetFactory.build(facetField);

        return facet;
    }

    protected Results getResults(QueryResponse queryResponse) {

        SolrDocumentList solrResults = queryResponse.getResults();

        Results results = new ResultsImpl();
        for (SolrDocument document : solrResults) {
            Result result = this.map(document);
            results.add(result);
        }

        return results;
    }

    protected Results getMoreLikeThisResults(QueryResponse queryResponse) {

        Results results = new ResultsImpl();

        NamedList<Object> moreLikeThis = (NamedList<Object>) queryResponse.getResponse().get("moreLikeThis");

        if (moreLikeThis != null && moreLikeThis.size() > 0) {

            List<SolrDocument> docs = (List<SolrDocument>) moreLikeThis.getVal(0);

            for (SolrDocument doc : docs) {
                Result result = this.map(doc);
                results.add(result);
            }
        }

        return results;
    }

    protected Result map(SolrDocument document) {

        ResultImpl result = new ResultImpl();

        result.setTitle(getSolrDocumentFieldValue(document, this.nameField));
        result.setDescription(getSolrDocumentFieldValue(document, this.descriptionField));
        result.setShortName(getSolrDocumentFieldValue(document, this.shortNameField));
        result.setAuthoritativeSourceURI(getSolrDocumentFieldValue(document, this.authoritativeSourceURIField));
        result.setDetailsURI(getSolrDocumentFieldValue(document, this.detailsURIField));
        result.setIdentifier(getSolrDocumentFieldValue(document, this.idField));
        result.setType(getSolrDocumentFieldValue(document, this.typeField));
        result.setDownloadable(isDownloadable(document));
        result.setRemoteIndexable(isRemoteIndexable(document));

        return result;
    }

    protected String getSolrDocumentFieldValue(SolrDocument document, String field) {

        String fieldValue;

        Object value = document.getFieldValue(field);

        if (null == value) {
            fieldValue = "";
        } else {
            fieldValue = value.toString();
        }

        return fieldValue;
    }

    protected Boolean isDownloadable(SolrDocument document) {

        Boolean downloadable = true;
        String type = getSolrDocumentFieldValue(document, this.typeField);

        if (nonDownloadableDocTypes.contains(type)) {
            downloadable = false;
        }

        return downloadable;
    }

    protected Boolean isRemoteIndexable(SolrDocument document) {

        Boolean remoteIndexable = false;
        String type = getSolrDocumentFieldValue(document, this.typeField);

        if (type.equals(this.remoteIndexableType)) {
            remoteIndexable = true;
        }

        return remoteIndexable;
    }
}
