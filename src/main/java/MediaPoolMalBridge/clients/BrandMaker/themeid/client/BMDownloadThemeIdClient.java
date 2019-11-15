package MediaPoolMalBridge.clients.BrandMaker.themeid.client;

import MediaPoolMalBridge.clients.BrandMaker.BrandMakerSoapClient;
import MediaPoolMalBridge.clients.BrandMaker.themeid.client.model.DownloadThemeIdResponse;
import MediaPoolMalBridge.clients.BrandMaker.model.BMTheme;
import com.brandmaker.webservices.theme.Theme;
import com.brandmaker.webservices.theme.ThemeName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class BMDownloadThemeIdClient extends BrandMakerSoapClient {

    private final static Logger logger = LoggerFactory.getLogger(BMDownloadThemeIdClient.class);

    public BMDownloadThemeIdClient() {
    }

    public DownloadThemeIdResponse downloadThemeId(final BMTheme theme) {
        try {
            final ThemeName request = new ThemeName();
            request.setValue(theme.getThemePath());
            final Theme response = getThemeWebServicePort().receiveThemeInformationByPath(request);
            return new DownloadThemeIdResponse(response);
        } catch (final Exception e) {
            logger.error("Error downloading theme id by theme path {}", theme, e);
            return new DownloadThemeIdResponse(false, e.getMessage());
        }
    }
}
