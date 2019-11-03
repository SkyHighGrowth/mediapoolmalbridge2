package MediaPoolMalBridge.clients.BrandMaker.themecreate.tranformer.request;

import MediaPoolMalBridge.model.theme.Theme;
import org.springframework.stereotype.Component;

@Component
public class BMThemeTransformer {

    public com.brandmaker.webservices.theme.Theme toBMTheme(final Theme theme) {
        return new com.brandmaker.webservices.theme.Theme();
    }
}
