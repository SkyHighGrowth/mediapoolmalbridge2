package MediaPoolMalBridge.model.MAL;

import MediaPoolMalBridge.model.MAL.propertyvariants.MALPropertyVariant;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class MALAssetStructures {

    private Map<String, String> brands = new HashMap<>();
    private Map<String, String> collections = new HashMap<>();
    private Map<String, String> colors = new HashMap<>();
    private Map<String, String> destinations = new HashMap<>();
    private Map<String, String> subjects = new HashMap<>();
    private Map<String, String> assetTypes = new HashMap<>();
    private Map<String, String> fileTypes = new HashMap<>();
    private Map<String, String> propertyTypes = new HashMap<>();
    private static Map<String, MALPropertyVariant> propertyVariants = new HashMap<>();

    static {
        propertyVariants.put( "000", new MALPropertyVariant( "000", "AFFILIATES_V000_[BRAND]", null, null, null, null, null, null ) );
        propertyVariants.put( "001", new MALPropertyVariant( "001", "AFFILIATES_V001_[BRAND]", "{name} {street}", "{city}", "{state} {zip}", null, null, "name, street, city, state, zip" ) );
        propertyVariants.put( "002", new MALPropertyVariant( "002", "AFFILIATES_V002_[BRAND]", "{name} {city}", "{state}", "{country}", null, null, "name, street, city, state, country" ) );
        propertyVariants.put( "003", new MALPropertyVariant( "003", "AFFILIATES_V003_[BRAND]", "{name} {street} {city}", "{state} {zip}", null, null, null, "name, street, city, state, zip" ) );
        propertyVariants.put( "004", new MALPropertyVariant( "004", "AFFILIATES_V004_[BRAND]", "{name}", "{street}", "{city}", "{state} {zip}", null, "name, street, city, state, zip" ) );
        propertyVariants.put( "005", new MALPropertyVariant( "005", "AFFILIATES_V005_[BRAND]", "{name} {street} {city}", "{state} {zip}", null, null, null, "name, street, city, state, zip" ) );
        propertyVariants.put( "006", new MALPropertyVariant( "006", "AFFILIATES_V006_[BRAND]", "{name} {street}", "{city}", "{state} {zip} {country}", null, null, "name, street, city, state, zip,country" ) );
        propertyVariants.put( "007", new MALPropertyVariant( "007", "AFFILIATES_V007_[BRAND]", "{name} {street}", "{city}", "{state}", null, null, "name, street, city, state" ) );
        propertyVariants.put( "008", new MALPropertyVariant( "008", "AFFILIATES_V008_[BRAND]", "{street} {city}", "{state} {zip}", null, null, null, "street, city, state, zip" ) );
        propertyVariants.put( "009", new MALPropertyVariant( "009", "AFFILIATES_V009_[BRAND]", "{name}*{street}*{city}*{state}*{telephone}", null, null, null, null, "name, street, city, state, telephone" ) );
        propertyVariants.put( "010", new MALPropertyVariant( "010", "AFFILIATES_V010_[BRAND]", "{name} {street}", "{city}", "{state} {zip}", null, null, "name, street, city, state, zip" ) );
        propertyVariants.put( "011", new MALPropertyVariant( "011", "AFFILIATES_V011_[BRAND]", "{name} {street} {state}", "{city} {zip} {country} {telephone} {property url}", null, null, null, "name, street, city, state, zip, telephone, property url" ) );
        propertyVariants.put( "012", new MALPropertyVariant( "012", "AFFILIATES_V012_[BRAND]", "{name} {street} {state}", "{city} {zip}", "{country} {telephone} {property url}", null, null, "name, street, city, state, zip, telephone, property url" ) );
        propertyVariants.put( "013", new MALPropertyVariant( "013", "AFFILIATES_V013_[BRAND]", "{name} {street} {state} {city} {zip} {country} {telephone} {property url}", null, null, null, null, "name, street, city, state, zip, telephone, property url" ) );
        propertyVariants.put( "014", new MALPropertyVariant( "014", "AFFILIATES_V014_[BRAND]", "{name} {city} {country}", null, null, null, null, "name, city, country" ) );
        propertyVariants.put( "015", new MALPropertyVariant( "015", "AFFILIATES_V015_[BRAND]", "{name} {street}", "{city}", "{state} {zip} {country}", null, null, "name, street, city, state, zip, country" ) );
        propertyVariants.put( "016", new MALPropertyVariant( "016", "AFFILIATES_V016_[BRAND]", "{name} {street}", "{city}", "{state} {zip} {country}", null, null, "name, street, city, state, zip, country" ) );
        propertyVariants.put( "017", new MALPropertyVariant( "017", "AFFILIATES_V017_[BRAND]", "{name} {street}", "{city}", "{state} {zip} {country}", null, null, "name, street, city, state, zip, country" ) );
        propertyVariants.put( "018", new MALPropertyVariant( "018", "AFFILIATES_V018_[BRAND]", "{street}", "{city}", "{state} {zip} {country}", null, null, "street, city, state, zip, country" ) );
    }

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

    public static Map<String, MALPropertyVariant> getPropertyVariants() {
        return propertyVariants;
    }

    public static void setPropertyVariants(Map<String, MALPropertyVariant> propertyVariants) {
        MALAssetStructures.propertyVariants = propertyVariants;
    }
}
