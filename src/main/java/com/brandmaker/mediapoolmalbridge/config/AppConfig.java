package com.brandmaker.mediapoolmalbridge.config;

import com.brandmaker.mediapoolmalbridge.constants.Constants;
import com.brandmaker.mediapoolmalbridge.exception.AppPropertiesParseException;
import com.brandmaker.mediapoolmalbridge.exception.ProfileMissingException;
import com.brandmaker.mediapoolmalbridge.model.mal.propertyvariants.MALPropertyVariant;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Application configuration
 */
@Component
@DependsOn("AppConfigData")
public class AppConfig {

    private static final Logger logger = LoggerFactory.getLogger(AppConfig.class);

    private AppConfigData appConfigData;
    private Map<String, MALPropertyVariant> propertyVariants;

    private static final String WORK_DIR = "/data/skyhigh"; //change to /data/skyhigh

    public AppConfig(final AppConfigData appConfigData,
                     final Environment environment,
                     final ObjectMapper objectMapper) {

        if (Arrays.asList(environment.getActiveProfiles()).contains("dev") &&
                Arrays.asList(environment.getActiveProfiles()).contains("production")) {
            logger.error("Can not start with profiles dev and production set at the same time");
            throw new ProfileMissingException("Can not start with profiles dev and production set at the same time");
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
                throw new AppPropertiesParseException("Fatal: Can not parse application.properties.json");
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
                TypeReference<HashMap<String,MALPropertyVariant>> typeRef
                        = new TypeReference<HashMap<String,MALPropertyVariant>>() {};

                this.setPropertyVariants(objectMapper.readValue(dataPropertyVariantsFile,typeRef));
            } catch (IOException e) {
                logger.error("Reading of propertyVariants.json file failed!", e);
            }

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
        final String filePath = WORK_DIR + File.separator + Constants.APPLICATION_DIR + File.separator + Constants.APPLICATION_PROPERTIES_JSON;
        File file = new File(filePath);
        try {
            return new ObjectMapper().readValue(file, AppConfigData.class);
        } catch (IOException e) {
            logger.error("Fatal: Can not parse application.properties.json", e);
        }
        return null;
    }

    public void setAppConfigData(AppConfigData appConfigData) {
        this.appConfigData = appConfigData;
    }

    public Map<String, MALPropertyVariant> getPropertyVariants() {
        final String filePath = WORK_DIR + File.separator + Constants.APPLICATION_DIR + File.separator + Constants.PROPERTY_VARIANTS_JSON;
        File file = new File(filePath);
        FileReader reader = null;
        try {
            reader = new FileReader(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Type type = new TypeToken<Map<String, MALPropertyVariant>>() {
        }.getType();

        assert reader != null;
        return (new Gson()).fromJson(new JsonReader(reader), type);
    }

    public void setPropertyVariants(Map<String, MALPropertyVariant> propertyVariants) {
        this.propertyVariants = propertyVariants;
    }

    public String getTempDir() {
        return getWorkingDirectory() + File.separator + Constants.APPLICATION_DIR + File.separator + Constants.ASSET_DOWNLOAD_DIR + File.separator;
    }

    public String getExcelDir() {
        return getWorkingDirectory() + File.separator + Constants.APPLICATION_DIR + File.separator + Constants.EXCEL_DIR + File.separator;
    }


    public String getBridgeDeleteFileCronExpression() {
        return appConfigData.getBridgeDeleteFileCronExpression();
    }

    public String getBridgeDatabaseRowsDeleterCronExpression() {
        return appConfigData.getBridgeDatabaseRowsDeleterCronExpression();
    }

    public String getBmExchangeSchedulerCronExpression() {
        return appConfigData.getBmExchangeSchedulerCronExpression();
    }

    public String getBmGetThemeIdCronExpression() {
        return appConfigData.getBmGetThemeIdCronExpression();
    }

    public String getBmUploadSchedulerCronExpression() {
        return appConfigData.getBmUploadSchedulerCronExpression();
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

    public String getMalNewAssetCronExpression(){
        return appConfigData.getMalNewAssetCronExpression();
    }

    public String getMalPropertiesCronExpression() {
        return appConfigData.getMalPropertiesCronExpression();
    }

    public int getDatabasePageSize() {
        AppConfigData loadConfigData = getAppConfigData();
        if (loadConfigData.getDatabasePageSize() <= 0) {
            throw new IllegalArgumentException("Database page size has to be positive number");
        }
        return loadConfigData.getDatabasePageSize();
    }

    public String getWorkingDirectory() {
        return appConfigData.getWorkingDirectory();
    }

}
