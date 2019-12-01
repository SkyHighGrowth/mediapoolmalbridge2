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
    private static Map<Integer, MALPropertyVariant> propertyVariants = new HashMap<>();

    static {
        propertyVariants.put( 21, new MALPropertyVariant( "21",
                "AFFILIATES_V000_lux",
                "AFFILIATES_V000_lux_Images",
                "AFFILIATES_V000_lux_Maps",
                "AFFILIATES_V000_lux_Floorplans",
                null,
                null,
                null,
                null,
                null,
                ",property_id, property_id - name, , status, name, property_id, state, address_2, address, zip, city, country, url, telephone, brand, parent_brand, latitude, longitude, , , , , , , , , , , , , , , , , , , , Address_Block_1, Address_Block_2, Address_Block_3, Address_Block_4, Address_Block_5, property_id") );
        propertyVariants.put( 19, new MALPropertyVariant( "19",
                "AFFILIATES_V000_str",
                "AFFILIATES_V000_str_Images",
                "AFFILIATES_V000_str_Maps",
                "AFFILIATES_V000_str_Floorplans",
                null,
                null,
                null,
                null,
                null,
                ",property_id, property_id - name, , status, name, property_id, state, address_2, address, zip, city, country, url, telephone, brand, parent_brand, latitude, longitude, , , , , , , , , , , , , , , , , , , , Address_Block_1, Address_Block_2, Address_Block_3, Address_Block_4, Address_Block_5, property_id" ) );
        propertyVariants.put( 23, new MALPropertyVariant( "23",
                "AFFILIATES_V000_swh",
                "AFFILIATES_V000_swh_Images",
                "AFFILIATES_V000_swh_Maps",
                "AFFILIATES_V000_swh_Floorplans",
                null,
                null,
                null,
                null,
                null,
                ",property_id, property_id - name, , status, name, property_id, state, address_2, address, zip, city, country, url, telephone, brand, parent_brand, latitude, longitude, , , , , , , , , , , , , , , , , , , , Address_Block_1, Address_Block_2, Address_Block_3, Address_Block_4, Address_Block_5, property_id" ) );
        propertyVariants.put( 20, new MALPropertyVariant( "20",
                "AFFILIATES_V000_wes",
                "AFFILIATES_V000_wes_Images",
                "AFFILIATES_V000_wes_Maps",
                "AFFILIATES_V000_wes_Floorplans",
                null,
                null,
                null,
                null,
                null,
                ",property_id, property_id - name, , status, name, property_id, state, address_2, address, zip, city, country, url, telephone, brand, parent_brand, latitude, longitude, , , , , , , , , , , , , , , , , , , , Address_Block_1, Address_Block_2, Address_Block_3, Address_Block_4, Address_Block_5, property_id" ) );
        propertyVariants.put( 1, new MALPropertyVariant( "1",
                "AFFILIATES_V001_alf",
                "AFFILIATES_V001_Aloft_IMAGES",
                "AFFILIATES_V001_Aloft_Maps",
                "AFFILIATES_V001_Aloft_Floorplans",
                "<div class=\"stylea3bbb4c7d3c1f4e2729cbdffda563ed3\" data-bullet-color=\"\" data-bullet-symbol=\"\" data-class-old=\"Address_1\" data-quark-style=\"Address 1\" ext:qtip=\"Address 1\" title=\"Address 1\"><span class=\"stylee2aa17a81d0267572ee5f09591c3538b\" data-class-old=\"Bold\" data-quark-text-style=\"Bold\" ext:qtip=\"Bold\" title=\"Bold\">{name}</span><br />\n{address}, {city}, {state} {zip}</div>",
                "<div class=\"stylea3bbb4c7d3c1f4e2729cbdffda563ed3\" data-bullet-color=\"\" data-bullet-symbol=\"\" data-class-old=\"Address_1\" data-quark-style=\"Address 1\" ext:qtip=\"Address 1\" title=\"Address 1\"><span class=\"stylee2aa17a81d0267572ee5f09591c3538b\" data-class-old=\"Bold\" data-quark-text-style=\"Bold\" ext:qtip=\"Bold\" title=\"Bold\">{name}<br />{address}, {city}, {state} {zip}</span></div>",
                "<div>\r\n<div class=\"stylebc8e96d9c7afda5f01ffe44f7459e368\" data-bullet-color=\"\" data-bullet-symbol=\"\" data-class-old=\"Address_1_Big\" data-quark-style=\"Address 1 Big\" ext:qtip=\"Address 1 Big\" title=\"Address 1 Big\">{name}<br />\r\n{address}, {city}, {state} {zip}</div>\r\n</div>",
                "<div class=\"stylebc8e96d9c7afda5f01ffe44f7459e368\" data-bullet-color=\"\" data-bullet-symbol=\"\" data-class-old=\"Address_1_Big\" data-quark-style=\"Address 1 Big\" ext:qtip=\"Address 1 Big\" title=\"Address 1 Big\">{name}</div>\r\n\r\n<div class=\"stylee556ef31c1e0062389356069dbb8d77a\" data-bullet-color=\"\" data-bullet-symbol=\"\" data-class-old=\"Address_2_Big\" data-quark-style=\"Address 2 Big\" ext:qtip=\"Address 2 Big\" title=\"Address 2 Big\">{address}, {city}, {state} {zip}</div>",
                null,
                ",property_id, property_id - name, , status, name, property_id, state, address_2, address, zip, city, country, url, telephone, brand, parent_brand, latitude, longitude, , , , , , , , , , , , , , , , , , , , Address_Block_1, Address_Block_2, Address_Block_3, Address_Block_4, Address_Block_5, property_id" ) );
        propertyVariants.put( 10, new MALPropertyVariant( "10",
                "AFFILIATES_V001_elm",
                "AFFILIATES_V001_Element_Images",
                "AFFILIATES_V001_Element_Maps",
                "AFFILIATES_V001_Element_Floorplans",
                "<div class=\"stylea3bbb4c7d3c1f4e2729cbdffda563ed3\" data-bullet-color=\"\" data-bullet-symbol=\"\" data-class-old=\"Address_1\" data-quark-style=\"Address 1\" ext:qtip=\"Address 1\" title=\"Address 1\"><span class=\"stylee2aa17a81d0267572ee5f09591c3538b\" data-class-old=\"Bold\" data-quark-text-style=\"Bold\" ext:qtip=\"Bold\" title=\"Bold\">{name}</span><br />\r\n{address}, {city}, {state} {zip}</div>",
                "<div class=\"stylea3bbb4c7d3c1f4e2729cbdffda563ed3\" data-bullet-color=\"\" data-bullet-symbol=\"\" data-class-old=\"Address_1\" data-quark-style=\"Address 1\" ext:qtip=\"Address 1\" title=\"Address 1\"><span class=\"stylee2aa17a81d0267572ee5f09591c3538b\" data-class-old=\"Bold\" data-quark-text-style=\"Bold\" ext:qtip=\"Bold\" title=\"Bold\">{name}<br />\r\n{address}, {city}, {state} {zip}</span></div>",
                "<div>\r\n<div class=\"stylebc8e96d9c7afda5f01ffe44f7459e368\" data-bullet-color=\"\" data-bullet-symbol=\"\" data-class-old=\"Address_1_Big\" data-quark-style=\"Address 1 Big\" ext:qtip=\"Address 1 Big\" title=\"Address 1 Big\">{name}<br />\r\n{address}, {city}, {state} {zip}</div>\r\n</div>",
                "<div class=\"stylebc8e96d9c7afda5f01ffe44f7459e368\" data-bullet-color=\"\" data-bullet-symbol=\"\" data-class-old=\"Address_1_Big\" data-quark-style=\"Address 1 Big\" ext:qtip=\"Address 1 Big\" title=\"Address 1 Big\">{name}</div>\r\n\r\n<div class=\"stylee556ef31c1e0062389356069dbb8d77a\" data-bullet-color=\"\" data-bullet-symbol=\"\" data-class-old=\"Address_2_Big\" data-quark-style=\"Address 2 Big\" ext:qtip=\"Address 2 Big\" title=\"Address 2 Big\">{address}, {city}, {state} {zip}</div>",
                null,
                ",property_id, property_id - name, , status, name, property_id, state, address_2, address, zip, city, country, url, telephone, brand, parent_brand, latitude, longitude, , , , , , , , , , , , , , , , , , , , Address_Block_1, Address_Block_2, Address_Block_3, Address_Block_4, Address_Block_5, property_id" ) );
        propertyVariants.put( 13, new MALPropertyVariant( "13",
                "AFFILIATES_V001_fpt",
                "AFFILIATES_V001_Four_Points_Images",
                "AFFILIATES_V001_Four_Points_Maps",
                "AFFILIATES_V001_Four_Points_Floorplans",
                "<div class=\"stylea3bbb4c7d3c1f4e2729cbdffda563ed3\" data-bullet-color=\"\" data-bullet-symbol=\"\" data-class-old=\"Address_1\" data-quark-style=\"Address 1\" ext:qtip=\"Address 1\" title=\"Address 1\"><span class=\"stylee2aa17a81d0267572ee5f09591c3538b\" data-class-old=\"Bold\" data-quark-text-style=\"Bold\" ext:qtip=\"Bold\" title=\"Bold\">{name}</span><br />\r\n{address}, {city}, {state} {zip}</div>",
                "<div class=\"stylea3bbb4c7d3c1f4e2729cbdffda563ed3\" data-bullet-color=\"\" data-bullet-symbol=\"\" data-class-old=\"Address_1\" data-quark-style=\"Address 1\" ext:qtip=\"Address 1\" title=\"Address 1\"><span class=\"stylee2aa17a81d0267572ee5f09591c3538b\" data-class-old=\"Bold\" data-quark-text-style=\"Bold\" ext:qtip=\"Bold\" title=\"Bold\">{name}<br />\r\n{address}, {city}, {state} {zip}</span></div>",
                "<div>\r\n<div class=\"stylebc8e96d9c7afda5f01ffe44f7459e368\" data-bullet-color=\"\" data-bullet-symbol=\"\" data-class-old=\"Address_1_Big\" data-quark-style=\"Address 1 Big\" ext:qtip=\"Address 1 Big\" title=\"Address 1 Big\">{name}<br />\r\n{address}, {city}, {state} {zip}</div>\r\n</div>",
                "<div class=\"stylebc8e96d9c7afda5f01ffe44f7459e368\" data-bullet-color=\"\" data-bullet-symbol=\"\" data-class-old=\"Address_1_Big\" data-quark-style=\"Address 1 Big\" ext:qtip=\"Address 1 Big\" title=\"Address 1 Big\">{name}</div>\r\n\r\n<div class=\"stylee556ef31c1e0062389356069dbb8d77a\" data-bullet-color=\"\" data-bullet-symbol=\"\" data-class-old=\"Address_2_Big\" data-quark-style=\"Address 2 Big\" ext:qtip=\"Address 2 Big\" title=\"Address 2 Big\">{address}, {city}, {state} {zip}</div>",
                null,
                ",property_id, property_id - name, , status, name, property_id, state, address_2, address, zip, city, country, url, telephone, brand, parent_brand, latitude, longitude, , , , , , , , , , , , , , , , , , , , Address_Block_1, Address_Block_2, Address_Block_3, Address_Block_4, Address_Block_5, property_id" ) );
        propertyVariants.put( 11, new MALPropertyVariant( "11",
                "AFFILIATES_V001_she",
                "AFFILIATES_V001_Sheraton_Images",
                "AFFILIATES_V001_Sheraton_Maps",
                "AFFILIATES_V001_Sheraton_Floorplans",
                "<div class=\"stylea3bbb4c7d3c1f4e2729cbdffda563ed3\" data-bullet-color=\"\" data-bullet-symbol=\"\" data-class-old=\"Address_1\" data-quark-style=\"Address 1\" ext:qtip=\"Address 1\" title=\"Address 1\"><span class=\"stylee2aa17a81d0267572ee5f09591c3538b\" data-class-old=\"Bold\" data-quark-text-style=\"Bold\" ext:qtip=\"Bold\" title=\"Bold\">{name}</span><br />\r\n{address}, {city}, {state} {zip}</div>",
                "<div class=\"stylea3bbb4c7d3c1f4e2729cbdffda563ed3\" data-bullet-color=\"\" data-bullet-symbol=\"\" data-class-old=\"Address_1\" data-quark-style=\"Address 1\" ext:qtip=\"Address 1\" title=\"Address 1\"><span class=\"stylee2aa17a81d0267572ee5f09591c3538b\" data-class-old=\"Bold\" data-quark-text-style=\"Bold\" ext:qtip=\"Bold\" title=\"Bold\">{name}<br />\r\n{address}, {city}, {state} {zip}</span></div>",
                "<div>\r\n<div class=\"stylebc8e96d9c7afda5f01ffe44f7459e368\" data-bullet-color=\"\" data-bullet-symbol=\"\" data-class-old=\"Address_1_Big\" data-quark-style=\"Address 1 Big\" ext:qtip=\"Address 1 Big\" title=\"Address 1 Big\">{name}<br />\r\n{address}, {city}, {state} {zip}</div>\r\n</div>",
                "<div class=\"stylebc8e96d9c7afda5f01ffe44f7459e368\" data-bullet-color=\"\" data-bullet-symbol=\"\" data-class-old=\"Address_1_Big\" data-quark-style=\"Address 1 Big\" ext:qtip=\"Address 1 Big\" title=\"Address 1 Big\">{name}</div>\r\n\r\n<div class=\"stylee556ef31c1e0062389356069dbb8d77a\" data-bullet-color=\"\" data-bullet-symbol=\"\" data-class-old=\"Address_2_Big\" data-quark-style=\"Address 2 Big\" ext:qtip=\"Address 2 Big\" title=\"Address 2 Big\">{address}, {city}, {state} {zip}</div>",
                null,
                ",property_id, property_id - name, , status, name, property_id, state, address_2, address, zip, city, country, url, telephone, brand, parent_brand, latitude, longitude, , , , , , , , , , , , , , , , , , , , Address_Block_1, Address_Block_2, Address_Block_3, Address_Block_4, Address_Block_5, property_id" ) );
        propertyVariants.put( 2, new MALPropertyVariant( "2",
                "AFFILIATES_V002_alf",
                "AFFILIATES_V002_Aloft_Images",
                "AFFILIATES_V002_Aloft_Maps",
                "AFFILIATES_V002_Aloft_Floorplans",
                "<div class=\"stylea3bbb4c7d3c1f4e2729cbdffda563ed3\" data-bullet-color=\"\" data-bullet-symbol=\"\" data-class-old=\"Address_1\" data-quark-style=\"Address 1\" ext:qtip=\"Address 1\" title=\"Address 1\">{name}<br></div><div class=\"styleef0a6cb908e6b0b10fcd3307979503f6\" data-bullet-color=\"\" data-bullet-symbol=\"\" data-class-old=\"Address_2\" data-quark-style=\"Address 2\" ext:qtip=\"Address 2\" title=\"Address 2\">{city}, {state},&nbsp;{country}</div>",
                null,
                null,
                null,
                null,
                ",property_id, property_id - name, , status, name, property_id, state, address_2, address, zip, city, country, url, telephone, brand, parent_brand, latitude, longitude, , , , , , , , , , , , , , , , , , , , Address_Block_1, Address_Block_2, Address_Block_3, Address_Block_4, Address_Block_5, property_id" ) );
        propertyVariants.put( 12, new MALPropertyVariant( "12",
                "AFFILIATES_V002_fpt",
                "AFFILIATES_V002_Four_Points_Images",
                "AFFILIATES_V002_Four_Points_Maps",
                "AFFILIATES_V002_Four_Points_Floorplans",
                "<div class=\"stylea3bbb4c7d3c1f4e2729cbdffda563ed3\" data-bullet-color=\"\" data-bullet-symbol=\"\" data-class-old=\"Address_1\" data-quark-style=\"Address 1\" ext:qtip=\"Address 1\" title=\"Address 1\">{name}<br></div><div class=\"styleef0a6cb908e6b0b10fcd3307979503f6\" data-bullet-color=\"\" data-bullet-symbol=\"\" data-class-old=\"Address_2\" data-quark-style=\"Address 2\" ext:qtip=\"Address 2\" title=\"Address 2\">{city}, {state},&nbsp;{country}</div>",
                null,
                null,
                null,
                null,
                ",property_id, property_id - name, , status, name, property_id, state, address_2, address, zip, city, country, url, telephone, brand, parent_brand, latitude, longitude, , , , , , , , , , , , , , , , , , , , Address_Block_1, Address_Block_2, Address_Block_3, Address_Block_4, Address_Block_5, property_id" ) );
        propertyVariants.put( 3, new MALPropertyVariant( "3",
                "AFFILIATES_V003_alf",
                "AFFILIATES_V003_Aloft_IMAGES",
                "AFFILIATES_V003_Aloft_Maps",
                "AFFILIATES_V003_Aloft_Floorplans",
                "<div class=\"stylea3bbb4c7d3c1f4e2729cbdffda563ed3\" data-bullet-color=\"\" data-bullet-symbol=\"\" data-class-old=\"Address_1\" data-quark-style=\"Address 1\" ext:qtip=\"Address 1\" title=\"Address 1\"><span class=\"stylee2aa17a81d0267572ee5f09591c3538b\" data-class-old=\"Bold\" data-quark-text-style=\"Bold\" ext:qtip=\"Bold\" title=\"Bold\">{name}</span></div><div class=\"stylea3bbb4c7d3c1f4e2729cbdffda563ed3\" data-bullet-color=\"\" data-bullet-symbol=\"\" data-class-old=\"Address_1\" data-quark-style=\"Address 1\" ext:qtip=\"Address 1\" title=\"Address 1\">{address}<br></div><div class=\"stylea3bbb4c7d3c1f4e2729cbdffda563ed3\" data-bullet-color=\"\" data-bullet-symbol=\"\" data-class-old=\"Address_1\" data-quark-style=\"Address 1\" ext:qtip=\"Address 1\" title=\"Address 1\">{city}, {state} {zip}<br></div>",
                null,
                null,
                null,
                null,
                ",property_id, property_id - name, , status, name, property_id, state, address_2, address, zip, city, country, url, telephone, brand, parent_brand, latitude, longitude, , , , , , , , , , , , , , , , , , , , Address_Block_1, Address_Block_2, Address_Block_3, Address_Block_4, Address_Block_5, property_id" ) );
        propertyVariants.put( 25, new MALPropertyVariant( "25",
                "AFFILIATES_V003_she",
                "AFFILIATES_V003_she_Images",
                "AFFILIATES_V003_she_Maps",
                "AFFILIATES_V003_she_Floorplans",
                "<div class=\"stylea3bbb4c7d3c1f4e2729cbdffda563ed3\" data-bullet-color=\"\" data-bullet-symbol=\"\" data-class-old=\"Address_1\" data-quark-style=\"Address 1\" ext:qtip=\"Address 1\" title=\"Address 1\"><span class=\"stylee2aa17a81d0267572ee5f09591c3538b\" data-class-old=\"Bold\" data-quark-text-style=\"Bold\" ext:qtip=\"Bold\" title=\"Bold\">{name}</span></div><div class=\"stylea3bbb4c7d3c1f4e2729cbdffda563ed3\" data-bullet-color=\"\" data-bullet-symbol=\"\" data-class-old=\"Address_1\" data-quark-style=\"Address 1\" ext:qtip=\"Address 1\" title=\"Address 1\">{address}<br></div><div class=\"stylea3bbb4c7d3c1f4e2729cbdffda563ed3\" data-bullet-color=\"\" data-bullet-symbol=\"\" data-class-old=\"Address_1\" data-quark-style=\"Address 1\" ext:qtip=\"Address 1\" title=\"Address 1\">{city}, {state} {zip}<br></div>",
                null,
                null,
                null,
                null,
                ",property_id, property_id - name, , status, name, property_id, state, address_2, address, zip, city, country, url, telephone, brand, parent_brand, latitude, longitude, , , , , , , , , , , , , , , , , , , , Address_Block_1, Address_Block_2, Address_Block_3, Address_Block_4, Address_Block_5, property_id" ) );
        propertyVariants.put( 14, new MALPropertyVariant( "14",
                "AFFILIATES_V003_who",
                "AFFILIATES_V003_W_Hotels_Images",
                "AFFILIATES_V003_W_Hotels_Maps",
                "AFFILIATES_V003_W_Hotels_Floorplans",
                "<div class=\"stylea3bbb4c7d3c1f4e2729cbdffda563ed3\" data-bullet-color=\"\" data-bullet-symbol=\"\" data-class-old=\"Address_1\" data-quark-style=\"Address 1\" ext:qtip=\"Address 1\" title=\"Address 1\"><span class=\"stylee2aa17a81d0267572ee5f09591c3538b\" data-class-old=\"Bold\" data-quark-text-style=\"Bold\" ext:qtip=\"Bold\" title=\"Bold\">{name}</span></div><div class=\"stylea3bbb4c7d3c1f4e2729cbdffda563ed3\" data-bullet-color=\"\" data-bullet-symbol=\"\" data-class-old=\"Address_1\" data-quark-style=\"Address 1\" ext:qtip=\"Address 1\" title=\"Address 1\">{address}<br></div><div class=\"stylea3bbb4c7d3c1f4e2729cbdffda563ed3\" data-bullet-color=\"\" data-bullet-symbol=\"\" data-class-old=\"Address_1\" data-quark-style=\"Address 1\" ext:qtip=\"Address 1\" title=\"Address 1\">{city}, {state} {zip}<br></div>",
                null,
                null,
                null,
                null,
                ",property_id, property_id - name, , status, name, property_id, state, address_2, address, zip, city, country, url, telephone, brand, parent_brand, latitude, longitude, , , , , , , , , , , , , , , , , , , , Address_Block_1, Address_Block_2, Address_Block_3, Address_Block_4, Address_Block_5, property_id" ) );
        propertyVariants.put( 4, new MALPropertyVariant( "4",
                "AFFILIATES_V004_elm",
                "AFFILIATES_V004_Element_Images",
                "AFFILIATES_V004_Element_Maps",
                "AFFILIATES_V004_Element_Floorplans",
                "<div class=\"stylea3bbb4c7d3c1f4e2729cbdffda563ed3\" data-bullet-color=\"\" data-bullet-symbol=\"\" data-class-old=\"Address_1\" data-quark-style=\"Address 1\" ext:qtip=\"Address 1\" title=\"Address 1\"><span class=\"stylee2aa17a81d0267572ee5f09591c3538b\" data-class-old=\"Bold\" data-quark-text-style=\"Bold\" ext:qtip=\"Bold\" title=\"Bold\">{name},</span> {address}, {city}, {state} {zip}</div>",
                null,
                null,
                null,
                null,
                ",property_id, property_id - name, , status, name, property_id, state, address_2, address, zip, city, country, url, telephone, brand, parent_brand, latitude, longitude, , , , , , , , , , , , , , , , , , , , Address_Block_1, Address_Block_2, Address_Block_3, Address_Block_4, Address_Block_5, property_id" ) );
        propertyVariants.put( 5, new MALPropertyVariant( "5",
                "AFFILIATES_V005_elm",
                "AFFILIATES_V005_Element_Images",
                "AFFILIATES_V005_Element_Maps",
                "AFFILIATES_V005_Element_Floorplans",
                "<div class=\"stylea3bbb4c7d3c1f4e2729cbdffda563ed3\" data-bullet-color=\"\" data-bullet-symbol=\"\" data-class-old=\"Address_1\" data-quark-style=\"Address 1\" ext:qtip=\"Address 1\" title=\"Address 1\"><span class=\"stylee2aa17a81d0267572ee5f09591c3538b\" data-class-old=\"Bold\" data-quark-text-style=\"Bold\" ext:qtip=\"Bold\" title=\"Bold\">{name}</span><br></div><div class=\"stylea3bbb4c7d3c1f4e2729cbdffda563ed3\" data-bullet-color=\"\" data-bullet-symbol=\"\" data-class-old=\"Address_1\" data-quark-style=\"Address 1\" ext:qtip=\"Address 1\" title=\"Address 1\">{address}<br></div><div class=\"stylea3bbb4c7d3c1f4e2729cbdffda563ed3\" data-bullet-color=\"\" data-bullet-symbol=\"\" data-class-old=\"Address_1\" data-quark-style=\"Address 1\" ext:qtip=\"Address 1\" title=\"Address 1\">{city}, {state} {zip}<br></div>",
                null,
                null,
                null,
                null,
                ",property_id, property_id - name, , status, name, property_id, state, address_2, address, zip, city, country, url, telephone, brand, parent_brand, latitude, longitude, , , , , , , , , , , , , , , , , , , , Address_Block_1, Address_Block_2, Address_Block_3, Address_Block_4, Address_Block_5, property_id" ) );
        propertyVariants.put( 6, new MALPropertyVariant( "6",
                "AFFILIATES_V006_fpt",
                "AFFILIATES_V006_Four_Points_Images",
                "AFFILIATES_V006_Four_Points_Maps",
                "AFFILIATES_V006_Four_Points_Floorplans",
                "<div class=\"stylea3bbb4c7d3c1f4e2729cbdffda563ed3\" data-bullet-color=\"\" data-bullet-symbol=\"\" data-class-old=\"Address_1\" data-quark-style=\"Address 1\" ext:qtip=\"Address 1\" title=\"Address 1\">{name}</div><div class=\"styleef0a6cb908e6b0b10fcd3307979503f6\" data-bullet-color=\"\" data-bullet-symbol=\"\" data-class-old=\"Address_2\" data-quark-style=\"Address 2\" ext:qtip=\"Address 2\" title=\"Address 2\">{address}<br>{city}, {state} {zip}</div>",
                null,
                null,
                null,
                null,
                ",property_id, property_id - name, , status, name, property_id, state, address_2, address, zip, city, country, url, telephone, brand, parent_brand, latitude, longitude, , , , , , , , , , , , , , , , , , , , Address_Block_1, Address_Block_2, Address_Block_3, Address_Block_4, Address_Block_5, property_id" ) );
        propertyVariants.put( 33, new MALPropertyVariant( "33",
                "AFFILIATES_V006_she",
                "AFFILIATES_V006_she_Images",
                "AFFILIATES_V006_she_Maps",
                "AFFILIATES_V006_she_Floorplans",
                "<div class=\"stylea3bbb4c7d3c1f4e2729cbdffda563ed3\" data-bullet-color=\"\" data-bullet-symbol=\"\" data-class-old=\"Address_1\" data-quark-style=\"Address 1\" ext:qtip=\"Address 1\" title=\"Address 1\">{name}</div><div class=\"styleef0a6cb908e6b0b10fcd3307979503f6\" data-bullet-color=\"\" data-bullet-symbol=\"\" data-class-old=\"Address_2\" data-quark-style=\"Address 2\" ext:qtip=\"Address 2\" title=\"Address 2\">{address}<br>{city}, {state} {zip}<br>{country}</div>",
                null,
                null,
                null,
                null,
                ",property_id, property_id - name, , status, name, property_id, state, address_2, address, zip, city, country, url, telephone, brand, parent_brand, latitude, longitude, , , , , , , , , , , , , , , , , , , , Address_Block_1, Address_Block_2, Address_Block_3, Address_Block_4, Address_Block_5, property_id" ) );
        propertyVariants.put( 22, new MALPropertyVariant( "22",
                "AFFILIATES_V006_wes",
                "AFFILIATES_V006_wes_Images",
                "AFFILIATES_V006_wes_Maps",
                "AFFILIATES_V006_wes_Floorplans",
                "<div class=\"stylea3bbb4c7d3c1f4e2729cbdffda563ed3\" data-bullet-color=\"\" data-bullet-symbol=\"\" data-class-old=\"Address_1\" data-quark-style=\"Address 1\" ext:qtip=\"Address 1\" title=\"Address 1\">{name}</div><div class=\"styleef0a6cb908e6b0b10fcd3307979503f6\" data-bullet-color=\"\" data-bullet-symbol=\"\" data-class-old=\"Address_2\" data-quark-style=\"Address 2\" ext:qtip=\"Address 2\" title=\"Address 2\">{address}<br>{city}, {state} {zip}<br>{country}</div>",
                null,
                null,
                null,
                null,
                ",property_id, property_id - name, , status, name, property_id, state, address_2, address, zip, city, country, url, telephone, brand, parent_brand, latitude, longitude, , , , , , , , , , , , , , , , , , , , Address_Block_1, Address_Block_2, Address_Block_3, Address_Block_4, Address_Block_5, property_id" ) );
        propertyVariants.put( 7, new MALPropertyVariant( "7",
                "AFFILIATES_V007_elm",
                "AFFILIATES_V007_Element_Images",
                "AFFILIATES_V007_Element_Maps",
                "AFFILIATES_V007_Element_Floorplans",
                "<div class=\"stylea3bbb4c7d3c1f4e2729cbdffda563ed3\" data-bullet-color=\"\" data-bullet-symbol=\"\" data-class-old=\"Address_1\" data-quark-style=\"Address 1\" ext:qtip=\"Address 1\" title=\"Address 1\">{name}</div><div class=\"styleef0a6cb908e6b0b10fcd3307979503f6\" data-bullet-color=\"\" data-bullet-symbol=\"\" data-class-old=\"Address_2\" data-quark-style=\"Address 2\" ext:qtip=\"Address 2\" title=\"Address 2\">{address}, {city}, {state}<br></div>",
                null,
                null,
                null,
                null,
                ",property_id, property_id - name, , status, name, property_id, state, address_2, address, zip, city, country, url, telephone, brand, parent_brand, latitude, longitude, , , , , , , , , , , , , , , , , , , , Address_Block_1, Address_Block_2, Address_Block_3, Address_Block_4, Address_Block_5, property_id" ) );
        propertyVariants.put( 8, new MALPropertyVariant( "8",
                "AFFILIATES_V008_elm",
                "AFFILIATES_V008_Element_Images",
                "AFFILIATES_V008_Element_Maps",
                "AFFILIATES_V008_Element_Floorplans",
                "{address}    {city}, {state} {zip}",
                null,
                null,
                null,
                null,
                ",property_id, property_id - name, , status, name, property_id, state, address_2, address, zip, city, country, url, telephone, brand, parent_brand, latitude, longitude, , , , , , , , , , , , , , , , , , , , Address_Block_1, Address_Block_2, Address_Block_3, Address_Block_4, Address_Block_5, property_id" ) );
        propertyVariants.put( 9, new MALPropertyVariant( "9",
                "AFFILIATES_V009_elm",
                "AFFILIATES_V009_Element_Images",
                "AFFILIATES_V009_Element_Maps",
                "AFFILIATES_V009_Element_Floorplans",
                "{address}    {city}, {state} {zip}{name} • {address}, {city}, {state} {zip} • {telephone}",
                "<div class=\"stylea3bbb4c7d3c1f4e2729cbdffda563ed3\" data-bullet-color=\"\" data-bullet-symbol=\"\" data-class-old=\"Address_1\" data-quark-style=\"Address 1\" ext:qtip=\"Address 1\" title=\"Address 1\"><span class=\"stylee2aa17a81d0267572ee5f09591c3538b\" data-class-old=\"Bold\" data-quark-text-style=\"Bold\" ext:qtip=\"Bold\" title=\"Bold\">{name}</span><br />\r\n{address}, {city}, {state} {zip}</div>",
                "<div class=\"stylea3bbb4c7d3c1f4e2729cbdffda563ed3\" data-bullet-color=\"\" data-bullet-symbol=\"\" data-class-old=\"Address_1\" data-quark-style=\"Address 1\" ext:qtip=\"Address 1\" title=\"Address 1\"><span class=\"stylee2aa17a81d0267572ee5f09591c3538b\" data-class-old=\"Bold\" data-quark-text-style=\"Bold\" ext:qtip=\"Bold\" title=\"Bold\">{name}<br />\r\n{address}, {city}, {state} {zip}</span></div>",
                "<div>\n" +
                        "<div class=\"stylebc8e96d9c7afda5f01ffe44f7459e368\" data-bullet-color=\"\" data-bullet-symbol=\"\" data-class-old=\"Address_1_Big\" data-quark-style=\"Address 1 Big\" ext:qtip=\"Address 1 Big\" title=\"Address 1 Big\">{name}<br />\r\n{address}, {city}, {state} {zip}</div>\r\n</div>",
                "<div class=\"stylebc8e96d9c7afda5f01ffe44f7459e368\" data-bullet-color=\"\" data-bullet-symbol=\"\" data-class-old=\"Address_1_Big\" data-quark-style=\"Address 1 Big\" ext:qtip=\"Address 1 Big\" title=\"Address 1 Big\">{name}</div>\r\n\r\n<div class=\"stylee556ef31c1e0062389356069dbb8d77a\" data-bullet-color=\"\" data-bullet-symbol=\"\" data-class-old=\"Address_2_Big\" data-quark-style=\"Address 2 Big\" ext:qtip=\"Address 2 Big\" title=\"Address 2 Big\">{address}, {city}, {state} {zip}</div>",
                ",property_id, property_id - name, , status, name, property_id, state, address_2, address, zip, city, country, url, telephone, brand, parent_brand, latitude, longitude, , , , , , , , , , , , , , , , , , , , Address_Block_1, Address_Block_2, Address_Block_3, Address_Block_4, Address_Block_5, property_id" ) );
        propertyVariants.put( 15, new MALPropertyVariant( "15",
                "AFFILIATES_V010_alf",
                "AFFILIATES_V010_alf_Images",
                "AFFILIATES_V010_alf_Maps",
                "AFFILIATES_V010_alf_Floorplans",
                "<div class=\"stylea3bbb4c7d3c1f4e2729cbdffda563ed3\" data-bullet-color=\"\" data-bullet-symbol=\"\" data-class-old=\"Address_1\" data-quark-style=\"Address 1\" ext:qtip=\"Address 1\" title=\"Address 1\"><span class=\"stylee2aa17a81d0267572ee5f09591c3538b\" data-class-old=\"Bold\" data-quark-text-style=\"Bold\" ext:qtip=\"Bold\" title=\"Bold\">{name}</span></div>\r\n<div class=\"stylea3bbb4c7d3c1f4e2729cbdffda563ed3\" data-bullet-color=\"\" data-bullet-symbol=\"\" data-class-old=\"Address_1\" data-quark-style=\"Address 1\" ext:qtip=\"Address 1\" title=\"Address 1\">{address}, {city}, {state} {zip}</div>",
                null,
                null,
                null,
                null,
                ",property_id, property_id - name, , status, name, property_id, state, address_2, address, zip, city, country, url, telephone, brand, parent_brand, latitude, longitude, , , , , , , , , , , , , , , , , , , , Address_Block_1, Address_Block_2, Address_Block_3, Address_Block_4, Address_Block_5, property_id" ) );
        propertyVariants.put( 16, new MALPropertyVariant( "16",
                "AFFILIATES_V011_mer",
                "AFFILIATES_V011_mer_Images",
                "AFFILIATES_V011_mer_Maps",
                "AFFILIATES_V011_mer_Floorplans",
                "<div class=\"stylea3bbb4c7d3c1f4e2729cbdffda563ed3\" data-bullet-color=\"\" data-bullet-symbol=\"\" data-class-old=\"Address_1\" data-quark-style=\"Address 1\" ext:qtip=\"Address 1\" title=\"Address 1\">{name}</div>\r\n<div class=\"styleef0a6cb908e6b0b10fcd3307979503f6\" data-bullet-color=\"\" data-bullet-symbol=\"\" data-class-old=\"Address_2\" data-quark-style=\"Address 2\" ext:qtip=\"Address 2\" title=\"Address 2\">{address}<br />\r\n{state}, {city} {zip}<br />\r\n{country}<br />\r\nT {telephone}<br />\r\n{url}</div>",
                null,
                null,
                null,
                null,
                ",property_id, property_id - name, , status, name, property_id, state, address_2, address, zip, city, country, url, telephone, brand, parent_brand, latitude, longitude, , , , , , , , , , , , , , , , , , , , Address_Block_1, Address_Block_2, Address_Block_3, Address_Block_4, Address_Block_5, property_id" ) );
        propertyVariants.put( 17, new MALPropertyVariant( "17",
                "AFFILIATES_V012_she",
                "AFFILIATES_V012_she_Images",
                "AFFILIATES_V012_she_Maps",
                "AFFILIATES_V012_she_Floorplans",
                "<div class=\"stylea3bbb4c7d3c1f4e2729cbdffda563ed3\" data-bullet-color=\"\" data-bullet-symbol=\"\" data-class-old=\"Address_1\" data-quark-style=\"Address 1\" ext:qtip=\"Address 1\" title=\"Address 1\">{name}</div>\r\n<div class=\"styleef0a6cb908e6b0b10fcd3307979503f6\" data-bullet-color=\"\" data-bullet-symbol=\"\" data-class-old=\"Address_2\" data-quark-style=\"Address 2\" ext:qtip=\"Address 2\" title=\"Address 2\">{address}<br />\r\n{city} {zip}, {country}<br />\r\nT&nbsp;&nbsp;&nbsp;{telephone}<br />\r\n{url}</div>",
                null,
                null,
                null,
                null,
                ",property_id, property_id - name, , status, name, property_id, state, address_2, address, zip, city, country, url, telephone, brand, parent_brand, latitude, longitude, , , , , , , , , , , , , , , , , , , , Address_Block_1, Address_Block_2, Address_Block_3, Address_Block_4, Address_Block_5, property_id" ) );
        propertyVariants.put( 18, new MALPropertyVariant( "18",
                "AFFILIATES_V013_mer",
                "AFFILIATES_V013_mer_Images",
                "AFFILIATES_V013_mer_Maps",
                "AFFILIATES_V013_mer_Floorplans",
                "<div class=\"stylea3bbb4c7d3c1f4e2729cbdffda563ed3\" data-bullet-color=\"\" data-bullet-symbol=\"\" data-class-old=\"Address_1\" data-quark-style=\"Address 1\" ext:qtip=\"Address 1\" title=\"Address 1\">{name}</div>\r\n<div class=\"styleef0a6cb908e6b0b10fcd3307979503f6\" data-bullet-color=\"\" data-bullet-symbol=\"\" data-class-old=\"Address_2\" data-quark-style=\"Address 2\" ext:qtip=\"Address 2\" title=\"Address 2\">{address}<br />\r\n{state}<br />\r\n{city} {zip}<br />\r\n{country}<br />\r\n{telephone}<br />\r\n{url}</div>",
                null,
                null,
                null,
                null,
                ",property_id, property_id - name, , status, name, property_id, state, address_2, address, zip, city, country, url, telephone, brand, parent_brand, latitude, longitude, , , , , , , , , , , , , , , , , , , , Address_Block_1, Address_Block_2, Address_Block_3, Address_Block_4, Address_Block_5, property_id" ) );
        propertyVariants.put( 24, new MALPropertyVariant( "24",
                "AFFILIATES_V015_lux",
                "AFFILIATES_V015_lux_Images",
                "AFFILIATES_V015_lux_Maps",
                "AFFILIATES_V015_lux_Floorplans",
                "{name}<br>{address}, {city}, {state} {zip} {country}",
                null,
                null,
                null,
                null,
                ",property_id, property_id - name, , status, name, property_id, state, address_2, address, zip, city, country, url, telephone, brand, parent_brand, latitude, longitude, , , , , , , , , , , , , , , , , , , , Address_Block_1, Address_Block_2, Address_Block_3, Address_Block_4, Address_Block_5, property_id" ) );
        propertyVariants.put( 26, new MALPropertyVariant( "26",
                "AFFILIATES_V016_lux",
                "AFFILIATES_V016_lux_Images",
                "AFFILIATES_V016_lux_Maps",
                "AFFILIATES_V016_lux_Floorplans",
                "{name}<br>{address}<br>{city}, {state} {zip} {country}",
                null,
                null,
                null,
                null,
                ",property_id, property_id - name, , status, name, property_id, state, address_2, address, zip, city, country, url, telephone, brand, parent_brand, latitude, longitude, , , , , , , , , , , , , , , , , , , , Address_Block_1, Address_Block_2, Address_Block_3, Address_Block_4, Address_Block_5, property_id" ) );
        propertyVariants.put( 27, new MALPropertyVariant( "27",
                "AFFILIATES_V017_fpt",
                "AFFILIATES_V017_Four_Points_Images",
                "AFFILIATES_V017_Four_Points_Maps",
                "AFFILIATES_V017_Four_Points_Floorplans",
                "<div class=\"stylea3bbb4c7d3c1f4e2729cbdffda563ed3\" data-bullet-color=\"\" data-bullet-symbol=\"\" data-class-old=\"Address_1\" data-quark-style=\"Address 1\" ext:qtip=\"Address 1\" title=\"Address 1\"><span class=\"stylee2aa17a81d0267572ee5f09591c3538b\" data-class-old=\"Bold\" data-quark-text-style=\"Bold\" ext:qtip=\"Bold\" title=\"Bold\">{name}</span></div>\r\n<div class=\"stylea3bbb4c7d3c1f4e2729cbdffda563ed3\" data-bullet-color=\"\" data-bullet-symbol=\"\" data-class-old=\"Address_1\" data-quark-style=\"Address 1\" ext:qtip=\"Address 1\" title=\"Address 1\">{address}, {city}, {state} {zip} {country}</div>",
                null,
                null,
                null,
                null,
                ",property_id, property_id - name, , status, name, property_id, state, address_2, address, zip, city, country, url, telephone, brand, parent_brand, latitude, longitude, , , , , , , , , , , , , , , , , , , , Address_Block_1, Address_Block_2, Address_Block_3, Address_Block_4, Address_Block_5, property_id" ) );
        propertyVariants.put( 28, new MALPropertyVariant( "28",
                "AFFILIATES_V018_fpt",
                "AFFILIATES_V018_Four_Points_Images",
                "AFFILIATES_V018_Four_Points_Maps",
                "AFFILIATES_V018_Four_Points_Floorplans",
                "{address}, {city}, {state} {zip} {country}",
                null,
                null,
                null,
                null,
                ",property_id, property_id - name, , status, name, property_id, state, address_2, address, zip, city, country, url, telephone, brand, parent_brand, latitude, longitude, , , , , , , , , , , , , , , , , , , , Address_Block_1, Address_Block_2, Address_Block_3, Address_Block_4, Address_Block_5, property_id" ) );
        propertyVariants.put( 29, new MALPropertyVariant( "29",
                "AFFILIATES_V019_str",
                "AFFILIATES_V019_str_Images",
                "AFFILIATES_V019_str_Maps",
                "AFFILIATES_V019_str_Floorplans",
                "{name}<br>{address}, {city}, {state} {zip}<br>{telephone}<br>{url}",
                null,
                null,
                null,
                null,
                ",property_id, property_id - name, , status, name, property_id, state, address_2, address, zip, city, country, url, telephone, brand, parent_brand, latitude, longitude, , , , , , , , , , , , , , , , , , , , Address_Block_1, Address_Block_2, Address_Block_3, Address_Block_4, Address_Block_5, property_id" ) );
        propertyVariants.put( 30, new MALPropertyVariant( "30",
                "AFFILIATES_V020_str",
                "AFFILIATES_V020_str_Images",
                "AFFILIATES_V020_str_Maps",
                "AFFILIATES_V020_str_Floorplans",
                "{name}<br>{address}<br>{city}, {state} {zip}",
                null,
                null,
                null,
                null,
                ",property_id, property_id - name, , status, name, property_id, state, address_2, address, zip, city, country, url, telephone, brand, parent_brand, latitude, longitude, , , , , , , , , , , , , , , , , , , , Address_Block_1, Address_Block_2, Address_Block_3, Address_Block_4, Address_Block_5, property_id" ) );
        propertyVariants.put( 31, new MALPropertyVariant( "31",
                "AFFILIATES_V021_who",
                "AFFILIATES_V021_who_Images",
                "AFFILIATES_V021_who_Maps",
                "AFFILIATES_V021_who_Floorplans",
                "<div class=\"stylea3bbb4c7d3c1f4e2729cbdffda563ed3\" data-bullet-color=\"\" data-bullet-symbol=\"\" data-class-old=\"Address_1\" data-quark-style=\"Address 1\" ext:qtip=\"Address 1\" title=\"Address 1\"><span class=\"stylee2aa17a81d0267572ee5f09591c3538b\" data-class-old=\"Bold\" data-quark-text-style=\"Bold\" ext:qtip=\"Bold\" title=\"Bold\">{name}</span></div><div class=\"stylea3bbb4c7d3c1f4e2729cbdffda563ed3\" data-bullet-color=\"\" data-bullet-symbol=\"\" data-class-old=\"Address_1\" data-quark-style=\"Address 1\" ext:qtip=\"Address 1\" title=\"Address 1\">{address}<br></div><div class=\"stylea3bbb4c7d3c1f4e2729cbdffda563ed3\" data-bullet-color=\"\" data-bullet-symbol=\"\" data-class-old=\"Address_1\" data-quark-style=\"Address 1\" ext:qtip=\"Address 1\" title=\"Address 1\">{city}, {state}, {zip}<br></div><div class=\"stylea3bbb4c7d3c1f4e2729cbdffda563ed3\" data-bullet-color=\"\" data-bullet-symbol=\"\" data-class-old=\"Address_1\" data-quark-style=\"Address 1\" ext:qtip=\"Address 1\" title=\"Address 1\">{country}<br></div><div class=\"stylea3bbb4c7d3c1f4e2729cbdffda563ed3\" data-bullet-color=\"\" data-bullet-symbol=\"\" data-class-old=\"Address_1\" data-quark-style=\"Address 1\" ext:qtip=\"Address 1\" title=\"Address 1\">{url}<br></div><div class=\"stylea3bbb4c7d3c1f4e2729cbdffda563ed3\" data-bullet-color=\"\" data-bullet-symbol=\"\" data-class-old=\"Address_1\" data-quark-style=\"Address 1\" ext:qtip=\"Address 1\" title=\"Address 1\">{telephone}</div>",
                null,
                null,
                null,
                null,
                ",property_id, property_id - name, , status, name, property_id, state, address_2, address, zip, city, country, url, telephone, brand, parent_brand, latitude, longitude, , , , , , , , , , , , , , , , , , , , Address_Block_1, Address_Block_2, Address_Block_3, Address_Block_4, Address_Block_5, property_id" ) );
        propertyVariants.put( 32, new MALPropertyVariant( "32",
                "AFFILIATES_V022_str",
                "AFFILIATES_V022_str_Images",
                "AFFILIATES_V022_str_Maps",
                "AFFILIATES_V022_str_Floorplans",
                "{address}, {city}, {state} {zip} {country} {url}",
                null,
                null,
                null,
                null,
                ",property_id, property_id - name, , status, name, property_id, state, address_2, address, zip, city, country, url, telephone, brand, parent_brand, latitude, longitude, , , , , , , , , , , , , , , , , , , , Address_Block_1, Address_Block_2, Address_Block_3, Address_Block_4, Address_Block_5, property_id" ) );
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

    public static Map<Integer, MALPropertyVariant> getPropertyVariants() {
        return propertyVariants;
    }

    public static void setPropertyVariants(Map<Integer, MALPropertyVariant> propertyVariants) {
        MALAssetStructures.propertyVariants = propertyVariants;
    }
}
