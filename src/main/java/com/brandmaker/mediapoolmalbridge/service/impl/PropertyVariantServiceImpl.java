package com.brandmaker.mediapoolmalbridge.service.impl;

import com.brandmaker.mediapoolmalbridge.config.AppConfig;
import com.brandmaker.mediapoolmalbridge.constants.Constants;
import com.brandmaker.mediapoolmalbridge.model.mal.propertyvariants.MALPropertyVariant;
import com.brandmaker.mediapoolmalbridge.service.PropertyVariantService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * {@inheritDoc}
 */
@Service
public class PropertyVariantServiceImpl implements PropertyVariantService {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private final AppConfig appConfig;

    /**
     * PropertyVariantServiceImpl constructor
     *
     * @param appConfig {@link AppConfig}
     */
    public PropertyVariantServiceImpl(AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    /**
     * {@inheritDoc}
     */
    public Map<String, MALPropertyVariant> getPropertyVariantsSorted() {
        return appConfig.getPropertyVariants()
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey(Comparator.comparing(Integer::valueOf)))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue, LinkedHashMap::new));
    }

    /**
     * {@inheritDoc}
     */
    public void exportPropertyVariantsAsFile(HttpServletResponse response) {
        try {
            String myString = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(appConfig.getPropertyVariants());
            response.setContentType("text/plain; charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename=propertyVariants.json");
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
    public Map<String, MALPropertyVariant> savePropertyVariant(MALPropertyVariant malPropertyVariant) {
        String propertyVariantsFilePath = appConfig.getWorkingDirectory() + File.separator + Constants.APPLICATION_DIR
                + File.separator + Constants.PROPERTY_VARIANTS_JSON;
        File propertiesFile = new File(propertyVariantsFilePath);
        ObjectMapper objectMapper = new ObjectMapper();
        FileReader reader = null;

        try {
            reader = new FileReader(propertiesFile);
        } catch (FileNotFoundException e) {
            logger.error("Can not find file {}", propertiesFile.getAbsolutePath(), e);
        }

        assert reader != null;
        Type type = new TypeToken<Map<String, MALPropertyVariant>>() {
        }.getType();
        Map<String, MALPropertyVariant> propertyVariantsMap = (new Gson()).fromJson(new JsonReader(reader), type);

        try {
            if (malPropertyVariant != null && malPropertyVariant.getId() != null) {
                propertyVariantsMap.put(malPropertyVariant.getId(), malPropertyVariant);
                objectMapper.writerWithDefaultPrettyPrinter().writeValue(propertiesFile, propertyVariantsMap);
                appConfig.setPropertyVariants(propertyVariantsMap);
            }
        } catch (IOException e) {
            logger.error(String.format("Saving in %s failed!", propertyVariantsFilePath), e);
        }
        return propertyVariantsMap;
    }
}
