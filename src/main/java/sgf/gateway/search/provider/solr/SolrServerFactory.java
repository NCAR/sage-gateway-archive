package sgf.gateway.search.provider.solr;

import org.apache.solr.client.solrj.SolrServer;

public interface SolrServerFactory {

    SolrServer get();

}
