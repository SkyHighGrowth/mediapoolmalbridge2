package com.brandmaker.mediapoolmalbridge.clients.brandmaker;

import com.brandmaker.mediapoolmalbridge.config.AppConfig;
import com.brandmaker.mediapoolmalbridge.config.AppConfigData;
import com.brandmaker.mediapoolmalbridge.persistence.entity.bridge.ReportsEntity;
import com.brandmaker.mediapoolmalbridge.persistence.entity.enums.ReportTo;
import com.brandmaker.mediapoolmalbridge.persistence.entity.enums.ReportType;
import com.brandmaker.mediapoolmalbridge.persistence.repository.bridge.AssetRepository;
import com.brandmaker.mediapoolmalbridge.persistence.repository.bridge.ReportsRepository;
import com.brandmaker.webservices.mediapool.MediaPoolServiceV2;
import com.brandmaker.webservices.mediapool.MediaPoolWebServicePortV2;
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

    public static final String PRODUCTION = "production";
    public static final String DEV = "dev";
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    protected static final Gson GSON = new Gson();

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
    private MediaPoolServiceV2 mediaPoolService;

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

    public MediaPoolWebServicePortV2 getMediaPoolPort() {
        final MediaPoolWebServicePortV2 port = mediaPoolService.getMediaPoolPortV2();
        AppConfigData appConfigData = appConfig.getAppConfigData();
        if( Arrays.asList( environment.getActiveProfiles() ).contains(DEV) &&
            !Arrays.asList( environment.getActiveProfiles() ).contains(PRODUCTION) ) {
            Map<String, Object> reqContext = ((BindingProvider) port).getRequestContext();
            reqContext.put(BindingProvider.USERNAME_PROPERTY, appConfigData.getMediapoolUsernameDev());
            reqContext.put(BindingProvider.PASSWORD_PROPERTY, appConfigData.getMediapoolPasswordDev());
            reqContext.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, appConfigData.getMediapoolUrlDev());
        }
        if( Arrays.asList( environment.getActiveProfiles() ).contains(PRODUCTION) &&
            !Arrays.asList( environment.getActiveProfiles() ).contains(DEV) ) {
            Map<String, Object> reqContext = ((BindingProvider) port).getRequestContext();
            reqContext.put(BindingProvider.USERNAME_PROPERTY, appConfigData.getMediapoolUsernameProduction());
            reqContext.put(BindingProvider.PASSWORD_PROPERTY, appConfigData.getMediapoolPasswordProduction());
            reqContext.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, appConfigData.getMediapoolUrlProduction());
        }
        return port;
    }

    protected ThemeWebServicePort getThemeWebServicePort() {
        final ThemeWebServicePort port = themeService.getThemePort();
        AppConfigData appConfigData = appConfig.getAppConfigData();
        if( Arrays.asList( environment.getActiveProfiles() ).contains(DEV) &&
            !Arrays.asList( environment.getActiveProfiles() ).contains(PRODUCTION) ) {
            Map<String, Object> reqContext = ((BindingProvider) port).getRequestContext();
            reqContext.put(BindingProvider.USERNAME_PROPERTY, appConfigData.getMediapoolUsernameDev());
            reqContext.put(BindingProvider.PASSWORD_PROPERTY, appConfigData.getMediapoolPasswordDev());
            reqContext.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, appConfigData.getThemeUrlDev());
        }
        if( Arrays.asList( environment.getActiveProfiles() ).contains(PRODUCTION) &&
            !Arrays.asList( environment.getActiveProfiles() ).contains(DEV) ) {
            Map<String, Object> reqContext = ((BindingProvider) port).getRequestContext();
            reqContext.put(BindingProvider.USERNAME_PROPERTY, appConfigData.getMediapoolUsernameProduction());
            reqContext.put(BindingProvider.PASSWORD_PROPERTY, appConfigData.getMediapoolPasswordProduction());
            reqContext.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, appConfigData.getThemeUrlProduction());

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
