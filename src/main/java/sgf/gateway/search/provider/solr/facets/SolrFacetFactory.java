package sgf.gateway.search.provider.solr.facets;

import org.apache.solr.client.solrj.response.FacetField;
import sgf.gateway.search.api.Facet;

public interface SolrFacetFactory {

    Facet build(FacetField facetField);

}
