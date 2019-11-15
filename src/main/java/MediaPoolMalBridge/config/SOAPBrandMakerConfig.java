package MediaPoolMalBridge.config;

import com.brandmaker.webservices.mediapool.MediaPoolService;
import com.brandmaker.webservices.mediapool.MediaPoolWebServicePort;
import com.brandmaker.webservices.theme.ThemeService;
import com.brandmaker.webservices.theme.ThemeWebServicePort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.xml.ws.BindingProvider;
import java.util.Map;

@Configuration
public class SOAPBrandMakerConfig {

    private static final Logger logger = LoggerFactory.getLogger(SOAPBrandMakerConfig.class);

    @Bean
    @Profile("dev")
    public MediaPoolWebServicePort mediaPoolWebServicePortDev() {
        logger.error("DEV PROFILE");

        final MediaPoolWebServicePort port = (new MediaPoolService()).getMediaPoolPort();
        Map<String, Object> reqContext = ((BindingProvider) port).getRequestContext();
        reqContext.put(BindingProvider.USERNAME_PROPERTY, "tsupport.de");
        reqContext.put(BindingProvider.PASSWORD_PROPERTY, "de!SuPPort$4");
        reqContext.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, "https://qamarriott.brandmakerinc.com/webservices/MediaPool/");
        return port;
    }

    @Bean
    @Profile("production")
    public MediaPoolWebServicePort mediaPoolWebServicePortProduction() {
        logger.error("PRODUCTION PROFILE");

        final MediaPoolWebServicePort port = (new MediaPoolService()).getMediaPoolPort();
        Map<String, Object> reqContext = ((BindingProvider) port).getRequestContext();
        reqContext.put(BindingProvider.USERNAME_PROPERTY, "${mediapool.username.prod}");
        reqContext.put(BindingProvider.PASSWORD_PROPERTY, "${mediapool.password.prod}");
        reqContext.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, "https://qamarriott.brandmakerinc.com/webservices/MediaPool/");
        return port;
    }

    @Bean
    @Profile("dev")
    public ThemeWebServicePort themeWebServicePortDev() {
        logger.error("DEV PROFILE");

        final ThemeWebServicePort port = (new ThemeService()).getThemePort();
        Map<String, Object> reqContext = ((BindingProvider) port).getRequestContext();
        reqContext.put(BindingProvider.USERNAME_PROPERTY, "tsupport.de");
        reqContext.put(BindingProvider.PASSWORD_PROPERTY, "de!SuPPort$4");
        reqContext.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, "https://qamarriott.brandmakerinc.com/webservices/Theme/");
        return port;
    }

    @Bean
    @Profile("production")
    public ThemeWebServicePort themeWebServicePortProduction() {
        logger.error("PRODUCTION PROFILE");

        final ThemeWebServicePort port = (new ThemeService()).getThemePort();
        Map<String, Object> reqContext = ((BindingProvider) port).getRequestContext();
        reqContext.put(BindingProvider.USERNAME_PROPERTY, "${mediapool.username.prod}");
        reqContext.put(BindingProvider.PASSWORD_PROPERTY, "${mediapool.password.prod}");
        reqContext.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, "https://qamarriott.brandmakerinc.com/webservices/Theme/");
        return port;
    }
}
