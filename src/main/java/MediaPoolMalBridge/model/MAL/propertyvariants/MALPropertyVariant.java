package MediaPoolMalBridge.model.MAL.propertyvariants;

import org.apache.commons.lang3.StringUtils;

public class MALPropertyVariant {

    private String id;

    private String structureName;

    private String structureNameRadiobutton;

    private String subName;

    private String subNameRadiobutton;

    private String brandName;

    private String subNameMaps;

    private String subNameMapsRadiobutton;

    private String subNameFloorPlans;

    private String subNameFloorPlansRadiobutton;

    private String addressField01;

    private String addressField02;

    private String addressField03;

    private String addressField04;

    private String addressField05;

    private String fields;

    public String getId() {
        return id;
    }

    public String getStructureName() {
        return structureName;
    }

    public String getStructureNameRadiobutton() {
        return structureNameRadiobutton;
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

    public String getSubNameRadiobutton() {
        return subNameRadiobutton;
    }

    public String getSubNameMaps() {
        return subNameMaps;
    }

    public String getSubNameMapsRadiobutton() {
        return subNameMapsRadiobutton;
    }

    public String getSubNameFloorPlans() {
        return subNameFloorPlans;
    }

    public String getSubNameFloorPlansRadiobutton() {
        return subNameFloorPlansRadiobutton;
    }

    public boolean isBrandStructure(){
        return this.structureName.startsWith("BRAND_");
    }

    public String getCollection() {
        String collectionName = structureName.substring(6, structureName.lastIndexOf("_"));
        collectionName = collectionName.replaceAll("_", " ");
        return collectionName;
    }

    public String getBrandName() {
        return this.brandName;
    }
}
