package sgf.gateway.search.provider.solr;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.embedded.EmbeddedSolrServer;
import org.apache.solr.core.CoreContainer;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;

public class EmbeddedSolrServerFactory implements SolrServerFactory {

    private static final String DEFAULT_CORE_NAME = "default-core";
    private static final String CORE_CONFIG_FILENAME = "solr.xml";
    private static final String SOLR_DATA_DIR_PROPERTY = "solr.data.dir";

    private final File home;
    private CoreContainer coreContainer;

    public EmbeddedSolrServerFactory(Resource homeResource, String dataPath) {
        this.home = getHome(homeResource);
        System.setProperty(SOLR_DATA_DIR_PROPERTY, dataPath); // used by solr configuration to set data directory
    }

    private File getHome(Resource homeResource) {
        try {
            return homeResource.getFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public SolrServer get() {

        createCoreContainer();

        EmbeddedSolrServer server = new EmbeddedSolrServer(coreContainer, DEFAULT_CORE_NAME);

        return server;
    }

    private void createCoreContainer() {
        File coreConfig = new File(home, CORE_CONFIG_FILENAME);
        coreContainer = CoreContainer.createAndLoad(home.getAbsolutePath(), coreConfig);
    }

    public void shutDownContainer() {
        coreContainer.shutdown();
    }
}
