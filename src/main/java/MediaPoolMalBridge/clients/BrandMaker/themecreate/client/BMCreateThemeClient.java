package MediaPoolMalBridge.clients.BrandMaker.themecreate.client;

import MediaPoolMalBridge.clients.BrandMaker.BrandMakerSoapClient;
import MediaPoolMalBridge.clients.BrandMaker.model.BMTheme;
import MediaPoolMalBridge.clients.BrandMaker.themecreate.client.model.CreateThemeResponse;
import MediaPoolMalBridge.clients.BrandMaker.themecreate.tranformer.request.BMThemeTransformer;
import com.brandmaker.webservices.theme.ThemeResult;
import org.springframework.stereotype.Component;

@Component
public class BMCreateThemeClient extends BrandMakerSoapClient {

    private final BMThemeTransformer transformer;

    public BMCreateThemeClient(final BMThemeTransformer transformer) {
        this.transformer = transformer;
    }

    public CreateThemeResponse createTheme(final BMTheme theme) {
        try {
            final com.brandmaker.webservices.theme.Theme request = transformer.toBMTheme(theme);
            final ThemeResult result = getThemeWebServicePort().createTheme(request);
            final CreateThemeResponse response = new CreateThemeResponse(result);
            if (!response.isStatus()) {
                reportErrorOnResponse(String.valueOf(theme.getThemeId()), response);
            }
            return response;
        } catch (final Exception e) {
            reportErrorOnException(String.valueOf(theme.getThemeId()), e);
            return new CreateThemeResponse(false, e.getMessage());
        }
    }
}