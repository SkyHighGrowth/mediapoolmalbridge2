package MediaPoolMalBridge.clients.BrandMaker.themecreate.client;

import MediaPoolMalBridge.clients.BrandMaker.BrandMakerSoapClient;
import MediaPoolMalBridge.clients.BrandMaker.themecreate.client.model.CreateThemeResponse;
import MediaPoolMalBridge.clients.BrandMaker.themecreate.tranformer.request.BMThemeTransformer;
import MediaPoolMalBridge.model.theme.Theme;
import com.brandmaker.webservices.theme.ThemeResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BMCreateThemeClient extends BrandMakerSoapClient {

    private final static Logger logger = LoggerFactory.getLogger(BMCreateThemeClient.class);

    private final BMThemeTransformer transformer;

    public BMCreateThemeClient(final BMThemeTransformer transformer) {
        this.transformer = transformer;
    }

    public CreateThemeResponse createTheme(final Theme theme) {
        try {
            final com.brandmaker.webservices.theme.Theme request = transformer.toBMTheme(theme);
            final ThemeResult response = getThemeWebServicePort().createTheme(request);
            return new CreateThemeResponse(response);
        } catch (final Exception e) {
            logger.error("Error creating theme {}", theme, e);
            return new CreateThemeResponse(false, e.getMessage());
        }
    }
}