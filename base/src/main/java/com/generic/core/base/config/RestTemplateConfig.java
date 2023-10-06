package com.generic.core.base.config;

import com.generic.core.base.exceptions.handler.RestErrorExceptionHandler;
import com.generic.logger.interceptors.ClientInterceptor;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import javax.net.ssl.SSLContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.util.Collections;

/**
 * The type Rest template config.
 */
@Configuration
public class RestTemplateConfig {

    /**
     * Rest template rest template.
     *
     * @return the rest template
     */
    @Bean
    public RestTemplate restTemplate( ) {
        ClientHttpRequestFactory factory = new BufferingClientHttpRequestFactory(
                new SimpleClientHttpRequestFactory( ) );

        RestTemplate restTemplate = new RestTemplate( factory );
        restTemplate.setErrorHandler( new RestErrorExceptionHandler( ) );
        restTemplate.setInterceptors( Collections.singletonList( new ClientInterceptor( ) ) );
        return restTemplate;
    }
//
//    public static RestTemplate restTemplate()
//        throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
//        TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;
//
//        SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom()
//            .loadTrustMaterial(null, acceptingTrustStrategy)
//            .build();
//
//        SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext);
//
//        CloseableHttpClient httpClient = HttpClients.custom()
//            .setSSLSocketFactory(csf)
//            .build();
//
//        HttpComponentsClientHttpRequestFactory requestFactory =
//            new HttpComponentsClientHttpRequestFactory();
//
//        requestFactory.setHttpClient(httpClient);
//        RestTemplate restTemplate = new RestTemplate(requestFactory);
//        return restTemplate;
//    }
}
