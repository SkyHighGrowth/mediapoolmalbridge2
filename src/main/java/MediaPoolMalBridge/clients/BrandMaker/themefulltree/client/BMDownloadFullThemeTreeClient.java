package MediaPoolMalBridge.clients.BrandMaker.themefulltree.client;

import MediaPoolMalBridge.clients.BrandMaker.BrandMakerSoapClient;
import MediaPoolMalBridge.clients.BrandMaker.themefulltree.client.model.DownloadFullThemeTreeResponse;
import MediaPoolMalBridge.clients.BrandMaker.themeid.client.BMDownloadThemeId;
import MediaPoolMalBridge.model.theme.Theme;
import com.brandmaker.webservices.theme.ThemesResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BMDownloadFullThemeTreeClient extends BrandMakerSoapClient {

    private final static Logger logger = LoggerFactory.getLogger(BMDownloadThemeId.class);

    public BMDownloadFullThemeTreeClient() {
    }

    public DownloadFullThemeTreeResponse downloadFullThemeTree(final Theme theme) {
        try {
            final ThemesResult response = getThemeWebServicePort().receiveFullThemeTree(theme.getThemeId());
            return new DownloadFullThemeTreeResponse(response);
        } catch (final Exception e) {
            logger.error("Error downloading full theme tree for theme {}", theme, e);
            return new DownloadFullThemeTreeResponse(false, e.getMessage());
        }
    }
}