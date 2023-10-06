package com.generic.core.base.config;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.boot.actuate.health.HealthStatusHttpMapper;
import org.springframework.boot.actuate.info.InfoEndpoint;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/***
 * Auto-configures the health and info endpoint back to the application port for the monitoring
 * systems
 */
@Configuration
@RestController
public class MonitoringConfig {

    /**
     * The Health endpoint.
     */
    final HealthEndpoint healthEndpoint;
    /**
     * The Status mapper.
     */
    final HealthStatusHttpMapper statusMapper;
    /**
     * The Info endpoint.
     */
    final InfoEndpoint infoEndpoint;

    /**
     * Instantiates a new Monitoring config.
     *
     * @param healthEndpoint the health endpoint
     * @param statusMapper   the status mapper
     * @param infoEndpoint   the info endpoint
     */
    MonitoringConfig( HealthEndpoint healthEndpoint, HealthStatusHttpMapper statusMapper, InfoEndpoint infoEndpoint ) {
        this.healthEndpoint = healthEndpoint;
        this.statusMapper = statusMapper;
        this.infoEndpoint = infoEndpoint;
    }

    /**
     * Gets health.
     *
     * @return the health
     */
    @GetMapping( "/health" )
    public ResponseEntity getHealth( ) {
        Health health = healthEndpoint.health( );
        return ResponseEntity.status( statusMapper.mapStatus( health.getStatus( ) ) ).body( health );
    }

    /**
     * Gets info.
     *
     * @return the info
     */
    @GetMapping( "/info" )
    public ResponseEntity getInfo( ) {
        return ResponseEntity.ok( infoEndpoint.info( ) );
    }

}