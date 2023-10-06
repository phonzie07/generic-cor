package com.generic.core.base.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.generic.core.base.constants.ErrorCode;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import static com.generic.core.base.constants.ErrorCode.getErrorMessage;
import static com.generic.utils.MapperUtil.objectToJson;
import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;

/**
 * The type Endpoint result.
 *
 * @param <T> the type parameter
 */
@Data
public class EndpointResult< T > {
    private String responseMessage;
    private String responseCode;
    private T data;
    @JsonIgnore
    private List dataList = new ArrayList();
    @JsonIgnore
    private Map< String, Object > dataMap = new HashMap< String, Object >( );

    /**
     * Instantiates a new Endpoint result.
     */
    public EndpointResult( ) { setOk( ); }

    /**
     * Instantiates a new Endpoint result.
     *
     * @param data the data
     */
    public EndpointResult( T data ) {
        setOk( ).addData( data );
    }

    private EndpointResult< T > setOk( ) {
        responseCode = "0";
        setResponseMessage( responseCode );
        return this;
    }

    /**
     * Add error.
     *
     * @param responseCode the response code
     * @return the endpoint result
     */
    @Deprecated
    public EndpointResult< T > addError( String responseCode ) {
        setResponseCodeMessage( responseCode );
        return this;
    }

    /**
     * Add error.
     *
     * @param responseCode the response code
     * @param params       the params
     * @return the endpoint result
     */
    @Deprecated
    public EndpointResult< T > addError( String responseCode, String... params ) {
        setResponseCodeMessage( responseCode );
        this.responseMessage = MessageFormat.format( this.responseMessage, ( Object[] ) params );
        return this;
    }

    /**
     * Sets response message.
     *
     * @param responseCode the response code
     * @param params       the params
     * @return the response message
     */
    public EndpointResult< T > setResponseCodeMessage( String responseCode, String... params ) {
        setResponseCodeMessage( responseCode );
        this.responseMessage = MessageFormat.format( this.responseMessage, ( Object[] ) params );
        return this;
    }

    /**
     * Is data not null boolean.
     *
     * @return the boolean
     */
    @JsonIgnore
    public boolean isDataNotNull( ) { return data != null; }

    /**
     * Gets data.
     *
     * @return the data
     */
    public T getData( ) { return defaultIfNull( data, null ); }

    /**
     * Has error boolean.
     *
     * @return the boolean
     */
    @JsonIgnore
    public boolean hasError( ) {
        return !responseCode.equals( "0" );
    }

    /**
     * Add data.
     *
     * @param key   the key
     * @param value the value
     * @return the endpoint result
     */
    @JsonIgnore
    public EndpointResult< T > addData( String key, Object value ) {
        if ( value != null ) {
            Map< String, Object > map = new HashMap< String, Object >( );
            map.put( key, value );
            data = ( T ) map;
        }
        return this;
    }
    /**
     * Add data.
     *
     * @param key   the key
     * @param value the value
     * @return the endpoint result
     */
    @JsonIgnore
    public EndpointResult< T > addDataMap( String key, Object value ) {
        if ( value != null ) {
            dataMap.put( key, value );
            this.data = ( T ) dataMap;
        }
        return this;
    }

    /**
     * Add data.
     *
     * @param data the data
     * @return the endpoint result
     */
    @JsonIgnore
    public EndpointResult< T > addDataList( T data ) {
        dataList.add(data);
        this.data = (T) dataList;
        return this;
    }
    /**
     * Add data.
     *
     * @param data the data
     * @return the endpoint result
     */
    @JsonIgnore
    public EndpointResult< T > addData( T data ) {
        this.data = data;
        return this;
    }

    /**
     * To json string string.
     *
     * @param isDataOnly the is data only
     * @return the string
     */
    @JsonIgnore
    public String toJsonString( boolean isDataOnly ) {
        return objectToJson( isDataOnly ? data : this );
    }

    /**
     * Sets response code message.
     *
     * @param responseCode the response code
     * @return the response code message
     */
    public EndpointResult< T > setResponseCodeMessage( String responseCode ) {
        this.responseCode = responseCode;
        this.responseMessage = ErrorCode.getErrorMessage( responseCode );
        return this;
    }

    public EndpointResult setResponseMessage( String responseMessage ) {
        this.responseMessage = responseMessage;
        return  this;
    }

    public EndpointResult setResponseCode( String responseCode ) {
        this.responseCode = responseCode;
        return this;
    }

    public EndpointResult setData( T data ) {
        this.data = data;
        return this;
    }

}
