package sgf.gateway.utils.httpclient.impl;

import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import sgf.gateway.utils.httpclient.HttpClient4Factory;

public class HttpClient4FactoryImpl implements HttpClient4Factory {

    private int connectTimeout;
    private int readTimeout;

    public HttpClient4FactoryImpl(int connectTimeout, int readTimeout) {

        this.connectTimeout = connectTimeout;
        this.readTimeout = readTimeout;
    }

    @Override
    public HttpClient create() {

        HttpClientConnectionManager httpClientConnectionManager = new PoolingHttpClientConnectionManager();

        RequestConfig.Builder requestConfigBuilder = RequestConfig.custom();
        requestConfigBuilder.setConnectTimeout(this.connectTimeout);
        requestConfigBuilder.setConnectionRequestTimeout(this.readTimeout);

        RequestConfig requestConfig = requestConfigBuilder.build();

        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        httpClientBuilder.setConnectionManager(httpClientConnectionManager);
        httpClientBuilder.setDefaultRequestConfig(requestConfig);

        HttpClient httpClient = httpClientBuilder.build();

        return httpClient;
    }

}
