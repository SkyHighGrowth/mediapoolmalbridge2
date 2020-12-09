package com.brandmaker.mediapoolmalbridge.model.mal.propertyvariants;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.apache.commons.lang3.StringUtils;

public class MALPropertyVariant {

    private String id;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String brandName;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String structureName;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String structureNameRadiobutton;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String subName;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String subNameRadiobutton;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String subNameMaps;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String subNameMapsRadiobutton;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String subNameFloorPlans;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String subNameFloorPlansRadiobutton;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String addressField01;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String addressField02;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String addressField03;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String addressField04;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String addressField05;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String fields;

    public String getId() {
        return id;
    }

    public String getBrandName() {
        return this.brandName;
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

    @JsonIgnore
    public String getBrandId() {
        if (StringUtils.isNotBlank(structureName)) {
            final String[] explode = structureName.split("_");
            return explode[explode.length - 1];
        }
        return null;
    }

    @JsonIgnore
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

    @JsonIgnore
    public boolean isBrandStructure(){
        return this.structureName.startsWith("BRAND_");
    }

    @JsonIgnore
    public String getCollection() {
        String collectionName = structureName.substring(6, structureName.lastIndexOf("_"));
        collectionName = collectionName.replace("_", " ");
        return collectionName;
    }


    public void setId(String id) {
        this.id = id;
    }

    public void setStructureName(String structureName) {
        this.structureName = structureName;
    }

    public void setStructureNameRadiobutton(String structureNameRadiobutton) {
        this.structureNameRadiobutton = structureNameRadiobutton;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public void setSubNameRadiobutton(String subNameRadiobutton) {
        this.subNameRadiobutton = subNameRadiobutton;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public void setSubNameMaps(String subNameMaps) {
        this.subNameMaps = subNameMaps;
    }

    public void setSubNameMapsRadiobutton(String subNameMapsRadiobutton) {
        this.subNameMapsRadiobutton = subNameMapsRadiobutton;
    }

    public void setSubNameFloorPlans(String subNameFloorPlans) {
        this.subNameFloorPlans = subNameFloorPlans;
    }

    public void setSubNameFloorPlansRadiobutton(String subNameFloorPlansRadiobutton) {
        this.subNameFloorPlansRadiobutton = subNameFloorPlansRadiobutton;
    }

    public void setAddressField01(String addressField01) {
        this.addressField01 = addressField01;
    }

    public void setAddressField02(String addressField02) {
        this.addressField02 = addressField02;
    }

    public void setAddressField03(String addressField03) {
        this.addressField03 = addressField03;
    }

    public void setAddressField04(String addressField04) {
        this.addressField04 = addressField04;
    }

    public void setAddressField05(String addressField05) {
        this.addressField05 = addressField05;
    }

    public void setFields(String fields) {
        this.fields = fields;
    }
}
