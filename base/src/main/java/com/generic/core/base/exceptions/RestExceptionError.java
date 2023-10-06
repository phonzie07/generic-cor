package com.generic.core.base.exceptions;

import java.io.IOException;

/**
 * The type Rest exception error.
 */
public class RestExceptionError extends IOException {

    /**
     * Instantiates a new Rest exception error.
     *
     * @param message the message
     */
    public RestExceptionError( String message ) {
        super( message );
    }

    /**
     * Instantiates a new Rest exception error.
     */
    public RestExceptionError( ) {
        super( );
    }

    /**
     * Instantiates a new Rest exception error.
     *
     * @param message the message
     * @param cause   the cause
     */
    public RestExceptionError( String message, Throwable cause ) {
        super( message, cause );
    }

    /**
     * Instantiates a new Rest exception error.
     *
     * @param cause the cause
     */
    public RestExceptionError( Throwable cause ) {
        super( cause );
    }

}
