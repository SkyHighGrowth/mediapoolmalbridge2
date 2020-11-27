package com.brandmaker.mediapoolmalbridge.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ObjectMapperConfig {

    @Bean( "JacksonSerializer" )
    public ObjectMapper getObjectMapper() {
        return new ObjectMapper();
    }
}
