package sgf.gateway.main.publishing.hessian.client.release;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import sgf.gateway.main.publishing.hessian.client.RemotePublishingServiceClient;
import sgf.gateway.service.publishing.api.RemotePublishingService;

import java.io.IOException;

/**
 * An example of a remote publishing client.
 */
public class EncryptedRemotePublishingServiceClient extends RemotePublishingServiceClient {

    public EncryptedRemotePublishingServiceClient(RemotePublishingService remotePublishingService) {

        super(remotePublishingService);
    }

    /**
     * Main takes no arguments
     *
     * @param s
     * @throws IOException
     * @throws InterruptedException
     */
    public static void main(String... s) throws IOException, InterruptedException {

        // If needed, the keystore and truststore should be set by virtual machine arguments:
        // -Djavax.net.ssl.trustStore="<file location of your truststore>"
        // -Djavax.net.ssl.trustStorePassword="<not needed if password is 'changeit'>"
        // -Djavax.net.ssl.keyStore="<file location of your keystore>"
        // -Djavax.net.ssl.keyStorePassword="<not needed if password is 'changeit'"
        //
        //ClassLoader cloader = EncryptedRemotePublishingServiceClient.class.getClassLoader();
        //ClassPathResource trustore = new ClassPathResource("sgf/gateway/applications/keystores/keystore-esg-cet.ucar.edu", cloader);
        //System.setProperty("javax.net.ssl.trustStore", trustore.getFile().getAbsolutePath());
        //System.setProperty("javax.net.ssl.trustStorePassword", "changeit");

        ApplicationContext ctxt = new ClassPathXmlApplicationContext("classpath:sgf.gateway/main/hessian/client/release/client-release.xml");
        RemotePublishingService remotePublishingService = (RemotePublishingService) ctxt.getBean("encryptedRemotePublishingServiceProxy");

        EncryptedRemotePublishingServiceClient self = new EncryptedRemotePublishingServiceClient(remotePublishingService);
        self.createDataset();
    }
}
