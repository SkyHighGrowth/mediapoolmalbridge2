package MediaPoolMalBridge.model.MAL.propertyvariants;

import org.apache.commons.lang3.StringUtils;

public class MALPropertyVariant {

    private final String id;

    private final String structureName;

    private final String subName;

    private final String brandName;

    private final String subNameMaps;

    private final String subNameFloorPlans;

    private final String addressField01;

    private final String addressField02;

    private final String addressField03;

    private final String addressField04;

    private final String addressField05;

    private final String fields;

    public MALPropertyVariant(final String id,
                              final String structureName,
                              final String subName,
                              final String brandName,
                              final String subNameMaps,
                              final String subNameFloorPlans,
                              final String addressField01,
                              final String addressField02,
                              final String addressField03,
                              final String addressField04,
                              final String addressField05,
                              final String fields) {
        this.id = id;
        this.structureName = structureName;
        this.subName = subName;
        this.brandName = brandName;
        this.subNameMaps = subNameMaps;
        this.subNameFloorPlans = subNameFloorPlans;
        this.addressField01 = addressField01;
        this.addressField02 = addressField02;
        this.addressField03 = addressField03;
        this.addressField04 = addressField04;
        this.addressField05 = addressField05;
        this.fields = fields;
    }

    public String getId() {
        return id;
    }

    public String getStructureName() {
        return structureName;
    }

    public String getAddressField01() {
        return addressField01;
    }

    public String getAddressField02() {
        return addressField02;
    }

    public String getAddressField03() {
        return addressField03;
    }

    public String getAddressField04() {
        return addressField04;
    }

    public String getAddressField05() {
        return addressField05;
    }

    public String getFields() {
        return fields;
    }

    public String getBrandId() {
        if (StringUtils.isNotBlank(structureName)) {
            final String[] explode = structureName.split("_");
            return explode[explode.length - 1];
        }
        return null;
    }

    public String[] getFieldsArray() {
        return fields.split(",");
    }

    public String getSubName() {
        return subName;
    }

    public String getSubNameMaps() {
        return subNameMaps;
    }

    public String getSubNameFloorPlans() {
        return subNameFloorPlans;
    }

    public boolean isBrandStructure(){
        return this.structureName.startsWith("BRAND_");
    }

    public String getBrandName() {
        return this.brandName;
    }
}
