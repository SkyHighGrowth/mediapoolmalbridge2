package MediaPoolMalBridge.service.Bridge.excelcreator.excelfilesserver6_5;

import MediaPoolMalBridge.model.MAL.MALAssetStructures;
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
import jxl.Workbook;
import jxl.write.*;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class BridgeCreateExcelFileUniqueThreadService extends AbstractBridgeUniqueExcelService {

    protected static final String[] STRUCTURES_COLUMN_NAMES = {"List ID * (no editable)",
            "List name *", "Type * (no editable)", "ID of Parent list", "Name of default entry",
            "Affiliate type", "Replace deleted entries",
            "Show preview for list entry", "Attributes"};

    protected static final String[] OBJECTS_COLUMN_NAMES = {"List ID * (no editable)",
            "List Entry ID * (no editable)", "Display name *", "Name of parent entry",
            "State *", "Affiliate", "Attributes"};

    private MALAssetStructures assetStructures;

    public BridgeCreateExcelFileUniqueThreadService(final MALAssetStructures assetStructures )
    {
        this.assetStructures = assetStructures;
    }

    @Override
    protected void run()
    {
        writeToFile( "DataStructures.xls" );
    }

    private void writeToFile(final String fileName) {
        final Gson gsonWithNulls = new GsonBuilder().serializeNulls().create();

        try {
            WritableWorkbook workbook = Workbook.createWorkbook(new File(appConfig.getExcelDir() + fileName));
            WritableSheet sheet = workbook.createSheet("structures", 0);

            WritableCellFormat headerFormat = new WritableCellFormat();
            WritableFont font = new WritableFont(WritableFont.ARIAL, 16, WritableFont.NO_BOLD);
            headerFormat.setFont(font);

            int colIndex = 0;
            int rowIndex = 0;
            for (final String column : AFFILIATE_CODE_COLUMN_NAMES) {
                Label headerLabel = new Label(colIndex++, rowIndex, column, headerFormat);
                sheet.addCell(headerLabel);
            }

            ++rowIndex;
            colIndex = 0;
            Label headerLabel = new Label(colIndex++, rowIndex, "AFFILIATES_CODE", headerFormat);
            sheet.addCell(headerLabel);
            headerLabel = new Label(colIndex++, rowIndex, "{\"default\":\"AFFILIATES_CODE\"}", headerFormat);
            sheet.addCell(headerLabel);
            headerLabel = new Label(colIndex++, rowIndex, "SYSTEM", headerFormat);
            sheet.addCell(headerLabel);
            colIndex++;
            colIndex++;
            headerLabel = new Label(colIndex++, rowIndex, "NON_AFFILIATE_STRUCTURE", headerFormat);
            sheet.addCell(headerLabel);
            headerLabel = new Label(colIndex++, rowIndex, "false", headerFormat);
            sheet.addCell(headerLabel);
            colIndex++;
            headerLabel = new Label(colIndex++, rowIndex, "Attributes", headerFormat);
            sheet.addCell(headerLabel);

            final List<Attribute> attributes = new ArrayList<>();
            attributes.add( new Attribute(1, "AFFILIATE_NAME", "Affiliate Name", null, 2, "TEXT", null) );
            attributes.add( new Attribute(2, "AFFILIATE_CODE", "Affiliate Code", null, 1, "TEXT", null) );
            attributes.add( new Attribute(3, "DEPARTMENT", "Department", null, 3, "TEXT", null) );
            attributes.add( new Attribute(4, "ADDRESS", "Address", null, 4, "TEXT", null) );
            attributes.add( new Attribute(5, "STREET", "Street", null, 5, "TEXT", null) );
            attributes.add( new Attribute(6, "ZIP", "Zip Code", null, 6, "TEXT", null) );
            attributes.add( new Attribute(7, "CITY", "City", null, 7, "TEXT", null) );
            attributes.add( new Attribute(8, "COUNTRY", "Country", null, 8, "TEXT", null) );

            headerLabel = new Label(colIndex++, rowIndex, gsonWithNulls.toJson(attributes), headerFormat);
            sheet.addCell(headerLabel);



            //second worksheet
            sheet = workbook.createSheet("objects", 1);

            colIndex = 0;
            rowIndex = 0;
            for (final String column : OBJECTS_COLUMN_NAMES) {
                headerLabel = new Label(colIndex++, rowIndex, column, headerFormat);
                sheet.addCell(headerLabel);
            }

            MALAssetStructures.getPropertyVariants().values().forEach(
                    propertyVariant -> {
                        try {
                            final String[] fields = propertyVariant.getFieldsArray();
                            final String brandName = assetStructures.getBrands().get(propertyVariant.getBrandId());
                            if (StringUtils.isBlank(brandName)) {
                                return;
                            }
                            final List<String[]> rows = new ArrayList<>();
                            addHeaderForPorpertyVariant( rows, propertyVariant );
                            final List<MALPropertyEntity> malPropertyEntities = malPropertyRepository.findByBrandAndUpdatedIsAfter(brandName, getMidnightBridgeLookInThePast());
                            malPropertyEntities.forEach(malPropertyEntity -> {
                                String addressField01 = propertyVariant.getAddressField01();
                                String addressField02 = propertyVariant.getAddressField02();
                                String addressField03 = propertyVariant.getAddressField03();
                                String addressField04 = propertyVariant.getAddressField04();
                                String addressField05 = propertyVariant.getAddressField05();
                                for (final String field : fields) {
                                    final String replacement;
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
                                List<AssetEntity> logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "ko", TransferringAssetStatus.DONE, getMidnightBridgeLookInThePast());
                                String propertyLogo1c = "";
                                if (logo != null && !logo.isEmpty()) {
                                    propertyLogo1c = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
                                }
                                logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "cmyk", TransferringAssetStatus.DONE, getMidnightBridgeLookInThePast());
                                String propertyLogo4c = "";
                                if (logo != null && !logo.isEmpty()) {
                                    propertyLogo4c = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
                                }
                                logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "cmyk-B", TransferringAssetStatus.DONE, getMidnightBridgeLookInThePast());
                                String propertyLogo4cb = "";
                                if (logo != null && !logo.isEmpty()) {
                                    propertyLogo4cb = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
                                }
                                logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "k", TransferringAssetStatus.DONE, getMidnightBridgeLookInThePast());
                                String propertyLogolcBlack = "";
                                if (logo != null && !logo.isEmpty()) {
                                    propertyLogolcBlack = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
                                }
                                logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "pms", TransferringAssetStatus.DONE, getMidnightBridgeLookInThePast());
                                String propertyLogoPMS = "";
                                if (logo != null && !logo.isEmpty()) {
                                    propertyLogoPMS = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
                                }
                                logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "pms-c", TransferringAssetStatus.DONE, getMidnightBridgeLookInThePast());
                                String propertyLogoPMSC = "";
                                if (logo != null && !logo.isEmpty()) {
                                    propertyLogoPMSC = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
                                }
                                logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "cmyk-C", TransferringAssetStatus.DONE, getMidnightBridgeLookInThePast());
                                String propertyLogo4cc = "";
                                if (logo != null && !logo.isEmpty()) {
                                    propertyLogo4cc = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
                                }
                                logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "ko-D", TransferringAssetStatus.DONE, getMidnightBridgeLookInThePast());
                                String propertyLogolcd = "";
                                if (logo != null && !logo.isEmpty()) {
                                    propertyLogolcd = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
                                }

                                String[] row = new String[44];
                                row[0] = propertyVariant.getStructureName();
                                row[1] = malPropertyEntity.getPropertyId();
                                row[2] = malPropertyEntity.getPropertyId() + " - " + malPropertyEntity.getName();
                                row[3] = "";
                                row[4] = MALPropertyStatus.OBSERVED.equals( malPropertyEntity.getMalPropertyStatus() ) ? "1" : "0";
                                row[5] = malPropertyEntity.getName();
                                row[6] = malPropertyEntity.getPropertyId();
                                row[7] = malPropertyEntity.getState();
                                row[8] = malPropertyEntity.getAddress2();
                                row[9] = malPropertyEntity.getAddress();
                                row[10] = malPropertyEntity.getZip();
                                row[11] = malPropertyEntity.getCity();
                                row[12] = malPropertyEntity.getCountry();
                                row[13] = malPropertyEntity.getUrl();
                                row[14] = malPropertyEntity.getTelephone();
                                row[15] = malPropertyEntity.getBrand();
                                row[16] = malPropertyEntity.getParentBrand();
                                row[17] = malPropertyEntity.getLatitude();
                                row[18] = malPropertyEntity.getLongitude();
                                row[19] = "";
                                row[20] = "";
                                row[21] = "";
                                row[22] = "";
                                row[23] = "";
                                row[24] = "";
                                row[25] = "";
                                row[26] = "";
                                row[27] = "";
                                row[28] = propertyLogo1c;
                                row[29] = propertyLogo4c;
                                row[30] = propertyLogo4cb;
                                row[31] = propertyLogolcBlack;
                                row[32] = propertyLogoPMS;
                                row[33] = propertyLogoPMSC;
                                row[34] = propertyLogo4cc;
                                row[35] = propertyLogolcd;
                                row[36] = "";
                                row[37] = "";
                                row[38] = addressField01;
                                row[39] = addressField02;
                                row[40] = addressField03;
                                row[41] = addressField04;
                                row[42] = addressField05;
                                row[43] = malPropertyEntity.getPropertyId();
                                rows.add(row);

                                List<AssetEntity> assetEntities = assetRepository.findPropertyAssets(malPropertyEntity.getPropertyId(), "1", getMidnightBridgeLookInThePast());
                                if (assetEntities != null && !assetEntities.isEmpty()) {
                                    assetEntities.forEach(assetEntity ->
                                            addRow(rows, assetEntity, propertyVariant.getSubName(), malPropertyEntity.getPropertyId()));
                                }

                                final String[] mapColors = new String[3];
                                mapColors[0] = "Mat";
                                mapColors[1] = "Mla";
                                mapColors[2] = "Mrm";
                                for (final String mapColor : mapColors) {
                                    assetEntities = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "5", mapColor, TransferringAssetStatus.DONE, getMidnightBridgeLookInThePast());
                                    if (assetEntities != null && !assetEntities.isEmpty()) {
                                        assetEntities.forEach(assetEntity ->
                                                addRow(rows, assetEntity, propertyVariant.getSubNameMaps(), malPropertyEntity.getPropertyId()));
                                    }
                                }

                                final String[] floorTypes = new String[2];
                                floorTypes[0] = "Fmt";
                                floorTypes[1] = "Fgr";
                                for (final String floorType : floorTypes) {
                                    assetEntities = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "5", floorType, TransferringAssetStatus.DONE, getMidnightBridgeLookInThePast());
                                    if (assetEntities != null && !assetEntities.isEmpty()) {
                                        assetEntities.forEach(assetEntity ->
                                                addRow(rows, assetEntity, propertyVariant.getSubNameFloorPlans(), malPropertyEntity.getPropertyId()));
                                    }
                                }
                            });

                            writeToFile(propertyVariant.getStructureName() + ".xls", rows);
                        } catch( final Exception e ) {
                            final String message = String.format( "Can not create Excel file for property variant [%s] with message [%s]", propertyVariant.getStructureName(), e.getMessage() );
                            final ReportsEntity reportsEntity = new ReportsEntity( ReportType.ERROR, getClass().getName(), null, message, ReportTo.BM, GSON.toJson( propertyVariant), null, null );
                            reportsRepository.save( reportsEntity );
                            logger.error( message, e );
                        }
                    });
        } catch (final Exception e) {
            final String message = String.format("Can not create file [%s]", fileName);
            final ReportsEntity reportsEntity = new ReportsEntity(ReportType.ERROR, getClass().getName(), null, message, ReportTo.BM, null, null, null);
            reportsRepository.save(reportsEntity);
            logger.error(message, e);
        }
    }

    public class Attribute {
        private int number;

        private String name;

        private String label;

        private String comment;

        private int order;

        private String type;

        private List<String> props;

        public Attribute() {}

        public Attribute( final int number, final String name, final String label, final String comment, final int order, final String type, final List<String> props ) {
            this.number = number;
            this.name = name;
            this.label = label;
            this.comment = comment;
            this.order = order;
            this.type = type;
            this.props = props;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
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

        public List<String> getProps() {
            return props;
        }

        public void setProps(List<String> props) {
            this.props = props;
        }
    }
}
