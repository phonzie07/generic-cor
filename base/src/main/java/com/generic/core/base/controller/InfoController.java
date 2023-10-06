package com.generic.core.base.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class InfoController {

    @Autowired
    private Info info;
    private ObjectMapper mapper = new ObjectMapper();

    @GetMapping("info")
    public ResponseEntity getInfo() throws JsonProcessingException {
        return ResponseEntity.ok(mapper.writeValueAsString(
                info
        ));
    }
}

@ConfigurationProperties("${spring.application.name}.info")
@Component
@Getter
@Setter
class Info {
    private String version;
    private Map<String, Object> build;
}
