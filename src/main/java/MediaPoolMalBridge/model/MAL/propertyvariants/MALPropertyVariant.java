package MediaPoolMalBridge.model.MAL.propertyvariants;

import org.apache.commons.lang3.StringUtils;

public class MALPropertyVariant {

    private String id;

    private String structureName;

    private String addressField01;

    private String addressField02;

    private String addressField03;

    private String addressField04;

    private String addressField05;

    private String fields;

    public MALPropertyVariant( final String id, final String structureName, final String addressField01, final String addressField02, final String addressField03, final String addressField04, final String addressField05, final String fields )
    {
        this.id = id;
        this.structureName = structureName;
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

    public void setId(String id) {
        this.id = id;
    }

    public String getStructureName() {
        return structureName;
    }

    public void setStructureName(String structureName) {
        this.structureName = structureName;
    }

    public String getAddressField01() {
        return addressField01;
    }

    public void setAddressField01(String addressField01) {
        this.addressField01 = addressField01;
    }

    public String getAddressField02() {
        return addressField02;
    }

    public void setAddressField02(String addressField02) {
        this.addressField02 = addressField02;
    }

    public String getAddressField03() {
        return addressField03;
    }

    public void setAddressField03(String addressField03) {
        this.addressField03 = addressField03;
    }

    public String getAddressField04() {
        return addressField04;
    }

    public void setAddressField04(String addressField04) {
        this.addressField04 = addressField04;
    }

    public String getAddressField05() {
        return addressField05;
    }

    public void setAddressField05(String addressField05) {
        this.addressField05 = addressField05;
    }

    public String getFields() {
        return fields;
    }

    public void setFields(String fields) {
        this.fields = fields;
    }

    public String getBrandId()
    {
        if( StringUtils.isNotBlank( structureName ) ) {
            final String[] explode = structureName.split("_");
            return explode[explode.length - 1];
        }
        return null;
    }

    public String[] getFieldsArray()
    {
        return fields.split( "," );
    }

    public String getSubName()
    {
        //TODO compute subname to be used with photos in excel creation
        return "SUB_NAME";
    }

    public String getSubNameMaps()
    {
        //TODO compute subname maps to be used with photos in excel creation
        return "SUB_NAME_MAPS";
    }

    public String getSubNameFloorPlans()
    {
        //TODO compute subname floor plans to be used with floor plans in excel generation
        return "SUB_NAME_FLOOR_PLANS";
    }
}
