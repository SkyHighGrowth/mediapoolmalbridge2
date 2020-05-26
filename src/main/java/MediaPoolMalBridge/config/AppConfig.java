package MediaPoolMalBridge.config;

import MediaPoolMalBridge.constants.Constants;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Application configuration
 */
@Component
@DependsOn( "AppConfigData" )
public class AppConfig {

    private static Logger logger = LoggerFactory.getLogger(AppConfig.class);

    private AppConfigData appConfigData;

    private String workDir = "/home/strahinja/data/skyhigh"; //change to /data/skyhigh

    public AppConfig( final AppConfigData appConfigData,
                      final Environment environment,
                      final ObjectMapper objectMapper )
    {

        if(Arrays.asList( environment.getActiveProfiles() ).contains( "dev" ) &&
                Arrays.asList( environment.getActiveProfiles() ).contains( "production" ) ) {
            logger.error( "Can not start with profiles dev and production set at the same time" );
            throw new RuntimeException( "Can not start with profiles dev and production set at the same time" );
        }

        File file = new File( workDir + File.separator + Constants.APPLICATION_DIR);
        //File file = new File( "C:/Users/User" + File.separator + Constants.APPLICATION_DIR);
        if (!file.exists()) {
            if (!file.mkdir()) {
                logger.info("Dir {} can not be created", file.getAbsolutePath());
                throw new RuntimeException();
            }
        }
        final String filePath = workDir + File.separator + Constants.APPLICATION_DIR + File.separator + Constants.APPLICATION_PROPERTIES_JSON;
        //final String filePath = "C:/Users/User" + File.separator + Constants.APPLICATION_DIR + File.separator + Constants.APPLICATION_PROPERTIES_JSON;
        file = new File( filePath );
        logger.info( "FILE PATH {}", file.getAbsolutePath() );
        if( file.exists() ) {
            try {
                this.appConfigData = objectMapper.readValue( file, AppConfigData.class );
                logger.info( "App config data loaded {}", (new Gson()).toJson(this.appConfigData) );
            } catch (final Exception e) {
                logger.error( "Fatal: Can not parse application.properties.json", e );
                throw new RuntimeException( "Fatal: Can not parse application.properties.json" );
            }
        } else {
            try {
                this.appConfigData = appConfigData;
                objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, appConfigData);
                logger.info( "Using default app config data loaded {}", (new Gson()).toJson(this.appConfigData) );
            } catch (final Exception e) {
                logger.error("Can not write to file {}", file.getAbsolutePath(), e);
            }
        }

        file = new File(getWorkingDirectory() + File.separator + Constants.APPLICATION_DIR + File.separator + Constants.EXCEL_DIR );
        if (!file.exists()) {
            if (!file.mkdir()) {
                logger.info("Dir {} can not be created", file.getAbsolutePath());
                throw new RuntimeException();
            }
        }

        file = new File(getWorkingDirectory() + File.separator + Constants.APPLICATION_DIR + File.separator + "logs" );
        if (!file.exists()) {
            if (!file.mkdir()) {
                logger.info("Dir {} can not be created", file.getAbsolutePath());
                throw new RuntimeException();
            }
        }

        file = new File(getWorkingDirectory() + File.separator + Constants.APPLICATION_DIR + File.separator + Constants.ASSET_DOWNLOAD_DIR );
        if (!file.exists()) {
            if (!file.mkdir()) {
                logger.info("Dir {} can not be created", file.getAbsolutePath());
                throw new RuntimeException();
            }
        }
    }

    public AppConfigData getAppConfigData() {
        return appConfigData;
    }

    public String getTempDir() {
        return getWorkingDirectory() + File.separator + Constants.APPLICATION_DIR + File.separator + Constants.ASSET_DOWNLOAD_DIR + File.separator;
    }

    public String getExcelDir() {
        return getWorkingDirectory() + File.separator + Constants.APPLICATION_DIR + File.separator + Constants.EXCEL_DIR + File.separator;
    }

    public boolean isUseSftp() { return appConfigData.isUseSftp(); }

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

    public int getMalThreadexecutorPoolSize() {
        return appConfigData.getMalThreadexecutorPoolSize();
    }

    public int getMalThreadexecutorQueueSize() {
        return appConfigData.getMalThreadexecutorQueueSize();
    }

    public int getMalThreadexecutorQueueLengthMax() {
        return appConfigData.getMalThreadexecutorQueueLengthMax();
    }

    public int getBmThreadexecutorPoolSize() {
        return appConfigData.getBmThreadexecutorPoolSize();
    }

    public int getBmThreadexecutorQueueSize() {
        return appConfigData.getBmThreadexecutorQueueSize();
    }

    public int getBmThreadexecutorQueueLengthMax() {
        return appConfigData.getBmThreadexecutorQueueLengthMax();
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

    public int getBridgeResolverWindow() {
        return appConfigData.getBridgeResolverWindow();
    }

    public int getBridgeDatabaseRowsDeleterDays() { return appConfigData.getBridgeDatabaseRowsDeleterDays(); }

    public String getBmExchangeSchedulerCronExpression() {
        return appConfigData.getBmExchangeSchedulerCronExpression();
    }

    public String getBmUploadSchedulerCronExpression() {
        return appConfigData.getBmUploadSchedulerCronExpression();
    }

    public String getBmGetThemeIdCronExpression() {
        return appConfigData.getBmGetThemeIdCronExpression();
    }

    public String getBridgeExchangeAssetsCronExpression() {
        return appConfigData.getBridgeExchangeAssetsCronExpression();
    }

    public String getBridgeDeleteFileCronExpression() {
        return appConfigData.getBridgeDeleteFileCronExpression();
    }

    public String getBridgeDatabaseRowsDeleterCronExpression() { return appConfigData.getBridgeDatabaseRowsDeleterCronExpression(); }

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

    public String getBridgeAssetOnBoardingCronExpression() {
        return appConfigData.getBridgeAssetOnBoardingCronExpression();
    }

    public String getBridgeAssetResolverCronExpression() {
        return appConfigData.getBridgeAssetResolverCronExpression();
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
        if( appConfigData.getDatabasePageSize() <= 0 ) {
            throw new RuntimeException( "Database page size has to be positive number" );
        }
        return appConfigData.getDatabasePageSize();
    }

    public int getMalPageSize() {
        return appConfigData.getMalPageSize();
    }

    public Map<Integer, List<String>> getMalPriorities() {
        return appConfigData.getMalPriorities();
    }

    public String getWorkingDirectory() {
        return appConfigData.getWorkingDirectory();
    }

    public int getAssetFileMaximalLivingDaysOnDisc() {
        return appConfigData.getAssetFileMaximalLivingDaysOnDisc();
    }

    public Map<String, String> getIncludedAssetTypes() { return appConfigData.getIncludedAssetTypes(); }

    public boolean isDisableAbsoluteDelete() { return appConfigData.isDisableAbsoluteDelete(); }

    public boolean isDoNotCreateExcelFiles() { return appConfigData.isDoNotCreateExcelFiles(); }
}
