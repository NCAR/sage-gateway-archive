package sgf.gateway.main.security;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.KeyStore;

/**
 * An example of how to define a keystore for mutual authentication using HttpClient 4.0.1.
 *
 * @author nhook
 */
public class HttpClient401CertificateRequest {

    public static void main(String[] args) throws Exception {

        HttpParams httpParams = new BasicHttpParams();

        // load the keystore containing the client certificate - keystore type is probably jks or pkcs12
        KeyStore keystore = KeyStore.getInstance("jks");
        InputStream keystoreInput = ClassLoader.getSystemResourceAsStream("gateway-client.jks");
        keystore.load(keystoreInput, "changeit".toCharArray());

        // load the trustore, leave it null to rely on cacerts distributed with the JVM - truststore type is probably jks or pkcs12
        KeyStore truststore = KeyStore.getInstance("jks");
        InputStream truststoreInput = ClassLoader.getSystemResourceAsStream("jssecacerts");
        truststore.load(truststoreInput, "changeit".toCharArray());

        SchemeRegistry schemeRegistry = new SchemeRegistry();
        schemeRegistry.register(new Scheme("https", new SSLSocketFactory(keystore, "changeit", truststore), 443));

        DefaultHttpClient httpClient = new DefaultHttpClient(new ThreadSafeClientConnManager(httpParams, schemeRegistry), httpParams);

        HttpGet httpget = new HttpGet("https://localhost:8443/clientCert.htm");

        HttpResponse response = httpClient.execute(httpget);
        HttpEntity entity = response.getEntity();

        System.out.println(response.getStatusLine());
        if (entity != null) {
            System.out.println("Response content length: " + entity.getContentLength());
        }

        InputStream contentInputStream = entity.getContent();
        InputStreamReader inputStreamReader = new InputStreamReader(contentInputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        String line;

        while ((line = bufferedReader.readLine()) != null) {
            System.out.println(line);
        }

        // When HttpClient instance is no longer needed,
        // shut down the connection manager to ensure
        // immediate deallocation of all system resources
        httpClient.getConnectionManager().shutdown();
    }
}
