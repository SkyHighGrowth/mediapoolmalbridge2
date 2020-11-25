package MediaPoolMalBridge.service.Bridge.excelcreator.excelfilesserver6_5;

import MediaPoolMalBridge.model.MAL.MALAssetStructures;
import MediaPoolMalBridge.model.MAL.propertyvariants.MALPropertyVariant;
import MediaPoolMalBridge.persistence.entity.Bridge.AssetEntity;
import MediaPoolMalBridge.persistence.entity.Bridge.ReportsEntity;
import MediaPoolMalBridge.persistence.entity.MAL.MALPropertyEntity;
import MediaPoolMalBridge.persistence.entity.enums.ReportTo;
import MediaPoolMalBridge.persistence.entity.enums.ReportType;
import MediaPoolMalBridge.persistence.entity.enums.asset.TransferringAssetStatus;
import MediaPoolMalBridge.persistence.entity.enums.property.MALPropertyStatus;
import MediaPoolMalBridge.persistence.repository.MAL.MALPropertyRepository;
import MediaPoolMalBridge.service.Bridge.AbstractBridgeUniqueThreadService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

@Service
public class BridgeCreateExcelXSSFFileUniqueThreadService extends AbstractBridgeUniqueThreadService {

    protected static final String[] STRUCTURES_COLUMN_NAMES = {"List ID * (no editable)",
            "List name *", "Type * (no editable)", "ID of Parent list", "Name of default entry",
            "Affiliate type", "Show preview for list entry", "Attributes"};

    protected static final String[] OBJECTS_COLUMN_NAMES = {"List ID * (no editable)",
            "List Entry ID * (no editable)", "Display name *", "Name of parent entry",
            "State *", "Affiliate", "Attributes"};

    private static final Gson gsonWithNulls = new GsonBuilder().serializeNulls().create();

    private static final String ASSET_TYPE_ID = "2";

    private final MALAssetStructures assetStructures;

    private int rowIndex = 0;

    private static final String MEDIA = "MEDIA";

    private static final String DEFAULT_STRING = "{\"default\":\"%s\"}";

    private static final String RICHTEXT = "RICHTEXT";

    private static final String TEXT = "TEXT";

    @Autowired
    private MALPropertyRepository malPropertyRepository;

    public BridgeCreateExcelXSSFFileUniqueThreadService(final MALAssetStructures assetStructures) {
        this.assetStructures = assetStructures;
    }

    @Override
    protected void run() {
        try {
            writeToFile();
        } catch (IOException e) {
            logger.error(String.format("Error occurred while writing to file: %s", e.getMessage()));
        }
    }

    private void createFile(String fileName, LinkedHashSet<MALPropertyPair> allProperties, boolean isBrand) {
        try {
            final Workbook workbook = new XSSFWorkbook();
            createStructuresSheet(workbook);
            if (isBrand) {
                boolean hasRows = createBrandObjectsSheet(workbook, allProperties);
                if (!hasRows) {
                    return;
                }
            } else {
                createObjectsSheet(workbook, allProperties);
            }
            final OutputStream fileOutputStream = new FileOutputStream(new File(appConfig.getExcelDir() + fileName));
            workbook.write(fileOutputStream);
            fileOutputStream.close();
            workbook.close();
        } catch (final Exception e) {
            final String message = String.format("Can not create file [%s]", fileName);
            final ReportsEntity reportsEntity = new ReportsEntity(ReportType.ERROR, getClass().getName(), null, message, ReportTo.BM, null, null, null);
            reportsRepository.save(reportsEntity);
            logger.error(message, e);
        }
    }

    private void writeToFile() throws IOException {
        deleteOldFilesFromExcelDirectory();
        final List<MALPropertyVariant> propertyVariants = new ArrayList<>(assetStructures.getPropertyVariants().values());
        LinkedHashSet<MALPropertyPair> malPropertyPairSet = new LinkedHashSet<>();
        LinkedHashSet<MALPropertyPair> malPropertyBrandPairSet = new LinkedHashSet<>();
        int count = 0;
        int countProperties = 0;

        for (MALPropertyVariant propertyVariant : propertyVariants) {
            final String brandName = propertyVariant.getBrandName();
            final List<MALPropertyEntity> malPropertyEntities = malPropertyRepository.findByBrandAndMalPropertyStatus(brandName, MALPropertyStatus.OBSERVED);
            for (MALPropertyEntity malPropertyEntity : malPropertyEntities) {
                if (!propertyVariant.isBrandStructure() && countProperties == appConfig.getFileMaxRecords() && appConfig.getFileMaxRecords() > 0) {
                    count++;
                    String fileName = String.format("DataStructures_%s.xlsx", count);
                    createFile(fileName, malPropertyPairSet, false);
                    malPropertyPairSet.clear();
                    countProperties = 0;
                }
                if (propertyVariant.isBrandStructure()) {
                    MALPropertyPair malProperty = new MALPropertyPair(malPropertyEntity, propertyVariant);
                    malPropertyBrandPairSet.add(malProperty);
                } else {
                    MALPropertyPair malProperty = new MALPropertyPair(malPropertyEntity, propertyVariant);
                    malPropertyPairSet.add(malProperty);
                    countProperties++;
                }
            }
        }
        if (countProperties > 0) {
            count++;
            String fileName = String.format("DataStructures_%s.xlsx", count);
            createFile(fileName, malPropertyPairSet, false);
        }

        String fileName = "DataStructures_BRAND_IMAGES.xlsx";
        createFile(fileName, malPropertyBrandPairSet, true);
    }

    private void deleteOldFilesFromExcelDirectory() throws IOException {
        final File[] files = new File(appConfig.getExcelDir()).listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.exists()) {
                    Files.delete(file.toPath());
                }
            }
        }
    }

    private void createObjectsSheet(final Workbook workbook, LinkedHashSet<MALPropertyPair> malPropertyPairSet) {
        final Sheet sheet = workbook.createSheet("objects");

        final Font font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 16);
        final CellStyle headerFormat = workbook.createCellStyle();
        headerFormat.setFont(font);

        int colIndex = 0;
        rowIndex = 0;
        Row row = sheet.createRow(rowIndex++);
        for (final String column : OBJECTS_COLUMN_NAMES) {
            Cell cell = row.createCell(colIndex++);
            cell.setCellValue(column);
            cell.setCellStyle(headerFormat);
        }

        for (MALPropertyPair malProperty : malPropertyPairSet) {
            MALPropertyEntity malPropertyEntity = malProperty.getMalPropertyEntity();
            MALPropertyVariant propertyVariant = malProperty.getMalPropertyVariant();
            try {
                if (!propertyVariant.isBrandStructure()) {
                    final String[] combinedAddressFields = digestCombinedAddressField(propertyVariant, malPropertyEntity);
                    digestLogos(sheet, propertyVariant, malPropertyEntity, combinedAddressFields);

                    if (!StringUtils.isBlank(propertyVariant.getSubName())) {
                        digestAssets(sheet, propertyVariant, malPropertyEntity);
                    }

                    if (!StringUtils.isBlank(propertyVariant.getSubNameMaps())) {
                        digestMaps(sheet, propertyVariant, malPropertyEntity);
                    }

                    if (!StringUtils.isBlank(propertyVariant.getSubNameFloorPlans())) {
                        digestFloorTypes(sheet, propertyVariant, malPropertyEntity);
                    }
                }

            } catch (final Exception e) {
                final String message = String.format("Can not create Excel file for property variant [%s] with message [%s]", propertyVariant.getStructureName(), e.getMessage());
                final ReportsEntity reportsEntity = new ReportsEntity(ReportType.ERROR, getClass().getName(), null, message, ReportTo.BM, GSON.toJson(propertyVariant), null, null);
                reportsRepository.save(reportsEntity);
                logger.error(message, e);
            }
        }
    }

    private boolean createBrandObjectsSheet(final Workbook workbook, LinkedHashSet<MALPropertyPair> malPropertyPairSet) {
        final Sheet sheet = workbook.createSheet("objects");

        final Font font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 16);
        final CellStyle headerFormat = workbook.createCellStyle();
        headerFormat.setFont(font);

        int colIndex = 0;
        rowIndex = 0;
        Row row = sheet.createRow(rowIndex++);
        for (final String column : OBJECTS_COLUMN_NAMES) {
            Cell cell = row.createCell(colIndex++);
            cell.setCellValue(column);
            cell.setCellStyle(headerFormat);
        }

        boolean hasRows = false;
        List<String> structureNameAlreadyAdded = new ArrayList<>();
        for (MALPropertyPair malProperty : malPropertyPairSet) {
            MALPropertyVariant propertyVariant = malProperty.getMalPropertyVariant();
            try {
                if (propertyVariant.isBrandStructure()) {
                    final List<AssetEntity> assetEntities =
                            assetRepository.findAssetEntitiesByCollection(propertyVariant.getBrandId(), "1", "%Collections/" + propertyVariant.getCollection() + "%");
                    int order = 1;

                    if (assetEntities != null && !assetEntities.isEmpty() && (structureNameAlreadyAdded.isEmpty() || !structureNameAlreadyAdded.contains(propertyVariant.getStructureName()))) {
                        for (final AssetEntity assetEntity : assetEntities) {
                            final List<AttributeShort> attributes = new ArrayList<>();
                            attributes.add(new AttributeShort(order,
                                    "Image",
                                    getPropertyMedia(assetEntity.getBmAssetId()),
                                    MEDIA));
                            addRow(sheet, propertyVariant.getStructureName(), "", assetEntity.getCaption(), gsonWithNulls.toJson(attributes), assetEntity.getMalAssetId());
                            hasRows = true;
                        }
                        structureNameAlreadyAdded.add(propertyVariant.getStructureName());
                    }
                }

            } catch (final Exception e) {
                final String message = String.format("Can not create Excel file for property variant [%s] with message [%s]", propertyVariant.getStructureName(), e.getMessage());
                final ReportsEntity reportsEntity = new ReportsEntity(ReportType.ERROR, getClass().getName(), null, message, ReportTo.BM, GSON.toJson(propertyVariant), null, null);
                reportsRepository.save(reportsEntity);
                logger.error(message, e);
            }
        }

        return hasRows;
    }


    private void digestFloorTypes(final Sheet sheet, final MALPropertyVariant propertyVariant,
                                  final MALPropertyEntity malPropertyEntity) {
        final String[] floorTypes = new String[2];
        floorTypes[0] = "Fmt";
        floorTypes[1] = "Fgr";
        int order = 1;
        for (final String floorType : floorTypes) {
            final List<AssetEntity> assetEntities = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "13", floorType, TransferringAssetStatus.DONE);
            if (assetEntities != null && !assetEntities.isEmpty()) {
                for (final AssetEntity assetEntity : assetEntities) {
                    final List<AttributeShort> attributes = new ArrayList<>();
                    attributes.add(new AttributeShort(order,
                            "Floorplan" + order,
                            getPropertyMedia(assetEntity.getBmAssetId()),
                            MEDIA));
                    addRow(sheet, propertyVariant.getSubNameFloorPlans(), malPropertyEntity.getPropertyId(), assetEntity.getCaption(), gsonWithNulls.toJson(attributes), assetEntity.getMalAssetId());
                }
                ++order;
            }
        }
    }

    private void digestMaps(final Sheet sheet, final MALPropertyVariant propertyVariant,
                            final MALPropertyEntity malPropertyEntity) {
        final String[] mapColors = new String[3];
        mapColors[0] = "Mat";
        mapColors[1] = "Mla";
        mapColors[2] = "Mrm";
        int order = 1;
        for (final String mapColor : mapColors) {
            final List<AssetEntity> assetEntities = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "12", mapColor, TransferringAssetStatus.DONE);
            if (assetEntities != null && !assetEntities.isEmpty()) {
                for (final AssetEntity assetEntity : assetEntities) {
                    final List<AttributeShort> attributes = new ArrayList<>();
                    attributes.add(new AttributeShort(order,
                            "Map" + order,
                            getPropertyMedia(assetEntity.getBmAssetId()),
                            MEDIA));
                    addRow(sheet, propertyVariant.getSubNameMaps(), malPropertyEntity.getPropertyId(), assetEntity.getCaption(), gsonWithNulls.toJson(attributes), assetEntity.getMalAssetId());
                }
                ++order;
            }
        }
    }

    private void digestAssets(final Sheet sheet, final MALPropertyVariant propertyVariant,
                              final MALPropertyEntity malPropertyEntity) {
        final List<AssetEntity> assetEntities = assetRepository.findPropertyAssets(malPropertyEntity.getPropertyId(), "1", TransferringAssetStatus.DONE);
        int order = 1;
        if (assetEntities != null && !assetEntities.isEmpty()) {
            for (final AssetEntity assetEntity : assetEntities) {
                final List<AttributeShort> attributes = new ArrayList<>();
                attributes.add(new AttributeShort(order,
                        "Image" + order,
                        getPropertyMedia(assetEntity.getBmAssetId()),
                        MEDIA));
                String structureName = propertyVariant.getSubName();
                if (StringUtils.isBlank(structureName)) {
                    structureName = propertyVariant.getStructureName();
                }
                addRow(sheet, structureName, malPropertyEntity.getPropertyId(), assetEntity.getCaption(), gsonWithNulls.toJson(attributes), assetEntity.getMalAssetId());
            }
        }
    }

    private void addRow(final Sheet sheet, final String subName, final String propertyId, String name,
                        final String jsonedAttributes, final String malAssetId) {
        Row row = sheet.createRow(rowIndex++);
        int colIndex = 0;

        name = name.replace("\"", "");
        name = name.trim();
        if (StringUtils.isNotBlank(malAssetId)) {
            row.createCell(colIndex++).setCellValue(subName);
            row.createCell(colIndex++).setCellValue(malAssetId);
            row.createCell(colIndex++).setCellValue(String.format(DEFAULT_STRING, malAssetId + " - " + name));
            row.createCell(colIndex++).setCellValue(propertyId);
            row.createCell(colIndex++).setCellValue("EDIT_AND_ADD");
            row.createCell(colIndex++).setCellValue(malAssetId);
            row.createCell(colIndex++).setCellValue(jsonedAttributes);
        } else {
            row.createCell(colIndex++).setCellValue(subName);
            row.createCell(colIndex++).setCellValue(propertyId);
            row.createCell(colIndex++).setCellValue(String.format(DEFAULT_STRING, propertyId + " - " + name));
            row.createCell(colIndex++).setCellValue("");
            row.createCell(colIndex++).setCellValue("EDIT_AND_ADD");
            row.createCell(colIndex++).setCellValue(propertyId);
            row.createCell(colIndex++).setCellValue(jsonedAttributes);
        }
    }

    private void digestLogos(final Sheet sheet, MALPropertyVariant propertyVariant,
                             final MALPropertyEntity malPropertyEntity, final String[] combinedAddressFields) {
        final List<Attribute> attributes = new ArrayList<>();
        final String propertyVariantFields = propertyVariant.getFields();
        addLogoAttribute(malPropertyEntity, attributes, propertyVariantFields, PropertyVariantFields.PROPERTY_LOGO_1C, "ko", 24);
        addLogoAttribute(malPropertyEntity, attributes, propertyVariantFields, PropertyVariantFields.PROPERTY_LOGO_4C, "cmyk", 25);
        addLogoAttribute(malPropertyEntity, attributes, propertyVariantFields, PropertyVariantFields.PROPERTY_LOGO_4CB, "cmyk-B", 26);
        addLogoAttribute(malPropertyEntity, attributes, propertyVariantFields, PropertyVariantFields.PROPERTY_LOGO_1C_BLACK, "k", 27);
        addLogoAttribute(malPropertyEntity, attributes, propertyVariantFields, PropertyVariantFields.PROPERTY_LOGO_PMS, "pms", 28);
        addLogoAttribute(malPropertyEntity, attributes, propertyVariantFields, PropertyVariantFields.PROPERTY_LOGO_PMSC, "pms-C", 29);
        addLogoAttribute(malPropertyEntity, attributes, propertyVariantFields, PropertyVariantFields.PROPERTY_LOGO_4CC, "cmyk-C", 30);
        addLogoAttribute(malPropertyEntity, attributes, propertyVariantFields, PropertyVariantFields.PROPERTY_LOGO_KOD, "ko-D", 31);
        //this should be checked particularly
        addLogoAttribute(malPropertyEntity, attributes, propertyVariantFields, PropertyVariantFields.PROPERTY_LOGO_BLACKK, "k", 32);
        addLogoAttribute(malPropertyEntity, attributes, propertyVariantFields, PropertyVariantFields.PROPERTY_LOGO_4CK, "cmyk-K", 33);
        addLogoAttribute(malPropertyEntity, attributes, propertyVariantFields, PropertyVariantFields.PROPERTY_LOGO_SHERATON_BLACK, "Sheraton Black logo-1", 39);
        addLogoAttribute(malPropertyEntity, attributes, propertyVariantFields, PropertyVariantFields.PROPERTY_LOGO_SHERATON_DUSK, "dusk", 40);
        addLogoAttribute(malPropertyEntity, attributes, propertyVariantFields, PropertyVariantFields.PROPERTY_LOGO_SHERATON_GRAPHITE_CODED_CMYK, "Graphite_C_cmyk", 40);
        addLogoAttribute(malPropertyEntity, attributes, propertyVariantFields, PropertyVariantFields.PROPERTY_LOGO_SHERATON_GRAPHITE_CODED_PMS, "Graphite_C_pms", 42);
        addLogoAttribute(malPropertyEntity, attributes, propertyVariantFields, PropertyVariantFields.PROPERTY_LOGO_SHERATON_GRAPHITE_RGB, "Graphite_rgb", 43);
        addLogoAttribute(malPropertyEntity, attributes, propertyVariantFields, PropertyVariantFields.PROPERTY_LOGO_SHERATON_GRAPHITE_UNCOATED_CMYK, "Graphite_U_cmyk", 44);
        addLogoAttribute(malPropertyEntity, attributes, propertyVariantFields, PropertyVariantFields.PROPERTY_LOGO_SHERATON_GRAPHITE_UNCOATED_PMS, "Graphite_U_pms", 45);
        addLogoAttribute(malPropertyEntity, attributes, propertyVariantFields, PropertyVariantFields.PROPERTY_LOGO_SHERATON_IVORY, "ivory", 46);
        addLogoAttribute(malPropertyEntity, attributes, propertyVariantFields, PropertyVariantFields.PROPERTY_LOGO_SHERATON_KNOCKOUT, "Knockout", 47);
        addLogoAttribute(malPropertyEntity, attributes, propertyVariantFields, PropertyVariantFields.PROPERTY_LOGO_SHERATON_LOGO_SPECS, "Logo_Specs", 47);
        addLogoAttribute(malPropertyEntity, attributes, propertyVariantFields, PropertyVariantFields.PROPERTY_LOGO_SHERATON_OYSTER_COATED_CMYK, "Logo_Specs", 49);
        addLogoAttribute(malPropertyEntity, attributes, propertyVariantFields, PropertyVariantFields.PROPERTY_LOGO_SHERATON_OYSTER_COATED_PMS, "Oyster_C_pms", 50);
        addLogoAttribute(malPropertyEntity, attributes, propertyVariantFields, PropertyVariantFields.PROPERTY_LOGO_SHERATON_OYSTER_RGB, "Oyster_rgb", 51);
        addLogoAttribute(malPropertyEntity, attributes, propertyVariantFields, PropertyVariantFields.PROPERTY_LOGO_SHERATON_OYSTER_UNCOATED_CMYK, "Oyster_U_cmyk", 52);
        addLogoAttribute(malPropertyEntity, attributes, propertyVariantFields, PropertyVariantFields.PROPERTY_LOGO_SHERATON_OYSTER_UNCOATED_PMS, "Oyster_U_pms", 53);
        addLogoAttribute(malPropertyEntity, attributes, propertyVariantFields, PropertyVariantFields.PROPERTY_LOGO_SHERATON_RESORT_FRENCH_GRAY_COATED_CMYK, "FrenchGray_C_cmyk", 54);
        addLogoAttribute(malPropertyEntity, attributes, propertyVariantFields, PropertyVariantFields.PROPERTY_LOGO_SHERATON_RESORT_FRENCH_GRAY_COATED_PMS, "FrenchGray_C_pms", 55);
        addLogoAttribute(malPropertyEntity, attributes, propertyVariantFields, PropertyVariantFields.PROPERTY_LOGO_SHERATON_RESORT_FRENCH_GRAY_RGB, "FrenchGray_rgb", 56);
        addLogoAttribute(malPropertyEntity, attributes, propertyVariantFields, PropertyVariantFields.PROPERTY_LOGO_SHERATON_RESORT_FRENCH_GRAY_UNCOATED_CMYK, "FrenchGray_U_cmyk", 57);
        addLogoAttribute(malPropertyEntity, attributes, propertyVariantFields, PropertyVariantFields.PROPERTY_LOGO_SHERATON_RESORT_FRENCH_GRAY_UNCOATED_PMS, "FrenchGray_U_pms", 58);
        addLogoAttribute(malPropertyEntity, attributes, propertyVariantFields, PropertyVariantFields.PROPERTY_LOGO_SHERATON_TUNGSTEN_COATED_CMYK, "Tungsten_C_cmyk", 59);
        addLogoAttribute(malPropertyEntity, attributes, propertyVariantFields, PropertyVariantFields.PROPERTY_LOGO_SHERATON_TUNGSTEN_COATED_PMS, "Tungsten_C_pms", 60);
        addLogoAttribute(malPropertyEntity, attributes, propertyVariantFields, PropertyVariantFields.PROPERTY_LOGO_SHERATON_TUNGSTEN_RGB, "Tungsten_rgb", 61);
        addLogoAttribute(malPropertyEntity, attributes, propertyVariantFields, PropertyVariantFields.PROPERTY_LOGO_SHERATON_TUNGSTEN_UNCOATED_CMYK, "Tungsten_U_cmyk", 62);
        addLogoAttribute(malPropertyEntity, attributes, propertyVariantFields, PropertyVariantFields.PROPERTY_LOGO_SHERATON_TUNGSTEN_UNCOATED_PMS, "Tungsten_U_pms", 63);
        addLogoAttribute(malPropertyEntity, attributes, propertyVariantFields, PropertyVariantFields.PROPERTY_LOGO_US_NAVY, "usna", 64);

        addAttribute(attributes, propertyVariantFields, PropertyVariantFields.AFFILIATE_NAME, 1, TEXT, malPropertyEntity.getName());
        addAttribute(attributes, propertyVariantFields, PropertyVariantFields.AFFILIATES_CODE, 2, TEXT, malPropertyEntity.getPropertyId());
        addAttribute(attributes, propertyVariantFields, PropertyVariantFields.PROPERTY_STATE, 3, TEXT, malPropertyEntity.getState());
        addAttribute(attributes, propertyVariantFields, PropertyVariantFields.COMBINED_ADDRESS, 34, RICHTEXT, combinedAddressFields[0]);
        addAttribute(attributes, propertyVariantFields, PropertyVariantFields.COMBINED_ADDRESS_ADDRESS1, 35, RICHTEXT, combinedAddressFields[1]);
        addAttribute(attributes, propertyVariantFields, PropertyVariantFields.COMBINED_ADDRESS_ADDRESS1_BOLD, 36, RICHTEXT, combinedAddressFields[2]);
        addCombinedAddressAttribute(combinedAddressFields, attributes, propertyVariantFields);
        addAttribute(attributes, propertyVariantFields, PropertyVariantFields.ADDRESS, 4, TEXT, malPropertyEntity.getAddress());
        addAttribute(attributes, propertyVariantFields, PropertyVariantFields.STREET, 5, TEXT, malPropertyEntity.getAddress2());
        addAttribute(attributes, propertyVariantFields, PropertyVariantFields.ZIP, 6, TEXT, malPropertyEntity.getZip());
        addAttribute(attributes, propertyVariantFields, PropertyVariantFields.CITY, 7, TEXT, malPropertyEntity.getCity());
        addAttribute(attributes, propertyVariantFields, PropertyVariantFields.COUNTRY, 8, TEXT, malPropertyEntity.getCountry());
        addAttribute(attributes, propertyVariantFields, PropertyVariantFields.PROPERTY_URL, 9, TEXT, malPropertyEntity.getUrl());
        addAttribute(attributes, propertyVariantFields, PropertyVariantFields.PROPERTY_TELEPHONE, 10, TEXT, malPropertyEntity.getTelephone());
        addAttribute(attributes, propertyVariantFields, PropertyVariantFields.LATITUDE, 13, TEXT, malPropertyEntity.getLatitude());
        addAttribute(attributes, propertyVariantFields, PropertyVariantFields.LONGITUDE, 14, TEXT, malPropertyEntity.getLongitude());
        addRow(sheet, propertyVariant.getStructureName(), malPropertyEntity.getPropertyId(), malPropertyEntity.getName(), gsonWithNulls.toJson(attributes), null);
    }

    private void addCombinedAddressAttribute(String[] combinedAddressFields, List<Attribute> attributes, String propertyVariantFields) {
        if (propertyVariantFields.contains(PropertyVariantFields.COMBINED_ADDRESS_ADDRESS11ST_AND_2ND_LINE_BOLD)) {
            String result = null;
            if (combinedAddressFields[3] != null && combinedAddressFields[4] != null) {
                result = combinedAddressFields[3] + combinedAddressFields[4];
            } else if (combinedAddressFields[3] != null) {
                result = combinedAddressFields[3];
            } else if (combinedAddressFields[4] != null) {
                result = combinedAddressFields[4];
            }

            attributes.add(new Attribute(37,
                    PropertyVariantFields.COMBINED_ADDRESS_ADDRESS11ST_AND_2ND_LINE_BOLD,
                    RICHTEXT,
                    result));
        }
    }

    private void addAttribute(List<Attribute> attributes, String propertyVariantFields, String propertyVariantField, int orderNumber, String attributeType, String value) {
        if (propertyVariantFields.contains(propertyVariantField)) {
            attributes.add(new Attribute(orderNumber,
                    propertyVariantField,
                    attributeType,
                    value));
        }
    }

    private void addLogoAttribute(MALPropertyEntity malPropertyEntity, List<Attribute> attributes, String propertyVariantFields, String propertyVariantField, String colorId, int orderNumber) {
        List<AssetEntity> logo;
        if (propertyVariantFields.contains(propertyVariantField)) {
            logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), ASSET_TYPE_ID, colorId, TransferringAssetStatus.DONE);
            if (logo != null && !logo.isEmpty()) {
                String propertyLogo1c = getPropertyMedia(logo.get(0).getBmAssetId());
                attributes.add(new Attribute(orderNumber,
                        propertyVariantField,
                        MEDIA,
                        propertyLogo1c));
            }
        }
    }

    private String getPropertyMedia(String bmAssetId) {
        return "[MEDIA_GUID=" + bmAssetId + ";MEDIA_VERSION=0;]";
    }

    private String[] digestCombinedAddressField(final MALPropertyVariant propertyVariant,
                                                final MALPropertyEntity malPropertyEntity) {
        final String[] fields = propertyVariant.getFieldsArray();
        String addressField01 = propertyVariant.getAddressField01();
        String addressField02 = propertyVariant.getAddressField02();
        String addressField03 = propertyVariant.getAddressField03();
        String addressField04 = propertyVariant.getAddressField04();
        String addressField05 = propertyVariant.getAddressField05();
        for (String field : fields) {
            String replacement;
            field = field.trim();
            switch (field) {
                case "name":
                    replacement = malPropertyEntity.getBrand();
                    break;
                case "street":
                    replacement = malPropertyEntity.getAddress();
                    break;
                case "country":
                    replacement = malPropertyEntity.getCountry();
                    break;
                case "state":
                    replacement = malPropertyEntity.getState();
                    break;
                case "city":
                    replacement = malPropertyEntity.getCity();
                    break;
                case "zip":
                    replacement = malPropertyEntity.getZip();
                    break;
                case "telephone":
                    replacement = malPropertyEntity.getTelephone();
                    break;
                case "property url":
                    replacement = malPropertyEntity.getUrl();
                    break;
                default:
                    replacement = "";
            }

            if (replacement == null) {
                replacement = "";
            }
            if (StringUtils.isNotBlank(addressField01)) {
                addressField01 = addressField01.replace("{" + field + "}", replacement);
            }
            if (StringUtils.isNotBlank(addressField02)) {
                addressField02 = addressField02.replace("{" + field + "}", replacement);
            }
            if (StringUtils.isNotBlank(addressField03)) {
                addressField03 = addressField03.replace("{" + field + "}", replacement);
            }
            if (StringUtils.isNotBlank(addressField04)) {
                addressField04 = addressField04.replace("{" + field + "}", replacement);
            }
            if (StringUtils.isNotBlank(addressField05)) {
                addressField05 = addressField05.replace("{" + field + "}", replacement);
            }
        }
        final String[] addressFields = new String[5];
        addressFields[0] = addressField01;
        addressFields[1] = addressField02;
        addressFields[2] = addressField03;
        addressFields[3] = addressField04;
        addressFields[4] = addressField05;
        return addressFields;
    }


    private void createStructuresSheet(final Workbook workbook) {

        final Sheet sheet = workbook.createSheet("structures");

        final Font font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 16);
        final CellStyle headerFormat = workbook.createCellStyle();
        headerFormat.setFont(font);

        int colIndex = 0;
        int rowIndex = 0;
        Row row = sheet.createRow(rowIndex++);
        for (final String column : STRUCTURES_COLUMN_NAMES) {
            Cell cell = row.createCell(colIndex++);
            cell.setCellValue(column);
            cell.setCellStyle(headerFormat);
        }

        for (final MALPropertyVariant malPropertyVariant : assetStructures.getPropertyVariants().values()) {
            //parent
            row = sheet.createRow(rowIndex++);

            colIndex = 0;

            row.createCell(colIndex++).setCellValue(malPropertyVariant.getStructureName());

            row.createCell(colIndex++).setCellValue(String.format(DEFAULT_STRING, malPropertyVariant.getStructureName()));

            row.createCell(colIndex++).setCellValue("DEFAULT");

            colIndex++;

            colIndex++;

            row.createCell(colIndex++).setCellValue("MULTI_OBJECT_AFFILIATE_STRUCTURE");

            row.createCell(colIndex++).setCellValue(malPropertyVariant.getStructureNameRadiobutton());

            if (malPropertyVariant.isBrandStructure()) {
                row.createCell(colIndex++).setCellValue("[{\"number\": 24,\"name\": \"Image\",\"label\": \"Image\",\"comment\": \"\",\"order\": 0,\"type\": \"MEDIA\",\"props\": null}]");
            } else {
                row.createCell(colIndex++).setCellValue("[{\"number\":3,\"name\":\"PropertyState\",\"label\":\"Property State\",\"comment\":\"\",\"order\":-1,\"type\":\"TEXT\",\"props\":null},{\"number\":9,\"name\":\"PropertyURL\",\"label\":\"Property URL\",\"comment\":\"\",\"order\":-1,\"type\":\"TEXT\",\"props\":null},{\"number\":10,\"name\":\"PropertyTelephone\",\"label\":\"Property Telephone\",\"comment\":\"\",\"order\":-1,\"type\":\"TEXT\",\"props\":null},{\"number\":11,\"name\":\"PropertyFacsimile\",\"label\":\"Property Facsimile\",\"comment\":\"\",\"order\":-1,\"type\":\"TEXT\",\"props\":null},{\"number\":13,\"name\":\"Latitude\",\"label\":\"Latitude\",\"comment\":\"\",\"order\":-1,\"type\":\"TEXT\",\"props\":null},{\"number\":14,\"name\":\"Longitude\",\"label\":\"Longitude\",\"comment\":\"\",\"order\":-1,\"type\":\"TEXT\",\"props\":null},{\"number\":24,\"name\":\"PropertyLogo1c\",\"label\":\"Property Logo 1c\",\"comment\":\"\",\"order\":-1,\"type\":\"MEDIA\",\"props\":null},{\"number\":25,\"name\":\"PropertyLogo4c\",\"label\":\"Property Logo 4c\",\"comment\":\"\",\"order\":-1,\"type\":\"MEDIA\",\"props\":null},{\"number\":26,\"name\":\"PropertyLogo4cb\",\"label\":\"Property Logo 4c-b\",\"comment\":\"\",\"order\":-1,\"type\":\"MEDIA\",\"props\":null},{\"number\":27,\"name\":\"PropertyLogo1cblack\",\"label\":\"Property Logo 1c-black\",\"comment\":\"\",\"order\":-1,\"type\":\"MEDIA\",\"props\":null},{\"number\":28,\"name\":\"PropertyLogopms\",\"label\":\"Property Logo pms\",\"comment\":\"\",\"order\":-1,\"type\":\"MEDIA\",\"props\":null},{\"number\":29,\"name\":\"PropertyLogopmsc\",\"label\":\"Property Logo pms-c\",\"comment\":\"\",\"order\":-1,\"type\":\"MEDIA\",\"props\":null},{\"number\":30,\"name\":\"PropertyLogo4cc\",\"label\":\"Property Logo 4c-c\",\"comment\":\"\",\"order\":-1,\"type\":\"MEDIA\",\"props\":null},{\"number\":31,\"name\":\"PropertyLogokod\",\"label\":\"Property Logo ko-d\",\"comment\":\"\",\"order\":-1,\"type\":\"MEDIA\",\"props\":null},{\"number\":32,\"name\":\"PropertyLogoblackK\",\"label\":\"Property Logo black-K\",\"comment\":\"\",\"order\":-1,\"type\":\"MEDIA\",\"props\":null},{\"number\":33,\"name\":\"PropertyLogo4cK\",\"label\":\"Property Logo 4c-K\",\"comment\":\"\",\"order\":-1,\"type\":\"MEDIA\",\"props\":null},{\"number\":34,\"name\":\"CombinedAddress\",\"label\":\"Combined Address\",\"comment\":\"\",\"order\":-1,\"type\":\"RICHTEXT\",\"props\":null},{\"number\":39,\"name\":\"PropertyLogoSheratonBlack\",\"label\":\"Property Logo Sheraton Black\",\"comment\":\"\",\"order\":0,\"type\":\"MEDIA\",\"props\":null},{\"number\":40,\"name\":\"PropertyLogoSheratonDusk\",\"label\":\"Property Logo Sheraton Dusk\",\"comment\":\"\",\"order\":0,\"type\":\"MEDIA\",\"props\":null},{\"number\":41,\"name\":\"PropertyLogoSheratonGraphiteCodedCMYK\",\"label\":\"Property Logo Sheraton Graphite Coded CMYK\",\"comment\":\"\",\"order\":0,\"type\":\"MEDIA\",\"props\":null},{\"number\":42,\"name\":\"PropertyLogoSheratonGraphiteCodedPMS\",\"label\":\"Property Logo Sheraton Graphite Coded PMS\",\"comment\":\"\",\"order\":0,\"type\":\"MEDIA\",\"props\":null},{\"number\":43,\"name\":\"PropertyLogoSheratonGraphiteRGB\",\"label\":\"Property Logo Sheraton Graphite RGB\",\"comment\":\"\",\"order\":0,\"type\":\"MEDIA\",\"props\":null},{\"number\":44,\"name\":\"PropertyLogoSheratonGraphiteUncoatedCMYK\",\"label\":\"Property Logo Sheraton Graphite Uncoated CMYK\",\"comment\":\"\",\"order\":0,\"type\":\"MEDIA\",\"props\":null},{\"number\":45,\"name\":\"PropertyLogoSheratonGraphiteUncoatedPMS\",\"label\":\"Property Logo Sheraton Graphite Uncoated PMS\",\"comment\":\"\",\"order\":0,\"type\":\"MEDIA\",\"props\":null},{\"number\":46,\"name\":\"PropertyLogoSheratonIvory\",\"label\":\"Property Logo Sheraton Ivory\",\"comment\":\"\",\"order\":0,\"type\":\"MEDIA\",\"props\":null},{\"number\":47,\"name\":\"PropertyLogoSheratonKnockout\",\"label\":\"Property Logo Sheraton Knockout\",\"comment\":\"\",\"order\":0,\"type\":\"MEDIA\",\"props\":null},{\"number\":48,\"name\":\"PropertyLogoSheratonLogoSpecs\",\"label\":\"Property Logo Sheraton Logo Specs\",\"comment\":\"\",\"order\":0,\"type\":\"MEDIA\",\"props\":null},{\"number\":49,\"name\":\"PropertyLogoSheratonOysterCoatedCMYK\",\"label\":\"Property Logo Sheraton Oyster Coated CMYK\",\"comment\":\"\",\"order\":0,\"type\":\"MEDIA\",\"props\":null},{\"number\":50,\"name\":\"PropertyLogoSheratonOysterCoatedPMS\",\"label\":\"Property Logo Sheraton Oyster Coated PMS\",\"comment\":\"\",\"order\":0,\"type\":\"MEDIA\",\"props\":null},{\"number\":51,\"name\":\"PropertyLogoSheratonOysterRGB\",\"label\":\"Property Logo Sheraton Oyster RGB\",\"comment\":\"\",\"order\":0,\"type\":\"MEDIA\",\"props\":null},{\"number\":52,\"name\":\"PropertyLogoSheratonOysterUncoatedCMYK\",\"label\":\"Property Logo Sheraton Oyster Uncoated CMYK\",\"comment\":\"\",\"order\":0,\"type\":\"MEDIA\",\"props\":null},{\"number\":53,\"name\":\"PropertyLogoSheratonOysterUncoatedPMS\",\"label\":\"Property Logo Sheraton Oyster Uncoated PMS\",\"comment\":\"\",\"order\":0,\"type\":\"MEDIA\",\"props\":null},{\"number\":54,\"name\":\"PropertyLogoSheratonResortFrenchGrayCoatedCMYK\",\"label\":\"Property Logo Sheraton Resort French Gray Coated CMYK\",\"comment\":\"\",\"order\":0,\"type\":\"MEDIA\",\"props\":null},{\"number\":55,\"name\":\"PropertyLogoSheratonResortFrenchGrayCoatedPMS\",\"label\":\"Property Logo Sheraton Resort French Gray Coated PMS\",\"comment\":\"\",\"order\":0,\"type\":\"MEDIA\",\"props\":null},{\"number\":56,\"name\":\"PropertyLogoSheratonResortFrenchGrayRGB\",\"label\":\"Property Logo Sheraton Resort French Gray RGB\",\"comment\":\"\",\"order\":0,\"type\":\"MEDIA\",\"props\":null},{\"number\":57,\"name\":\"PropertyLogoSheratonResortFrenchGrayUncoatedCMYK\",\"label\":\"Property Logo Sheraton Resort French Gray Uncoated CMYK\",\"comment\":\"\",\"order\":0,\"type\":\"MEDIA\",\"props\":null},{\"number\":58,\"name\":\"PropertyLogoSheratonResortFrenchGrayUncoatedPMS\",\"label\":\"Property Logo Sheraton Resort French Gray Uncoated PMS\",\"comment\":\"\",\"order\":0,\"type\":\"MEDIA\",\"props\":null},{\"number\":59,\"name\":\"PropertyLogoSheratonTungstenCoatedCMYK\",\"label\":\"Property Logo Sheraton Tungsten Coated CMYK\",\"comment\":\"\",\"order\":0,\"type\":\"MEDIA\",\"props\":null},{\"number\":60,\"name\":\"PropertyLogoSheratonTungstenCoatedPMS\",\"label\":\"Property Logo Sheraton Tungsten Coated PMS\",\"comment\":\"\",\"order\":0,\"type\":\"MEDIA\",\"props\":null},{\"number\":61,\"name\":\"PropertyLogoSheratonTungstenRGB\",\"label\":\"Property Logo Sheraton Tungsten RGB\",\"comment\":\"\",\"order\":0,\"type\":\"MEDIA\",\"props\":null},{\"number\":62,\"name\":\"PropertyLogoSheratonTungstenUncoatedCMYK\",\"label\":\"Property Logo Sheraton Tungsten Uncoated CMYK\",\"comment\":\"\",\"order\":0,\"type\":\"MEDIA\",\"props\":null},{\"number\":63,\"name\":\"PropertyLogoSheratonTungstenUncoatedPMS\",\"label\":\"Property Logo Sheraton Tungsten Uncoated PMS\",\"comment\":\"\",\"order\":0,\"type\":\"MEDIA\",\"props\":null},{\"number\":64,\"name\":\"PropertyLogoUSNavy\",\"label\":\"Property Logo US Navy\",\"comment\":\"\",\"order\":0,\"type\":\"MEDIA\",\"props\":null},{\"number\":2,\"name\":\"AFFILIATES_CODE\",\"label\":\"Property Number\",\"comment\":\"\",\"order\":1,\"type\":\"TEXT\",\"props\":null},{\"number\":1,\"name\":\"AFFILIATE_NAME\",\"label\":\"Property Name\",\"comment\":\"\",\"order\":2,\"type\":\"TEXT\",\"props\":null},{\"number\":4,\"name\":\"ADDRESS\",\"label\":\"Property Address\",\"comment\":\"\",\"order\":4,\"type\":\"TEXT\",\"props\":null},{\"number\":5,\"name\":\"STREET\",\"label\":\"Property Street\",\"comment\":\"\",\"order\":5,\"type\":\"TEXT\",\"props\":null},{\"number\":6,\"name\":\"ZIP\",\"label\":\"Property Zip Code\",\"comment\":\"\",\"order\":6,\"type\":\"TEXT\",\"props\":null},{\"number\":7,\"name\":\"CITY\",\"label\":\"Property City\",\"comment\":\"\",\"order\":7,\"type\":\"TEXT\",\"props\":null},{\"number\":8,\"name\":\"COUNTRY\",\"label\":\"Property Country\",\"comment\":\"\",\"order\":8,\"type\":\"TEXT\",\"props\":null},{\"number\":35,\"name\":\"CombinedAddressAddress1\",\"label\":\"CombinedAddress - Address 1\",\"comment\":\"\",\"order\":-1,\"type\":\"RICHTEXT\",\"props\":null},\n" +
                        "{\"number\":36,\"name\":\"CombinedAddressAddress1Bold\",\"label\":\"CombinedAddress - Address 1 - Bold\",\"comment\":\"\",\"order\":-1,\"type\":\"RICHTEXT\",\"props\":null},\n" +
                        "{\"number\":37,\"name\":\"CombinedAddressAddress11stand2ndLineBold\",\"label\":\"CombinedAddress - Address 1 - 1st and 2nd Line Bold\",\"comment\":\"\",\"order\":-1,\"type\":\"RICHTEXT\",\"props\":null}]");

                // substructure floor plans
                rowIndex = createSubStructure(sheet, rowIndex, malPropertyVariant, malPropertyVariant.getSubNameFloorPlans(),
                        malPropertyVariant.getSubNameFloorPlansRadiobutton(), "[{\"number\":24,\"name\":\"Floorplan1\",\"label\":\"Floorplan 1\",\"comment\":\"\",\"order\":-1,\"type\":\"MEDIA\",\"props\":null},{\"number\":25,\"name\":\"Floorplan2\",\"label\":\"Floorplan 2\",\"comment\":\"\",\"order\":-1,\"type\":\"MEDIA\",\"props\":null},{\"number\":26,\"name\":\"Floorplan3\",\"label\":\"Floorplan 3\",\"comment\":\"\",\"order\":-1,\"type\":\"MEDIA\",\"props\":null},{\"number\":27,\"name\":\"Floorplan4\",\"label\":\"Floorplan 4\",\"comment\":\"\",\"order\":-1,\"type\":\"MEDIA\",\"props\":null},{\"number\":28,\"name\":\"Floorplan5\",\"label\":\"Floorplan 5\",\"comment\":\"\",\"order\":-1,\"type\":\"MEDIA\",\"props\":null}]");
                // substructure maps
                rowIndex = createSubStructure(sheet, rowIndex, malPropertyVariant, malPropertyVariant.getSubNameMaps(),
                        malPropertyVariant.getSubNameMapsRadiobutton(), "[{\"number\":24,\"name\":\"Map1\",\"label\":\"Map 1\",\"comment\":\"\",\"order\":-1,\"type\":\"MEDIA\",\"props\":null},{\"number\":25,\"name\":\"Map2\",\"label\":\"Map 2\",\"comment\":\"\",\"order\":-1,\"type\":\"MEDIA\",\"props\":null},{\"number\":26,\"name\":\"Map3\",\"label\":\"Map 3\",\"comment\":\"\",\"order\":-1,\"type\":\"MEDIA\",\"props\":null},{\"number\":27,\"name\":\"Map4\",\"label\":\"Map 4\",\"comment\":\"\",\"order\":-1,\"type\":\"MEDIA\",\"props\":null},{\"number\":28,\"name\":\"Map5\",\"label\":\"Map 5\",\"comment\":\"\",\"order\":-1,\"type\":\"MEDIA\",\"props\":null}]");
                // substructure images
                rowIndex = createSubStructure(sheet, rowIndex, malPropertyVariant, malPropertyVariant.getSubName(),
                        malPropertyVariant.getSubNameRadiobutton(), "[{\"number\":24,\"name\":\"Image1\",\"label\":\"Image 1\",\"comment\":\"\",\"order\":-1,\"type\":\"MEDIA\",\"props\":null},{\"number\":25,\"name\":\"Image2\",\"label\":\"Image 2\",\"comment\":\"\",\"order\":-1,\"type\":\"MEDIA\",\"props\":null},{\"number\":26,\"name\":\"Image3\",\"label\":\"Image 3\",\"comment\":\"\",\"order\":-1,\"type\":\"MEDIA\",\"props\":null},{\"number\":27,\"name\":\"Image4\",\"label\":\"Image 4\",\"comment\":\"\",\"order\":-1,\"type\":\"MEDIA\",\"props\":null},{\"number\":28,\"name\":\"Image5\",\"label\":\"Image 5\",\"comment\":\"\",\"order\":-1,\"type\":\"MEDIA\",\"props\":null}]");
            }
        }
    }

    private int createSubStructure(Sheet sheet, int rowIndex, MALPropertyVariant malPropertyVariant, String
            subStructureName, String radioButton, String s) {
        if (subStructureName != null) {
            Row row = sheet.createRow(rowIndex++);

            int colIndex = 0;

            row.createCell(colIndex++).setCellValue(subStructureName);

            row.createCell(colIndex++).setCellValue(String.format(DEFAULT_STRING, subStructureName));

            row.createCell(colIndex++).setCellValue("DEFAULT");

            row.createCell(colIndex++).setCellValue(malPropertyVariant.getStructureName());

            colIndex++;

            row.createCell(colIndex++).setCellValue("NON_AFFILIATE_STRUCTURE");

            row.createCell(colIndex++).setCellValue(radioButton);

            row.createCell(colIndex++).setCellValue(s);
        }
        return rowIndex;
    }

    public static class AttributeShort {
        private int number;

        private final String attributeName;

        private String value;

        private String type;

        public AttributeShort(final int number, final String attributeName, final String value, final String type) {
            this.number = number;
            this.attributeName = attributeName;
            this.value = value;
            this.type = type;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public String getAttributeName() {
            return attributeName;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

    public static class Attribute {
        private int number;

        private final String attributeName;

        private String label;

        private String comment;

        private int order;

        private String type;

        private Props props;

        private String value;


        public Attribute(final int number, final String attributeName, final String type, final String value) {
            this.number = number;
            this.attributeName = attributeName;
            this.type = type;
            this.value = value;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public String getAttributeName() {
            return attributeName;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public static class Props {

        private Integer ckeditor_id;

    }


}