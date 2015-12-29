package sgf.gateway.search.api;

import org.apache.solr.client.solrj.SolrServerException;

import java.io.IOException;
import java.util.Date;

public interface IncrementalIndexer {

    void index(Date lastIndexTime) throws SolrServerException, IOException;

    void reindex();
}
