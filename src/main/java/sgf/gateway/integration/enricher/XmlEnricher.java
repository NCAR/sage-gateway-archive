package sgf.gateway.integration.enricher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Scanner;

public class XmlEnricher {

    private final static Logger LOG = LoggerFactory.getLogger(XmlEnricher.class);

    private final RestTemplate restTemplate;

    public XmlEnricher(RestTemplate restTemplate) {
        super();
        this.restTemplate = restTemplate;
    }

    public String handle(URI xmlURI) {

        if (LOG.isDebugEnabled()) {
            LOG.debug("enrichment request received, requesting xml for URI: " + xmlURI);
        }

        String xml = requestXml(xmlURI);

        return xml;
    }

    private String requestXml(URI xmlURI) {

        XmlExtractor extractor = new XmlExtractor();
        String xml = restTemplate.execute(xmlURI, HttpMethod.GET, null, extractor);

        return xml;
    }

    private class XmlExtractor implements ResponseExtractor<String> {

        @Override
        public String extractData(ClientHttpResponse response) throws IOException {

            int rawStatusCode = response.getRawStatusCode();
            HttpStatus statusCode = response.getStatusCode();
            String statusText = response.getStatusText();

            if (LOG.isDebugEnabled()) {
                LOG.debug("response status: " + rawStatusCode + " " + statusCode + " " + statusText);
            }

            InputStream body = response.getBody();
            String xml = transform(body);

            return xml;
        }

        public String transform(InputStream inputStream) {

            String str;

            // stackoverflow post: "Scanner iterates over tokens in the stream, and in this case we separate tokens using
            // 'beginning of the input boundary' (\A) thus giving us only one token for the entire contents of the stream"
            Scanner scanner = new Scanner(inputStream, "UTF-8");
            scanner.useDelimiter("\\A");
            str = scanner.next();

            return str;
        }
    }
}
