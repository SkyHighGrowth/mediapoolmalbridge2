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
import MediaPoolMalBridge.service.Bridge.excelcreator.AbstractBridgeUniqueExcelService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

@Service
public class BridgeCreateExcelXSSFFileUniqueThreadService extends AbstractBridgeUniqueExcelService {

    protected static final String[] STRUCTURES_COLUMN_NAMES = {"List ID * (no editable)",
            "List name *", "Type * (no editable)", "ID of Parent list", "Name of default entry",
            "Affiliate type", "Show preview for list entry", "Attributes"};

    protected static final String[] OBJECTS_COLUMN_NAMES = {"List ID * (no editable)",
            "List Entry ID * (no editable)", "Display name *", "Name of parent entry",
            "State *", "Affiliate", "Attributes"};

    private static final Gson gsonWithNulls = new GsonBuilder().serializeNulls().create();

    private final MALAssetStructures assetStructures;

    private int rowIndex = 0;

    public BridgeCreateExcelXSSFFileUniqueThreadService(final MALAssetStructures assetStructures) {
        this.assetStructures = assetStructures;
    }

    @Override
    protected void run() {
        writeToFile();
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

    private void writeToFile() {
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
                if (!propertyVariant.isBrandStructure() && countProperties == appConfig.getFileMaxRecords()) {
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

    private void deleteOldFilesFromExcelDirectory() {
        final File[] files = new File(appConfig.getExcelDir()).listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.exists()) {
                    file.delete();
                }
            }
        }
    }

    private void createObjectsSheet(final Workbook workbook, LinkedHashSet<MALPropertyPair> malPropertyPairSet) throws Exception {
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

    private boolean createBrandObjectsSheet(final Workbook workbook, LinkedHashSet<MALPropertyPair> malPropertyPairSet) throws Exception {
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
                                    "[MEDIA_GUID=" + assetEntity.getBmAssetId() + ";MEDIA_VERSION=0]",
                                    "MEDIA"));
                            addRow(sheet, propertyVariant.getStructureName(), "", assetEntity.getCaption(), gsonWithNulls.toJson(attributes), assetEntity.getMalAssetId());
                            hasRows = true;
                        }
                        ++order;
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
                                  final MALPropertyEntity malPropertyEntity) throws Exception {
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
                            "[MD5_HASH=" + assetEntity.getBmMd5Hash() + ";MEDIA_GUID=" + assetEntity.getBmAssetId() + ";]",
                            "MEDIA"));
                    addRow(sheet, propertyVariant.getSubNameFloorPlans(), malPropertyEntity.getPropertyId(), assetEntity.getCaption(), gsonWithNulls.toJson(attributes), assetEntity.getMalAssetId());
                }
                ++order;
            }
        }
    }

    private void digestMaps(final Sheet sheet, final MALPropertyVariant propertyVariant,
                            final MALPropertyEntity malPropertyEntity) throws Exception {
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
                            "[MD5_HASH=" + assetEntity.getBmMd5Hash() + ";MEDIA_GUID=" + assetEntity.getBmAssetId() + ";]",
                            "MEDIA"));
                    addRow(sheet, propertyVariant.getSubNameMaps(), malPropertyEntity.getPropertyId(), assetEntity.getCaption(), gsonWithNulls.toJson(attributes), assetEntity.getMalAssetId());
                }
                ++order;
            }
        }
    }

    private void digestAssets(final Sheet sheet, final MALPropertyVariant propertyVariant,
                              final MALPropertyEntity malPropertyEntity) throws Exception {
        final List<AssetEntity> assetEntities = assetRepository.findPropertyAssets(malPropertyEntity.getPropertyId(), "1", TransferringAssetStatus.DONE);
        int order = 1;
        if (assetEntities != null && !assetEntities.isEmpty()) {
            for (final AssetEntity assetEntity : assetEntities) {
                final List<AttributeShort> attributes = new ArrayList<>();
                attributes.add(new AttributeShort(order,
                        "Image" + order,
                        "[MD5_HASH=" + assetEntity.getBmMd5Hash() + ";MEDIA_GUID=" + assetEntity.getBmAssetId() + ";]",
                        "MEDIA"));
                String structureName = propertyVariant.getSubName();
                if (StringUtils.isBlank(structureName)) {
                    structureName = propertyVariant.getStructureName();
                }
                addRow(sheet, structureName, malPropertyEntity.getPropertyId(), assetEntity.getCaption(), gsonWithNulls.toJson(attributes), assetEntity.getMalAssetId());
            }
            ++order;
        }
    }

    private void addRow(final Sheet sheet, final String subName, final String propertyId, String name,
                        final String jsonedAttributes, final String malAssetId) throws Exception {
        Row row = sheet.createRow(rowIndex++);
        int colIndex = 0;

        name = name.replaceAll("\"", "");
        name = name.trim();
        if (StringUtils.isNotBlank(malAssetId)) {
            row.createCell(colIndex++).setCellValue(subName);
            row.createCell(colIndex++).setCellValue(malAssetId);
            row.createCell(colIndex++).setCellValue("{\"default\":\"" + malAssetId + " - " + name + "\"}");
            row.createCell(colIndex++).setCellValue(propertyId);
            row.createCell(colIndex++).setCellValue("EDIT_AND_ADD");
            row.createCell(colIndex++).setCellValue(malAssetId);
            row.createCell(colIndex++).setCellValue(jsonedAttributes);
        } else {
            row.createCell(colIndex++).setCellValue(subName);
            row.createCell(colIndex++).setCellValue(propertyId);
            row.createCell(colIndex++).setCellValue("{\"default\":\"" + propertyId + " - " + name + "\"}");
            row.createCell(colIndex++).setCellValue("");
            row.createCell(colIndex++).setCellValue("EDIT_AND_ADD");
            row.createCell(colIndex++).setCellValue(propertyId);
            row.createCell(colIndex++).setCellValue(jsonedAttributes);
        }
    }

    private void digestLogos(final Sheet sheet, MALPropertyVariant propertyVariant,
                             final MALPropertyEntity malPropertyEntity, final String[] combinedAddressFields) throws Exception {
        final List<Attribute> attributes = new ArrayList<>();
        List<AssetEntity> logo;
        String propertyVariantFields = propertyVariant.getFields();
        if (propertyVariantFields.contains(PropertyVariantFields.PROPERTY_LOGO_1C)) {
            logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "ko", TransferringAssetStatus.DONE);
            String propertyLogo1c = "";
            if (logo != null && !logo.isEmpty()) {
                propertyLogo1c = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
                attributes.add(new Attribute(24,
                        PropertyVariantFields.PROPERTY_LOGO_1C,
                        "MEDIA",
                        propertyLogo1c));
            }
        }
        if (propertyVariantFields.contains(PropertyVariantFields.PROPERTY_LOGO_4C)) {
            logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "cmyk", TransferringAssetStatus.DONE);
            String propertyLogo4c = "";
            if (logo != null && !logo.isEmpty()) {
                propertyLogo4c = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
                attributes.add(new Attribute(25,
                        PropertyVariantFields.PROPERTY_LOGO_4C,
                        "MEDIA",
                        propertyLogo4c));
            }
        }
        if (propertyVariantFields.contains(PropertyVariantFields.PROPERTY_LOGO_4CB)) {
            logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "cmyk-B", TransferringAssetStatus.DONE);
            String propertyLogocmykb = "";
            if (logo != null && !logo.isEmpty()) {
                propertyLogocmykb = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
                attributes.add(new Attribute(26,
                        PropertyVariantFields.PROPERTY_LOGO_4CB,
                        "MEDIA",
                        propertyLogocmykb));
            }
        }
        if (propertyVariantFields.contains(PropertyVariantFields.PROPERTY_LOGO_1C_BLACK)) {
            logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "k", TransferringAssetStatus.DONE);
            String propertyLogo1cBlack = "";
            if (logo != null && !logo.isEmpty()) {
                propertyLogo1cBlack = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
                attributes.add(new Attribute(27,
                        PropertyVariantFields.PROPERTY_LOGO_1C_BLACK,
                        "MEDIA",
                        propertyLogo1cBlack));
            }
        }
        if (propertyVariantFields.contains(PropertyVariantFields.PROPERTY_LOGO_PMS)) {
            logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "pms", TransferringAssetStatus.DONE);
            String propertyLogoPMS = "";
            if (logo != null && !logo.isEmpty()) {
                propertyLogoPMS = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
                attributes.add(new Attribute(28,
                        PropertyVariantFields.PROPERTY_LOGO_PMS,
                        "MEDIA",
                        propertyLogoPMS));
            }
        }
        if (propertyVariantFields.contains(PropertyVariantFields.PROPERTY_LOGO_PMSC)) {
            logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "pms-C", TransferringAssetStatus.DONE);
            String propertyLogoPMSC = "";
            if (logo != null && !logo.isEmpty()) {
                propertyLogoPMSC = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
                attributes.add(new Attribute(29,
                        PropertyVariantFields.PROPERTY_LOGO_PMSC,
                        "MEDIA",
                        propertyLogoPMSC));
            }
        }
        if (propertyVariantFields.contains(PropertyVariantFields.PROPERTY_LOGO_4CC)) {
            logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "cmyk-C", TransferringAssetStatus.DONE);
            String propertyLogocmykC = "";
            if (logo != null && !logo.isEmpty()) {
                propertyLogocmykC = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
                attributes.add(new Attribute(30,
                        PropertyVariantFields.PROPERTY_LOGO_4CC,
                        "MEDIA",
                        propertyLogocmykC));
            }
        }
        if (propertyVariantFields.contains(PropertyVariantFields.PROPERTY_LOGO_KOD)) {
            logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "ko-D", TransferringAssetStatus.DONE);
            String propertyLogokoD = "";
            if (logo != null && !logo.isEmpty()) {
                propertyLogokoD = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
                attributes.add(new Attribute(31,
                        PropertyVariantFields.PROPERTY_LOGO_KOD,
                        "MEDIA",
                        propertyLogokoD));
            }
        }
        //this should be checked particularly
        if (propertyVariantFields.contains(PropertyVariantFields.PROPERTY_LOGO_BLACKK)) {
            logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "k", TransferringAssetStatus.DONE);
            String propertyLogoBlackK = "";
            if (logo != null && !logo.isEmpty()) {
                propertyLogoBlackK = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
                attributes.add(new Attribute(32,
                        PropertyVariantFields.PROPERTY_LOGO_BLACKK,
                        "MEDIA",
                        propertyLogoBlackK));
            }
        }
        if (propertyVariantFields.contains(PropertyVariantFields.PROPERTY_LOGO_4CK)) {
            logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "cmyk-K", TransferringAssetStatus.DONE);
            String propertyLogo4cK = "";
            if (logo != null && !logo.isEmpty()) {
                propertyLogo4cK = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
                attributes.add(new Attribute(33,
                        PropertyVariantFields.PROPERTY_LOGO_4CK,
                        "MEDIA",
                        propertyLogo4cK));
            }
        }
        if (propertyVariantFields.contains(PropertyVariantFields.PROPERTY_LOGO_SHERATON_BLACK)) {
            logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "Sheraton Black logo-1", TransferringAssetStatus.DONE);
            String propertyLogoSheratonBlack = "";
            if (logo != null && !logo.isEmpty()) {
                propertyLogoSheratonBlack = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
                attributes.add(new Attribute(39,
                        PropertyVariantFields.PROPERTY_LOGO_SHERATON_BLACK,
                        "MEDIA",
                        propertyLogoSheratonBlack));
            }
        }
        if (propertyVariantFields.contains(PropertyVariantFields.PROPERTY_LOGO_SHERATON_DUSK)) {
            logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "dusk", TransferringAssetStatus.DONE);
            String propertyLogoSheratonDusk = "";
            if (logo != null && !logo.isEmpty()) {
                propertyLogoSheratonDusk = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
                attributes.add(new Attribute(40,
                        PropertyVariantFields.PROPERTY_LOGO_SHERATON_DUSK,
                        "MEDIA",
                        propertyLogoSheratonDusk));
            }
        }
        if (propertyVariantFields.contains(PropertyVariantFields.PROPERTY_LOGO_SHERATON_GRAPHITE_CODED_CMYK)) {
            logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "Graphite_C_cmyk", TransferringAssetStatus.DONE);
            String propertyLogoSheratonGraphiteCodedCMYK = "";
            if (logo != null && !logo.isEmpty()) {
                propertyLogoSheratonGraphiteCodedCMYK = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
                attributes.add(new Attribute(40,
                        PropertyVariantFields.PROPERTY_LOGO_SHERATON_GRAPHITE_CODED_CMYK,
                        "MEDIA",
                        propertyLogoSheratonGraphiteCodedCMYK));
            }
        }
        if (propertyVariantFields.contains(PropertyVariantFields.PROPERTY_LOGO_SHERATON_GRAPHITE_CODED_PMS)) {
            logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "Graphite_C_pms", TransferringAssetStatus.DONE);
            String propertyLogoSheratonGraphiteCodedPMS = "";
            if (logo != null && !logo.isEmpty()) {
                propertyLogoSheratonGraphiteCodedPMS = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
                attributes.add(new Attribute(42,
                        PropertyVariantFields.PROPERTY_LOGO_SHERATON_GRAPHITE_CODED_PMS,
                        "MEDIA",
                        propertyLogoSheratonGraphiteCodedPMS));
            }
        }
        if (propertyVariantFields.contains(PropertyVariantFields.PROPERTY_LOGO_SHERATON_GRAPHITE_RGB)) {
            logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "Graphite_rgb", TransferringAssetStatus.DONE);
            String propertyLogoSheratonGraphiteRGB = "";
            if (logo != null && !logo.isEmpty()) {
                propertyLogoSheratonGraphiteRGB = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
                attributes.add(new Attribute(43,
                        PropertyVariantFields.PROPERTY_LOGO_SHERATON_GRAPHITE_RGB,
                        "MEDIA",
                        propertyLogoSheratonGraphiteRGB));
            }
        }
        if (propertyVariantFields.contains(PropertyVariantFields.PROPERTY_LOGO_SHERATON_GRAPHITE_UNCOATED_CMYK)) {
            logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "Graphite_U_cmyk", TransferringAssetStatus.DONE);
            String propertyLogoSheratonGraphiteUncoatedCMYK = "";
            if (logo != null && !logo.isEmpty()) {
                propertyLogoSheratonGraphiteUncoatedCMYK = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
                attributes.add(new Attribute(44,
                        PropertyVariantFields.PROPERTY_LOGO_SHERATON_GRAPHITE_UNCOATED_CMYK,
                        "MEDIA",
                        propertyLogoSheratonGraphiteUncoatedCMYK));
            }
        }
        if (propertyVariantFields.contains(PropertyVariantFields.PROPERTY_LOGO_SHERATON_GRAPHITE_UNCOATED_PMS)) {
            logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "Graphite_U_pms", TransferringAssetStatus.DONE);
            String propertyLogoSheratonGraphiteUncoatedPMS = "";
            if (logo != null && !logo.isEmpty()) {
                propertyLogoSheratonGraphiteUncoatedPMS = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
                attributes.add(new Attribute(45,
                        PropertyVariantFields.PROPERTY_LOGO_SHERATON_GRAPHITE_UNCOATED_PMS,
                        "MEDIA",
                        propertyLogoSheratonGraphiteUncoatedPMS));
            }
        }
        if (propertyVariantFields.contains(PropertyVariantFields.PROPERTY_LOGO_SHERATON_IVORY)) {
            logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "ivory", TransferringAssetStatus.DONE);
            String propertyLogoSheratonIvory = "";
            if (logo != null && !logo.isEmpty()) {
                propertyLogoSheratonIvory = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
                attributes.add(new Attribute(46,
                        PropertyVariantFields.PROPERTY_LOGO_SHERATON_IVORY,
                        "MEDIA",
                        propertyLogoSheratonIvory));
            }
        }
        if (propertyVariantFields.contains(PropertyVariantFields.PROPERTY_LOGO_SHERATON_KNOCKOUT)) {
            logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "Knockout", TransferringAssetStatus.DONE);
            String propertyLogoSheratonKnockout = "";
            if (logo != null && !logo.isEmpty()) {
                propertyLogoSheratonKnockout = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
                attributes.add(new Attribute(47,
                        PropertyVariantFields.PROPERTY_LOGO_SHERATON_KNOCKOUT,
                        "MEDIA",
                        propertyLogoSheratonKnockout));
            }
        }
        if (propertyVariantFields.contains(PropertyVariantFields.PROPERTY_LOGO_SHERATON_LOGO_SPECS)) {
            logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "Logo_Specs", TransferringAssetStatus.DONE);
            String propertyLogoSheratonLogoSpecs = "";
            if (logo != null && !logo.isEmpty()) {
                propertyLogoSheratonLogoSpecs = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
                attributes.add(new Attribute(47,
                        PropertyVariantFields.PROPERTY_LOGO_SHERATON_LOGO_SPECS,
                        "MEDIA",
                        propertyLogoSheratonLogoSpecs));
            }
        }
        if (propertyVariantFields.contains(PropertyVariantFields.PROPERTY_LOGO_SHERATON_OYSTER_COATED_CMYK)) {
            logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "Logo_Specs", TransferringAssetStatus.DONE);
            String propertyLogoSheratonOysterCoatedCMYK = "";
            if (logo != null && !logo.isEmpty()) {
                propertyLogoSheratonOysterCoatedCMYK = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
                attributes.add(new Attribute(49,
                        PropertyVariantFields.PROPERTY_LOGO_SHERATON_OYSTER_COATED_CMYK,
                        "MEDIA",
                        propertyLogoSheratonOysterCoatedCMYK));
            }
        }
        if (propertyVariantFields.contains(PropertyVariantFields.PROPERTY_LOGO_SHERATON_OYSTER_COATED_PMS)) {
            logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "Oyster_C_pms", TransferringAssetStatus.DONE);
            String propertyLogoSheratonOysterCoatedPMS = "";
            if (logo != null && !logo.isEmpty()) {
                propertyLogoSheratonOysterCoatedPMS = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
                attributes.add(new Attribute(50,
                        PropertyVariantFields.PROPERTY_LOGO_SHERATON_OYSTER_COATED_PMS,
                        "MEDIA",
                        propertyLogoSheratonOysterCoatedPMS));
            }
        }
        if (propertyVariantFields.contains(PropertyVariantFields.PROPERTY_LOGO_SHERATON_OYSTER_RGB)) {
            logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "Oyster_rgb", TransferringAssetStatus.DONE);
            String propertyLogoSheratonOysterRGB = "";
            if (logo != null && !logo.isEmpty()) {
                propertyLogoSheratonOysterRGB = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
                attributes.add(new Attribute(51,
                        PropertyVariantFields.PROPERTY_LOGO_SHERATON_OYSTER_RGB,
                        "MEDIA",
                        propertyLogoSheratonOysterRGB));
            }
        }
        if (propertyVariantFields.contains(PropertyVariantFields.PROPERTY_LOGO_SHERATON_OYSTER_UNCOATED_CMYK)) {
            logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "Oyster_U_cmyk", TransferringAssetStatus.DONE);
            String propertyLogoSheratonOysterUncoatedCMYK = "";
            if (logo != null && !logo.isEmpty()) {
                propertyLogoSheratonOysterUncoatedCMYK = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
                attributes.add(new Attribute(52,
                        PropertyVariantFields.PROPERTY_LOGO_SHERATON_OYSTER_UNCOATED_CMYK,
                        "MEDIA",
                        propertyLogoSheratonOysterUncoatedCMYK));
            }
        }
        if (propertyVariantFields.contains(PropertyVariantFields.PROPERTY_LOGO_SHERATON_OYSTER_UNCOATED_PMS)) {
            logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "Oyster_U_pms", TransferringAssetStatus.DONE);
            String propertyLogoSheratonOysterUncoatedPMS = "";
            if (logo != null && !logo.isEmpty()) {
                propertyLogoSheratonOysterUncoatedPMS = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
                attributes.add(new Attribute(53,
                        PropertyVariantFields.PROPERTY_LOGO_SHERATON_OYSTER_UNCOATED_PMS,
                        "MEDIA",
                        propertyLogoSheratonOysterUncoatedPMS));
            }
        }
        if (propertyVariantFields.contains(PropertyVariantFields.PROPERTY_LOGO_SHERATON_RESORT_FRENCH_GRAY_COATED_CMYK)) {
            logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "FrenchGray_C_cmyk", TransferringAssetStatus.DONE);
            String propertyLogoSheratonResortFrenchGrayCoatedCMYK = "";
            if (logo != null && !logo.isEmpty()) {
                propertyLogoSheratonResortFrenchGrayCoatedCMYK = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
                attributes.add(new Attribute(54,
                        PropertyVariantFields.PROPERTY_LOGO_SHERATON_RESORT_FRENCH_GRAY_COATED_CMYK,
                        "MEDIA",
                        propertyLogoSheratonResortFrenchGrayCoatedCMYK));
            }
        }
        if (propertyVariantFields.contains(PropertyVariantFields.PROPERTY_LOGO_SHERATON_RESORT_FRENCH_GRAY_COATED_PMS)) {
            logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "FrenchGray_C_pms", TransferringAssetStatus.DONE);
            String propertyLogoSheratonResortFrenchGrayCoatedPMS = "";
            if (logo != null && !logo.isEmpty()) {
                propertyLogoSheratonResortFrenchGrayCoatedPMS = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
                attributes.add(new Attribute(55,
                        PropertyVariantFields.PROPERTY_LOGO_SHERATON_RESORT_FRENCH_GRAY_COATED_PMS,
                        "MEDIA",
                        propertyLogoSheratonResortFrenchGrayCoatedPMS));
            }
        }
        if (propertyVariantFields.contains(PropertyVariantFields.PROPERTY_LOGO_SHERATON_RESORT_FRENCH_GRAY_RGB)) {
            logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "FrenchGray_rgb", TransferringAssetStatus.DONE);
            String propertyLogoSheratonResortFrenchGrayRGB = "";
            if (logo != null && !logo.isEmpty()) {
                propertyLogoSheratonResortFrenchGrayRGB = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
                attributes.add(new Attribute(56,
                        PropertyVariantFields.PROPERTY_LOGO_SHERATON_RESORT_FRENCH_GRAY_RGB,
                        "MEDIA",
                        propertyLogoSheratonResortFrenchGrayRGB));
            }
        }
        if (propertyVariantFields.contains(PropertyVariantFields.PROPERTY_LOGO_SHERATON_RESORT_FRENCH_GRAY_UNCOATED_CMYK)) {
            logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "FrenchGray_U_cmyk", TransferringAssetStatus.DONE);
            String propertyLogoSheratonResortFrenchGrayUncoatedCMYK = "";
            if (logo != null && !logo.isEmpty()) {
                propertyLogoSheratonResortFrenchGrayUncoatedCMYK = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
                attributes.add(new Attribute(57,
                        PropertyVariantFields.PROPERTY_LOGO_SHERATON_RESORT_FRENCH_GRAY_UNCOATED_CMYK,
                        "MEDIA",
                        propertyLogoSheratonResortFrenchGrayUncoatedCMYK));
            }
        }
        if (propertyVariantFields.contains(PropertyVariantFields.PROPERTY_LOGO_SHERATON_RESORT_FRENCH_GRAY_UNCOATED_PMS)) {
            logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "FrenchGray_U_pms", TransferringAssetStatus.DONE);
            String propertyLogoSheratonResortFrenchGrayUncoatedPMS = "";
            if (logo != null && !logo.isEmpty()) {
                propertyLogoSheratonResortFrenchGrayUncoatedPMS = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
                attributes.add(new Attribute(58,
                        PropertyVariantFields.PROPERTY_LOGO_SHERATON_RESORT_FRENCH_GRAY_UNCOATED_PMS,
                        "MEDIA",
                        propertyLogoSheratonResortFrenchGrayUncoatedPMS));
            }
        }
        if (propertyVariantFields.contains(PropertyVariantFields.PROPERTY_LOGO_SHERATON_TUNGSTEN_COATED_CMYK)) {
            logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "Tungsten_C_cmyk", TransferringAssetStatus.DONE);
            String propertyLogoSheratonTungstenCoatedCMYK = "";
            if (logo != null && !logo.isEmpty()) {
                propertyLogoSheratonTungstenCoatedCMYK = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
                attributes.add(new Attribute(59,
                        PropertyVariantFields.PROPERTY_LOGO_SHERATON_TUNGSTEN_COATED_CMYK,
                        "MEDIA",
                        propertyLogoSheratonTungstenCoatedCMYK));
            }
        }
        if (propertyVariantFields.contains(PropertyVariantFields.PROPERTY_LOGO_SHERATON_TUNGSTEN_COATED_PMS)) {
            logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "Tungsten_C_pms", TransferringAssetStatus.DONE);
            String propertyLogoSheratonTungstenCoatedPMS = "";
            if (logo != null && !logo.isEmpty()) {
                propertyLogoSheratonTungstenCoatedPMS = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
                attributes.add(new Attribute(60,
                        PropertyVariantFields.PROPERTY_LOGO_SHERATON_TUNGSTEN_COATED_PMS,
                        "MEDIA",
                        propertyLogoSheratonTungstenCoatedPMS));
            }
        }
        if (propertyVariantFields.contains(PropertyVariantFields.PROPERTY_LOGO_SHERATON_TUNGSTEN_RGB)) {
            logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "Tungsten_rgb", TransferringAssetStatus.DONE);
            String propertyLogoSheratonTungstenRGB = "";
            if (logo != null && !logo.isEmpty()) {
                propertyLogoSheratonTungstenRGB = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
                attributes.add(new Attribute(61,
                        PropertyVariantFields.PROPERTY_LOGO_SHERATON_TUNGSTEN_RGB,
                        "MEDIA",
                        propertyLogoSheratonTungstenRGB));
            }
        }
        if (propertyVariantFields.contains(PropertyVariantFields.PROPERTY_LOGO_SHERATON_TUNGSTEN_UNCOATED_CMYK)) {
            logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "Tungsten_U_cmyk", TransferringAssetStatus.DONE);
            String propertyLogoSheratonTungstenUncoatedCMYK = "";
            if (logo != null && !logo.isEmpty()) {
                propertyLogoSheratonTungstenUncoatedCMYK = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
                attributes.add(new Attribute(62,
                        PropertyVariantFields.PROPERTY_LOGO_SHERATON_TUNGSTEN_UNCOATED_CMYK,
                        "MEDIA",
                        propertyLogoSheratonTungstenUncoatedCMYK));
            }
        }
        if (propertyVariantFields.contains(PropertyVariantFields.PROPERTY_LOGO_SHERATON_TUNGSTEN_UNCOATED_PMS)) {
            logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "Tungsten_U_pms", TransferringAssetStatus.DONE);
            String propertyLogoSheratonTungstenUncoatedPMS = "";
            if (logo != null && !logo.isEmpty()) {
                propertyLogoSheratonTungstenUncoatedPMS = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
                attributes.add(new Attribute(63,
                        PropertyVariantFields.PROPERTY_LOGO_SHERATON_TUNGSTEN_UNCOATED_PMS,
                        "MEDIA",
                        propertyLogoSheratonTungstenUncoatedPMS));
            }
        }
        if (propertyVariantFields.contains(PropertyVariantFields.PROPERTY_LOGO_US_NAVY)) {
            logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "usna", TransferringAssetStatus.DONE);
            String propertyLogoUSNavy = "";
            if (logo != null && !logo.isEmpty()) {
                propertyLogoUSNavy = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
                attributes.add(new Attribute(64,
                        PropertyVariantFields.PROPERTY_LOGO_US_NAVY,
                        "MEDIA",
                        propertyLogoUSNavy));
            }
        }

        if (propertyVariantFields.contains(PropertyVariantFields.AFFILIATE_NAME)) {
            attributes.add(new Attribute(1,
                    PropertyVariantFields.AFFILIATE_NAME,
                    "TEXT",
                    malPropertyEntity.getName()));
        }
        if (propertyVariantFields.contains(PropertyVariantFields.AFFILIATES_CODE)) {
            attributes.add(new Attribute(2,
                    PropertyVariantFields.AFFILIATES_CODE,
                    "TEXT",
                    malPropertyEntity.getPropertyId()));
        }
        if (propertyVariantFields.contains(PropertyVariantFields.PROPERTY_STATE)) {
            attributes.add(new Attribute(3,
                    PropertyVariantFields.PROPERTY_STATE,
                    "TEXT",
                    malPropertyEntity.getState()));
        }
        if (propertyVariantFields.contains(PropertyVariantFields.COMBINED_ADDRESS)) {
            attributes.add(new Attribute(34,
                    PropertyVariantFields.COMBINED_ADDRESS,
                    "RICHTEXT",
                    combinedAddressFields[0]));
        }
        if (propertyVariantFields.contains(PropertyVariantFields.COMBINED_ADDRESS_ADDRESS1)) {
            attributes.add(new Attribute(35,
                    PropertyVariantFields.COMBINED_ADDRESS_ADDRESS1,
                    "RICHTEXT",
                    combinedAddressFields[1]));
        }
        if (propertyVariantFields.contains(PropertyVariantFields.COMBINED_ADDRESS_ADDRESS1_BOLD)) {
            attributes.add(new Attribute(36,
                    PropertyVariantFields.COMBINED_ADDRESS_ADDRESS1_BOLD,
                    "RICHTEXT",
                    combinedAddressFields[2]));
        }
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
                    "RICHTEXT",
                    result));
        }
        if (propertyVariantFields.contains(PropertyVariantFields.ADDRESS)) {
            attributes.add(new Attribute(4,
                    PropertyVariantFields.ADDRESS,
                    "TEXT",
                    malPropertyEntity.getAddress()));
        }
        if (propertyVariantFields.contains(PropertyVariantFields.STREET)) {
            attributes.add(new Attribute(5,
                    PropertyVariantFields.STREET,
                    "TEXT",
                    malPropertyEntity.getAddress2()));
        }
        if (propertyVariantFields.contains(PropertyVariantFields.ZIP)) {
            attributes.add(new Attribute(6,
                    PropertyVariantFields.ZIP,
                    "TEXT",
                    malPropertyEntity.getZip()));
        }
        if (propertyVariantFields.contains(PropertyVariantFields.CITY)) {
            attributes.add(new Attribute(7,
                    PropertyVariantFields.CITY,
                    "TEXT",
                    malPropertyEntity.getCity()));
        }
        if (propertyVariantFields.contains(PropertyVariantFields.COUNTRY)) {
            attributes.add(new Attribute(8,
                    PropertyVariantFields.COUNTRY,
                    "TEXT",
                    malPropertyEntity.getCountry()));
        }
        if (propertyVariantFields.contains(PropertyVariantFields.PROPERTY_URL)) {
            attributes.add(new Attribute(9,
                    PropertyVariantFields.PROPERTY_URL,
                    "TEXT",
                    malPropertyEntity.getUrl()));
        }
        if (propertyVariantFields.contains(PropertyVariantFields.PROPERTY_TELEPHONE)) {
            attributes.add(new Attribute(10,
                    PropertyVariantFields.PROPERTY_TELEPHONE,
                    "TEXT",
                    malPropertyEntity.getTelephone()));
        }
        if (propertyVariantFields.contains(PropertyVariantFields.LATITUDE)) {
            attributes.add(new Attribute(13,
                    PropertyVariantFields.LATITUDE,
                    "TEXT",
                    malPropertyEntity.getLatitude()));
        }
        if (propertyVariantFields.contains(PropertyVariantFields.LONGITUDE)) {
            attributes.add(new Attribute(14,
                    PropertyVariantFields.LONGITUDE,
                    "TEXT",
                    malPropertyEntity.getLongitude()));
        }
        addRow(sheet, propertyVariant.getStructureName(), malPropertyEntity.getPropertyId(), malPropertyEntity.getName(), gsonWithNulls.toJson(attributes), null);
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


    private void createStructuresSheet(final Workbook workbook) throws Exception {

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

            row.createCell(colIndex++).setCellValue("{\"default\":\"" + malPropertyVariant.getStructureName() + "\"}");

            row.createCell(colIndex++).setCellValue("DEFAULT");

            colIndex++;
            colIndex++;

            row.createCell(colIndex++).setCellValue("MULTI_OBJECT_AFFILIATE_STRUCTURE");

            colIndex++;

            if (malPropertyVariant.isBrandStructure()) {
                row.createCell(colIndex++).setCellValue("[{\"number\": 24,\"name\": \"Image\",\"label\": \"Image\",\"comment\": \"\",\"order\": 0,\"type\": \"MEDIA\",\"props\": null}]");
            } else {
                row.createCell(colIndex++).setCellValue("[{\"number\":3,\"name\":\"PropertyState\",\"label\":\"Property State\",\"comment\":\"\",\"order\":-1,\"type\":\"TEXT\",\"props\":null},{\"number\":9,\"name\":\"PropertyURL\",\"label\":\"Property URL\",\"comment\":\"\",\"order\":-1,\"type\":\"TEXT\",\"props\":null},{\"number\":10,\"name\":\"PropertyTelephone\",\"label\":\"Property Telephone\",\"comment\":\"\",\"order\":-1,\"type\":\"TEXT\",\"props\":null},{\"number\":11,\"name\":\"PropertyFacsimile\",\"label\":\"Property Facsimile\",\"comment\":\"\",\"order\":-1,\"type\":\"TEXT\",\"props\":null},{\"number\":13,\"name\":\"Latitude\",\"label\":\"Latitude\",\"comment\":\"\",\"order\":-1,\"type\":\"TEXT\",\"props\":null},{\"number\":14,\"name\":\"Longitude\",\"label\":\"Longitude\",\"comment\":\"\",\"order\":-1,\"type\":\"TEXT\",\"props\":null},{\"number\":24,\"name\":\"PropertyLogo1c\",\"label\":\"Property Logo 1c\",\"comment\":\"\",\"order\":-1,\"type\":\"MEDIA\",\"props\":null},{\"number\":25,\"name\":\"PropertyLogo4c\",\"label\":\"Property Logo 4c\",\"comment\":\"\",\"order\":-1,\"type\":\"MEDIA\",\"props\":null},{\"number\":26,\"name\":\"PropertyLogo4cb\",\"label\":\"Property Logo 4c-b\",\"comment\":\"\",\"order\":-1,\"type\":\"MEDIA\",\"props\":null},{\"number\":27,\"name\":\"PropertyLogo1cblack\",\"label\":\"Property Logo 1c-black\",\"comment\":\"\",\"order\":-1,\"type\":\"MEDIA\",\"props\":null},{\"number\":28,\"name\":\"PropertyLogopms\",\"label\":\"Property Logo pms\",\"comment\":\"\",\"order\":-1,\"type\":\"MEDIA\",\"props\":null},{\"number\":29,\"name\":\"PropertyLogopmsc\",\"label\":\"Property Logo pms-c\",\"comment\":\"\",\"order\":-1,\"type\":\"MEDIA\",\"props\":null},{\"number\":30,\"name\":\"PropertyLogo4cc\",\"label\":\"Property Logo 4c-c\",\"comment\":\"\",\"order\":-1,\"type\":\"MEDIA\",\"props\":null},{\"number\":31,\"name\":\"PropertyLogokod\",\"label\":\"Property Logo ko-d\",\"comment\":\"\",\"order\":-1,\"type\":\"MEDIA\",\"props\":null},{\"number\":32,\"name\":\"PropertyLogoblackK\",\"label\":\"Property Logo black-K\",\"comment\":\"\",\"order\":-1,\"type\":\"MEDIA\",\"props\":null},{\"number\":33,\"name\":\"PropertyLogo4cK\",\"label\":\"Property Logo 4c-K\",\"comment\":\"\",\"order\":-1,\"type\":\"MEDIA\",\"props\":null},{\"number\":34,\"name\":\"CombinedAddress\",\"label\":\"Combined Address\",\"comment\":\"\",\"order\":-1,\"type\":\"RICHTEXT\",\"props\":null},{\"number\":39,\"name\":\"PropertyLogoSheratonBlack\",\"label\":\"Property Logo Sheraton Black\",\"comment\":\"\",\"order\":0,\"type\":\"MEDIA\",\"props\":null},{\"number\":40,\"name\":\"PropertyLogoSheratonDusk\",\"label\":\"Property Logo Sheraton Dusk\",\"comment\":\"\",\"order\":0,\"type\":\"MEDIA\",\"props\":null},{\"number\":41,\"name\":\"PropertyLogoSheratonGraphiteCodedCMYK\",\"label\":\"Property Logo Sheraton Graphite Coded CMYK\",\"comment\":\"\",\"order\":0,\"type\":\"MEDIA\",\"props\":null},{\"number\":42,\"name\":\"PropertyLogoSheratonGraphiteCodedPMS\",\"label\":\"Property Logo Sheraton Graphite Coded PMS\",\"comment\":\"\",\"order\":0,\"type\":\"MEDIA\",\"props\":null},{\"number\":43,\"name\":\"PropertyLogoSheratonGraphiteRGB\",\"label\":\"Property Logo Sheraton Graphite RGB\",\"comment\":\"\",\"order\":0,\"type\":\"MEDIA\",\"props\":null},{\"number\":44,\"name\":\"PropertyLogoSheratonGraphiteUncoatedCMYK\",\"label\":\"Property Logo Sheraton Graphite Uncoated CMYK\",\"comment\":\"\",\"order\":0,\"type\":\"MEDIA\",\"props\":null},{\"number\":45,\"name\":\"PropertyLogoSheratonGraphiteUncoatedPMS\",\"label\":\"Property Logo Sheraton Graphite Uncoated PMS\",\"comment\":\"\",\"order\":0,\"type\":\"MEDIA\",\"props\":null},{\"number\":46,\"name\":\"PropertyLogoSheratonIvory\",\"label\":\"Property Logo Sheraton Ivory\",\"comment\":\"\",\"order\":0,\"type\":\"MEDIA\",\"props\":null},{\"number\":47,\"name\":\"PropertyLogoSheratonKnockout\",\"label\":\"Property Logo Sheraton Knockout\",\"comment\":\"\",\"order\":0,\"type\":\"MEDIA\",\"props\":null},{\"number\":48,\"name\":\"PropertyLogoSheratonLogoSpecs\",\"label\":\"Property Logo Sheraton Logo Specs\",\"comment\":\"\",\"order\":0,\"type\":\"MEDIA\",\"props\":null},{\"number\":49,\"name\":\"PropertyLogoSheratonOysterCoatedCMYK\",\"label\":\"Property Logo Sheraton Oyster Coated CMYK\",\"comment\":\"\",\"order\":0,\"type\":\"MEDIA\",\"props\":null},{\"number\":50,\"name\":\"PropertyLogoSheratonOysterCoatedPMS\",\"label\":\"Property Logo Sheraton Oyster Coated PMS\",\"comment\":\"\",\"order\":0,\"type\":\"MEDIA\",\"props\":null},{\"number\":51,\"name\":\"PropertyLogoSheratonOysterRGB\",\"label\":\"Property Logo Sheraton Oyster RGB\",\"comment\":\"\",\"order\":0,\"type\":\"MEDIA\",\"props\":null},{\"number\":52,\"name\":\"PropertyLogoSheratonOysterUncoatedCMYK\",\"label\":\"Property Logo Sheraton Oyster Uncoated CMYK\",\"comment\":\"\",\"order\":0,\"type\":\"MEDIA\",\"props\":null},{\"number\":53,\"name\":\"PropertyLogoSheratonOysterUncoatedPMS\",\"label\":\"Property Logo Sheraton Oyster Uncoated PMS\",\"comment\":\"\",\"order\":0,\"type\":\"MEDIA\",\"props\":null},{\"number\":54,\"name\":\"PropertyLogoSheratonResortFrenchGrayCoatedCMYK\",\"label\":\"Property Logo Sheraton Resort French Gray Coated CMYK\",\"comment\":\"\",\"order\":0,\"type\":\"MEDIA\",\"props\":null},{\"number\":55,\"name\":\"PropertyLogoSheratonResortFrenchGrayCoatedPMS\",\"label\":\"Property Logo Sheraton Resort French Gray Coated PMS\",\"comment\":\"\",\"order\":0,\"type\":\"MEDIA\",\"props\":null},{\"number\":56,\"name\":\"PropertyLogoSheratonResortFrenchGrayRGB\",\"label\":\"Property Logo Sheraton Resort French Gray RGB\",\"comment\":\"\",\"order\":0,\"type\":\"MEDIA\",\"props\":null},{\"number\":57,\"name\":\"PropertyLogoSheratonResortFrenchGrayUncoatedCMYK\",\"label\":\"Property Logo Sheraton Resort French Gray Uncoated CMYK\",\"comment\":\"\",\"order\":0,\"type\":\"MEDIA\",\"props\":null},{\"number\":58,\"name\":\"PropertyLogoSheratonResortFrenchGrayUncoatedPMS\",\"label\":\"Property Logo Sheraton Resort French Gray Uncoated PMS\",\"comment\":\"\",\"order\":0,\"type\":\"MEDIA\",\"props\":null},{\"number\":59,\"name\":\"PropertyLogoSheratonTungstenCoatedCMYK\",\"label\":\"Property Logo Sheraton Tungsten Coated CMYK\",\"comment\":\"\",\"order\":0,\"type\":\"MEDIA\",\"props\":null},{\"number\":60,\"name\":\"PropertyLogoSheratonTungstenCoatedPMS\",\"label\":\"Property Logo Sheraton Tungsten Coated PMS\",\"comment\":\"\",\"order\":0,\"type\":\"MEDIA\",\"props\":null},{\"number\":61,\"name\":\"PropertyLogoSheratonTungstenRGB\",\"label\":\"Property Logo Sheraton Tungsten RGB\",\"comment\":\"\",\"order\":0,\"type\":\"MEDIA\",\"props\":null},{\"number\":62,\"name\":\"PropertyLogoSheratonTungstenUncoatedCMYK\",\"label\":\"Property Logo Sheraton Tungsten Uncoated CMYK\",\"comment\":\"\",\"order\":0,\"type\":\"MEDIA\",\"props\":null},{\"number\":63,\"name\":\"PropertyLogoSheratonTungstenUncoatedPMS\",\"label\":\"Property Logo Sheraton Tungsten Uncoated PMS\",\"comment\":\"\",\"order\":0,\"type\":\"MEDIA\",\"props\":null},{\"number\":64,\"name\":\"PropertyLogoUSNavy\",\"label\":\"Property Logo US Navy\",\"comment\":\"\",\"order\":0,\"type\":\"MEDIA\",\"props\":null},{\"number\":2,\"name\":\"AFFILIATES_CODE\",\"label\":\"Property Number\",\"comment\":\"\",\"order\":1,\"type\":\"TEXT\",\"props\":null},{\"number\":1,\"name\":\"AFFILIATE_NAME\",\"label\":\"Property Name\",\"comment\":\"\",\"order\":2,\"type\":\"TEXT\",\"props\":null},{\"number\":4,\"name\":\"ADDRESS\",\"label\":\"Property Address\",\"comment\":\"\",\"order\":4,\"type\":\"TEXT\",\"props\":null},{\"number\":5,\"name\":\"STREET\",\"label\":\"Property Street\",\"comment\":\"\",\"order\":5,\"type\":\"TEXT\",\"props\":null},{\"number\":6,\"name\":\"ZIP\",\"label\":\"Property Zip Code\",\"comment\":\"\",\"order\":6,\"type\":\"TEXT\",\"props\":null},{\"number\":7,\"name\":\"CITY\",\"label\":\"Property City\",\"comment\":\"\",\"order\":7,\"type\":\"TEXT\",\"props\":null},{\"number\":8,\"name\":\"COUNTRY\",\"label\":\"Property Country\",\"comment\":\"\",\"order\":8,\"type\":\"TEXT\",\"props\":null},{\"number\":35,\"name\":\"CombinedAddressAddress1\",\"label\":\"CombinedAddress - Address 1\",\"comment\":\"\",\"order\":-1,\"type\":\"RICHTEXT\",\"props\":null},\n" +
                        "{\"number\":36,\"name\":\"CombinedAddressAddress1Bold\",\"label\":\"CombinedAddress - Address 1 - Bold\",\"comment\":\"\",\"order\":-1,\"type\":\"RICHTEXT\",\"props\":null},\n" +
                        "{\"number\":37,\"name\":\"CombinedAddressAddress11stand2ndLineBold\",\"label\":\"CombinedAddress - Address 1 - 1st and 2nd Line Bold\",\"comment\":\"\",\"order\":-1,\"type\":\"RICHTEXT\",\"props\":null}]");

                // substructure floor plans
                rowIndex = createSubStructure(sheet, rowIndex, malPropertyVariant, malPropertyVariant.getSubNameFloorPlans(), "[{\"number\":24,\"name\":\"Floorplan1\",\"label\":\"Floorplan 1\",\"comment\":\"\",\"order\":-1,\"type\":\"MEDIA\",\"props\":null},{\"number\":25,\"name\":\"Floorplan2\",\"label\":\"Floorplan 2\",\"comment\":\"\",\"order\":-1,\"type\":\"MEDIA\",\"props\":null},{\"number\":26,\"name\":\"Floorplan3\",\"label\":\"Floorplan 3\",\"comment\":\"\",\"order\":-1,\"type\":\"MEDIA\",\"props\":null},{\"number\":27,\"name\":\"Floorplan4\",\"label\":\"Floorplan 4\",\"comment\":\"\",\"order\":-1,\"type\":\"MEDIA\",\"props\":null},{\"number\":28,\"name\":\"Floorplan5\",\"label\":\"Floorplan 5\",\"comment\":\"\",\"order\":-1,\"type\":\"MEDIA\",\"props\":null}]");
                // substructure maps
                rowIndex = createSubStructure(sheet, rowIndex, malPropertyVariant, malPropertyVariant.getSubNameMaps(), "[{\"number\":24,\"name\":\"Map1\",\"label\":\"Map 1\",\"comment\":\"\",\"order\":-1,\"type\":\"MEDIA\",\"props\":null},{\"number\":25,\"name\":\"Map2\",\"label\":\"Map 2\",\"comment\":\"\",\"order\":-1,\"type\":\"MEDIA\",\"props\":null},{\"number\":26,\"name\":\"Map3\",\"label\":\"Map 3\",\"comment\":\"\",\"order\":-1,\"type\":\"MEDIA\",\"props\":null},{\"number\":27,\"name\":\"Map4\",\"label\":\"Map 4\",\"comment\":\"\",\"order\":-1,\"type\":\"MEDIA\",\"props\":null},{\"number\":28,\"name\":\"Map5\",\"label\":\"Map 5\",\"comment\":\"\",\"order\":-1,\"type\":\"MEDIA\",\"props\":null}]");
                // substructure images
                rowIndex = createSubStructure(sheet, rowIndex, malPropertyVariant, malPropertyVariant.getSubName(), "[{\"number\":24,\"name\":\"Image1\",\"label\":\"Image 1\",\"comment\":\"\",\"order\":-1,\"type\":\"MEDIA\",\"props\":null},{\"number\":25,\"name\":\"Image2\",\"label\":\"Image 2\",\"comment\":\"\",\"order\":-1,\"type\":\"MEDIA\",\"props\":null},{\"number\":26,\"name\":\"Image3\",\"label\":\"Image 3\",\"comment\":\"\",\"order\":-1,\"type\":\"MEDIA\",\"props\":null},{\"number\":27,\"name\":\"Image4\",\"label\":\"Image 4\",\"comment\":\"\",\"order\":-1,\"type\":\"MEDIA\",\"props\":null},{\"number\":28,\"name\":\"Image5\",\"label\":\"Image 5\",\"comment\":\"\",\"order\":-1,\"type\":\"MEDIA\",\"props\":null}]");
            }
        }
    }

    private int createSubStructure(Sheet sheet, int rowIndex, MALPropertyVariant malPropertyVariant, String
            subStructureName, String s) {
        if (subStructureName != null) {
            Row row = sheet.createRow(rowIndex++);

            int colIndex = 0;

            row.createCell(colIndex++).setCellValue(subStructureName);

            row.createCell(colIndex++).setCellValue("{\"default\":\"" + subStructureName + "\"}");

            row.createCell(colIndex++).setCellValue("DEFAULT");

            row.createCell(colIndex++).setCellValue(malPropertyVariant.getStructureName());

            colIndex++;

            row.createCell(colIndex++).setCellValue("NON_AFFILIATE_STRUCTURE");

            colIndex++;

            row.createCell(colIndex++).setCellValue(s);
        }
        return rowIndex;
    }

    public class AttributeShort {
        private int number;

        private String attributeName;

        private String value;

        private String type;

        public AttributeShort() {
        }

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

        public void setAttributeName(String attributeName) {
            this.attributeName = attributeName;
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

    public class Attribute {
        private int number;

        private String attributeName;

        private String label;

        private String comment;

        private int order;

        private String type;

        private Props props;

        private String value;

        public Attribute() {
        }

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

        public void setAttributeName(String attributeName) {
            this.attributeName = attributeName;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public int getOrder() {
            return order;
        }

        public void setOrder(int order) {
            this.order = order;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Props getProps() {
            return props;
        }

        public void setProps(Props props) {
            this.props = props;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public class Props {

        private Integer ckeditor_id;

        public Props() {
        }

        public Props(final Integer ckeditor_id) {
            this.ckeditor_id = ckeditor_id;
        }

        public Integer getCkeditor_id() {
            return ckeditor_id;
        }

        public void setCkeditor_id(Integer ckeditor_id) {
            this.ckeditor_id = ckeditor_id;
        }
    }
}