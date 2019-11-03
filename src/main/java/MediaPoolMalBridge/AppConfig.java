package MediaPoolMalBridge;

import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class AppConfig {

    public String getTempDir() {
        return System.getProperty("user.home") + File.pathSeparator + "temp";
    }
}
