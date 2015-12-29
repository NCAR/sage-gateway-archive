package sgf.gateway.search.index;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.SolrInputField;
import sgf.gateway.search.core.SearchIndexException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class SearchIndexSolr implements SearchIndex {

    private static final Log LOG = LogFactory.getLog(SearchIndex.class);

    private final SolrServer solrServer;

    public SearchIndexSolr(SolrServer solrServer) {
        super();
        this.solrServer = solrServer;
    }

    @Override
    public void purge() {

        try {
            this.solrServer.deleteByQuery("*:*");
        } catch (Exception e) {
            throw new SearchIndexException(e);
        }
    }

    @Override
    public void commit() {

        try {
            this.solrServer.commit();
        } catch (Exception e) {
            throw new SearchIndexException(e);
        }
    }

    @Override
    public void optimize() {

        try {
            this.solrServer.optimize();
        } catch (Exception e) {
            throw new SearchIndexException(e);
        }
    }

    @Override
    public void delete(SearchDocument searchDocument) {

        List<String> deleteQueryComponents = new ArrayList<String>();

        for (SearchField searchField : searchDocument) {
            deleteQueryComponents.add(searchField.getFieldName() + ":(" + searchField.getValue() + ")");
        }

        String deleteQuery = StringUtils.join(deleteQueryComponents, " AND ");

        this.delete(deleteQuery);
    }

    @Override
    public void add(SearchDocument searchDocument) {
        SolrInputDocument inputDocument = this.transform(searchDocument);
        this.load(inputDocument);
    }

    private SolrInputDocument transform(SearchDocument searchDocument) {

        Collection<SolrInputField> inputFields = createInputFields(searchDocument);
        SolrInputDocument inputDocument = createInputDocument(inputFields);

        return inputDocument;
    }

    private Collection<SolrInputField> createInputFields(SearchDocument document) {

        List<SolrInputField> solrInputFields = new ArrayList<SolrInputField>();

        for (SearchField searchField : document) {
            SolrInputField inputField = new SolrInputField(searchField.getFieldName());
            inputField.addValue(searchField.getValue(), 1.0F);
            solrInputFields.add(inputField);
        }

        return solrInputFields;
    }

    private SolrInputDocument createInputDocument(Collection<SolrInputField> inputFields) {

        SolrInputDocument inputDocument = new SolrInputDocument();

        for (SolrInputField inputField : inputFields) {
            inputDocument.put(inputField.getName(), inputField);
        }

        return inputDocument;
    }

    private void load(SolrInputDocument inputDocument) {

        this.logSolrInputDocument(inputDocument);

        try {
            this.solrServer.add(inputDocument);
        } catch (Exception e) {
            throw new SearchIndexException(e);
        }
    }

    private void delete(String query) {

        try {
            this.solrServer.deleteByQuery(query);
        } catch (Exception e) {
            throw new SearchIndexException(e);
        }
    }

    private void logSolrInputDocument(SolrInputDocument document) {

        if (LOG.isTraceEnabled()) {

            LOG.trace("SolrInputDocument start");

            for (String name : document.getFieldNames()) {

                Collection<Object> fieldValues = document.getFieldValues(name);

                if (null != fieldValues) {

                    for (Object value : fieldValues) {
                        LOG.trace(name + "|" + value);
                    }
                }
            }

            LOG.trace("SolrInputDocument finish");
        }
    }
}
