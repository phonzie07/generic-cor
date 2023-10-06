package com.generic.logger.config;

import com.generic.logger.client.LoggerApiClient;
import com.generic.logger.exceptions.handler.LoggerRestHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

/**
 * The type Logging config.
 */
@EnableAsync
@Configuration
public class LoggerConfig {

    /**
     * The constant STATUS.
     */
    public static Boolean STATUS = true;

    @Value( "${logger.toggle}" )
    public void setStatus( Boolean status ) {
        LoggerConfig.STATUS = status;
    }

    /**
     * Init.
     */
    @PostConstruct
    public void init( ) {
        RestTemplate restTemplate = new RestTemplate( );
        restTemplate.setErrorHandler( new LoggerRestHandler( ) );
        LoggerApiClient.setRestTemplate( restTemplate );
    }

}
