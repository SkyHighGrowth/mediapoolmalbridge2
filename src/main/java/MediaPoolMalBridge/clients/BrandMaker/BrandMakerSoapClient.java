package MediaPoolMalBridge.clients.BrandMaker;

import com.brandmaker.webservices.mediapool.MediaPoolWebServicePort;
import com.brandmaker.webservices.theme.ThemeWebServicePort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public abstract class BrandMakerSoapClient {

    @Autowired
    private MediaPoolWebServicePort mediaPoolWebServicePort;

    @Autowired
    private ThemeWebServicePort themeWebServicePort;

    protected MediaPoolWebServicePort getMediaPoolPort() {
        return mediaPoolWebServicePort;
    }

    protected ThemeWebServicePort getThemeWebServicePort() {
        return themeWebServicePort;
    }
}
