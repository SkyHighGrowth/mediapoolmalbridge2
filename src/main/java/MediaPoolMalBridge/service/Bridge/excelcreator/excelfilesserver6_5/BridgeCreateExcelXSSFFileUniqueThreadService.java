package MediaPoolMalBridge.service.Bridge.excelcreator.excelfilesserver6_5;

import MediaPoolMalBridge.model.MAL.MALAssetStructures;
import MediaPoolMalBridge.model.MAL.propertyvariants.MALPropertyVariant;
import MediaPoolMalBridge.persistence.entity.Bridge.AssetEntity;
import MediaPoolMalBridge.persistence.entity.Bridge.AssetJsonedValuesEntity;
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

    private void createFile(String fileName, LinkedHashSet<MALPropertyPair> allProperties) {
        try {
            final Workbook workbook = new XSSFWorkbook();
            createStructuresSheet(workbook);
            createObjectsSheet(workbook, allProperties);
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
        int count = 0;
        int countProperties = 0;

        for (MALPropertyVariant propertyVariant : propertyVariants) {
            final String brandName = propertyVariant.getBrandName();
            final List<MALPropertyEntity> malPropertyEntities = malPropertyRepository.findByBrandAndMalPropertyStatus(brandName, MALPropertyStatus.OBSERVED);
            for (MALPropertyEntity malPropertyEntity : malPropertyEntities) {
                if (countProperties == appConfig.getFileMaxRecords()) {
                    count++;
                    String fileName = String.format("DataStructures_%s.xlsx", count);
                    createFile(fileName, malPropertyPairSet);
                    malPropertyPairSet.clear();
                    countProperties = 0;
                }
                MALPropertyPair malProperty = new MALPropertyPair(malPropertyEntity, propertyVariant);
                malPropertyPairSet.add(malProperty);
                countProperties++;
            }
        }
        if (countProperties > 0) {
            count++;
            String fileName = String.format("DataStructures_%s.xlsx", count);
            createFile(fileName, malPropertyPairSet);
        }
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

        List<String> structureNameAlreadyAdded = new ArrayList<>();
        for (MALPropertyPair malProperty : malPropertyPairSet) {
            MALPropertyEntity malPropertyEntity = malProperty.getMalPropertyEntity();
            MALPropertyVariant propertyVariant = malProperty.getMalPropertyVariant();
            try {
                if (propertyVariant.isBrandStructure()) {
                    if (malPropertyEntity.getPrimaryPropertyImage() > 0) {
                        String primaryPropertyImageId = String.valueOf(malPropertyEntity.getPrimaryPropertyImage());
                        final List<AssetEntity> assetEntities =
                                assetRepository.findAllByBrandIdAndMalAssetIdAndAssetTypeIdAndTransferringAssetStatus(
                                        propertyVariant.getBrandId(), primaryPropertyImageId, "1", TransferringAssetStatus.DONE);
                        int order = 1;

                        if (assetEntities != null && !assetEntities.isEmpty() && (structureNameAlreadyAdded.isEmpty() || !structureNameAlreadyAdded.contains(propertyVariant.getStructureName()))) {
                            for (final AssetEntity assetEntity : assetEntities) {
                                if (checkIfAssetEntityCollectionExist(assetEntity, propertyVariant)) {
                                    final List<AttributeShort> attributes = new ArrayList<>();
                                    attributes.add(new AttributeShort(order,
                                            "Image" + order,
                                            "[MD5_HASH=" + assetEntity.getBmMd5Hash() + ";MEDIA_GUID=" + assetEntity.getBmAssetId() + ";]",
                                            "MEDIA"));
                                    addRow(sheet, propertyVariant.getStructureName(), "", assetEntity.getCaption(), gsonWithNulls.toJson(attributes), assetEntity.getMalAssetId());
                                }
                            }
                            ++order;
                            structureNameAlreadyAdded.add(propertyVariant.getStructureName());
                        }
                    }
                } else {
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

    private boolean checkIfAssetEntityCollectionExist(AssetEntity assetEntity, MALPropertyVariant propertyVariant) {
        AssetJsonedValuesEntity assetJsonedValuesEntity = assetJsonedValuesRepository.findAssetJsonedValuesEntitiesById(assetEntity.getAssetJsonedValuesEntity().getId());
        String collectionName = propertyVariant.getCollection();

        return assetJsonedValuesEntity.getBmUploadMetadataArgumentJson().contains("Collections/" + collectionName);
    }


    private void digestFloorTypes(final Sheet sheet, final MALPropertyVariant propertyVariant,
                                  final MALPropertyEntity malPropertyEntity) throws Exception {
        final String[] floorTypes = new String[2];
        floorTypes[0] = "Fmt";
        floorTypes[1] = "Fgr";
        int order = 1;
        for (final String floorType : floorTypes) {
            final List<AssetEntity> assetEntities = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "13", floorType, TransferringAssetStatus.DONE, getMidnightBridgeLookInThePast());
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
            final List<AssetEntity> assetEntities = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "12", mapColor, TransferringAssetStatus.DONE, getMidnightBridgeLookInThePast());
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
        final List<AssetEntity> assetEntities = assetRepository.findPropertyAssets(malPropertyEntity.getPropertyId(), "1", TransferringAssetStatus.DONE, getMidnightBridgeLookInThePast());
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

        name = name.replaceAll("\"","\\\"");
        if (StringUtils.isNotBlank(malAssetId)) {
            row.createCell(colIndex++).setCellValue(subName);
            row.createCell(colIndex++).setCellValue(malAssetId);
            row.createCell(colIndex++).setCellValue("{\"default\":\"" + malAssetId + " - " + name + "\"}");
            row.createCell(colIndex++).setCellValue(propertyId);
            row.createCell(colIndex++).setCellValue("EDIT_AND_ADD");
            row.createCell(colIndex++).setCellValue("");
            row.createCell(colIndex++).setCellValue(jsonedAttributes);
        } else {
            row.createCell(colIndex++).setCellValue(subName);
            row.createCell(colIndex++).setCellValue(propertyId);
            row.createCell(colIndex++).setCellValue("{\"default\":\"" + propertyId + " - " + name + "\"}");
            row.createCell(colIndex++).setCellValue("");
            row.createCell(colIndex++).setCellValue("EDIT_AND_ADD");
            row.createCell(colIndex++).setCellValue("");
            row.createCell(colIndex++).setCellValue(jsonedAttributes);
        }
    }

    private void digestLogos(final Sheet sheet, MALPropertyVariant propertyVariant,
                             final MALPropertyEntity malPropertyEntity, final String[] combinedAddressFields) throws Exception {
        final List<Attribute> attributes = new ArrayList<>();
        List<AssetEntity> logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "ko", TransferringAssetStatus.DONE, getMidnightBridgeLookInThePast());
        String propertyLogo1c = "";
        if (logo != null && !logo.isEmpty()) {
            propertyLogo1c = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
            attributes.add(new Attribute(24,
                    "PropertyLogo1c",
                    "MEDIA",
                    propertyLogo1c));
        }
        logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "cmyk", TransferringAssetStatus.DONE, getMidnightBridgeLookInThePast());
        String propertyLogo4c = "";
        if (logo != null && !logo.isEmpty()) {
            propertyLogo4c = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
            attributes.add(new Attribute(25,
                    "PropertyLogo4c",
                    "MEDIA",
                    propertyLogo4c));
        }
        logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "cmyk-B", TransferringAssetStatus.DONE, getMidnightBridgeLookInThePast());
        String propertyLogocmykb = "";
        if (logo != null && !logo.isEmpty()) {
            propertyLogocmykb = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
            attributes.add(new Attribute(26,
                    "PropertyLogo4cb",
                    "MEDIA",
                    propertyLogocmykb));
        }
        logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "k", TransferringAssetStatus.DONE, getMidnightBridgeLookInThePast());
        String propertyLogo1cBlack = "";
        if (logo != null && !logo.isEmpty()) {
            propertyLogo1cBlack = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
            attributes.add(new Attribute(27,
                    "PropertyLogo1cblack",
                    "MEDIA",
                    propertyLogo1cBlack));
        }
        logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "pms", TransferringAssetStatus.DONE, getMidnightBridgeLookInThePast());
        String propertyLogoPMS = "";
        if (logo != null && !logo.isEmpty()) {
            propertyLogoPMS = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
            attributes.add(new Attribute(28,
                    "PropertyLogopms",
                    "MEDIA",
                    propertyLogoPMS));
        }
        logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "pms-C", TransferringAssetStatus.DONE, getMidnightBridgeLookInThePast());
        String propertyLogoPMSC = "";
        if (logo != null && !logo.isEmpty()) {
            propertyLogoPMSC = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
            attributes.add(new Attribute(29,
                    "PropertyLogopmsc",
                    "MEDIA",
                    propertyLogoPMSC));
        }
        logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "cmyk-C", TransferringAssetStatus.DONE, getMidnightBridgeLookInThePast());
        String propertyLogocmykC = "";
        if (logo != null && !logo.isEmpty()) {
            propertyLogocmykC = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
            attributes.add(new Attribute(30,
                    "PropertyLogo4cc",
                    "MEDIA",
                    propertyLogocmykC));
        }
        logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "ko-D", TransferringAssetStatus.DONE, getMidnightBridgeLookInThePast());
        String propertyLogokoD = "";
        if (logo != null && !logo.isEmpty()) {
            propertyLogokoD = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
            attributes.add(new Attribute(31,
                    "PropertyLogokod",
                    "MEDIA",
                    propertyLogokoD));
        }

        //this should be checked particularly
        logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "k", TransferringAssetStatus.DONE, getMidnightBridgeLookInThePast());
        String propertyLogoBlackK = "";
        if (logo != null && !logo.isEmpty()) {
            propertyLogoBlackK = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
            attributes.add(new Attribute(32,
                    "PropertyLogoblackK",
                    "MEDIA",
                    propertyLogoBlackK));
        }
        logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "cmyk-K", TransferringAssetStatus.DONE, getMidnightBridgeLookInThePast());
        String propertyLogo4cK = "";
        if (logo != null && !logo.isEmpty()) {
            propertyLogo4cK = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
            attributes.add(new Attribute(33,
                    "PropertyLogo4cK",
                    "MEDIA",
                    propertyLogoBlackK));
        }
        logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "Sheraton Black logo-1", TransferringAssetStatus.DONE, getMidnightBridgeLookInThePast());
        String propertyLogoSheratonBlack = "";
        if (logo != null && !logo.isEmpty()) {
            propertyLogoSheratonBlack = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
            attributes.add(new Attribute(39,
                    "PropertyLogoSheratonBlack",
                    "MEDIA",
                    propertyLogoSheratonBlack));
        }
        logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "dusk", TransferringAssetStatus.DONE, getMidnightBridgeLookInThePast());
        String propertyLogoSheratonDusk = "";
        if (logo != null && !logo.isEmpty()) {
            propertyLogoSheratonDusk = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
            attributes.add(new Attribute(40,
                    "propertyLogoSheratonDusk",
                    "MEDIA",
                    propertyLogoSheratonDusk));
        }
        logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "Graphite_C_cmyk", TransferringAssetStatus.DONE, getMidnightBridgeLookInThePast());
        String propertyLogoSheratonGraphiteCodedCMYK = "";
        if (logo != null && !logo.isEmpty()) {
            propertyLogoSheratonGraphiteCodedCMYK = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
            attributes.add(new Attribute(40,
                    "PropertyLogoSheratonGraphiteCodedCMYK",
                    "MEDIA",
                    propertyLogoSheratonGraphiteCodedCMYK));
        }
        logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "Graphite_C_pms", TransferringAssetStatus.DONE, getMidnightBridgeLookInThePast());
        String propertyLogoSheratonGraphiteCodedPMS = "";
        if (logo != null && !logo.isEmpty()) {
            propertyLogoSheratonGraphiteCodedPMS = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
            attributes.add(new Attribute(42,
                    "PropertyLogoSheratonGraphiteCodedPMS",
                    "MEDIA",
                    propertyLogoSheratonGraphiteCodedPMS));
        }
        logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "Graphite_rgb", TransferringAssetStatus.DONE, getMidnightBridgeLookInThePast());
        String propertyLogoSheratonGraphiteRGB = "";
        if (logo != null && !logo.isEmpty()) {
            propertyLogoSheratonGraphiteRGB = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
            attributes.add(new Attribute(43,
                    "PropertyLogoSheratonGraphiteRGB",
                    "MEDIA",
                    propertyLogoSheratonGraphiteRGB));
        }
        logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "Graphite_U_cmyk", TransferringAssetStatus.DONE, getMidnightBridgeLookInThePast());
        String propertyLogoSheratonGraphiteUncoatedCMYK = "";
        if (logo != null && !logo.isEmpty()) {
            propertyLogoSheratonGraphiteUncoatedCMYK = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
            attributes.add(new Attribute(44,
                    "PropertyLogoSheratonGraphiteUncoatedCMYK",
                    "MEDIA",
                    propertyLogoSheratonGraphiteUncoatedCMYK));
        }
        logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "Graphite_U_pms", TransferringAssetStatus.DONE, getMidnightBridgeLookInThePast());
        String propertyLogoSheratonGraphiteUncoatedPMS = "";
        if (logo != null && !logo.isEmpty()) {
            propertyLogoSheratonGraphiteUncoatedPMS = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
            attributes.add(new Attribute(45,
                    "PropertyLogoSheratonGraphiteUncoatedPMS",
                    "MEDIA",
                    propertyLogoSheratonGraphiteUncoatedPMS));
        }
        logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "ivory", TransferringAssetStatus.DONE, getMidnightBridgeLookInThePast());
        String propertyLogoSheratonIvory = "";
        if (logo != null && !logo.isEmpty()) {
            propertyLogoSheratonIvory = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
            attributes.add(new Attribute(46,
                    "PropertyLogoSheratonIvory",
                    "MEDIA",
                    propertyLogoSheratonIvory));
        }
        logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "Knockout", TransferringAssetStatus.DONE, getMidnightBridgeLookInThePast());
        String propertyLogoSheratonKnockout = "";
        if (logo != null && !logo.isEmpty()) {
            propertyLogoSheratonKnockout = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
            attributes.add(new Attribute(47,
                    "PropertyLogoSheratonKnockout",
                    "MEDIA",
                    propertyLogoSheratonKnockout));
        }
        logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "Logo_Specs", TransferringAssetStatus.DONE, getMidnightBridgeLookInThePast());
        String propertyLogoSheratonLogoSpecs = "";
        if (logo != null && !logo.isEmpty()) {
            propertyLogoSheratonLogoSpecs = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
            attributes.add(new Attribute(47,
                    "PropertyLogoSheratonLogoSpecs",
                    "MEDIA",
                    propertyLogoSheratonLogoSpecs));
        }
        logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "Logo_Specs", TransferringAssetStatus.DONE, getMidnightBridgeLookInThePast());
        String propertyLogoSheratonOysterCoatedCMYK = "";
        if (logo != null && !logo.isEmpty()) {
            propertyLogoSheratonOysterCoatedCMYK = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
            attributes.add(new Attribute(49,
                    "PropertyLogoSheratonOysterCoatedCMYK",
                    "MEDIA",
                    propertyLogoSheratonOysterCoatedCMYK));
        }
        logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "Oyster_C_pms", TransferringAssetStatus.DONE, getMidnightBridgeLookInThePast());
        String propertyLogoSheratonOysterCoatedPMS = "";
        if (logo != null && !logo.isEmpty()) {
            propertyLogoSheratonOysterCoatedPMS = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
            attributes.add(new Attribute(50,
                    "PropertyLogoSheratonOysterCoatedPMS",
                    "MEDIA",
                    propertyLogoSheratonOysterCoatedPMS));
        }
        logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "Oyster_rgb", TransferringAssetStatus.DONE, getMidnightBridgeLookInThePast());
        String propertyLogoSheratonOysterRGB = "";
        if (logo != null && !logo.isEmpty()) {
            propertyLogoSheratonOysterRGB = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
            attributes.add(new Attribute(51,
                    "PropertyLogoSheratonOysterRGB",
                    "MEDIA",
                    propertyLogoSheratonOysterRGB));
        }
        logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "Oyster_U_cmyk", TransferringAssetStatus.DONE, getMidnightBridgeLookInThePast());
        String propertyLogoSheratonOysterUncoatedCMYK = "";
        if (logo != null && !logo.isEmpty()) {
            propertyLogoSheratonOysterUncoatedCMYK = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
            attributes.add(new Attribute(52,
                    "PropertyLogoSheratonOysterUncoatedCMYK",
                    "MEDIA",
                    propertyLogoSheratonOysterUncoatedCMYK));
        }
        logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "Oyster_U_pms", TransferringAssetStatus.DONE, getMidnightBridgeLookInThePast());
        String propertyLogoSheratonOysterUncoatedPMS = "";
        if (logo != null && !logo.isEmpty()) {
            propertyLogoSheratonOysterUncoatedPMS = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
            attributes.add(new Attribute(53,
                    "PropertyLogoSheratonOysterUncoatedPMS",
                    "MEDIA",
                    propertyLogoSheratonOysterUncoatedPMS));
        }
        logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "FrenchGray_C_cmyk", TransferringAssetStatus.DONE, getMidnightBridgeLookInThePast());
        String propertyLogoSheratonResortFrenchGrayCoatedCMYK = "";
        if (logo != null && !logo.isEmpty()) {
            propertyLogoSheratonResortFrenchGrayCoatedCMYK = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
            attributes.add(new Attribute(54,
                    "PropertyLogoSheratonResortFrenchGrayCoatedCMYK",
                    "MEDIA",
                    propertyLogoSheratonResortFrenchGrayCoatedCMYK));
        }
        logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "FrenchGray_C_pms", TransferringAssetStatus.DONE, getMidnightBridgeLookInThePast());
        String propertyLogoSheratonResortFrenchGrayCoatedPMS = "";
        if (logo != null && !logo.isEmpty()) {
            propertyLogoSheratonResortFrenchGrayCoatedPMS = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
            attributes.add(new Attribute(55,
                    "PropertyLogoSheratonResortFrenchGrayCoatedPMS",
                    "MEDIA",
                    propertyLogoSheratonResortFrenchGrayCoatedPMS));
        }
        logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "FrenchGray_rgb", TransferringAssetStatus.DONE, getMidnightBridgeLookInThePast());
        String propertyLogoSheratonResortFrenchGrayRGB = "";
        if (logo != null && !logo.isEmpty()) {
            propertyLogoSheratonResortFrenchGrayRGB = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
            attributes.add(new Attribute(56,
                    "PropertyLogoSheratonResortFrenchGrayRGB",
                    "MEDIA",
                    propertyLogoSheratonResortFrenchGrayRGB));
        }
        logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "FrenchGray_U_cmyk", TransferringAssetStatus.DONE, getMidnightBridgeLookInThePast());
        String propertyLogoSheratonResortFrenchGrayUncoatedCMYK = "";
        if (logo != null && !logo.isEmpty()) {
            propertyLogoSheratonResortFrenchGrayUncoatedCMYK = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
            attributes.add(new Attribute(57,
                    "PropertyLogoSheratonResortFrenchGrayUncoatedCMYK",
                    "MEDIA",
                    propertyLogoSheratonResortFrenchGrayUncoatedCMYK));
        }
        logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "FrenchGray_U_pms", TransferringAssetStatus.DONE, getMidnightBridgeLookInThePast());
        String propertyLogoSheratonResortFrenchGrayUncoatedPMS = "";
        if (logo != null && !logo.isEmpty()) {
            propertyLogoSheratonResortFrenchGrayUncoatedPMS = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
            attributes.add(new Attribute(58,
                    "PropertyLogoSheratonResortFrenchGrayUncoatedPMS",
                    "MEDIA",
                    propertyLogoSheratonResortFrenchGrayUncoatedPMS));
        }
        logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "Tungsten_C_cmyk", TransferringAssetStatus.DONE, getMidnightBridgeLookInThePast());
        String propertyLogoSheratonTungstenCoatedCMYK = "";
        if (logo != null && !logo.isEmpty()) {
            propertyLogoSheratonTungstenCoatedCMYK = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
            attributes.add(new Attribute(59,
                    "PropertyLogoSheratonTungstenCoatedCMYK",
                    "MEDIA",
                    propertyLogoSheratonTungstenCoatedCMYK));
        }
        logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "Tungsten_C_pms", TransferringAssetStatus.DONE, getMidnightBridgeLookInThePast());
        String propertyLogoSheratonTungstenCoatedPMS = "";
        if (logo != null && !logo.isEmpty()) {
            propertyLogoSheratonTungstenCoatedPMS = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
            attributes.add(new Attribute(60,
                    "PropertyLogoSheratonTungstenCoatedPMS",
                    "MEDIA",
                    propertyLogoSheratonTungstenCoatedPMS));
        }
        logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "Tungsten_rgb", TransferringAssetStatus.DONE, getMidnightBridgeLookInThePast());
        String propertyLogoSheratonTungstenRGB = "";
        if (logo != null && !logo.isEmpty()) {
            propertyLogoSheratonTungstenRGB = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
            attributes.add(new Attribute(61,
                    "PropertyLogoSheratonTungstenRGB",
                    "MEDIA",
                    propertyLogoSheratonTungstenRGB));
        }
        logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "Tungsten_U_cmyk", TransferringAssetStatus.DONE, getMidnightBridgeLookInThePast());
        String propertyLogoSheratonTungstenUncoatedCMYK = "";
        if (logo != null && !logo.isEmpty()) {
            propertyLogoSheratonTungstenUncoatedCMYK = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
            attributes.add(new Attribute(62,
                    "PropertyLogoSheratonTungstenUncoatedCMYK",
                    "MEDIA",
                    propertyLogoSheratonTungstenUncoatedCMYK));
        }
        logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "Tungsten_U_pms", TransferringAssetStatus.DONE, getMidnightBridgeLookInThePast());
        String propertyLogoSheratonTungstenUncoatedPMS = "";
        if (logo != null && !logo.isEmpty()) {
            propertyLogoSheratonTungstenUncoatedPMS = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
            attributes.add(new Attribute(63,
                    "PropertyLogoSheratonTungstenUncoatedPMS",
                    "MEDIA",
                    propertyLogoSheratonTungstenUncoatedPMS));
        }
        logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "usna", TransferringAssetStatus.DONE, getMidnightBridgeLookInThePast());
        String propertyLogoUSNavy = "";
        if (logo != null && !logo.isEmpty()) {
            propertyLogoUSNavy = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
            attributes.add(new Attribute(64,
                    "PropertyLogoUSNavy",
                    "MEDIA",
                    propertyLogoUSNavy));
        }

        attributes.add(new Attribute(1,
                "AFFILIATE_NAME",
                "TEXT",
                malPropertyEntity.getName()));
        attributes.add(new Attribute(2,
                "AFFILIATES_CODE",
                "TEXT",
                malPropertyEntity.getPropertyId()));


        attributes.add(new Attribute(3,
                "PropertyState",
                "TEXT",
                malPropertyEntity.getState()));
        attributes.add(new Attribute(34,
                "CombinedAddress",
                "RICHTEXT",
                combinedAddressFields[0]));
        attributes.add(new Attribute(35,
                "CombinedAddressAddress1",
                "RICHTEXT",
                combinedAddressFields[1]));
        attributes.add(new Attribute(36,
                "CombinedAddressAddress1Bold",
                "RICHTEXT",
                combinedAddressFields[2]));

        String result = null;
        if (combinedAddressFields[3] != null && combinedAddressFields[4] != null) {
            result = combinedAddressFields[3] + combinedAddressFields[4];
        } else if (combinedAddressFields[3] != null) {
            result = combinedAddressFields[3];
        } else if (combinedAddressFields[4] != null) {
            result = combinedAddressFields[4];
        }

        attributes.add(new Attribute(37,
                "CombinedAddressAddress11stand2ndLineBold",
                "RICHTEXT",
                result));
        attributes.add(new Attribute(4,
                "ADDRESS",
                "TEXT",
                malPropertyEntity.getAddress()));
        attributes.add(new Attribute(5,
                "STREET",
                "TEXT",
                malPropertyEntity.getAddress2()));
        attributes.add(new Attribute(6,
                "ZIP",
                "TEXT",
                malPropertyEntity.getZip()));
        attributes.add(new Attribute(7,
                "CITY",
                "TEXT",
                malPropertyEntity.getCity()));
        attributes.add(new Attribute(8,
                "COUNTRY",
                "TEXT",
                malPropertyEntity.getCountry()));
        attributes.add(new Attribute(9,
                "PropertyURL",
                "TEXT",
                malPropertyEntity.getUrl()));
        attributes.add(new Attribute(10,
                "PropertyTelephone",
                "TEXT",
                malPropertyEntity.getTelephone()));
        attributes.add(new Attribute(13,
                "Latitude",
                "TEXT",
                malPropertyEntity.getLatitude()));
        attributes.add(new Attribute(14,
                "Longitude",
                "TEXT",
                malPropertyEntity.getLongitude()));
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
