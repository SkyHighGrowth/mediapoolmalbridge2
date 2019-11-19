package MediaPoolMalBridge.config;

import com.brandmaker.webservices.mediapool.MediaPoolService;
import com.brandmaker.webservices.mediapool.MediaPoolWebServicePort;
import com.brandmaker.webservices.theme.ThemeService;
import com.brandmaker.webservices.theme.ThemeWebServicePort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.xml.ws.BindingProvider;
import java.util.Map;

@Configuration
public class SOAPBrandMakerConfig {

    @Bean
    @Profile("dev")
    public MediaPoolWebServicePort mediaPoolWebServicePortDev(@Value("${mediapool.username.test}") final String username,
                                                              @Value("${mediapool.password.test}") final String password,
                                                              @Value("${mediapool.url.test}") final String url) {
        final MediaPoolWebServicePort port = (new MediaPoolService()).getMediaPoolPort();
        Map<String, Object> reqContext = ((BindingProvider) port).getRequestContext();
        reqContext.put(BindingProvider.USERNAME_PROPERTY, username);
        reqContext.put(BindingProvider.PASSWORD_PROPERTY, password);
        reqContext.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, url);
        return port;
    }

    @Bean
    @Profile("production")
    public MediaPoolWebServicePort mediaPoolWebServicePortProduction(@Value("${mediapool.username.live}") final String username,
                                                                     @Value("${mediapool.password.live}") final String password,
                                                                     @Value("${mediapool.url.live}") final String url) {
        final MediaPoolWebServicePort port = (new MediaPoolService()).getMediaPoolPort();
        Map<String, Object> reqContext = ((BindingProvider) port).getRequestContext();
        reqContext.put(BindingProvider.USERNAME_PROPERTY, username);
        reqContext.put(BindingProvider.PASSWORD_PROPERTY, password);
        reqContext.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, url);
        return port;
    }

    @Bean
    @Profile("dev")
    public ThemeWebServicePort themeWebServicePortDev(@Value("${mediapool.username.test}") final String username,
                                                      @Value("${mediapool.password.test}") final String password,
                                                      @Value("${mediapool.url.test}") final String url) {
        final ThemeWebServicePort port = (new ThemeService()).getThemePort();
        Map<String, Object> reqContext = ((BindingProvider) port).getRequestContext();
        reqContext.put(BindingProvider.USERNAME_PROPERTY, username);
        reqContext.put(BindingProvider.PASSWORD_PROPERTY, password);
        reqContext.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, url);
        return port;
    }

    @Bean
    @Profile("production")
    public ThemeWebServicePort themeWebServicePortProduction(@Value("${mediapool.username.test}") final String username,
                                                             @Value("${mediapool.password.test}") final String password,
                                                             @Value("${mediapool.url.test}") final String url) {
        final ThemeWebServicePort port = (new ThemeService()).getThemePort();
        Map<String, Object> reqContext = ((BindingProvider) port).getRequestContext();
        reqContext.put(BindingProvider.USERNAME_PROPERTY, username);
        reqContext.put(BindingProvider.PASSWORD_PROPERTY, password);
        reqContext.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, url);
        return port;
    }
}
