package sgf.gateway.main.publishing.hessian.client.localhost;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import sgf.gateway.main.publishing.hessian.client.RemotePublishingServiceClient;
import sgf.gateway.service.publishing.api.RemotePublishingService;

/**
 * An example of a remote publishing client.
 */
public class ClientCertRemotePublishingServiceClient extends RemotePublishingServiceClient {

    public ClientCertRemotePublishingServiceClient(RemotePublishingService remotePublishingService) {

        super(remotePublishingService);
    }

    public static void main(String... s) throws Exception {

        // If needed, the keystore and truststore should be set by virtual machine arguments:
        // -Djavax.net.ssl.trustStore="<file location of your truststore>"
        // -Djavax.net.ssl.trustStorePassword="<not needed if password is 'changeit'>"
        // -Djavax.net.ssl.keyStore="<file location of your keystore>"
        // -Djavax.net.ssl.keyStorePassword="<not needed if password is 'changeit'"
        //
        // To get this client to work, there needs to be mutual authentication between the server and the client.
        // Please see the following wiki page on how to setup/enable mutual authentication:
        // https://wiki.ucar.edu/display/VETS/Enabling+Mutual+Authentication+over+SSL

        ApplicationContext ctxt = new ClassPathXmlApplicationContext("classpath:sgf/gateway/main/publishing/hessian/client/localhost/client-localhost.xml");
        RemotePublishingService remotePublishingService = (RemotePublishingService) ctxt.getBean("clientCertRemotePublishingServiceProxy");

        ClientCertRemotePublishingServiceClient self = new ClientCertRemotePublishingServiceClient(remotePublishingService);

        // new methods
        self.createDataset();
        //self.updateDataset();
        //self.updateDataset();
        //self.retractDataset();
        //self.deleteDataset();
        //self.cancel();
    }
}
