package com.generic.core.base.config;

import com.generic.core.base.constants.ErrorCode;
import com.generic.logger.config.LoggerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.client.RestTemplate;
import com.generic.core.base.client.ApiClient;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * The type Base initializer config.
 */
@Configuration
@EnableConfigurationProperties
//@EnableEurekaClient
//@EnableFeignClients(basePackages = {"com.generic.core.base.client"})
@Import( { ErrorCode.class, RestTemplateConfig.class, LoggerConfig.class,
        SwaggerConfig.class, MonitoringConfig.class } )
public class BaseInitializerConfig {
    @Resource
    private RestTemplate restTemplateConf;

    /**
     * Init.
     */
    @PostConstruct
    public void init( ) {
        ApiClient.setRestTemplate( restTemplateConf );
    }

}
