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

    private static final Logger logger = LoggerFactory.getLogger( SOAPBrandMakerConfig.class );

    @Bean
    @Profile("dev")
    public MediaPoolWebServicePort mediaPoolWebServicePortDev() {
        logger.error( "DEV PROFILE" );
        final String hostName = "https://starwood.brandmakerinc.com";
        final String username = "soap.api";
        final String password = "Kz9CznHdUE";

        final MediaPoolWebServicePort port = (new MediaPoolService()).getMediaPoolPort();
        Map<String, Object> reqContext = ((BindingProvider) port).getRequestContext();
        reqContext.put(BindingProvider.USERNAME_PROPERTY, username);
        reqContext.put(BindingProvider.PASSWORD_PROPERTY, password);
        reqContext.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, hostName + "/webservices/MediaPool/");
        return port;
    }

    @Bean
    @Profile("production")
    public MediaPoolWebServicePort mediaPoolWebServicePortProduction() {
        logger.error( "PRODUCTION PROFILE" );
        final String hostName = "https://59starwood.brandmakerinc.com";
        final String username = "soap.api";
        final String password = "Kz9CznHdUE";

        final MediaPoolWebServicePort port = (new MediaPoolService()).getMediaPoolPort();
        Map<String, Object> reqContext = ((BindingProvider) port).getRequestContext();
        reqContext.put(BindingProvider.USERNAME_PROPERTY, username);
        reqContext.put(BindingProvider.PASSWORD_PROPERTY, password);
        reqContext.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, hostName + "/webservices/MediaPool/");
        return port;
    }

    @Bean
    @Profile("dev")
    public ThemeWebServicePort themeWebServicePortDev() {
        logger.error( "DEV PROFILE" );
        final String hostName = "https://59starwood.brandmakerinc.com";
        final String username = "soap.api";
        final String password = "Kz9CznHdUE";

        final ThemeWebServicePort port = (new ThemeService()).getThemePort();
        Map<String, Object> reqContext = ((BindingProvider) port).getRequestContext();
        reqContext.put(BindingProvider.USERNAME_PROPERTY, username);
        reqContext.put(BindingProvider.PASSWORD_PROPERTY, password);
        reqContext.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, hostName + "/webservices/Theme/");
        return port;
    }

    @Bean
    @Profile("production")
    public ThemeWebServicePort themeWebServicePortProduction() {
        logger.error( "PRODUCTION PROFILE" );
        final String hostName = "https://starwood.brandmakerinc.com";
        final String username = "soap.api";
        final String password = "Kz9CznHdUE";

        final ThemeWebServicePort port = (new ThemeService()).getThemePort();
        Map<String, Object> reqContext = ((BindingProvider) port).getRequestContext();
        reqContext.put(BindingProvider.USERNAME_PROPERTY, username);
        reqContext.put(BindingProvider.PASSWORD_PROPERTY, password);
        reqContext.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, hostName + "/webservices/Theme/");
        return port;
    }
}
