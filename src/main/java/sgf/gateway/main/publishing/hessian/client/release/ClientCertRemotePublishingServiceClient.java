package sgf.gateway.main.publishing.hessian.client.release;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import sgf.gateway.main.publishing.hessian.client.RemotePublishingServiceClient;
import sgf.gateway.service.publishing.api.RemotePublishingService;

import java.io.IOException;

/**
 * An example of a remote publishing client.
 */
public class ClientCertRemotePublishingServiceClient extends RemotePublishingServiceClient {

    public ClientCertRemotePublishingServiceClient(RemotePublishingService remotePublishingService) {

        super(remotePublishingService);
    }

    /**
     * Main takes no arguments
     *
     * @param s
     * @throws IOException
     * @throws InterruptedException
     */
    public static void main(String... s) throws Exception {

        // If needed, the keystore and truststore should be set by virtual machine arguments:
        // -Djavax.net.ssl.trustStore="<file location of your truststore>"
        // -Djavax.net.ssl.trustStorePassword="<not needed if password is 'changeit'>"
        // -Djavax.net.ssl.keyStore="<file location of your keystore>"
        // -Djavax.net.ssl.keyStorePassword="<not needed if password is 'changeit'"
        //
        //KeystoreUtils.setTruststore("sgf/gateway/applications/keystores/jssecacerts");
        //KeystoreUtils.setKeystore("sgf/gateway/applications/keystores/keystore-rootAdmin-ncar");

        ApplicationContext ctxt = new ClassPathXmlApplicationContext("classpath:sgf/gateway/main/publishing/hessian/client/release/client-release.xml");
        RemotePublishingService remotePublishingService = (RemotePublishingService) ctxt.getBean("clientCertRemotePublishingServiceProxy");

        ClientCertRemotePublishingServiceClient self = new ClientCertRemotePublishingServiceClient(remotePublishingService);
        self.createDataset();
    }
}
