package sgf.gateway.utils.httpclient.impl;


import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpConnectionManager;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import sgf.gateway.utils.httpclient.HttpClient3Factory;

/**
 * This uses the HttpClient 3.1 code base.
 */
public class HttpClient3FactoryImpl implements HttpClient3Factory {

    private int connectTimeout;
    private int readTimeout;

    public HttpClient3FactoryImpl() {

        this.connectTimeout = 0;
        this.readTimeout = 0;
    }

    public HttpClient3FactoryImpl(int connectTimeout, int readTimeout) {

        this.connectTimeout = connectTimeout;
        this.readTimeout = readTimeout;
    }

    @Override
    public HttpClient create() {

        // TODO - Would be good to inject these.
        HttpConnectionManagerParams params = new HttpConnectionManagerParams();
        HttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();

        params.setDefaultMaxConnectionsPerHost(20);
        params.setMaxTotalConnections(30);

        connectionManager.setParams(params);

        HttpClient httpClient = new HttpClient(connectionManager);

        httpClient.getHttpConnectionManager().getParams().setSoTimeout(this.readTimeout);
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(connectTimeout);

        return httpClient;
    }

}
