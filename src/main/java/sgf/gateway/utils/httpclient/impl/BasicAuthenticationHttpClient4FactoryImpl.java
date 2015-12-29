package sgf.gateway.utils.httpclient.impl;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import sgf.gateway.utils.httpclient.HttpClient4Factory;

public class BasicAuthenticationHttpClient4FactoryImpl implements HttpClient4Factory {

    private int connectTimeout;
    private int readTimeout;
    private String username;
    private String password;


    public BasicAuthenticationHttpClient4FactoryImpl(int connectTimeout, int readTimeout, String username, String password) {

        this.connectTimeout = connectTimeout;
        this.readTimeout = readTimeout;
        this.username = username;
        this.password = password;
    }

    @Override
    public HttpClient create() {

        HttpClientConnectionManager httpClientConnectionManager = new PoolingHttpClientConnectionManager();

        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(new AuthScope(null, -1), new UsernamePasswordCredentials(this.username, this.password));

        RequestConfig.Builder requestConfigBuilder = RequestConfig.custom();
        requestConfigBuilder.setConnectTimeout(this.connectTimeout);
        requestConfigBuilder.setConnectionRequestTimeout(this.readTimeout);

        RequestConfig requestConfig = requestConfigBuilder.build();

        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        httpClientBuilder.setConnectionManager(httpClientConnectionManager);
        httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
        httpClientBuilder.setDefaultRequestConfig(requestConfig);

        HttpClient httpClient = httpClientBuilder.build();

        return httpClient;
    }
}
