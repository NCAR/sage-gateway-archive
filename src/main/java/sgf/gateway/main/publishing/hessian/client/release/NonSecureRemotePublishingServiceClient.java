package sgf.gateway.main.publishing.hessian.client.release;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import sgf.gateway.main.publishing.hessian.client.RemotePublishingServiceClient;
import sgf.gateway.service.publishing.api.RemotePublishingService;

import java.io.IOException;

/**
 * An example of a remote publishing client.
 */
public class NonSecureRemotePublishingServiceClient extends RemotePublishingServiceClient {

    public NonSecureRemotePublishingServiceClient(RemotePublishingService remotePublishingService) {

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

        ApplicationContext ctxt = new ClassPathXmlApplicationContext("classpath:sgf/gateway/main/publishing/hessian/client/release/client-release.xml");
        RemotePublishingService remotePublishingService = (RemotePublishingService) ctxt.getBean("nonSecureRemotePublishingServiceProxy");

        NonSecureRemotePublishingServiceClient self = new NonSecureRemotePublishingServiceClient(remotePublishingService);
        self.createDataset();
    }
}
