package com.brandmaker.mediapoolmalbridge.service.impl;

import com.brandmaker.mediapoolmalbridge.config.AppConfig;
import com.brandmaker.mediapoolmalbridge.config.AppConfigData;
import com.brandmaker.mediapoolmalbridge.constants.Constants;
import com.brandmaker.mediapoolmalbridge.service.ConfigurationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * {@inheritDoc}
 */
@Service
public class ConfigurationServiceImpl implements ConfigurationService {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private final AppConfig appConfig;

    /**
     * ConfigurationServiceImpl constructor
     *
     * @param appConfig {@link AppConfig}
     */
    public ConfigurationServiceImpl(AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    /**
     * {@inheritDoc}
     */
    public AppConfigData getAppConfigData() {
        AppConfigData appConfigData = appConfig.getAppConfigData();
        try {
            appConfigData.setMalPrioritiesString(new ObjectMapper().writeValueAsString(appConfigData.getMalPriorities()));
            appConfigData.setIncludedAssetTypesString(new ObjectMapper().writeValueAsString(appConfigData.getIncludedAssetTypes()));
        } catch (JsonProcessingException e) {
            logger.error("Parsing of Mal priorities and Included asset types values failed! ", e);
        }
        return appConfigData;
    }

    /**
     * {@inheritDoc}
     */
    public void exportAppConfigurationProperties(HttpServletResponse response) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String myString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(appConfig.getAppConfigData());
            response.setContentType("text/plain");
            response.setHeader("Content-Disposition", "attachment;filename=application.properties.json");
            ServletOutputStream out = response.getOutputStream();
            out.println(myString);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * {@inheritDoc}
     */
    public AppConfigData saveAppConfigData(AppConfigData appConfigData) {
        ObjectMapper objectMapper = new ObjectMapper();
        String applicationPropertyFile = appConfig.getWorkingDirectory() + File.separator + Constants.APPLICATION_DIR + File.separator + Constants.APPLICATION_PROPERTIES_JSON;


        TypeReference<HashMap<String, String>> typeRefIncludedAssetTypes
                = new TypeReference<HashMap<String, String>>() {
        };
        try {
            Map<String, String> includedAssetTypes = objectMapper.readValue(appConfigData.getIncludedAssetTypesString(), typeRefIncludedAssetTypes);
            appConfigData.setIncludedAssetTypes(includedAssetTypes);
        } catch (JsonProcessingException e) {
            logger.error("Parsing of Included asset types values failed! ", e);
        }


        TypeReference<HashMap<Integer, List<String>>> typeRefMalPriorities
                = new TypeReference<HashMap<Integer, List<String>>>() {
        };
        try {
            Map<Integer, List<String>> malPriorities = objectMapper.readValue(appConfigData.getMalPrioritiesString(), typeRefMalPriorities);
            appConfigData.setMalPriorities(malPriorities);
        } catch (JsonProcessingException e) {
            logger.error("Parsing of Mal priorities values failed! ", e);
        }

        try {
            File propertiesFile = new File(applicationPropertyFile);
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(propertiesFile, appConfigData);
        } catch (IOException e) {
            logger.error(String.format("Saving in %s failed!", applicationPropertyFile), e);
        }
        appConfig.setAppConfigData(appConfigData);
        return appConfigData;
    }
}
