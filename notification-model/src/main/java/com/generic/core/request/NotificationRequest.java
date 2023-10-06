package com.generic.core.request;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * The type Notification request.
 */
@Data
public class NotificationRequest {
    //  TODO: may change to int
    private String type;
    private Integer notifType;
    private Map< String, String > params = new HashMap<>( );
    private String to;

    /**
     * Add param.
     *
     * @param key   the key
     * @param value the value
     */
    public void addParam( String key, String value ) {
        params.put( key, value );
    }

    /**
     * Remove param.
     *
     * @param key the key
     */
    public void removeParam( String key ) {
        params.remove( key );
    }

}
