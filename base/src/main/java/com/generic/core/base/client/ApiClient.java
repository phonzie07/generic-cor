package com.generic.core.base.client;

import com.generic.core.base.response.EndpointResult;
import org.apache.commons.collections4.MapUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * The type Api client.
 */
public class ApiClient {
    private static RestTemplate restTemplate;

    /**
     * Sets rest template.
     *
     * @param restTemplate the rest template
     */
    public static void setRestTemplate( RestTemplate restTemplate ) {
        ApiClient.restTemplate = restTemplate;
    }

    /**
     * Post for object endpoint result.
     *
     * @param url    the url
     * @param params the params
     * @return the endpoint result
     */
    public static EndpointResult postForObject( String url, Map< String, Object > params ) {

        HttpHeaders headers = new HttpHeaders( );
        headers.setContentType( MediaType.APPLICATION_JSON_UTF8 );

        Map< String, Object > map = new HashMap<>( );

        if ( MapUtils.isNotEmpty( params ) ) map.putAll( params );

        return restTemplate.postForObject( url, new HttpEntity<>( params, headers ), EndpointResult.class );
    }

    /**
     * Post for object endpoint result.
     *
     * @param url     the url
     * @param request the request
     * @return the endpoint result
     */
    public static EndpointResult postForObject( String url, Object request ) {
        return postForObject( url, request, EndpointResult.class );
    }

    /**
     * Post for object t.
     *
     * @param <T>     the type parameter
     * @param url     the url
     * @param request the request
     * @param clazz   the clazz
     * @return the t
     */
    public static < T > T postForObject( String url, Object request, Class< T > clazz ) {
        return restTemplate.postForObject( url, request, clazz );
    }

    /**
     * Gets for object.
     *
     * @param url    the url
     * @param params the params
     * @return the for object
     */
    public static EndpointResult getForObject( String url, Map< String, Object > params ) {
        return getForObject( url, EndpointResult.class, params );
    }

    /**
     * Gets for object.
     *
     * @param <T>    the type parameter
     * @param url    the url
     * @param clazz  the clazz
     * @param params the params
     * @return the for object
     */
    public static < T > T getForObject( String url, Class< T > clazz, Map< String, Object > params ) {
        return MapUtils.isEmpty( params ) ? restTemplate.getForObject( url, clazz ) :
                restTemplate.getForObject( url, clazz, params );
    }
}
