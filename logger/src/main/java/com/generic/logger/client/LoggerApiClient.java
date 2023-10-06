package com.generic.logger.client;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import com.generic.logger.request.LogRequest;

import static org.springframework.util.StringUtils.isEmpty;

/**
 * The type Logging api client.
 */
@Log4j2
public class LoggerApiClient {

    private static RestTemplate restTemplate;
    private static String loggerUri;
    private static String serviceUri;

    /**
     * Sets rest template.
     *
     * @param restTemplate the rest template
     */
    public static void setRestTemplate( RestTemplate restTemplate ) {
        LoggerApiClient.restTemplate = restTemplate;
    }

    /**
     * Sets logger uri.
     *
     * @param loggerUri the logger uri
     */
    public static void setLoggerUri( String loggerUri ) {
        LoggerApiClient.loggerUri = loggerUri;
    }

    /**
     * Sets service uri.
     *
     * @param serviceUri the service uri
     */
    public static void setServiceUri( String serviceUri ) {
        LoggerApiClient.serviceUri = serviceUri;
    }

    /**
     * Send log.
     *
     * @param logRequest the log request
     */
    public static void sendLog( LogRequest logRequest ) {
        HttpHeaders headers = new HttpHeaders( );
        headers.setContentType( MediaType.APPLICATION_JSON );

        if ( isEmpty( logRequest.getServiceUri( ) ) ) logRequest.setServiceUri( serviceUri );

        HttpEntity< MultiValueMap< String, String > > request = new HttpEntity( logRequest, headers );

        try {
            restTemplate.postForEntity( loggerUri, request, ResponseEntity.class );
        } catch ( HttpServerErrorException e ) {
            log.debug( "Error in sending Logs" );
            log.debug( "e: {}", e );
            log.error( e.getLocalizedMessage( ) );
        } catch ( IllegalArgumentException e ) {
            log.debug( "Error in sending Logs" );
            log.debug( "e: {}", e );
            log.error( e.getLocalizedMessage( ) );
        }
    }

}
