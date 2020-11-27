package com.brandmaker.mediapoolmalbridge.config;

import com.brandmaker.webservices.mediapool.MediaPoolService;
import com.brandmaker.webservices.theme.ThemeService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * SOAP configuration bean
 */
@Configuration
public class SOAPBrandMakerConfig {

    @Bean
    public MediaPoolService mediaPoolWebServiceDev() {
        return new MediaPoolService();
    }

    @Bean
    public ThemeService themeWebServicePortDev() {
        return new ThemeService();
    }
}
