package sgf.gateway.service.doi.impl;

import org.safehaus.uuid.UUID;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import sgf.gateway.model.Gateway;
import sgf.gateway.service.doi.DataciteDoiRequest;
import sgf.gateway.service.doi.DoiMetadata;
import sgf.gateway.service.doi.RemoteDoiFacade;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.net.URI;
import java.util.Properties;

public class RemoteDoiFacadeImpl implements RemoteDoiFacade {

    private final static String EZID_URL = "https://ezid.cdlib.org/id/";

    private final RestTemplate restTemplate;
    private final Gateway gateway;
    private final String ezidShoulderUrl;
    private final String publisher;

    public RemoteDoiFacadeImpl(RestTemplate restTemplate, Gateway gateway, String ezidShoulderUrl, String publisher) {

        this.restTemplate = restTemplate;
        this.gateway = gateway;
        this.ezidShoulderUrl = ezidShoulderUrl;
        this.publisher = publisher;
    }

    public String mintDoi(DataciteDoiRequest dataciteDoiRequest) {

        HttpEntity<String> httpEntity = this.createHttpEntity(dataciteDoiRequest);

        ResponseEntity<String> responseEntity = this.restTemplate.exchange(this.ezidShoulderUrl, HttpMethod.POST, httpEntity, String.class);

        String doi = this.getDoiIdentifierFromResponse(responseEntity);

        return doi;
    }

    private HttpEntity<String> createHttpEntity(DataciteDoiRequest dataciteDoiRequest) {

        HttpHeaders headers = this.createTextPlainHeaders();

        String requestBody = this.createRequestBody(dataciteDoiRequest);

        HttpEntity<String> httpEntity = new HttpEntity<String>(requestBody, headers);

        return httpEntity;
    }

    private HttpHeaders createTextPlainHeaders() {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);

        return headers;
    }

    private String createRequestBody(DataciteDoiRequest dataciteDoiRequest) {

        StringBuilder body = new StringBuilder();
        body.append("datacite.creator: " + dataciteDoiRequest.getCreator() + "\n");
        body.append("datacite.title: " + dataciteDoiRequest.getTitle() + "\n");
        body.append("datacite.publisher: " + this.publisher + "\n");
        body.append("datacite.publicationyear: " + dataciteDoiRequest.getPublicationYear() + "\n");
        body.append("datacite.resourcetype: " + dataciteDoiRequest.getResourceType() + "\n");

        String datasetUri = this.createDatasetUrl(dataciteDoiRequest.getDatasetIdentifier());
        body.append("_target: " + datasetUri + "\n");

        return body.toString();
    }

    public String createDatasetUrl(UUID datasetIdentifier) {

        URI uri = URI.create(this.gateway.getBaseSecureURL() + "dataset/id/" + datasetIdentifier + ".html");

        uri = uri.normalize();

        return uri.toString();
    }

    private String getDoiIdentifierFromResponse(ResponseEntity<String> responseEntity) {

        String body = responseEntity.getBody();

        String doi = body.replaceAll("success: ", "");

        doi = doi.replaceAll("\\s\\|\\s.*", "");

        return doi;
    }


    public void updateDoi(DataciteDoiRequest dataciteDoiRequest) {

        HttpEntity<String> httpEntity = this.createHttpEntity(dataciteDoiRequest);

        this.restTemplate.exchange(EZID_URL + dataciteDoiRequest.getDoi(), HttpMethod.POST, httpEntity, String.class);
    }

    public DoiMetadata getDoiMetadata(String doi) {

        HttpHeaders headers = this.createTextPlainHeaders();

        HttpEntity<String> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<String> responseEntity =
                this.restTemplate.exchange(EZID_URL + doi, HttpMethod.GET, httpEntity, String.class);

        DoiMetadata dataciteDoi = this.getDoiMetadataFromResponse(responseEntity);

        return dataciteDoi;
    }

    private DoiMetadata getDoiMetadataFromResponse(ResponseEntity<String> responseEntity) {

        Properties properties = this.createPropertiesFromResponseBody(responseEntity.getBody());

        DoiMetadata dataciteDoi = new DoiMetadata();

        dataciteDoi.setCreator(properties.getProperty("datacite.creator"));
        dataciteDoi.setTitle(properties.getProperty("datacite.title"));
        dataciteDoi.setPublisher(properties.getProperty("datacite.publisher"));
        dataciteDoi.setPublicationYear(properties.getProperty("datacite.publicationyear"));
        dataciteDoi.setResourceType(properties.getProperty("datacite.resourcetype"));

        return dataciteDoi;
    }

    private Properties createPropertiesFromResponseBody(String body) {

        String propertiesString = body.replaceFirst(": ", "=");

        Reader stringReader = new StringReader(propertiesString);

        Properties properties = new Properties();

        try {

            properties.load(stringReader);

        } catch (IOException e) {

            throw new RuntimeException(e);
        }

        return properties;
    }
}
