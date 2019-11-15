package MediaPoolMalBridge.clients.BrandMaker.themefulltree.client;

import MediaPoolMalBridge.clients.BrandMaker.BrandMakerSoapClient;
import MediaPoolMalBridge.clients.BrandMaker.themefulltree.client.model.DownloadFullThemeTreeResponse;
import MediaPoolMalBridge.clients.BrandMaker.themeid.client.BMDownloadThemeIdClient;
import MediaPoolMalBridge.clients.BrandMaker.model.BMTheme;
import com.brandmaker.webservices.theme.ThemesResult;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class BMDownloadFullThemeTreeClient extends BrandMakerSoapClient {

    private final static Logger logger = LoggerFactory.getLogger(BMDownloadThemeIdClient.class);

    public BMDownloadFullThemeTreeClient() {
    }

    public DownloadFullThemeTreeResponse downloadFullThemeTree(final BMTheme theme) {
        try {
            final ThemesResult response = getThemeWebServicePort().receiveFullThemeTree(theme.getThemeId());
            logger.info( "BMDownloadFullThemeTree {}", (new Gson()).toJson(response) );
            return new DownloadFullThemeTreeResponse(response);
        } catch (final Exception e) {
            logger.error("Error downloading full theme tree for theme {}", theme, e);
            return new DownloadFullThemeTreeResponse(false, e.getMessage());
        }
    }
}