package com.generic.logger.aspect;

import com.generic.logger.client.LoggerApiClient;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.generic.logger.request.LogRequest;

import javax.servlet.http.HttpServletRequest;

import java.time.Duration;
import java.time.Instant;

import static com.generic.utils.MapperUtil.objectToJson;
import static java.lang.String.valueOf;
import static org.apache.commons.lang3.ArrayUtils.isEmpty;
import static com.generic.logger.config.LoggerConfig.STATUS;
import static org.springframework.http.HttpStatus.OK;
import static com.generic.logger.client.LoggerApiClient.sendLog;
import static com.generic.utils.MapperUtil.objectToJson;

/**
 * The type Logging aspect.
 */
@Log4j2
@Aspect
@Component
public class LoggerAspect {

    /**
     * Log around object.
     *
     * @param joinPoint the join point
     * @return the object
     * @throws Throwable the throwable
     */
    @Around( "bean(*Controller) && execution(* com..*(..)) && !bean(BaseController)" )
    public Object logAround( ProceedingJoinPoint joinPoint ) throws Throwable {
        HttpServletRequest request = ( ( ServletRequestAttributes ) RequestContextHolder
                .currentRequestAttributes( ) ).getRequest( );

        Instant start = Instant.now( );

        Object responseObj = joinPoint.proceed( );

        Long duration = Duration.between( start, Instant.now( ) ).toMillis( );

        String respMsg;
        if ( responseObj instanceof ResponseEntity ) {
            ResponseEntity entity = ( ResponseEntity ) responseObj;
            respMsg = objectToJson( entity.getBody( ) );
        } else respMsg = objectToJson( responseObj );

        String reqMsg = objectToJson( !isEmpty( joinPoint.getArgs( ) ) ?
                joinPoint.getArgs( )[ 0 ] : "{}" );
        String uri = request.getRequestURI( );
        String method = request.getMethod( );

        LogRequest logRequest = new LogRequest( reqMsg, respMsg, uri, method, valueOf( OK.value( ) ), duration );

        if ( STATUS ) log( logRequest );

        log.info( objectToJson( logRequest ) );

        return responseObj;

    }

    /**
     * Log.
     *
     * @param logRequest the log request
     */
    @Async
    public void log( LogRequest logRequest ) {
        sendLog( logRequest );
    }

}
