package com.generic.utils;

import lombok.extern.log4j.Log4j2;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

/**
 * The type Ssl util.
 */
@Log4j2
public final class SSLUtil {

    private static final TrustManager[] UNQUESTIONING_TRUST_MANAGER = new TrustManager[] {
            new X509TrustManager( ) {
                public void checkClientTrusted( X509Certificate[] certs, String authType ) {
                }

                public void checkServerTrusted( X509Certificate[] certs, String authType ) {
                }

                public X509Certificate[] getAcceptedIssuers( ) {
                    return null;
                }
            }
    };

    private SSLUtil( ) {
        throw new UnsupportedOperationException( "Do not instantiate libraries." );
    }

    /**
     * Turn off ssl checking.
     *
     * @throws NoSuchAlgorithmException the no such algorithm exception
     * @throws KeyManagementException   the key management exception
     */
    public static void turnOffSslChecking( ) throws NoSuchAlgorithmException, KeyManagementException {
        // Install the all-trusting trust manager
        final SSLContext sc = SSLContext.getInstance( "SSL" );
        sc.init( null, UNQUESTIONING_TRUST_MANAGER, null );
        HttpsURLConnection.setDefaultSSLSocketFactory( sc.getSocketFactory( ) );
//        HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) -> true);
    }

    /**
     * Turn on ssl checking.
     *
     * @throws KeyManagementException   the key management exception
     * @throws NoSuchAlgorithmException the no such algorithm exception
     */
    public static void turnOnSslChecking( ) throws KeyManagementException, NoSuchAlgorithmException {
        // Return it to the initial state (discovered by reflection, now hardcoded)
        SSLContext.getInstance( "SSL" ).init( null, null, null );
        HttpsURLConnection.setDefaultHostnameVerifier( ( hostname, session ) -> false );
    }

}