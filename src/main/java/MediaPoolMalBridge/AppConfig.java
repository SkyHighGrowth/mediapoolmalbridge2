package MediaPoolMalBridge;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class AppConfig {

    private static Logger logger = LoggerFactory.getLogger(AppConfig.class);

    static {
        logger.info( "Creating app config" );
        final File file = new File(System.getProperty("user.home") + File.separator + "marriott_temp");
        if (!file.exists()) {
            if( file.mkdir() )
            {
                logger.info( "Dir {} created", file.getAbsolutePath() );
            }
            else
            {
                logger.info( "Dir {} exists", file.getAbsolutePath() );
            }
        }
    }

    public String getTempDir() {
        return System.getProperty("user.home") + File.separator + "marriott_temp" + File.separator;
    }
}
