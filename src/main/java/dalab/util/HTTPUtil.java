package dalab.util;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Jang Jeong Hyeon on 2017-08-08.
 */
public class HTTPUtil {

    public static CloseableHttpClient createAcceptSelfSignedCertificateClient() throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException{

        // use the TrustSelfSignedStrategy to allow self signed certificates
        SSLContext sslContext = SSLContextBuilder.create().loadTrustMaterial(new TrustSelfSignedStrategy()).build();

        // we can optionally disable hostname verification
        // if you don't want to further weaken the security, you don't have to include this
        HostnameVerifier allowAllHosts = new NoopHostnameVerifier();

        // create an SSL socket factory to use the SSLContext with the trust self signed certificate strategy
        // and allow all host verifier
        SSLConnectionSocketFactory connectionSocketFactory = new SSLConnectionSocketFactory(sslContext, allowAllHosts);
        return HttpClients.custom().setSSLSocketFactory(connectionSocketFactory).build();
    }
}
