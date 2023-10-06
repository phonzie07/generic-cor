package com.generic.logger.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.generic.utils.DateUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * The type Log request.
 */
@Data
@AllArgsConstructor
public class LogRequest {

    private String requestMessage;
    private String responseMessage;
    private String uri;
    private String method;
    private Integer connectionStatus;
    private String serviceUri;
    private Long duration;
    private LocalDateTime createdTime;

    /**
     * Instantiates a new Log request.1
     *
     * @param requestMessage   the request message
     * @param responseMessage  the response message
     * @param uri              the uri
     * @param method           the method
     * @param connectionStatus the connection status
     */
    public LogRequest( String requestMessage, String responseMessage, String uri,
                       String method, String connectionStatus, Long duration ) {
        this.requestMessage = requestMessage;
        this.responseMessage = responseMessage;
        this.uri = uri;
        this.method = method;
        this.connectionStatus = Integer.parseInt( connectionStatus );
        this.duration = duration;
        if ( null == this.createdTime ) this.createdTime = LocalDateTime.now( );
    }

    public LogRequest( ) {
        this.createdTime = LocalDateTime.now( );
    }

    @JsonIgnore
    public LocalDateTime getCreatedTime( ) {
        return createdTime;
    }

    @JsonProperty( "createdTime" )
    public String getCreatedTimeString( ) {
        return DateUtil.formatDate( createdTime, "yyyy-MM-dd HH:mm:ss.SSS" );
    }

}
