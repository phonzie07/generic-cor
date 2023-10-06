package com.generic.core.base.exceptions.handler;

import com.generic.core.base.exceptions.RestExceptionError;
import com.generic.core.base.exceptions.model.ApiError;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;

/**
 * The type Rest error exception.
 */
@Log4j2
@Component
public class RestErrorExceptionHandler extends ResponseEntityExceptionHandler implements ResponseErrorHandler {

    @Override
    protected ResponseEntity< Object > handleHttpMessageNotReadable( HttpMessageNotReadableException ex,
                                                                     HttpHeaders headers, HttpStatus status,
                                                                     WebRequest request ) {
        String error = "Malformed JSON request";
        return buildResponseEntity( new ApiError( HttpStatus.BAD_REQUEST, error, ex ) );
    }

    private ResponseEntity< Object > buildResponseEntity( ApiError apiError ) {
        return new ResponseEntity<>( apiError, apiError.getStatus( ) );
    }

    @Override
    public boolean hasError( ClientHttpResponse response ) throws IOException {
        return false;
    }

    @Override
    public void handleError( ClientHttpResponse response ) throws IOException {
        if ( response.getStatusCode( ).series( ) == HttpStatus.Series.CLIENT_ERROR )
            throw new RestExceptionError( "An error occurred on Exchange" );
        else if ( response.getStatusCode( ).series( ) == HttpStatus.Series.CLIENT_ERROR ) {
            // handle CLIENT_ERROR
            if ( response.getStatusCode( ) == HttpStatus.NOT_FOUND )
                throw new RestExceptionError( "Site cannot be found" );
        }
    }
}
