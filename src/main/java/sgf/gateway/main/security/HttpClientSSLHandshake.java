package sgf.gateway.main.security;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * An HttpClient example that is used to show all the possible Certificate Authorities (CAs) that a server will
 * accept when Client Authentication is set to optional/want or required/true.
 * <p/>
 * To set a keystore please use the following two Virtual Machine (vm) parameters:<br/>
 * -Djavax.net.ssl.keyStore="<keystore.file.location>" -Djavax.net.ssl.keyStorePassword="<keystore.password>"
 * <p/>
 * Examples:<br/>
 * -Djavax.net.ssl.keyStore="C:\java\certs\gateway-client.jks" -Djavax.net.ssl.keyStorePassword="changeit"
 * <p/>
 * <p/>
 * You will also need to turn on Java's SSL debugging with the following Virtual Machine (vm) parameter:<br/>
 * -Djavax.net.debug=ssl
 * <p/>
 * <p/>
 * See also:<br/>
 * There are two nice links explaining how to generate a Java Keystore from a MyProxy certificate:<br/>
 * <a href="https://wiki.ucar.edu/display/VETS/Generating+a+PKCS12+Format+Certificate+and+Java+Keystore+from+a+MyProxy+Key-Certificate+Pair">Generating a PKCS12 Format Certificate and Java Keystore from a MyProxy Key-Certificate Pair</a><br/>
 * <a href="https://wiki.ucar.edu/display/VETS/Generating+MyProxy+Certificate+by+Command+Line+%28linux%29">Generating MyProxy Certificate by Command Line (linux)</a><br/>
 *
 * @author nhook
 */

public class HttpClientSSLHandshake {

    public static void main(String[] args) throws Exception {

        HttpParams httpParams = new BasicHttpParams();
        httpParams.setIntParameter(CoreConnectionPNames.SO_TIMEOUT, 10000);

        DefaultHttpClient httpClient = new DefaultHttpClient(httpParams);

        //HttpGet httpget = new HttpGet("http://esg.prototype.ucar.edu/home.html");
        HttpGet httpget = new HttpGet("https://esg.prototype.ucar.edu/saml/soap/secure/attributeService.htm");
        //httpget.addHeader(new BasicHeader("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.9.2.16) Gecko/20110319 Firefox/3.6.16 ( .NET CLR 3.5.30729)"));
        httpget.addHeader(new BasicHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8"));

        System.out.println("executing request" + httpget.getRequestLine());

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
