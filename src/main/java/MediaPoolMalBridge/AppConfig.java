package MediaPoolMalBridge;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class AppConfig {

    private static Logger logger = LoggerFactory.getLogger(AppConfig.class);

    static {
        File file = new File(System.getProperty("user.home") + File.separator + "marriott_temp");
        if (!file.exists()) {
            if (!file.mkdir()) {
                logger.info("Dir {} created", file.getAbsolutePath());
            }
        }
        file = new File(System.getProperty("user.home") + File.separator + "marriott_temp" + File.separator + "excel");
        if (!file.exists()) {
            if (!file.mkdir()) {
                logger.info("Dir {} created", file.getAbsolutePath());
            }
        }
    }

    public String getTempDir() {
        return System.getProperty("user.home") + File.separator + "marriott_temp" + File.separator;
    }

    public String getExcelDir() {
        return System.getProperty("user.home") + File.separator + "marriott_temp" + File.separator + "excel" + File.separator;
    }

    public String getExcelDirWithoutSeparator() {
        return System.getProperty("user.home") + File.separator + "marriott_temp" + File.separator + "excel";
    }
}
