package sgf.gateway.main.xml.hessian.client.localhost;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import sgf.gateway.main.xml.hessian.client.RemoteMetadataServiceClient;
import sgf.gateway.service.xml.api.RemoteMetadataService;

import java.io.IOException;

public class NonSecureRemoteMetadataServiceClient {

    /**
     * Main takes no arguments
     *
     * @param s
     * @throws IOException
     * @throws InterruptedException
     */
    public static void main(String... s) throws Exception {

        // obtain proxy of RemoteMetadataService
        ApplicationContext ctxt = new ClassPathXmlApplicationContext("classpath:sgf/gateway/main/xml/hessian/client/localhost/client-localhost.xml");
        RemoteMetadataService proxy = (RemoteMetadataService) ctxt.getBean("nonSecureRemoteMetadataServiceProxy");

        // execute standard invocations
        RemoteMetadataServiceClient client = new RemoteMetadataServiceClient(proxy);
        client.execute();

    }
}
