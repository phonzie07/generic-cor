package com.generic.core.base.controller;

import com.generic.core.base.response.EndpointResult;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import static java.lang.String.valueOf;
import static org.apache.commons.lang3.math.NumberUtils.isCreatable;
import static org.springframework.http.HttpHeaders.CONTENT_LENGTH;
import static org.springframework.http.HttpStatus.OK;
import static com.generic.utils.MapperUtil.objectToJson;

/**
 * The type Base controller.
 */
public class BaseController {

    /**
     * Validate message response entity.
     *
     * @param response the response
     * @param data     the data
     * @return the response entity
     */
    protected ResponseEntity<EndpointResult> validateMessage(EndpointResult response, Object data ) {
        return validateMessage( response, data, null, false );
    }

    /**
     * Validate message response entity.
     *
     * @param response the response
     * @param data     the data
     * @param isError  the is error
     * @return the response entity
     */
    protected ResponseEntity< EndpointResult > validateMessage(
            EndpointResult response, Object data, String formatter, Boolean isError ) {
        if ( isError ) {
            response.setResponseCodeMessage( isCreatable( valueOf( data ) ) ? valueOf( data ) : "22", formatter );
        } else response.addData( data );
        // make the header transfer-encoding not chunked
        HttpHeaders headers = new HttpHeaders( );
        headers.set( CONTENT_LENGTH, valueOf( objectToJson( response ).length( ) ) );

        return new ResponseEntity( response, headers, OK );
    }

    protected ResponseEntity< EndpointResult > validateErrorMessage( EndpointResult response, Object data,
                                                                     String formatter ) {
        return validateMessage( response, data, formatter, true );
    }

    protected ResponseEntity< EndpointResult > validateErrorMessage( EndpointResult response, Object data ) {
        return validateMessage( response, data, null, true );
    }

}
