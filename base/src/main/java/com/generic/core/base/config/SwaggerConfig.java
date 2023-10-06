package com.generic.core.base.config;

import org.apache.commons.lang3.text.WordUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.PostConstruct;

/**
 * The type Swagger config.
 */
@Configuration
@EnableSwagger2
@ConfigurationProperties(prefix = "spring.application")
public class SwaggerConfig {

    private static String appName = "";

    @Value("${spring.application.name}")
    private String name = "";
    @Value("${server.api.version}")
    private String version = "";
    /**
     * Init.
     */
    @PostConstruct
    public void init() {
        appName = WordUtils.capitalize(name + " Management ");
    }

    /**
     * Api docket.
     *
     * @return the docket
     */
    @Bean
    public Docket api( ) {
        return new Docket( DocumentationType.SWAGGER_2 )
                .select( )
                .apis( RequestHandlerSelectors.basePackage( "com" ) )
                .build( );
    }

    //Setting up api information
    private ApiInfo apiInfo( ) {
        return new ApiInfoBuilder()
                .title( appName )
                .version( version )
                // Author information
                .description( "Microservice for " + appName )
//                .contact(new Contact("", "", ""))
                .build();
    }

}