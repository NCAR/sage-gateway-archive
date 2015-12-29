package sgf.gateway.dao.metadata.impl.hibernate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import sgf.gateway.dao.metadata.DatasetRepository;
import sgf.gateway.model.metadata.Dataset;

import java.util.List;

/*
 * Run As: Java Application
 * 
 * Run Configuration or you wont' get gateway.properties, etc. startup values:
 * 
 * Took these from Server Arguments:
 * -Dcatalina.base="C:\eclipse_workspace\.metadata\.plugins\org.eclipse.wst.server.core\tmp3" -Dcatalina.home="C:\tomcat7\apache-tomcat-7.0.41" -Dwtp.deploy="C:\eclipse_workspace\.metadata\.plugins\org.eclipse.wst.server.core\tmp3\wtpwebapps" -Djava.endorsed.dirs="C:\tomcat7\apache-tomcat-7.0.41\endorsed"  -Djavax.net.ssl.trustStore="C:\gateway\home-cadis2\certs\jssecacerts" -Djavax.net.ssl.trustStorePassword="changeit" -Dgateway.home="C:\gateway\home-cadis2"  -XX:MaxPermSize=256M -Xmx1024m
 *
 * Stop app, Clear out terminated launches in debug window and console.  Else get
 * org.apache.solr.common.SolrException: Index locked for write for core default-core
 * 
 * To load AppContext, needed to fix xml by moving defn of wgetScriptViewSelector to dataset-controllers.xml
 */
//public class RepositoryTest  extends ApplicationObjectSupport implements ApplicationContextAware{
public class RepositoryTest implements ApplicationContextAware {

    private static final Log LOG = LogFactory.getLog(RepositoryTest.class);

    protected ApplicationContext applicationContext;
    private final PlatformTransactionManager transactionManager;

    TransactionTemplate transactionTemplate;

    DatasetRepository datasetRepository;


    public RepositoryTest() {

        final ClassPathXmlApplicationContext ctxt = new ClassPathXmlApplicationContext("classpath:sgf/gateway/application-context-main.xml");
        this.applicationContext = ctxt;

        this.transactionManager = (PlatformTransactionManager) applicationContext.getBean("transactionManager");

        this.transactionTemplate = new TransactionTemplate(this.transactionManager);
        transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
    }

    /**
     * MAIN CLASS
     * Run As Java Application (with a run config). No args.
     */
    public static void main(String[] args) {

        System.out.println("Begin Test....");

        RepositoryTest repositoryTest = new RepositoryTest();

        repositoryTest.testDatasetRepository();
    }

    // Test methods in the DatasetRepository
    public void testDatasetRepository() {
        System.out.println("Hello!!");

        this.datasetRepository = (DatasetRepository) this.applicationContext.getBean("datasetRepository");
        System.out.println("Got Repos!");

        // Option 1: Do all tests in same transaction (avoids dup Txn code)
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {

            public void doInTransactionWithoutResult(TransactionStatus status) {
                // Make method
                Dataset dataset = datasetRepository.getByShortName("Plume7");
                System.out.println("Got Dataset! ->" + dataset.getShortName());

                //DM
                List pointOfContacts = dataset.getDescriptiveMetadata().getPointOfContacts();
                System.out.println("This many POCS: " + pointOfContacts.size());

                List<Dataset> datasetDatasets = datasetRepository.getDatasetTypeDatasets();
                System.out.println("Got This many dataset type datasets ->" + datasetDatasets.size());

            }

        });

        // Option 2: Or do each in own transaction inside its own method - autonomous
        //testBadGetShortName(transactionTemplate, datasetRepository);
        testFindProjectTypeDatasets();

        // Close transaction?

    }

    void testBadGetShortName(TransactionTemplate transactionTemplate, final DatasetRepository datasetRepository) {

        transactionTemplate.execute(new TransactionCallbackWithoutResult() {

            public void doInTransactionWithoutResult(TransactionStatus status) {
                try {
                    Dataset dataset = datasetRepository.getByShortName("Plume8xxx");
                    System.out.println("Got Dataset Again! ->" + dataset.getShortName());
                } catch (Exception ex) {

                    System.out.println("Failed to get Plume8xxx");
                    status.setRollbackOnly();

                }
            }
        });
    }

    void testFindProjectTypeDatasets() {

        transactionTemplate.execute(new TransactionCallbackWithoutResult() {

            public void doInTransactionWithoutResult(TransactionStatus status) {

                List<Dataset> projectDatasets = datasetRepository.getProjectTypeDatasets();
                System.out.println("Got This many project datasets ->" + projectDatasets.size());

                Dataset projectDataset = datasetRepository.getProjectByShortName("a_modular_approach_to_building_an_arctic_observing_system_for_the_ipy_and_beyond_in_the_switchyard_region_of_the_arctic_ocean");
                System.out.println("Got projectDataset ->" + projectDataset.getShortName() + " - " + projectDataset.getIdentifier());

                Dataset notprojectDataset = datasetRepository.getProjectByShortName("Plume13");
                System.out.println("Got projectDataset ->" + notprojectDataset.getShortName() + " - " + notprojectDataset.getIdentifier());


            }
        });
    }

    void testFindDatasetTypeDatasets(TransactionTemplate transactionTemplate, final DatasetRepository datasetRepository) {

        transactionTemplate.execute(new TransactionCallbackWithoutResult() {

            public void doInTransactionWithoutResult(TransactionStatus status) {
                // Make method
                List<Dataset> datasetDatasets = datasetRepository.getDatasetTypeDatasets();
                System.out.println("Got This many dataset type datasets ->" + datasetDatasets.size());
            }
        });
    }

    // Must have this unless you do ApplicationObjectSupport
    public final void setApplicationContext(final ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }


}
