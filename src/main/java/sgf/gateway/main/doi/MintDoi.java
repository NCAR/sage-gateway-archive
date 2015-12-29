package sgf.gateway.main.doi;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

public class MintDoi {

    private RestTemplate restTemplate;

    public MintDoi() {

        HttpClientConnectionManager httpClientConnectionManager = new PoolingHttpClientConnectionManager();

        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(new AuthScope(null, -1), new UsernamePasswordCredentials("apitest", "apitest"));

        RequestConfig.Builder requestConfigBuilder = RequestConfig.custom();
        requestConfigBuilder.setConnectionRequestTimeout(60000);
        requestConfigBuilder.setConnectTimeout(60000);

        RequestConfig requestConfig = requestConfigBuilder.build();

        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        httpClientBuilder.setConnectionManager(httpClientConnectionManager);
        httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
        httpClientBuilder.setDefaultRequestConfig(requestConfig);

        HttpClient httpClient = httpClientBuilder.build();

        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);

        this.restTemplate = new RestTemplate(clientHttpRequestFactory);
    }

    public static void main(String[] args) {

        MintDoi mintDoi = new MintDoi();

        try {

            //mintDoi.checkStatus();
            //mintDoi.mintDoi();
            //mintDoi.updateDoi();
            mintDoi.getDoi();

        } catch (HttpClientErrorException e) {

            System.out.println("Exception: " + e.getMessage());
            System.out.println(e.getResponseBodyAsString());
        }
    }

    public void checkStatus() {

        ResponseEntity<String> responseEntity = restTemplate.exchange("https://ezid.cdlib.org/status", HttpMethod.GET, null, String.class);

        System.out.println(responseEntity.getBody());
    }

    public void mintDoi() {

        Datacite datacite = new Datacite();
        datacite.setCreator("test creator");
        datacite.setTitle("test title");
        datacite.setPublisher("test publisher");
        datacite.setPublicationyear("2014");
        datacite.setResourcetype("Collection");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);

        HttpEntity<String> requestEntity = new HttpEntity<String>(datacite.toString(), headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange("https://ezid.cdlib.org/shoulder/doi:10.5072/FK2", HttpMethod.POST, requestEntity, String.class);

        System.out.println("mint: " + responseEntity.getBody());
    }

    public void updateDoi() {

        Datacite datacite = new Datacite();
        datacite.setCreator("test creator updated");
        datacite.setTitle("test title updated");
        datacite.setPublisher("test publisher updated");
        datacite.setPublicationyear("2014");
        datacite.setResourcetype("Collection");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);

        HttpEntity<String> requestEntity = new HttpEntity<String>(datacite.toString(), headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange("https://ezid.cdlib.org/id/doi:10.5072/FK2QN6KJQ", HttpMethod.POST, requestEntity, String.class);

        System.out.println("update: " + responseEntity.getBody());
    }

    public void getDoi() {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);

        HttpEntity<String> requestEntity = new HttpEntity<String>(headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange("https://ezid.cdlib.org/id/doi:10.5072/FK2QN6KJQ", HttpMethod.GET, requestEntity, String.class);

        System.out.println(responseEntity.getBody());
    }
}
