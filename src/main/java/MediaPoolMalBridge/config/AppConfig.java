package MediaPoolMalBridge.config;

import MediaPoolMalBridge.constants.Constants;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Arrays;

/**
 * Application configuration
 */
@Component
public class AppConfig {

    private static Logger logger = LoggerFactory.getLogger(AppConfig.class);

    private AppConfigData appConfigData;

    private Environment environment;

    public AppConfig( final AppConfigData appConfigData, final Environment environment )
    {
        if(Arrays.asList( environment.getActiveProfiles() ).contains( "dev" ) &&
           Arrays.asList( environment.getActiveProfiles() ).contains( "production" ) ) {
            logger.error( "Can not start with profiles dev and production set at the same time" );
            throw new RuntimeException();
        }
        this.appConfigData = appConfigData;
        File file = new File(System.getProperty("user.home") + File.separator + Constants.APPLICATION_DIR);
        if (!file.exists()) {
            if (!file.mkdir()) {
                logger.info("Dir {} can not be created", file.getAbsolutePath());
                throw new RuntimeException();
            }
        }
        file = new File(System.getProperty("user.home") + File.separator + Constants.APPLICATION_DIR + File.separator + Constants.EXCEL_DIR );
        if (!file.exists()) {
            if (!file.mkdir()) {
                logger.info("Dir {} can not be created", file.getAbsolutePath());
                throw new RuntimeException();
            }
        }

        file = new File(System.getProperty("user.home") + File.separator + Constants.APPLICATION_DIR + File.separator + "logs" );
        if (!file.exists()) {
            if (!file.mkdir()) {
                logger.info("Dir {} can not be created", file.getAbsolutePath());
                throw new RuntimeException();
            }
        }

        file = new File(System.getProperty("user.home") + File.separator + Constants.APPLICATION_DIR + File.separator + Constants.ASSET_DOWNLOAD_DIR );
        if (!file.exists()) {
            if (!file.mkdir()) {
                logger.info("Dir {} can not be created", file.getAbsolutePath());
                throw new RuntimeException();
            }
        }

        final String filePath = System.getProperty("user.home") + File.separator + Constants.APPLICATION_DIR + File.separator + Constants.APPLICATION_PROPERTIES_JSON;
        file = new File( filePath );
        if( file.exists() ) {
            FileReader reader = null;
            try {
                reader = new FileReader(file);
                this.appConfigData = (new Gson()).fromJson(new JsonReader(reader), AppConfigData.class);
                reader.close();
                logger.info( "App config data loaded {}", (new Gson()).toJson(appConfigData) );
            } catch (final Exception e) {
                logger.error( "Fatal: Can not parse application.properties.json", e );
                throw new RuntimeException();
            } finally {
                if( reader != null )
                {
                    try {
                        reader.close();
                    } catch( final Exception e )
                    {

                    }
                }
            }
        } else {
            FileWriter writer = null;
            try {
                writer = new FileWriter(file, false);
                writer.write((new Gson()).toJson(appConfigData));
            } catch (final Exception e) {
                logger.error("Can not write to file {}", file.getAbsolutePath(), e);
            } finally {
                if (writer != null) {
                    try {
                        writer.close();
                    } catch (final Exception e) {

                    }
                }
            }
        }
    }

    public String getTempDir() {
        return System.getProperty("user.home") + File.separator + Constants.APPLICATION_DIR + File.separator + Constants.ASSET_DOWNLOAD_DIR + File.separator;
    }

    public String getExcelDir() {
        return System.getProperty("user.home") + File.separator + Constants.APPLICATION_DIR + File.separator + Constants.EXCEL_DIR + File.separator;
    }

    public String getSftpUsername() {
        return appConfigData.getSftpUsername();
    }

    public String getSftpPassword() {
        return appConfigData.getSftpPassword();
    }

    public String getSftpHostname() {
        return appConfigData.getSftpHostname();
    }

    public int getSftpPort() {
        return appConfigData.getSftpPort();
    }

    public String getSftpPublicKey() {
        return appConfigData.getSftpPublicKey();
    }

    public String getMalHostname() {
        return appConfigData.getMalHostname();
    }

    public String getMalLogin() {
        return appConfigData.getMalLogin();
    }

    public String getMalApiKey() {
        return appConfigData.getMalApiKey();
    }

    public String getMediapoolUsernameDev() {
        return appConfigData.getMediapoolUsernameDev();
    }

    public String getMediapoolPasswordDev() {
        return appConfigData.getMediapoolPasswordDev();
    }

    public String getMediapoolUrlDev() {
        return appConfigData.getMediapoolUrlDev();
    }

    public String getThemeUrlDev() {
        return appConfigData.getThemeUrlDev();
    }

    public String getMediapoolUsernameProduction() {
        return appConfigData.getMediapoolUsernameProduction();
    }

    public String getMediapoolPasswordProduction() {
        return appConfigData.getMediapoolPasswordProduction();
    }

    public String getMediapoolUrlProduction() {
        return appConfigData.getMediapoolUrlProduction();
    }

    public String getThemeUrlProduction() {
        return appConfigData.getThemeUrlProduction();
    }

    public int getThreadschedulerPoolSize() {
        return appConfigData.getThreadschedulerPoolSize();
    }

    public int getThreadexecutorPoolSize() {
        return appConfigData.getThreadexecutorPoolSize();
    }

    public int getThreadexecutorQueueSize() {
        return appConfigData.getThreadexecutorQueueSize();
    }

    public int getThreadexecutorQueueLengthMax() {
        return appConfigData.getThreadexecutorQueueLengthMax();
    }

    public String getMailHostname() {
        return appConfigData.getMailHostname();
    }

    public int getMailPort() {
        return appConfigData.getMailPort();
    }

    public String getMailUsername() {
        return appConfigData.getMailUsername();
    }

    public String getMailPassword() {
        return appConfigData.getMailPassword();
    }

    public String getMailTransportProtocol() {
        return appConfigData.getMailTransportProtocol();
    }

    public boolean isMailSmtpAuth() {
        return appConfigData.isMailSmtpAuth();
    }

    public boolean isMailSmtpStarttlsEnable() {
        return appConfigData.isMailSmtpStarttlsEnable();
    }

    public boolean isMailDebug() {
        return appConfigData.isMailDebug();
    }

    public String getMailBmAddress() {
        return appConfigData.getMailBmAddress();
    }

    public String getMailMalAddress() {
        return appConfigData.getMailMalAddress();
    }

    public int getAssetStateRepetitionMax() {
        return appConfigData.getAssetStateRepetitionMax();
    }

    public int getMalLookInThePastDays() {
        return appConfigData.getMalLookInThePastDays();
    }

    public int getBridgeLookInThePastDays() {
        return appConfigData.getBridgeLookInThePastDays();
    }

    public String getBmExchangeSchedulerCronExpression() {
        return appConfigData.getBmExchangeSchedulerCronExpression();
    }

    public String getBmUploadSchedulerCronExpression() {
        return appConfigData.getBmUploadSchedulerCronExpression();
    }

    public String getBridgeExchangeAssetsCronExpression() {
        return appConfigData.getBridgeExchangeAssetsCronExpression();
    }

    public String getBridgeDeleteFileCronExpression() {
        return appConfigData.getBridgeDeleteFileCronExpression();
    }

    public String getBridgeExcelFilesCronExpression() {
        return appConfigData.getBridgeExcelFilesCronExpression();
    }

    public String getBridgeUploadExcelFilesCronExpression() {
        return appConfigData.getBridgeUploadExcelFilesCronExpression();
    }

    public String getBridgeTransferThemeCronExpression() {
        return appConfigData.getBridgeTransferThemeCronExpression();
    }

    public String getBridgeSendMailCronExpression() {
        return appConfigData.getBridgeSendMailCronExpression();
    }

    public String getMalAssetCronExpression() {
        return appConfigData.getMalAssetCronExpression();
    }

    public String getMalDownloadAssetCronExpression() {
        return appConfigData.getMalDownloadAssetCronExpression();
    }

    public String getMalAssetStructureCronExpression() {
        return appConfigData.getMalAssetStructureCronExpression();
    }

    public String getMalPropertiesCronExpression() {
        return appConfigData.getMalPropertiesCronExpression();
    }

    public String getDatasourceUrlDev() {
        return appConfigData.getDatasourceUrlDev();
    }

    public String getDatasourceUsernameDev() {
        return appConfigData.getDatasourceUsernameDev();
    }

    public String getDatasourcePasswordDev() {
        return appConfigData.getDatasourcePasswordDev();
    }

    public String getDatasourceUrlProduction() {
        return appConfigData.getDatasourceUrlProduction();
    }

    public String getDatasourceUsernameProduction() {
        return appConfigData.getDatasourceUsernameProduction();
    }

    public String getDatasourcePasswordProduction() {
        return appConfigData.getDatasourcePasswordProduction();
    }

    public String getHibernateDialect() {
        return appConfigData.getHibernateDialect();
    }

    public boolean isHibernateShowSql() {
        return appConfigData.isHibernateShowSql();
    }

    public String getHibernateHbm2dlAuto() {
        return appConfigData.getHibernateHbm2dlAuto();
    }

    public int getHikariConnectionTimeout() {
        return appConfigData.getHikariConnectionTimeout();
    }

    public int getHikariIdleTimeout() {
        return appConfigData.getHikariIdleTimeout();
    }

    public int getHikariMaxLifeTime() {
        return appConfigData.getHikariMaxLifeTime();
    }

    public boolean isHikariAutoCommit() {
        return appConfigData.isHikariAutoCommit();
    }

    public int getHikariMaximumPoolSize() {
        return appConfigData.getHikariMaximumPoolSize();
    }

    public int getDatabasePageSize() {
        return appConfigData.getDatabasePageSize();
    }

    public int getMalPageSize() {
        return appConfigData.getMalPageSize();
    }
}
