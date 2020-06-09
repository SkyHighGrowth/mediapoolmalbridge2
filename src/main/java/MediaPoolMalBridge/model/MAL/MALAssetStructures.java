package MediaPoolMalBridge.model.MAL;

import MediaPoolMalBridge.model.MAL.propertyvariants.MALPropertyVariant;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

@Component
public class MALAssetStructures {

    public MALAssetStructures() {
        initAssetStructures();
    }

    private void initAssetStructures() {
        FileReader reader = null;
        File file;
        try {
            file = ResourceUtils.getFile("classpath:propertyVariants.json");
            reader = new FileReader(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        assert reader != null;
        Type type = new TypeToken<Map<Integer, MALPropertyVariant>>(){}.getType();
        Map<Integer, MALPropertyVariant> propertyVariants = (new Gson()).fromJson(new JsonReader(reader), type);
        this.setPropertyVariants(propertyVariants);
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
