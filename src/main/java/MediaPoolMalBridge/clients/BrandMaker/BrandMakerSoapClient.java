package MediaPoolMalBridge.clients.BrandMaker;

import MediaPoolMalBridge.config.AppConfig;
import MediaPoolMalBridge.persistence.entity.Bridge.ReportsEntity;
import MediaPoolMalBridge.persistence.entity.enums.ReportTo;
import MediaPoolMalBridge.persistence.entity.enums.ReportType;
import MediaPoolMalBridge.persistence.repository.Bridge.AssetRepository;
import MediaPoolMalBridge.persistence.repository.Bridge.ReportsRepository;
import com.brandmaker.webservices.mediapool.MediaPoolService;
import com.brandmaker.webservices.mediapool.MediaPoolWebServicePort;
import com.brandmaker.webservices.theme.ThemeService;
import com.brandmaker.webservices.theme.ThemeWebServicePort;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import javax.xml.ws.BindingProvider;
import java.util.Arrays;
import java.util.Map;

/**
 * Abstract class that collects all fields needed to run Mediapool SOAP clients
 */
public abstract class BrandMakerSoapClient {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    protected final static Gson GSON = new Gson();

    /**
     * Represents application configuration
     */
    @Autowired
    private AppConfig appConfig;

    /**
     * Application environment
     */
    @Autowired
    private Environment environment;

    /**
     * Bean representing MediapoolWebServicePort factory
     */
    @Autowired
    private MediaPoolService mediaPoolService;

    /**
     * Bean representing MediapoolWebThemePort factory
     */
    @Autowired
    private ThemeService themeService;

    /**
     * Reports repository
     */
    @Autowired
    protected ReportsRepository reportsRepository;

    /**
     * Asset repository
     */
    @Autowired
    protected AssetRepository assetRepository;

    protected MediaPoolWebServicePort getMediaPoolPort() {
        final MediaPoolWebServicePort port = mediaPoolService.getMediaPoolPort();
        if( Arrays.asList( environment.getActiveProfiles() ).contains( "dev" ) &&
            !Arrays.asList( environment.getActiveProfiles() ).contains( "production" ) ) {
            Map<String, Object> reqContext = ((BindingProvider) port).getRequestContext();
            reqContext.put(BindingProvider.USERNAME_PROPERTY, appConfig.getMediapoolUsernameDev());
            reqContext.put(BindingProvider.PASSWORD_PROPERTY, appConfig.getMediapoolPasswordDev());
            reqContext.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, appConfig.getMediapoolUrlDev());
        }
        if( Arrays.asList( environment.getActiveProfiles() ).contains( "production" ) &&
            !Arrays.asList( environment.getActiveProfiles() ).contains( "dev" ) ) {
            Map<String, Object> reqContext = ((BindingProvider) port).getRequestContext();
            reqContext.put(BindingProvider.USERNAME_PROPERTY, appConfig.getMediapoolUsernameProduction());
            reqContext.put(BindingProvider.PASSWORD_PROPERTY, appConfig.getMediapoolPasswordProduction());
            reqContext.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, appConfig.getMediapoolUrlProduction());
        }
        return port;
    }

    protected ThemeWebServicePort getThemeWebServicePort() {
        final ThemeWebServicePort port = themeService.getThemePort();
        if( Arrays.asList( environment.getActiveProfiles() ).contains( "dev" ) &&
            !Arrays.asList( environment.getActiveProfiles() ).contains( "production" ) ) {
            Map<String, Object> reqContext = ((BindingProvider) port).getRequestContext();
            reqContext.put(BindingProvider.USERNAME_PROPERTY, appConfig.getMediapoolUsernameDev());
            reqContext.put(BindingProvider.PASSWORD_PROPERTY, appConfig.getMediapoolPasswordDev());
            reqContext.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, appConfig.getThemeUrlDev());
        }
        if( Arrays.asList( environment.getActiveProfiles() ).contains( "production" ) &&
            !Arrays.asList( environment.getActiveProfiles() ).contains( "dev" ) ) {
            Map<String, Object> reqContext = ((BindingProvider) port).getRequestContext();
            reqContext.put(BindingProvider.USERNAME_PROPERTY, appConfig.getMediapoolUsernameProduction());
            reqContext.put(BindingProvider.PASSWORD_PROPERTY, appConfig.getMediapoolPasswordProduction());
            reqContext.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, appConfig.getThemeUrlProduction());

        }
        return port;
    }

    protected AppConfig getAppConfig() {
        return appConfig;
    }

    protected void reportErrorOnException(final String assetId, final Exception e) {
        final String message = String.format("Can not perform operation [%s] asset with id [%s], with exception message [%s]", getClass().getName(), assetId, e.getMessage());
        final ReportsEntity reportsEntity = new ReportsEntity( ReportType.ERROR, getClass().getName(), assetId, message, ReportTo.NONE, null, null, null );
        reportsRepository.save( reportsEntity );
        logger.error(message, e);
    }
}
