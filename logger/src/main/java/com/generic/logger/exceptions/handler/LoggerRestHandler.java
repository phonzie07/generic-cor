package com.generic.logger.exceptions.handler;

import org.springframework.web.client.DefaultResponseErrorHandler;

/**
 * The type Logging rest handler.
 */
public class LoggerRestHandler extends DefaultResponseErrorHandler {

    @Override
    protected boolean hasError( int unknownStatusCode ) {
        return false;
    }

}
