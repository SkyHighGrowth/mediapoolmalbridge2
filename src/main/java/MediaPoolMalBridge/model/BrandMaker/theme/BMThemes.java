package MediaPoolMalBridge.model.BrandMaker.theme;

import com.brandmaker.webservices.theme.Theme;
import com.brandmaker.webservices.theme.ThemesResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class BMThemes extends ConcurrentHashMap<String, String> {

    public synchronized void update(final ThemesResult response) {
        clear();
        for (final Theme theme : response.getThemes().getTheme()) {
            for (final Theme theme1 : theme.getThemes().getTheme()) {
                theme1.getNames().getName().forEach(name -> put(name.getValue(), String.valueOf(theme1.getId())));
            }
        }
    }
}
