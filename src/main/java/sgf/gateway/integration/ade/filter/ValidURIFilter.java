package sgf.gateway.integration.ade.filter;

import org.apache.commons.validator.routines.UrlValidator;
import sgf.gateway.integration.ade.opensearch.Entry;
import sgf.gateway.integration.ade.opensearch.Link;

import java.util.List;

public class ValidURIFilter {

    public Boolean filter(Entry entry) {

        Boolean isValid = false;

        List<Link> links = entry.getLinks();

        for (Link link : links) {

            String href = link.getHref();
            isValid = isValidURI(href);
            if (!isValid) {
                break; // If any are bad, reject all
            }
        }

        return isValid;
    }

    private Boolean isValidURI(String uriString) {

        // Allows http, https, ftp by default
        UrlValidator urlValidator = new UrlValidator(UrlValidator.ALLOW_2_SLASHES + UrlValidator.ALLOW_ALL_SCHEMES + UrlValidator.ALLOW_LOCAL_URLS);
        boolean isValidUri = urlValidator.isValid(uriString);

        return isValidUri;
    }
}
