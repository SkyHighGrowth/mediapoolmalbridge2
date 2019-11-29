package MediaPoolMalBridge.clients.BrandMaker;

import MediaPoolMalBridge.config.AppConfig;
import MediaPoolMalBridge.persistence.entity.Bridge.ReportsEntity;
import MediaPoolMalBridge.persistence.entity.enums.ReportTo;
import MediaPoolMalBridge.persistence.entity.enums.ReportType;
import MediaPoolMalBridge.persistence.repository.Bridge.AssetRepository;
import MediaPoolMalBridge.persistence.repository.Bridge.ReportsRepository;
import com.brandmaker.webservices.mediapool.MediaPoolWebServicePort;
import com.brandmaker.webservices.theme.ThemeWebServicePort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BrandMakerSoapClient {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private AppConfig appConfig;

    @Autowired
    private MediaPoolWebServicePort mediaPoolWebServicePort;

    @Autowired
    private ThemeWebServicePort themeWebServicePort;

    @Autowired
    protected ReportsRepository reportsRepository;

    @Autowired
    protected AssetRepository assetRepository;

    protected MediaPoolWebServicePort getMediaPoolPort() {
        return mediaPoolWebServicePort;
    }

    protected ThemeWebServicePort getThemeWebServicePort() {
        return themeWebServicePort;
    }

    protected AppConfig getAppConfig() {
        return appConfig;
    }

    protected void reportErrorOnException(final String assetId, final Exception e) {
        final String message = String.format("Can not perform operation [%s] asset with id [%s], with exception message [%s]", getClass().getName(), assetId, e.getMessage());
        final ReportsEntity reportsEntity = new ReportsEntity( ReportType.ERROR, getClass().getName(), message, ReportTo.BM, null, null, null );
        reportsRepository.save( reportsEntity );
        logger.error(message, e);
    }
}
