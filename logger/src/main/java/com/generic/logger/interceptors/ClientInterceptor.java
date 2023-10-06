package com.generic.logger.interceptors;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.scheduling.annotation.Async;
import com.generic.logger.request.LogRequest;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import static java.lang.String.valueOf;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.util.StreamUtils.copyToByteArray;
import static org.springframework.util.StringUtils.isEmpty;
import static com.generic.logger.client.LoggerApiClient.sendLog;
import static com.generic.logger.config.LoggerConfig.STATUS;
import static com.generic.utils.MapperUtil.objectToJson;
import static com.generic.utils.MapperUtil.jsonToObject;

/**
 * The type Client interceptor.
 */
@Log4j2
public class ClientInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept( HttpRequest request, byte[] body,
                                         ClientHttpRequestExecution execution ) throws IOException {
        Instant start = Instant.now( );
        ClientHttpResponse response = execution.execute( request, body );
        Long duration = Duration.between( start, Instant.now( ) ).toMillis( );
        String responseBody;
        String responseCode = "";
        try {
            HttpStatus statusCode = response.getStatusCode();
            responseCode = valueOf( statusCode.value() );
            responseBody = new String( copyToByteArray( response.getBody( ) ) );
        } catch ( IOException ex ) {
            log.debug( "Error in getting the response body" );
            log.debug( "e: {}", ex.getLocalizedMessage( ) );
            log.error( ex.getMessage( ) );
            responseBody = ex.getLocalizedMessage( );
        }

        String requestMsg;
        String decryptBody = new String( body, UTF_8 );

        if ( request.getMethodValue( ).equals( "POST" ) ) {
            Map< String, Object > decryptMap = jsonToObject( decryptBody, Map.class );
            requestMsg = objectToJson( decryptMap );
        } else {
            Map< String, Object > result = new HashMap<>( );
            String[] splitDecryptBody;

            if ( request.getURI( ).getQuery( ) != null ) {
                splitDecryptBody = request.getURI( ).getQuery( ).split( "&" );

                for ( String val : splitDecryptBody ) {
                    if ( val.contains( "=" ) )
                        result.put( val.substring( 0, val.indexOf( "=" ) ), val.substring( val.indexOf( "=" ) + 1 ) );
                }
            }
            requestMsg = objectToJson( !result.isEmpty( ) ? result : decryptBody );
        }

        LogRequest logRequest = new LogRequest( !isEmpty( requestMsg ) ? requestMsg : "{}", responseBody,
                request.getURI( ).getPath( ), request.getMethodValue( ), responseCode, duration );

        if ( STATUS ) log( logRequest );

        log.info( objectToJson( logRequest ) );

        return response;
    }

    /**
     * Log.
     *
     * @param logRequest the log request
     */
    @Async
    public void log( LogRequest logRequest ) {
        sendLog( logRequest );
    }

}