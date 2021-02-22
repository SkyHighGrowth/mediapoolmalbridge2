package com.brandmaker.mediapoolmalbridge.service.bridge.excelcreator.excelfilesserver6_5;

import com.brandmaker.mediapoolmalbridge.config.AppConfigData;
import com.brandmaker.mediapoolmalbridge.model.brandmaker.asset.BMAsset;
import com.brandmaker.mediapoolmalbridge.model.mal.MALAssetStructures;
import com.brandmaker.mediapoolmalbridge.model.mal.propertyvariants.MALPropertyVariant;
import com.brandmaker.mediapoolmalbridge.persistence.entity.bridge.AssetEntity;
import com.brandmaker.mediapoolmalbridge.persistence.entity.bridge.ReportsEntity;
import com.brandmaker.mediapoolmalbridge.persistence.entity.enums.ReportTo;
import com.brandmaker.mediapoolmalbridge.persistence.entity.enums.ReportType;
import com.brandmaker.mediapoolmalbridge.persistence.entity.enums.StructureType;
import com.brandmaker.mediapoolmalbridge.persistence.entity.enums.asset.TransferringAssetStatus;
import com.brandmaker.mediapoolmalbridge.persistence.entity.enums.property.MALPropertyStatus;
import com.brandmaker.mediapoolmalbridge.persistence.entity.mal.MALPropertyEntity;
import com.brandmaker.mediapoolmalbridge.persistence.repository.mal.MALPropertyRepository;
import com.brandmaker.mediapoolmalbridge.service.StructuresService;
import com.brandmaker.mediapoolmalbridge.service.brandmaker.AssetRestService;
import com.brandmaker.mediapoolmalbridge.service.brandmaker.ThemeRestService;
import com.brandmaker.mediapoolmalbridge.service.bridge.AbstractBridgeUniqueThreadService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.*;

@Service
public class BridgeCreateExcelXSSFFileUniqueThreadService extends AbstractBridgeUniqueThreadService {

    protected static final String[] STRUCTURES_COLUMN_NAMES = {"List ID * (no editable)",
            "List name *", "Type * (no editable)", "ID of Parent list", "Name of default entry",
            "Affiliate type", "Show preview for list entry", "Attributes"};

    protected static final String[] OBJECTS_COLUMN_NAMES = {"List ID * (no editable)",
            "List Entry ID * (no editable)", "Display name *", "Name of parent entry",
            "State *", "Affiliate", "Attributes"};

    private static final Gson gsonWithNulls = new GsonBuilder().serializeNulls().create();

    private final MALAssetStructures assetStructures;

    private int rowIndex = 0;

    private static final String MEDIA = "MEDIA";

    private static final String DEFAULT_STRING = "{\"default\":\"%s\"}";

    private static final String RICHTEXT = "RICHTEXT";

    private static final String TEXT = "TEXT";

    private final MALPropertyRepository malPropertyRepository;

    private final AssetRestService assetRestService;

    private final ThemeRestService themeRestService;

    private final StructuresService structuresService;


    public BridgeCreateExcelXSSFFileUniqueThreadService(final MALAssetStructures assetStructures,
                                                        MALPropertyRepository malPropertyRepository,
                                                        AssetRestService assetRestService,
                                                        ThemeRestService themeRestService,
                                                        final StructuresService structuresService) {
        this.assetStructures = assetStructures;
        this.malPropertyRepository = malPropertyRepository;
        this.assetRestService = assetRestService;
        this.themeRestService = themeRestService;
        this.structuresService = structuresService;
    }


    @Override
    protected void run() {
        try {
            writeToFile();
        } catch (IOException e) {
            logger.error(String.format("Error occurred while writing to file: %s", e.getMessage()));
        }
    }

    private void createFile(String fileName, LinkedHashSet<MALPropertyPair> allProperties, boolean isBrand, Map<String, List<BMAsset>> colorsMap) {
        try {
            final Workbook workbook = new XSSFWorkbook();
            createStructuresSheet(workbook);
            if (isBrand) {
                boolean hasRows = createBrandObjectsSheet(workbook, allProperties);
                if (!hasRows) {
                    return;
                }
            } else {
                createObjectsSheet(workbook, allProperties, colorsMap);
            }
            final OutputStream fileOutputStream = new FileOutputStream(appConfig.getExcelDir() + fileName);
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
        Map<String, List<BMAsset>> colorsMap = getColorsMap();
        int count = 0;
        int countProperties = 0;

        AppConfigData appConfigData = appConfig.getAppConfigData();
        for (MALPropertyVariant propertyVariant : propertyVariants) {
            final String brandName = propertyVariant.getBrandName();
            final List<MALPropertyEntity> malPropertyEntities = malPropertyRepository.findByBrandAndMalPropertyStatus(brandName, MALPropertyStatus.OBSERVED);
            for (MALPropertyEntity malPropertyEntity : malPropertyEntities) {
                if (!propertyVariant.isBrandStructure() && countProperties == appConfigData.getFileMaxRecords() && appConfigData.getFileMaxRecords() > 0) {
                    count++;
                    String fileName = String.format("DataStructures_%s.xlsx", count);
                    createFile(fileName, malPropertyPairSet, false, colorsMap);
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
            createFile(fileName, malPropertyPairSet, false, colorsMap);
        }

        String fileName = "DataStructures_BRAND_IMAGES.xlsx";
        createFile(fileName, malPropertyBrandPairSet, true, colorsMap);
    }

    private HashMap<String, List<BMAsset>> getColorsMap() {
        HashMap<String, List<BMAsset>> map = new HashMap<>();
        for (PropertyVariantFields propertyVariantField : PropertyVariantFields.values()) {
            if (!propertyVariantField.getColorId().equals("")) {
                String colorId = propertyVariantField.getColorId();
                String colorName = structuresService.getStructureByStructureIdAndStructureType(colorId, StructureType.COLOR).getStructureName();
                String bmColorThemeId = themeRestService.getColorIdByName(colorName);
                if (bmColorThemeId != null) {
                    List<BMAsset> assetIdsByThemeIdAndPropertyId = assetRestService.getAssetIdsByThemeIdAndPropertyId(bmColorThemeId);
                    map.put(colorId, assetIdsByThemeIdAndPropertyId);
                } else {
                    map.put(colorId, null);
                }
            }
        }
        return map;
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

    private void createObjectsSheet(final Workbook workbook, LinkedHashSet<MALPropertyPair> malPropertyPairSet, Map<String, List<BMAsset>> colorsMap) {
        final Sheet sheet = getObjectsHeader(workbook);

        for (MALPropertyPair malProperty : malPropertyPairSet) {
            MALPropertyEntity malPropertyEntity = malProperty.getMalPropertyEntity();
            MALPropertyVariant propertyVariant = malProperty.getMalPropertyVariant();
            try {
                if (!propertyVariant.isBrandStructure()) {
                    final String[] combinedAddressFields = digestCombinedAddressField(propertyVariant, malPropertyEntity);
                    digestLogos(sheet, propertyVariant, malPropertyEntity, combinedAddressFields, colorsMap);

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
                saveReports(propertyVariant, e);
            }
        }
    }

    private Sheet getObjectsHeader(Workbook workbook) {
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
        return sheet;
    }

    private void saveReports(MALPropertyVariant propertyVariant, Exception e) {
        final String message = String.format("Can not create Excel file for property variant [%s] with message [%s]", propertyVariant.getStructureName(), e.getMessage());
        final ReportsEntity reportsEntity = new ReportsEntity(ReportType.ERROR, getClass().getName(), null, message, ReportTo.BM, GSON.toJson(propertyVariant), null, null);
        reportsRepository.save(reportsEntity);
        logger.error(message, e);
    }

    private boolean createBrandObjectsSheet(final Workbook workbook, LinkedHashSet<MALPropertyPair> malPropertyPairSet) {
        final Sheet sheet = getObjectsHeader(workbook);

        boolean hasRows = false;
        List<String> structureNameAlreadyAdded = new ArrayList<>();
        for (MALPropertyPair malProperty : malPropertyPairSet) {
            MALPropertyVariant propertyVariant = malProperty.getMalPropertyVariant();
            try {
                if (propertyVariant.isBrandStructure()) {
                    final List<AssetEntity> assetEntities =
                            assetRepository.findAssetEntitiesByCollection(propertyVariant.getBrandId(), "1", "%Collections/" + propertyVariant.getCollectionName() + "%");
                    int order = 1;

                    if (assetEntities != null && !assetEntities.isEmpty() && (structureNameAlreadyAdded.isEmpty() || !structureNameAlreadyAdded.contains(propertyVariant.getStructureName()))) {
                        for (final AssetEntity assetEntity : assetEntities) {
                            final List<AttributeShort> attributes = new ArrayList<>();
                            attributes.add(new AttributeShort(order,
                                    "Image",
                                    getPropertyMedia(assetEntity.getBmAssetId()),
                                    MEDIA));
                            addRow(sheet, propertyVariant.getStructureName(), "", assetEntity.getCaption(), gsonWithNulls.toJson(attributes), assetEntity.getMalAssetId(), assetEntity.getMarshaCode());
                            hasRows = true;
                        }
                        structureNameAlreadyAdded.add(propertyVariant.getStructureName());
                    }
                }

            } catch (final Exception e) {
                saveReports(propertyVariant, e);
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
                    addRow(sheet, propertyVariant.getSubNameFloorPlans(), malPropertyEntity.getPropertyId(), assetEntity.getCaption(), gsonWithNulls.toJson(attributes), assetEntity.getMalAssetId(), assetEntity.getMarshaCode());
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
                    addRow(sheet, propertyVariant.getSubNameMaps(), malPropertyEntity.getPropertyId(), assetEntity.getCaption(), gsonWithNulls.toJson(attributes), assetEntity.getMalAssetId(), assetEntity.getMarshaCode());
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
                addRow(sheet, structureName, malPropertyEntity.getPropertyId(), assetEntity.getCaption(), gsonWithNulls.toJson(attributes), assetEntity.getMalAssetId(), assetEntity.getMarshaCode());
            }
        }
    }

    private void addRow(final Sheet sheet, final String subName, final String propertyId, String name,
                        final String jsonAttributes, final String malAssetId, final String marshaCode) {
        Row row = sheet.createRow(rowIndex++);
        int colIndex = 0;

        name = name.replace("\"", "");
        name = name.replace("\t", " ");
        name = name.trim();
        if (StringUtils.isNotBlank(malAssetId)) {
            row.createCell(colIndex++).setCellValue(subName);
            row.createCell(colIndex++).setCellValue(malAssetId);
            if (marshaCode != null) {
                row.createCell(colIndex++).setCellValue(String.format(DEFAULT_STRING, malAssetId + " - " + marshaCode + " - " + name));
            } else {
                row.createCell(colIndex++).setCellValue(String.format(DEFAULT_STRING, malAssetId + " - " + name));
            }
            row.createCell(colIndex++).setCellValue(propertyId);
            row.createCell(colIndex++).setCellValue("EDIT_AND_ADD");
            row.createCell(colIndex++).setCellValue(malAssetId);
            row.createCell(colIndex).setCellValue(jsonAttributes);
        } else {
            row.createCell(colIndex++).setCellValue(subName);
            row.createCell(colIndex++).setCellValue(propertyId);
            if (marshaCode != null) {
                row.createCell(colIndex++).setCellValue(String.format(DEFAULT_STRING, propertyId + " - " + marshaCode + " - " + name));
            } else {
                row.createCell(colIndex++).setCellValue(String.format(DEFAULT_STRING, propertyId + " - " + name));
            }
            row.createCell(colIndex++).setCellValue("");
            row.createCell(colIndex++).setCellValue("EDIT_AND_ADD");
            row.createCell(colIndex++).setCellValue(propertyId);
            row.createCell(colIndex).setCellValue(jsonAttributes);
        }
    }

    private void digestLogos(final Sheet sheet, MALPropertyVariant propertyVariant,
                             final MALPropertyEntity malPropertyEntity, final String[] combinedAddressFields, Map<String, List<BMAsset>> colorsMap) {
        final List<String> propertyVariantFields = Arrays.asList(propertyVariant.getFieldsArray());
        String jsonAttributes = getJsonAttributes(malPropertyEntity, combinedAddressFields, propertyVariantFields, false, colorsMap);
        addRow(sheet, propertyVariant.getStructureName(), malPropertyEntity.getPropertyId(), malPropertyEntity.getName(), jsonAttributes, null, malPropertyEntity.getMarshaCode());
    }

    public String getJsonAttributes(MALPropertyEntity malPropertyEntity, String[] combinedAddressFields,
                                    List<String> propertyVariantFields, boolean isCustomStructure, Map<String, List<BMAsset>> colorsMap) {
        final List<Object> attributes = new ArrayList<>();
        addLogoAttribute(malPropertyEntity, attributes, propertyVariantFields, PropertyVariantFields.PROPERTY_LOGO_1C, isCustomStructure, colorsMap);
        addLogoAttribute(malPropertyEntity, attributes, propertyVariantFields, PropertyVariantFields.PROPERTY_LOGO_4C, isCustomStructure, colorsMap);
        addLogoAttribute(malPropertyEntity, attributes, propertyVariantFields, PropertyVariantFields.PROPERTY_LOGO_4CB, isCustomStructure, colorsMap);
        addLogoAttribute(malPropertyEntity, attributes, propertyVariantFields, PropertyVariantFields.PROPERTY_LOGO_1C_BLACK, isCustomStructure, colorsMap);
        addLogoAttribute(malPropertyEntity, attributes, propertyVariantFields, PropertyVariantFields.PROPERTY_LOGO_PMS, isCustomStructure, colorsMap);
        addLogoAttribute(malPropertyEntity, attributes, propertyVariantFields, PropertyVariantFields.PROPERTY_LOGO_PMSC, isCustomStructure, colorsMap);
        addLogoAttribute(malPropertyEntity, attributes, propertyVariantFields, PropertyVariantFields.PROPERTY_LOGO_4CC, isCustomStructure, colorsMap);
        addLogoAttribute(malPropertyEntity, attributes, propertyVariantFields, PropertyVariantFields.PROPERTY_LOGO_KOD, isCustomStructure, colorsMap);
        addLogoAttribute(malPropertyEntity, attributes, propertyVariantFields, PropertyVariantFields.PROPERTY_LOGO_BLACKK, isCustomStructure, colorsMap);
        addLogoAttribute(malPropertyEntity, attributes, propertyVariantFields, PropertyVariantFields.PROPERTY_LOGO_4CK, isCustomStructure, colorsMap);
        addLogoAttribute(malPropertyEntity, attributes, propertyVariantFields, PropertyVariantFields.PROPERTY_LOGO_SHERATON_BLACK, isCustomStructure, colorsMap);
        addLogoAttribute(malPropertyEntity, attributes, propertyVariantFields, PropertyVariantFields.PROPERTY_LOGO_SHERATON_DUSK, isCustomStructure, colorsMap);
        addLogoAttribute(malPropertyEntity, attributes, propertyVariantFields, PropertyVariantFields.PROPERTY_LOGO_SHERATON_GRAPHITE_CODED_CMYK, isCustomStructure, colorsMap);
        addLogoAttribute(malPropertyEntity, attributes, propertyVariantFields, PropertyVariantFields.PROPERTY_LOGO_SHERATON_GRAPHITE_CODED_PMS, isCustomStructure, colorsMap);
        addLogoAttribute(malPropertyEntity, attributes, propertyVariantFields, PropertyVariantFields.PROPERTY_LOGO_SHERATON_GRAPHITE_RGB, isCustomStructure, colorsMap);
        addLogoAttribute(malPropertyEntity, attributes, propertyVariantFields, PropertyVariantFields.PROPERTY_LOGO_SHERATON_GRAPHITE_UNCOATED_CMYK, isCustomStructure, colorsMap);
        addLogoAttribute(malPropertyEntity, attributes, propertyVariantFields, PropertyVariantFields.PROPERTY_LOGO_SHERATON_GRAPHITE_UNCOATED_PMS, isCustomStructure, colorsMap);
        addLogoAttribute(malPropertyEntity, attributes, propertyVariantFields, PropertyVariantFields.PROPERTY_LOGO_SHERATON_IVORY, isCustomStructure, colorsMap);
        addLogoAttribute(malPropertyEntity, attributes, propertyVariantFields, PropertyVariantFields.PROPERTY_LOGO_SHERATON_KNOCKOUT, isCustomStructure, colorsMap);
        addLogoAttribute(malPropertyEntity, attributes, propertyVariantFields, PropertyVariantFields.PROPERTY_LOGO_SHERATON_LOGO_SPECS, isCustomStructure, colorsMap);
        addLogoAttribute(malPropertyEntity, attributes, propertyVariantFields, PropertyVariantFields.PROPERTY_LOGO_SHERATON_OYSTER_COATED_CMYK, isCustomStructure, colorsMap);
        addLogoAttribute(malPropertyEntity, attributes, propertyVariantFields, PropertyVariantFields.PROPERTY_LOGO_SHERATON_OYSTER_COATED_PMS, isCustomStructure, colorsMap);
        addLogoAttribute(malPropertyEntity, attributes, propertyVariantFields, PropertyVariantFields.PROPERTY_LOGO_SHERATON_OYSTER_RGB, isCustomStructure, colorsMap);
        addLogoAttribute(malPropertyEntity, attributes, propertyVariantFields, PropertyVariantFields.PROPERTY_LOGO_SHERATON_OYSTER_UNCOATED_CMYK, isCustomStructure, colorsMap);
        addLogoAttribute(malPropertyEntity, attributes, propertyVariantFields, PropertyVariantFields.PROPERTY_LOGO_SHERATON_OYSTER_UNCOATED_PMS, isCustomStructure, colorsMap);
        addLogoAttribute(malPropertyEntity, attributes, propertyVariantFields, PropertyVariantFields.PROPERTY_LOGO_SHERATON_RESORT_FRENCH_GRAY_COATED_CMYK, isCustomStructure, colorsMap);
        addLogoAttribute(malPropertyEntity, attributes, propertyVariantFields, PropertyVariantFields.PROPERTY_LOGO_SHERATON_RESORT_FRENCH_GRAY_COATED_PMS, isCustomStructure, colorsMap);
        addLogoAttribute(malPropertyEntity, attributes, propertyVariantFields, PropertyVariantFields.PROPERTY_LOGO_SHERATON_RESORT_FRENCH_GRAY_RGB, isCustomStructure, colorsMap);
        addLogoAttribute(malPropertyEntity, attributes, propertyVariantFields, PropertyVariantFields.PROPERTY_LOGO_SHERATON_RESORT_FRENCH_GRAY_UNCOATED_CMYK, isCustomStructure, colorsMap);
        addLogoAttribute(malPropertyEntity, attributes, propertyVariantFields, PropertyVariantFields.PROPERTY_LOGO_SHERATON_RESORT_FRENCH_GRAY_UNCOATED_PMS, isCustomStructure, colorsMap);
        addLogoAttribute(malPropertyEntity, attributes, propertyVariantFields, PropertyVariantFields.PROPERTY_LOGO_SHERATON_TUNGSTEN_COATED_CMYK, isCustomStructure, colorsMap);
        addLogoAttribute(malPropertyEntity, attributes, propertyVariantFields, PropertyVariantFields.PROPERTY_LOGO_SHERATON_TUNGSTEN_COATED_PMS, isCustomStructure, colorsMap);
        addLogoAttribute(malPropertyEntity, attributes, propertyVariantFields, PropertyVariantFields.PROPERTY_LOGO_SHERATON_TUNGSTEN_RGB, isCustomStructure, colorsMap);
        addLogoAttribute(malPropertyEntity, attributes, propertyVariantFields, PropertyVariantFields.PROPERTY_LOGO_SHERATON_TUNGSTEN_UNCOATED_CMYK, isCustomStructure, colorsMap);
        addLogoAttribute(malPropertyEntity, attributes, propertyVariantFields, PropertyVariantFields.PROPERTY_LOGO_SHERATON_TUNGSTEN_UNCOATED_PMS, isCustomStructure, colorsMap);
        addLogoAttribute(malPropertyEntity, attributes, propertyVariantFields, PropertyVariantFields.PROPERTY_LOGO_US_NAVY, isCustomStructure, colorsMap);

        addAttribute(attributes, propertyVariantFields, PropertyVariantFields.MARSHA_CODE, TEXT, malPropertyEntity != null ? malPropertyEntity.getMarshaCode() : "", isCustomStructure);
        addAttribute(attributes, propertyVariantFields, PropertyVariantFields.AFFILIATE_NAME, TEXT, malPropertyEntity != null ? malPropertyEntity.getName() : "", isCustomStructure);
        addAttribute(attributes, propertyVariantFields, PropertyVariantFields.AFFILIATES_CODE, TEXT, malPropertyEntity != null ? malPropertyEntity.getPropertyId() : "", isCustomStructure);
        addAttribute(attributes, propertyVariantFields, PropertyVariantFields.PROPERTY_STATE, TEXT, malPropertyEntity != null ? malPropertyEntity.getState() : "", isCustomStructure);
        addAttribute(attributes, propertyVariantFields, PropertyVariantFields.COMBINED_ADDRESS, RICHTEXT, combinedAddressFields != null ? combinedAddressFields[0] : "", isCustomStructure);
        addAttribute(attributes, propertyVariantFields, PropertyVariantFields.COMBINED_ADDRESS_ADDRESS1, RICHTEXT, combinedAddressFields != null ? combinedAddressFields[1] : "", isCustomStructure);
        addAttribute(attributes, propertyVariantFields, PropertyVariantFields.COMBINED_ADDRESS_ADDRESS1_BOLD, RICHTEXT, combinedAddressFields != null ? combinedAddressFields[2] : "", isCustomStructure);

        addCombinedAddressAttribute(attributes, propertyVariantFields, isCustomStructure, combinedAddressFields);

        addAttribute(attributes, propertyVariantFields, PropertyVariantFields.ADDRESS, TEXT, malPropertyEntity != null ? malPropertyEntity.getAddress() : "", isCustomStructure);
        addAttribute(attributes, propertyVariantFields, PropertyVariantFields.STREET, TEXT, malPropertyEntity != null ? malPropertyEntity.getAddress2() : "", isCustomStructure);
        addAttribute(attributes, propertyVariantFields, PropertyVariantFields.ZIP, TEXT, malPropertyEntity != null ? malPropertyEntity.getZip() : "", isCustomStructure);
        addAttribute(attributes, propertyVariantFields, PropertyVariantFields.CITY, TEXT, malPropertyEntity != null ? malPropertyEntity.getCity() : "", isCustomStructure);
        addAttribute(attributes, propertyVariantFields, PropertyVariantFields.COUNTRY, TEXT, malPropertyEntity != null ? malPropertyEntity.getCountry() : "", isCustomStructure);
        addAttribute(attributes, propertyVariantFields, PropertyVariantFields.PROPERTY_URL, TEXT, malPropertyEntity != null ? malPropertyEntity.getUrl() : "", isCustomStructure);
        addAttribute(attributes, propertyVariantFields, PropertyVariantFields.PROPERTY_TELEPHONE, TEXT, malPropertyEntity != null ? malPropertyEntity.getTelephone() : "", isCustomStructure);
        addAttribute(attributes, propertyVariantFields, PropertyVariantFields.LATITUDE, TEXT, malPropertyEntity != null ? malPropertyEntity.getLatitude() : "", isCustomStructure);
        addAttribute(attributes, propertyVariantFields, PropertyVariantFields.LONGITUDE, TEXT, malPropertyEntity != null ? malPropertyEntity.getLongitude() : "", isCustomStructure);
        return gsonWithNulls.toJson(attributes);
    }

    private void addCombinedAddressAttribute(List<Object> attributes, List<String> propertyVariantFields, boolean isCustomStructure, String... combinedAddressFields) {
        if (propertyVariantFields.contains(PropertyVariantFields.COMBINED_ADDRESS_ADDRESS11ST_AND_2ND_LINE_BOLD.getPropertyName())) {
            if (combinedAddressFields == null && isCustomStructure) {
                attributes.add(new AttributeCS(37,
                        PropertyVariantFields.COMBINED_ADDRESS_ADDRESS11ST_AND_2ND_LINE_BOLD.getPropertyName(),
                        PropertyVariantFields.COMBINED_ADDRESS_ADDRESS11ST_AND_2ND_LINE_BOLD.getPropertyLabel(),
                        -1,
                        RICHTEXT));
            } else if (combinedAddressFields != null && combinedAddressFields.length > 0) {
                String resultValue = null;
                if (combinedAddressFields[3] != null && combinedAddressFields[4] != null) {
                    resultValue = combinedAddressFields[3] + combinedAddressFields[4];
                } else if (combinedAddressFields[3] != null) {
                    resultValue = combinedAddressFields[3];
                } else if (combinedAddressFields[4] != null) {
                    resultValue = combinedAddressFields[4];
                }

                attributes.add(new AttributeCO(37,
                        PropertyVariantFields.COMBINED_ADDRESS_ADDRESS11ST_AND_2ND_LINE_BOLD.getPropertyName(),
                        RICHTEXT,
                        resultValue));
            }
        }
    }

    private void addAttribute(List<Object> attributes, List<String> propertyVariantFields, PropertyVariantFields propertyVariantField,
                              String attributeType, String value, boolean isCustomStructure) {
        if (propertyVariantFields.contains(propertyVariantField.getPropertyName())) {
            if (isCustomStructure) {
                attributes.add(new AttributeCS(propertyVariantField.getOrderNumber(),
                        propertyVariantField.getPropertyName(),
                        propertyVariantField.getPropertyLabel(),
                        propertyVariantField.getOrder(),
                        attributeType));
            } else {
                attributes.add(new AttributeCO(propertyVariantField.getOrderNumber(),
                        propertyVariantField.getPropertyName(),
                        attributeType,
                        value));
            }
        }
    }

    private void addLogoAttribute(MALPropertyEntity malPropertyEntity, List<Object> attributes, List<String> propertyVariantFields,
                                  PropertyVariantFields propertyVariantField, boolean isCustomStructure, Map<String, List<BMAsset>> colorsMap) {
        if (propertyVariantFields.contains(propertyVariantField.getPropertyName())) {
            if (isCustomStructure) {
                attributes.add(new AttributeCS(propertyVariantField.getOrderNumber(),
                        propertyVariantField.getPropertyName(),
                        propertyVariantField.getPropertyLabel(),
                        propertyVariantField.getOrder(),
                        MEDIA));
            } else {
                Optional<BMAsset> firstAsset = Optional.empty();
                List<BMAsset> bmAssets = colorsMap.get(propertyVariantField.getColorId());
                if (bmAssets != null) {
                    firstAsset = bmAssets
                            .stream()
                            .filter(asset -> asset != null
                                    && asset.getAffiliateNumber() != null
                                    && asset.getAffiliateNumber().equals(malPropertyEntity.getPropertyId()))
                            .findFirst().map(Optional::of).orElse(firstAsset);
                }
                if (firstAsset.isPresent()) {
                    String logo = firstAsset.get().getId();
                    if (logo != null) {
                        attributes.add(new AttributeCO(propertyVariantField.getOrderNumber(),
                                propertyVariantField.getPropertyName(),
                                MEDIA,
                                getPropertyMedia(logo)));
                    }
                }
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
                case "AFFILIATE_NAME":
                    replacement = malPropertyEntity.getName();
                    break;
                case "STREET":
                    replacement = malPropertyEntity.getAddress2();
                    break;
                case "ADDRESS":
                    replacement = malPropertyEntity.getAddress();
                    break;
                case "COUNTRY":
                    replacement = malPropertyEntity.getCountry();
                    break;
                case "PropertyState":
                    replacement = malPropertyEntity.getState();
                    break;
                case "CITY":
                    replacement = malPropertyEntity.getCity();
                    break;
                case "ZIP":
                    replacement = malPropertyEntity.getZip();
                    break;
                case "PropertyTelephone":
                    replacement = malPropertyEntity.getTelephone();
                    break;
                case "PropertyURL":
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
        int rowIndexCS = 0;
        Row row = sheet.createRow(rowIndexCS++);
        for (final String column : STRUCTURES_COLUMN_NAMES) {
            Cell cell = row.createCell(colIndex++);
            cell.setCellValue(column);
            cell.setCellStyle(headerFormat);
        }

        for (final MALPropertyVariant malPropertyVariant : assetStructures.getPropertyVariants().values()) {
            //parent
            row = sheet.createRow(rowIndexCS++);

            colIndex = 0;

            row.createCell(colIndex++).setCellValue(malPropertyVariant.getStructureName());

            row.createCell(colIndex++).setCellValue(String.format(DEFAULT_STRING, malPropertyVariant.getStructureName()));

            row.createCell(colIndex++).setCellValue("DEFAULT");

            colIndex++;

            colIndex++;

            if (malPropertyVariant.isBrandStructure()) {
                row.createCell(colIndex++).setCellValue("NON_AFFILIATE_STRUCTURE");
            } else {
                row.createCell(colIndex++).setCellValue("MULTI_OBJECT_AFFILIATE_STRUCTURE");
            }

            String structureNameRadiobutton = malPropertyVariant.getStructureNameRadiobutton();
            row.createCell(colIndex++).setCellValue(structureNameRadiobutton != null ? structureNameRadiobutton : "");

            if (malPropertyVariant.isBrandStructure()) {
                row.createCell(colIndex).setCellValue("[{\"number\": 24,\"name\": \"Image\",\"label\": \"Image\",\"comment\": \"\",\"order\": 0,\"type\": \"MEDIA\",\"props\": null}]");
            } else {

                final List<String> propertyVariantFields = new ArrayList<>();
                Collections.addAll(propertyVariantFields, malPropertyVariant.getFieldsArray());
                String newValue = getJsonAttributes(null, null, propertyVariantFields, true, null);
                row.createCell(colIndex).setCellValue(newValue);

                // substructure floor plans
                String subNameFloorPlansRadiobutton = malPropertyVariant.getSubNameFloorPlansRadiobutton();
                rowIndexCS = createSubStructure(sheet, rowIndexCS, malPropertyVariant, malPropertyVariant.getSubNameFloorPlans(),
                        subNameFloorPlansRadiobutton != null ? subNameFloorPlansRadiobutton : "",
                        "[{\"number\":24,\"name\":\"Floorplan1\",\"label\":\"Floorplan 1\",\"comment\":\"\",\"order\":-1,\"type\":\"MEDIA\",\"props\":null},{\"number\":25,\"name\":\"Floorplan2\",\"label\":\"Floorplan 2\",\"comment\":\"\",\"order\":-1,\"type\":\"MEDIA\",\"props\":null},{\"number\":26,\"name\":\"Floorplan3\",\"label\":\"Floorplan 3\",\"comment\":\"\",\"order\":-1,\"type\":\"MEDIA\",\"props\":null},{\"number\":27,\"name\":\"Floorplan4\",\"label\":\"Floorplan 4\",\"comment\":\"\",\"order\":-1,\"type\":\"MEDIA\",\"props\":null},{\"number\":28,\"name\":\"Floorplan5\",\"label\":\"Floorplan 5\",\"comment\":\"\",\"order\":-1,\"type\":\"MEDIA\",\"props\":null}]");
                // substructure maps
                String subNameMapsRadiobutton = malPropertyVariant.getSubNameMapsRadiobutton();
                rowIndexCS = createSubStructure(sheet, rowIndexCS, malPropertyVariant, malPropertyVariant.getSubNameMaps(),
                        subNameMapsRadiobutton != null ? subNameMapsRadiobutton : "",
                        "[{\"number\":24,\"name\":\"Map1\",\"label\":\"Map 1\",\"comment\":\"\",\"order\":-1,\"type\":\"MEDIA\",\"props\":null},{\"number\":25,\"name\":\"Map2\",\"label\":\"Map 2\",\"comment\":\"\",\"order\":-1,\"type\":\"MEDIA\",\"props\":null},{\"number\":26,\"name\":\"Map3\",\"label\":\"Map 3\",\"comment\":\"\",\"order\":-1,\"type\":\"MEDIA\",\"props\":null},{\"number\":27,\"name\":\"Map4\",\"label\":\"Map 4\",\"comment\":\"\",\"order\":-1,\"type\":\"MEDIA\",\"props\":null},{\"number\":28,\"name\":\"Map5\",\"label\":\"Map 5\",\"comment\":\"\",\"order\":-1,\"type\":\"MEDIA\",\"props\":null}]");
                // substructure images
                String subNameRadiobutton = malPropertyVariant.getSubNameRadiobutton();
                rowIndexCS = createSubStructure(sheet, rowIndexCS, malPropertyVariant, malPropertyVariant.getSubName(),
                        subNameRadiobutton != null ? subNameRadiobutton : "",
                        "[{\"number\":24,\"name\":\"Image1\",\"label\":\"Image 1\",\"comment\":\"\",\"order\":-1,\"type\":\"MEDIA\",\"props\":null},{\"number\":25,\"name\":\"Image2\",\"label\":\"Image 2\",\"comment\":\"\",\"order\":-1,\"type\":\"MEDIA\",\"props\":null},{\"number\":26,\"name\":\"Image3\",\"label\":\"Image 3\",\"comment\":\"\",\"order\":-1,\"type\":\"MEDIA\",\"props\":null},{\"number\":27,\"name\":\"Image4\",\"label\":\"Image 4\",\"comment\":\"\",\"order\":-1,\"type\":\"MEDIA\",\"props\":null},{\"number\":28,\"name\":\"Image5\",\"label\":\"Image 5\",\"comment\":\"\",\"order\":-1,\"type\":\"MEDIA\",\"props\":null}]");
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

            row.createCell(colIndex).setCellValue(s);
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

    public static class AttributeCO extends Attribute {
        private final String value;
        private final String attributeName;

        public AttributeCO(int orderNumber, String attributeName, String type, String value) {
            super(orderNumber, "", 0, type);
            this.value = value;
            this.attributeName = attributeName;
        }

        public String getValue() {
            return value;
        }

        public String getAttributeName() {
            return attributeName;
        }
    }

    public static class AttributeCS extends Attribute {
        public final String name;

        public AttributeCS(final int number, final String name, final String label, int order, final String type) {
            super(number, label, order, type);
            this.name = name;
        }
    }

    public static class Attribute {
        public final int number;
        public final String label;
        public final String comment;
        public final int order;
        public final String type;
        public final Object props;

        public Attribute(final int number, final String label, int order, final String type) {
            this.number = number;
            this.label = label;
            this.comment = "";
            this.order = order;
            this.type = type;
            this.props = null;
        }
    }
}