package sgf.gateway.utils.httpclient;

import org.apache.commons.httpclient.HttpClient;

/**
 * This uses the HttpClient 3.1 code base.
 */
public interface HttpClient3Factory {

    HttpClient create();
}
