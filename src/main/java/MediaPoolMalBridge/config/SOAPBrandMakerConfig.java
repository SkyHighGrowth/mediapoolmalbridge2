package MediaPoolMalBridge.config;

import com.brandmaker.webservices.mediapool.MediaPoolService;
import com.brandmaker.webservices.mediapool.MediaPoolWebServicePort;
import com.brandmaker.webservices.theme.ThemeService;
import com.brandmaker.webservices.theme.ThemeWebServicePort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.xml.ws.BindingProvider;
import java.util.Map;

@Configuration
public class SOAPBrandMakerConfig {

    private AppConfig appConfig;

    public SOAPBrandMakerConfig(final AppConfig appConfig )
    {
        this.appConfig = appConfig;
    }

    @Bean
    @Profile({"dev", "!production"})
    public MediaPoolWebServicePort mediaPoolWebServicePortDev() {
        final MediaPoolWebServicePort port = (new MediaPoolService()).getMediaPoolPort();
        Map<String, Object> reqContext = ((BindingProvider) port).getRequestContext();
        reqContext.put(BindingProvider.USERNAME_PROPERTY, appConfig.getMediapoolUsernameDev());
        reqContext.put(BindingProvider.PASSWORD_PROPERTY, appConfig.getMediapoolPasswordDev());
        reqContext.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, appConfig.getMediapoolUrlDev());
        return port;
    }

    @Bean
    @Profile({"production", "!dev"})
    public MediaPoolWebServicePort mediaPoolWebServicePortProduction() {
        final MediaPoolWebServicePort port = (new MediaPoolService()).getMediaPoolPort();
        Map<String, Object> reqContext = ((BindingProvider) port).getRequestContext();
        reqContext.put(BindingProvider.USERNAME_PROPERTY, appConfig.getMediapoolUsernameProduction());
        reqContext.put(BindingProvider.PASSWORD_PROPERTY, appConfig.getMediapoolPasswordProduction());
        reqContext.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, appConfig.getMediapoolUrlProduction());
        return port;
    }

    @Bean
    @Profile( {"dev", "!production"})
    public ThemeWebServicePort themeWebServicePortDev() {
        final ThemeWebServicePort port = (new ThemeService()).getThemePort();
        Map<String, Object> reqContext = ((BindingProvider) port).getRequestContext();
        reqContext.put(BindingProvider.USERNAME_PROPERTY, appConfig.getMediapoolUsernameDev());
        reqContext.put(BindingProvider.PASSWORD_PROPERTY, appConfig.getMediapoolPasswordDev());
        reqContext.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, appConfig.getThemeUrlDev());
        return port;
    }

    @Bean
    @Profile({"production", "!dev"})
    public ThemeWebServicePort themeWebServicePortProduction() {
        final ThemeWebServicePort port = (new ThemeService()).getThemePort();
        Map<String, Object> reqContext = ((BindingProvider) port).getRequestContext();
        reqContext.put(BindingProvider.USERNAME_PROPERTY, appConfig.getMediapoolUsernameProduction());
        reqContext.put(BindingProvider.PASSWORD_PROPERTY, appConfig.getMediapoolPasswordProduction());
        reqContext.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, appConfig.getThemeUrlProduction());
        return port;
    }
}
