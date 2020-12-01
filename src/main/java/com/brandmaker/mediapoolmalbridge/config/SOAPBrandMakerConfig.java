package com.brandmaker.mediapoolmalbridge.config;

import com.brandmaker.webservices.mediapool.MediaPoolServiceV2;
import com.brandmaker.webservices.theme.ThemeService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * SOAP configuration bean
 */
@Configuration
public class SOAPBrandMakerConfig {

    @Bean
    public MediaPoolServiceV2 mediaPoolWebServiceDev() {
        return new MediaPoolServiceV2();
    }

    @Bean
    public ThemeService themeWebServicePortDev() {
        return new ThemeService();
    }
}
