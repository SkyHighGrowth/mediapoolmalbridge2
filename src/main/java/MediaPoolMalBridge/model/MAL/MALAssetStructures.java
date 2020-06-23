package MediaPoolMalBridge.model.MAL;

import MediaPoolMalBridge.config.AppConfig;
import MediaPoolMalBridge.constants.Constants;
import MediaPoolMalBridge.model.MAL.propertyvariants.MALPropertyVariant;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.*;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

@Component
public class MALAssetStructures {

    private static final Logger logger = LoggerFactory.getLogger(MALAssetStructures.class);

    private final AppConfig appConfig;

    public MALAssetStructures(AppConfig appConfig) {
        this.appConfig = appConfig;
        initAssetStructures();
    }

    private void initAssetStructures() {
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

                assert reader != null;
                Type type = new TypeToken<Map<Integer, MALPropertyVariant>>() {
                }.getType();
                Map<Integer, MALPropertyVariant> propertyVariants = (new Gson()).fromJson(new JsonReader(reader), type);
                this.setPropertyVariants(propertyVariants);
            }
        }
    }

    private Map<String, String> brands = new HashMap<>();
    private Map<String, String> collections = new HashMap<>();
    private Map<String, String> colors = new HashMap<>();
    private Map<String, String> destinations = new HashMap<>();
    private Map<String, String> subjects = new HashMap<>();
    private Map<String, String> assetTypes = new HashMap<>();
    private Map<String, String> fileTypes = new HashMap<>();
    private Map<String, String> propertyTypes = new HashMap<>();
    private Map<Integer, MALPropertyVariant> propertyVariants = new HashMap<>();

    public Map<String, String> getBrands() {
        return brands;
    }

    public void setBrands(Map<String, String> brands) {
        this.brands = brands;
    }

    public Map<String, String> getCollections() {
        return collections;
    }

    public void setCollections(Map<String, String> collections) {
        this.collections = collections;
    }

    public Map<String, String> getColors() {
        return colors;
    }

    public void setColors(Map<String, String> colors) {
        this.colors = colors;
    }

    public Map<String, String> getDestinations() {
        return destinations;
    }

    public void setDestinations(Map<String, String> destinations) {
        this.destinations = destinations;
    }

    public Map<String, String> getSubjects() {
        return subjects;
    }

    public void setSubjects(Map<String, String> subjects) {
        this.subjects = subjects;
    }

    public Map<String, String> getAssetTypes() {
        return assetTypes;
    }

    public void setAssetTypes(Map<String, String> assetTypes) {
        this.assetTypes = assetTypes;
    }

    public Map<String, String> getFileTypes() {
        return fileTypes;
    }

    public void setFileTypes(Map<String, String> fileTypes) {
        this.fileTypes = fileTypes;
    }

    public Map<String, String> getPropertyTypes() {
        return propertyTypes;
    }

    public void setPropertyTypes(Map<String, String> propertyTypes) {
        this.propertyTypes = propertyTypes;
    }

    public Map<Integer, MALPropertyVariant> getPropertyVariants() {
        return propertyVariants;
    }

    public void setPropertyVariants(Map<Integer, MALPropertyVariant> propertyVariants) {
        this.propertyVariants = propertyVariants;
    }
}
