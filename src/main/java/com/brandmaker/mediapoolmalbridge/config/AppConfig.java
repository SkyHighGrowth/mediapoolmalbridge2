package com.brandmaker.mediapoolmalbridge.config;

import com.brandmaker.mediapoolmalbridge.constants.Constants;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Application configuration
 */
@Component
@DependsOn("AppConfigData")
public class AppConfig {

    private static final Logger logger = LoggerFactory.getLogger(AppConfig.class);

    private AppConfigData appConfigData;

    private static final String WORK_DIR = "/data/skyhigh"; //change to /data/skyhigh

    public AppConfig(final AppConfigData appConfigData,
                     final Environment environment,
                     final ObjectMapper objectMapper) {

        if (Arrays.asList(environment.getActiveProfiles()).contains("dev") &&
                Arrays.asList(environment.getActiveProfiles()).contains("production")) {
            logger.error("Can not start with profiles dev and production set at the same time");
            throw new RuntimeException("Can not start with profiles dev and production set at the same time");
        }

        File file = new File(WORK_DIR + File.separator + Constants.APPLICATION_DIR);
        checkIfFileExist(file);
        final String filePath = WORK_DIR + File.separator + Constants.APPLICATION_DIR + File.separator + Constants.APPLICATION_PROPERTIES_JSON;
        file = new File(filePath);
        logger.info("FILE PATH {}", file.getAbsolutePath());
        if (file.exists()) {
            try {
                this.appConfigData = objectMapper.readValue(file, AppConfigData.class);
            } catch (final Exception e) {
                logger.error("Fatal: Can not parse application.properties.json", e);
                throw new RuntimeException("Fatal: Can not parse application.properties.json");
            }
        } else {
            try {
                this.appConfigData = appConfigData;
                objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, appConfigData);
            } catch (final Exception e) {
                logger.error("Can not write to file {}", file.getAbsolutePath(), e);
            }
        }

        // Copy resources/propertyVariants.json file to data/skyhigh folder
        if (file.exists()) {
            InputStream propertyVariantsFile;
            String destinationPath = getWorkingDirectory() + File.separator + Constants.APPLICATION_DIR + File.separator + Constants.PROPERTY_VARIANTS_JSON;
            File dataPropertyVariantsFile = new File(destinationPath);
            try {
                if (!dataPropertyVariantsFile.exists()) {
                    ClassLoader classLoader = getClass().getClassLoader();
                    propertyVariantsFile = classLoader.getResourceAsStream("propertyVariants.json");
                    assert propertyVariantsFile != null;
                    Files.copy(propertyVariantsFile, Paths.get(destinationPath), StandardCopyOption.REPLACE_EXISTING);

                }
            } catch (FileNotFoundException e) {
                logger.error("Can not write to file {}", dataPropertyVariantsFile.getAbsolutePath(), e);
            } catch (IOException e) {
                logger.error("Can not copy file to destination file {}", destinationPath, e);
            }
        }

        file = new File(getWorkingDirectory() + File.separator + Constants.APPLICATION_DIR + File.separator + Constants.EXCEL_DIR);
        checkIfFileExist(file);

        file = new File(getWorkingDirectory() + File.separator + Constants.APPLICATION_DIR + File.separator + Constants.LOGS);
        checkIfFileExist(file);

        file = new File(getWorkingDirectory() + File.separator + Constants.APPLICATION_DIR + File.separator + Constants.ASSET_DOWNLOAD_DIR);
        checkIfFileExist(file);
    }

    private void checkIfFileExist(File file) {
        if (!file.exists() && !file.mkdir()) {
            logger.info("Dir {} can not be created", file.getAbsolutePath());
            throw new IllegalArgumentException();
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

    public boolean isUseSftp() {
        return appConfigData.isUseSftp();
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

    public int getBridgeDatabaseRowsDeleterDays() {
        return appConfigData.getBridgeDatabaseRowsDeleterDays();
    }

    public String getBmExchangeSchedulerCronExpression() {
        return appConfigData.getBmExchangeSchedulerCronExpression();
    }

    public String getBmUploadSchedulerCronExpression() {
        return appConfigData.getBmUploadSchedulerCronExpression();
    }

    public String getBmGetThemeIdCronExpression() {
        return appConfigData.getBmGetThemeIdCronExpression();
    }

    @SuppressWarnings("unused method")
    public String getBridgeExchangeAssetsCronExpression() {
        return appConfigData.getBridgeExchangeAssetsCronExpression();
    }

    public String getBridgeDeleteFileCronExpression() {
        return appConfigData.getBridgeDeleteFileCronExpression();
    }

    public String getBridgeDatabaseRowsDeleterCronExpression() {
        return appConfigData.getBridgeDatabaseRowsDeleterCronExpression();
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

    @SuppressWarnings("unused method")
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
        if (appConfigData.getDatabasePageSize() <= 0) {
            throw new IllegalArgumentException("Database page size has to be positive number");
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

    public Map<String, String> getIncludedAssetTypes() {
        return appConfigData.getIncludedAssetTypes();
    }

    public boolean isDisableAbsoluteDelete() {
        return appConfigData.isDisableAbsoluteDelete();
    }

    public boolean isDoNotCreateExcelFiles() {
        return appConfigData.isDoNotCreateExcelFiles();
    }

    public String getFailSaveCategoryName() {
        return appConfigData.getFailSaveCategoryName();
    }

    public boolean intervalFilterEnable() {
        return appConfigData.isIntervalFilterEnable();
    }

    public String getFilterStartDate() {
        return appConfigData.getFilterStartDate();
    }

    public String getFilterEndDate() {
        return appConfigData.getFilterEndDate();
    }

    public int getFileMaxRecords(){
        return appConfigData.getFileMaxRecords();
    }

    public long getDownloadFolderSizeLimit() {
        return appConfigData.getDownloadFolderSizeLimit();
    }

    public List<String> getFileFormatsOrder() {
        return appConfigData.getFileFormatsOrder();
    }

}
