package com.brandmaker.mediapoolmalbridge.config;

import com.brandmaker.mediapoolmalbridge.config.model.HibernateProxyTypeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Gson bean configuration
 */
@Configuration
public class GsonConfig {

    @Bean( name = "GsonSerializer" )
    public Gson getGson()
    {
        return new GsonBuilder().registerTypeAdapterFactory(HibernateProxyTypeAdapter.FACTORY)
                .excludeFieldsWithoutExposeAnnotation()
                .create();
    }
}
