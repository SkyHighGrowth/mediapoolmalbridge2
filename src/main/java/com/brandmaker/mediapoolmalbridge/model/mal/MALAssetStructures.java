package com.brandmaker.mediapoolmalbridge.model.mal;

import com.brandmaker.mediapoolmalbridge.config.AppConfig;
import com.brandmaker.mediapoolmalbridge.constants.Constants;
import com.brandmaker.mediapoolmalbridge.model.mal.propertyvariants.MALPropertyVariant;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.Map;

@Component
public class MALAssetStructures {

    private static final Logger logger = LoggerFactory.getLogger(MALAssetStructures.class);
    private final AppConfig appConfig;

    public MALAssetStructures(AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    public Map<Integer, MALPropertyVariant> getPropertyVariants() {
        FileReader reader = null;
        File propertyVariantsFile;
        String workingDirectoryPath = appConfig.getWorkingDirectory();
        File workingDirectory = new File(workingDirectoryPath);
        if (workingDirectory.exists()) {
            String filePath = workingDirectoryPath + File.separator + Constants.APPLICATION_DIR + File.separator + Constants.PROPERTY_VARIANTS_JSON;
            propertyVariantsFile = new File(filePath);
            if (propertyVariantsFile.exists()) {
                try {
                    reader = new FileReader(propertyVariantsFile);
                } catch (FileNotFoundException e) {
                    logger.error("Can not find file {}", propertyVariantsFile.getAbsolutePath(), e);
                }
            }
        }
        Type type = new TypeToken<Map<Integer, MALPropertyVariant>>() {
        }.getType();
        assert reader != null;
        return (new Gson()).fromJson(new JsonReader(reader), type);
    }

}
