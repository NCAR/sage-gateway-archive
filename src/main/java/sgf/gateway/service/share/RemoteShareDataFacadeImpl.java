package sgf.gateway.service.share;


import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.service.share.jackson.ShareRequest;
import sgf.gateway.service.share.jackson.ShareResponse;

public class RemoteShareDataFacadeImpl implements RemoteShareDataFacade{

    private RestTemplate restTemplate;
    private String shareAuthToken;
    private String shareUrl;
    private ShareRequestFactory shareRequestFactory;

    public RemoteShareDataFacadeImpl(RestTemplate restTemplate, String shareAuthToken, String shareUrl, ShareRequestFactory shareRequestFactory) {

        this.restTemplate = restTemplate;
        this.shareAuthToken = "Token " + shareAuthToken;
        this.shareUrl = shareUrl;
        this.shareRequestFactory = shareRequestFactory;
    }

    @Override
    public void pushToShare(Dataset dataset) {

        ShareRequest shareRequest = this.createShareRequest(dataset);

        // Set up Authorization in Header
        HttpHeaders headers = this.createHeaders();

        HttpEntity<ShareRequest> request = new HttpEntity<>(shareRequest, headers);

        restTemplate.postForObject(this.shareUrl, request, ShareResponse.class);
    }

    public ShareRequest createShareRequest (Dataset dataset) {

        ShareRequest shareRequest = shareRequestFactory.createShareRequest(dataset);
        return shareRequest;
    }

    protected HttpHeaders createHeaders() {

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);

        headers.add("Authorization", shareAuthToken);

        return headers;
    }
}
