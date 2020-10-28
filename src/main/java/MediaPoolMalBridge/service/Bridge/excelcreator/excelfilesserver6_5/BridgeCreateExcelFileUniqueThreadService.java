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
import jxl.Workbook;
import jxl.write.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
public class BridgeCreateExcelFileUniqueThreadService extends AbstractBridgeUniqueExcelService {

    protected static final String[] STRUCTURES_COLUMN_NAMES = {"List ID * (no editable)",
            "List name *", "Type * (no editable)", "ID of Parent list", "Name of default entry",
            "Affiliate type", "Show preview for list entry", "Attributes"};

    protected static final String[] OBJECTS_COLUMN_NAMES = {"List ID * (no editable)",
            "List Entry ID * (no editable)", "Display name *", "Name of parent entry",
            "State *", "Affiliate", "Attributes"};

    private static final Gson gsonWithNulls = new GsonBuilder().serializeNulls().create();

    private final MALAssetStructures assetStructures;

    private int rowIndex = 0;

    private final WritableCellFormat headerFormat;

    public BridgeCreateExcelFileUniqueThreadService(final MALAssetStructures assetStructures )
    {
        this.assetStructures = assetStructures;
        headerFormat = new WritableCellFormat();
        final WritableFont font = new WritableFont(WritableFont.ARIAL, 16, WritableFont.NO_BOLD);
        headerFormat.setFont(font);
    }

    @Override
    protected void run()
    {
        writeToFile( "DataStructures.xls" );
    }

    private void writeToFile(final String fileName) {
        try {
            final WritableWorkbook workbook = Workbook.createWorkbook(new File(appConfig.getExcelDir() + fileName));
            createStructuresSheet( workbook );
            createObjectsSheet( workbook );
            workbook.write();
            workbook.close();
        } catch (final Exception e) {
            final String message = String.format("Can not create file [%s]", fileName);
            final ReportsEntity reportsEntity = new ReportsEntity(ReportType.ERROR, getClass().getName(), null, message, ReportTo.BM, null, null, null);
            reportsRepository.save(reportsEntity);
            logger.error(message, e);
        }
    }

    private void createObjectsSheet( final WritableWorkbook workbook ) throws Exception {

        final WritableSheet sheet = workbook.createSheet( "objects", 1 );

        int colIndex = 0;
        rowIndex = 0;
        for (final String column : OBJECTS_COLUMN_NAMES) {
            Label headerLabel = new Label(colIndex++, rowIndex, column, headerFormat);
            sheet.addCell(headerLabel);
        }

        assetStructures.getPropertyVariants().values().forEach(
                propertyVariant -> {
                    //logger.error( "CRESTING OBJECTS VARIANTS {}", (new Gson()).toJson( propertyVariant ) );
                    try {
                        final String brandName = assetStructures.getBrands().get(propertyVariant.getBrandId());
                        if (StringUtils.isBlank(brandName)) {
                            return;
                        }
                        //final List<MALPropertyEntity> malPropertyEntities = malPropertyRepository.findByBrandAndUpdatedIsAfter(brandName);
                        final List<MALPropertyEntity> malPropertyEntities = malPropertyRepository.findByBrandAndMalPropertyStatus(brandName, MALPropertyStatus.OBSERVED);
                        for( final MALPropertyEntity malPropertyEntity : malPropertyEntities ) {
                            final String combinedAddressField = digestCombinedAddressField( propertyVariant, malPropertyEntity );
                            digestLogos( sheet, propertyVariant, malPropertyEntity, combinedAddressField );
                            digestAssets( sheet, propertyVariant, malPropertyEntity );
                            digestMaps( sheet, propertyVariant, malPropertyEntity );
                            digestFloorTypes( sheet, propertyVariant, malPropertyEntity );
                        }
                    } catch( final Exception e ) {
                        final String message = String.format( "Can not create Excel file for property variant [%s] with message [%s]", propertyVariant.getStructureName(), e.getMessage() );
                        final ReportsEntity reportsEntity = new ReportsEntity( ReportType.ERROR, getClass().getName(), null, message, ReportTo.BM, GSON.toJson( propertyVariant), null, null );
                        reportsRepository.save( reportsEntity );
                        logger.error( message, e );
                    }
                });
    }

    private void digestFloorTypes( final WritableSheet sheet, final MALPropertyVariant propertyVariant, final MALPropertyEntity malPropertyEntity ) throws Exception {
        final String[] floorTypes = new String[2];
        floorTypes[0] = "Fmt";
        floorTypes[1] = "Fgr";
        int order = 1;
        for (final String floorType : floorTypes) {
            final List<AssetEntity> assetEntities = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "5", floorType, TransferringAssetStatus.DONE);
            if (assetEntities != null && !assetEntities.isEmpty()) {
                for( final AssetEntity assetEntity : assetEntities ) {
                    final List<AttributeShort> attributes = new ArrayList<>();
                    attributes.add( new AttributeShort( order,
                            "Floorplan" + order,
                            "[MD5_HASH=" + assetEntity.getBmMd5Hash() + ";MEDIA_GUID=" + assetEntity.getBmAssetId() + ";]",
                            "MEDIA" ) );
                    addRow(sheet, propertyVariant.getSubNameFloorPlans(), malPropertyEntity.getPropertyId(), assetEntity.getCaption(), gsonWithNulls.toJson( attributes ), assetEntity.getMalAssetId() );
                }
                ++order;
            }
        }
    }

    private void digestMaps( final WritableSheet sheet, final MALPropertyVariant propertyVariant, final MALPropertyEntity malPropertyEntity ) throws Exception {
        final String[] mapColors = new String[3];
        mapColors[0] = "Mat";
        mapColors[1] = "Mla";
        mapColors[2] = "Mrm";
        int order = 1;
        for (final String mapColor : mapColors) {
            final List<AssetEntity> assetEntities = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "5", mapColor, TransferringAssetStatus.DONE);
            if (assetEntities != null && !assetEntities.isEmpty()) {
                for( final AssetEntity assetEntity : assetEntities ) {
                    final List<AttributeShort> attributes = new ArrayList<>();
                    attributes.add( new AttributeShort( order,
                            "Map" + order,
                            "[MD5_HASH=" + assetEntity.getBmMd5Hash() + ";MEDIA_GUID=" + assetEntity.getBmAssetId() + ";]",
                            "MEDIA" ) );
                    addRow(sheet, propertyVariant.getSubNameMaps(), malPropertyEntity.getPropertyId(), assetEntity.getCaption(), gsonWithNulls.toJson(attributes), assetEntity.getMalAssetId());
                }
                ++order;
            }
        }
    }

    private void digestAssets( final WritableSheet sheet, final MALPropertyVariant propertyVariant, final MALPropertyEntity malPropertyEntity ) throws Exception {
        final List<AssetEntity> assetEntities = assetRepository.findPropertyAssets(malPropertyEntity.getPropertyId(), "1", TransferringAssetStatus.DONE);
        int order = 1;
        if (assetEntities != null && !assetEntities.isEmpty()) {
            for( final AssetEntity assetEntity : assetEntities ) {
                final List<AttributeShort> attributes = new ArrayList<>();
                attributes.add( new AttributeShort( order,
                        "Image" + order,
                        "[MD5_HASH=" + assetEntity.getBmMd5Hash() + ";MEDIA_GUID=" + assetEntity.getBmAssetId() + ";]",
                        "MEDIA" ) );
                addRow(sheet, propertyVariant.getSubName(), malPropertyEntity.getPropertyId(), assetEntity.getCaption(), gsonWithNulls.toJson(attributes), assetEntity.getMalAssetId() );
            }
            ++order;
        }
    }

    private void addRow( final WritableSheet sheet, final String subName, final String propertyId, final String name, final String jsonedAttributes, final String malAssetId ) throws Exception
    {
        ++rowIndex;
        int colIndex = 0;

        //final String[] row_ = new String[6];
        //row_[0] = subName;
        //row_[1] = assetEntity.getMalAssetId();
        //row_[2] = assetEntity.getMalAssetId() + " - " + assetEntity.getCaption();
        //row_[3] = propertyId;
        //row_[4] = "1";
        //row_[5] = "";
        //row_[6] = "";
        //row_[7] = "";
        //row_[8] = "";
        //row_[9] = "";
        //row_[10] = "";
        //row_[11] = "";
        //row_[12] = "";
        //row_[13] = "";
        //row_[14] = "";
        //row_[15] = "";
        //row_[16] = "";
        //row_[17] = "";
        //row_[18] = "";
        //row_[19] = "";
        //row_[20] = "";
        //row_[21] = "";
        //row_[22] = "";
        //row_[23] = "";
        //row_[24] = "";
        //row_[25] = "";
        //row_[26] = "";
        //row_[27] = "";
        //row_[28] = "[MD5_HASH=" + assetEntity.getBmMd5Hash() + ";MEDIA_GUID=" + assetEntity.getBmAssetId() + ";]";
        //row_[29] = "";
        //row_[30] = "";
        //row_[31] = "";
        //row_[32] = "";
        //row_[33] = "";
        //row_[34] = "";
        //row_[35] = "";
        //row_[36] = "";
        //row_[37] = "";
        //row_[38] = "";
        //row_[39] = "";
        //row_[40] = "";
        //row_[41] = "";
        //row_[42] = "";
        //row_[43] = propertyId;
        if( StringUtils.isNotBlank( malAssetId ) ) {
            Label headerLabel = new Label(colIndex++, rowIndex, subName, headerFormat);
            sheet.addCell(headerLabel);
            headerLabel = new Label(colIndex++, rowIndex, malAssetId, headerFormat);
            sheet.addCell(headerLabel);
            headerLabel = new Label(colIndex++, rowIndex, "{\"default\":\"" + malAssetId + " - " + name + "\"}", headerFormat);
            sheet.addCell(headerLabel);
            headerLabel = new Label(colIndex++, rowIndex, propertyId, headerFormat);
            sheet.addCell(headerLabel);
            headerLabel = new Label(colIndex++, rowIndex, "EDIT_AND_ADD", headerFormat);
            sheet.addCell(headerLabel);
            headerLabel = new Label(colIndex++, rowIndex, "", headerFormat);
            sheet.addCell(headerLabel);
            headerLabel = new Label(colIndex++, rowIndex, jsonedAttributes, headerFormat);
            sheet.addCell(headerLabel);
        } else {
            Label headerLabel = new Label(colIndex++, rowIndex, subName, headerFormat);
            sheet.addCell(headerLabel);
            headerLabel = new Label(colIndex++, rowIndex, propertyId, headerFormat);
            sheet.addCell(headerLabel);
            headerLabel = new Label(colIndex++, rowIndex, "{\"default\":\"" + propertyId + " - " + name + "\"}", headerFormat);
            sheet.addCell(headerLabel);
            headerLabel = new Label(colIndex++, rowIndex, "", headerFormat);
            sheet.addCell(headerLabel);
            headerLabel = new Label(colIndex++, rowIndex, "EDIT_AND_ADD", headerFormat);
            sheet.addCell(headerLabel);
            headerLabel = new Label(colIndex++, rowIndex, "", headerFormat);
            sheet.addCell(headerLabel);
            headerLabel = new Label(colIndex++, rowIndex, jsonedAttributes, headerFormat);
            sheet.addCell(headerLabel);
        }
    }

    private void digestLogos( final WritableSheet sheet, MALPropertyVariant propertyVariant, final MALPropertyEntity malPropertyEntity, final String combinedAddressField ) throws Exception {
        final List<Attribute> attributes = new ArrayList<>();
        List<AssetEntity> logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "ko", TransferringAssetStatus.DONE);
        String propertyLogo1c = "";
        if (logo != null && !logo.isEmpty()) {
            propertyLogo1c = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
            attributes.add( new Attribute( 24,
                    "PropertyLogo1c",
                    "MEDIA",
                    propertyLogo1c ) );
        }
        logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "cmyk", TransferringAssetStatus.DONE);
        String propertyLogo4c = "";
        if (logo != null && !logo.isEmpty()) {
            propertyLogo4c = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
            attributes.add( new Attribute( 25,
                    "PropertyLogo4c",
                    "MEDIA",
                    propertyLogo4c ) );
        }
        logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "cmyk-B", TransferringAssetStatus.DONE);
        String propertyLogocmykb = "";
        if (logo != null && !logo.isEmpty()) {
            propertyLogocmykb = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
            attributes.add( new Attribute( 26,
                    "PropertyLogo4c-b",
                    "MEDIA",
                    propertyLogocmykb ) );
        }
        logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "k", TransferringAssetStatus.DONE);
        String propertyLogo1cBlack = "";
        if (logo != null && !logo.isEmpty()) {
            propertyLogo1cBlack = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
            attributes.add( new Attribute( 27,
                    "PropertyLogo1cblack",
                    "MEDIA",
                    propertyLogo1cBlack ) );
        }
        logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "pms", TransferringAssetStatus.DONE);
        String propertyLogoPMS = "";
        if (logo != null && !logo.isEmpty()) {
            propertyLogoPMS = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
            attributes.add( new Attribute( 28,
                    "PropertyLogopms",
                    "MEDIA",
                    propertyLogoPMS ) );
        }
        logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "pms-C", TransferringAssetStatus.DONE);
        String propertyLogoPMSC = "";
        if (logo != null && !logo.isEmpty()) {
            propertyLogoPMSC = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
            attributes.add( new Attribute( 29,
                    "PropertyLogopmsc",
                    "MEDIA",
                    propertyLogoPMSC ) );
        }
        logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "cmyk-C", TransferringAssetStatus.DONE);
        String propertyLogocmykC = "";
        if (logo != null && !logo.isEmpty()) {
            propertyLogocmykC = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
            attributes.add( new Attribute( 30,
                    "PropertyLogo4cc",
                    "MEDIA",
                    propertyLogocmykC ) );
        }
        logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "ko-D", TransferringAssetStatus.DONE);
        String propertyLogokoD = "";
        if (logo != null && !logo.isEmpty()) {
            propertyLogokoD = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
            attributes.add( new Attribute( 31,
                    "PropertyLogokod",
                    "MEDIA",
                    propertyLogokoD ) );
        }

        //this should be checked particularly
        logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "k", TransferringAssetStatus.DONE);
        String propertyLogoBlackK = "";
        if (logo != null && !logo.isEmpty()) {
            propertyLogoBlackK = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
            attributes.add( new Attribute( 32,
                    "PropertyLogoblackK",
                    "MEDIA",
                    propertyLogoBlackK ) );
        }
        logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "cmyk-K", TransferringAssetStatus.DONE);
        String propertyLogo4cK = "";
        if (logo != null && !logo.isEmpty()) {
            propertyLogo4cK = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
            attributes.add( new Attribute( 33,
                    "PropertyLogo4cK",
                    "MEDIA",
                    propertyLogoBlackK ) );
        }
        logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "Sheraton Black logo-1", TransferringAssetStatus.DONE);
        String propertyLogoSheratonBlack = "";
        if (logo != null && !logo.isEmpty()) {
            propertyLogoSheratonBlack = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
            attributes.add( new Attribute( 39,
                    "PropertyLogoSheratonBlack",
                    "MEDIA",
                    propertyLogoSheratonBlack ) );
        }
        logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "dusk", TransferringAssetStatus.DONE);
        String propertyLogoSheratonDusk = "";
        if (logo != null && !logo.isEmpty()) {
            propertyLogoSheratonDusk = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
            attributes.add( new Attribute( 40,
                    "propertyLogoSheratonDusk",
                    "MEDIA",
                    propertyLogoSheratonDusk ) );
        }
        logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "Graphite_C_cmyk", TransferringAssetStatus.DONE);
        String propertyLogoSheratonGraphiteCodedCMYK = "";
        if (logo != null && !logo.isEmpty()) {
            propertyLogoSheratonGraphiteCodedCMYK = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
            attributes.add( new Attribute( 40,
                    "PropertyLogoSheratonGraphiteCodedCMYK",
                    "MEDIA",
                    propertyLogoSheratonGraphiteCodedCMYK ) );
        }
        logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "Graphite_C_pms", TransferringAssetStatus.DONE);
        String propertyLogoSheratonGraphiteCodedPMS = "";
        if (logo != null && !logo.isEmpty()) {
            propertyLogoSheratonGraphiteCodedPMS = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
            attributes.add( new Attribute( 42,
                    "PropertyLogoSheratonGraphiteCodedPMS",
                    "MEDIA",
                    propertyLogoSheratonGraphiteCodedPMS ) );
        }
        logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "Graphite_rgb", TransferringAssetStatus.DONE);
        String propertyLogoSheratonGraphiteRGB = "";
        if (logo != null && !logo.isEmpty()) {
            propertyLogoSheratonGraphiteRGB = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
            attributes.add( new Attribute( 43,
                    "PropertyLogoSheratonGraphiteRGB",
                    "MEDIA",
                    propertyLogoSheratonGraphiteRGB ) );
        }
        logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "Graphite_U_cmyk", TransferringAssetStatus.DONE);
        String propertyLogoSheratonGraphiteUncoatedCMYK = "";
        if (logo != null && !logo.isEmpty()) {
            propertyLogoSheratonGraphiteUncoatedCMYK = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
            attributes.add( new Attribute( 44,
                    "PropertyLogoSheratonGraphiteUncoatedCMYK",
                    "MEDIA",
                    propertyLogoSheratonGraphiteUncoatedCMYK ) );
        }
        logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "Graphite_U_pms", TransferringAssetStatus.DONE);
        String propertyLogoSheratonGraphiteUncoatedPMS = "";
        if (logo != null && !logo.isEmpty()) {
            propertyLogoSheratonGraphiteUncoatedPMS = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
            attributes.add( new Attribute( 45,
                    "PropertyLogoSheratonGraphiteUncoatedPMS",
                    "MEDIA",
                    propertyLogoSheratonGraphiteUncoatedPMS ) );
        }
        logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "ivory", TransferringAssetStatus.DONE);
        String propertyLogoSheratonIvory = "";
        if (logo != null && !logo.isEmpty()) {
            propertyLogoSheratonIvory = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
            attributes.add( new Attribute( 46,
                    "PropertyLogoSheratonIvory",
                    "MEDIA",
                    propertyLogoSheratonIvory ) );
        }
        logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "Knockout", TransferringAssetStatus.DONE);
        String propertyLogoSheratonKnockout = "";
        if (logo != null && !logo.isEmpty()) {
            propertyLogoSheratonKnockout = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
            attributes.add( new Attribute( 47,
                    "PropertyLogoSheratonKnockout",
                    "MEDIA",
                    propertyLogoSheratonKnockout ) );
        }
        logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "Logo_Specs", TransferringAssetStatus.DONE);
        String propertyLogoSheratonLogoSpecs = "";
        if (logo != null && !logo.isEmpty()) {
            propertyLogoSheratonLogoSpecs = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
            attributes.add( new Attribute( 47,
                    "PropertyLogoSheratonLogoSpecs",
                    "MEDIA",
                    propertyLogoSheratonLogoSpecs ) );
        }
        logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "Logo_Specs", TransferringAssetStatus.DONE);
        String propertyLogoSheratonOysterCoatedCMYK = "";
        if (logo != null && !logo.isEmpty()) {
            propertyLogoSheratonOysterCoatedCMYK = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
            attributes.add( new Attribute( 49,
                    "PropertyLogoSheratonOysterCoatedCMYK",
                    "MEDIA",
                    propertyLogoSheratonOysterCoatedCMYK ) );
        }
        logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "Oyster_C_pms", TransferringAssetStatus.DONE);
        String propertyLogoSheratonOysterCoatedPMS = "";
        if (logo != null && !logo.isEmpty()) {
            propertyLogoSheratonOysterCoatedPMS = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
            attributes.add( new Attribute( 50,
                    "PropertyLogoSheratonOysterCoatedPMS",
                    "MEDIA",
                    propertyLogoSheratonOysterCoatedPMS ) );
        }
        logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "Oyster_rgb", TransferringAssetStatus.DONE);
        String propertyLogoSheratonOysterRGB = "";
        if (logo != null && !logo.isEmpty()) {
            propertyLogoSheratonOysterRGB = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
            attributes.add( new Attribute( 51,
                    "PropertyLogoSheratonOysterRGB",
                    "MEDIA",
                    propertyLogoSheratonOysterRGB ) );
        }
        logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "Oyster_U_cmyk", TransferringAssetStatus.DONE);
        String propertyLogoSheratonOysterUncoatedCMYK = "";
        if (logo != null && !logo.isEmpty()) {
            propertyLogoSheratonOysterUncoatedCMYK = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
            attributes.add( new Attribute( 52,
                    "PropertyLogoSheratonOysterUncoatedCMYK",
                    "MEDIA",
                    propertyLogoSheratonOysterUncoatedCMYK ) );
        }
        logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "Oyster_U_pms", TransferringAssetStatus.DONE);
        String propertyLogoSheratonOysterUncoatedPMS = "";
        if (logo != null && !logo.isEmpty()) {
            propertyLogoSheratonOysterUncoatedPMS = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
            attributes.add( new Attribute( 53,
                    "PropertyLogoSheratonOysterUncoatedPMS",
                    "MEDIA",
                    propertyLogoSheratonOysterUncoatedPMS ) );
        }
        logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "FrenchGray_C_cmyk", TransferringAssetStatus.DONE);
        String propertyLogoSheratonResortFrenchGrayCoatedCMYK = "";
        if (logo != null && !logo.isEmpty()) {
            propertyLogoSheratonResortFrenchGrayCoatedCMYK = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
            attributes.add( new Attribute( 54,
                    "PropertyLogoSheratonResortFrenchGrayCoatedCMYK",
                    "MEDIA",
                    propertyLogoSheratonResortFrenchGrayCoatedCMYK ) );
        }
        logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "FrenchGray_C_pms", TransferringAssetStatus.DONE);
        String propertyLogoSheratonResortFrenchGrayCoatedPMS = "";
        if (logo != null && !logo.isEmpty()) {
            propertyLogoSheratonResortFrenchGrayCoatedPMS = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
            attributes.add( new Attribute( 55,
                    "PropertyLogoSheratonResortFrenchGrayCoatedPMS",
                    "MEDIA",
                    propertyLogoSheratonResortFrenchGrayCoatedPMS ) );
        }
        logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "FrenchGray_rgb", TransferringAssetStatus.DONE);
        String propertyLogoSheratonResortFrenchGrayRGB = "";
        if (logo != null && !logo.isEmpty()) {
            propertyLogoSheratonResortFrenchGrayRGB = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
            attributes.add( new Attribute( 56,
                    "PropertyLogoSheratonResortFrenchGrayRGB",
                    "MEDIA",
                    propertyLogoSheratonResortFrenchGrayRGB ) );
        }
        logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "FrenchGray_U_cmyk", TransferringAssetStatus.DONE);
        String propertyLogoSheratonResortFrenchGrayUncoatedCMYK = "";
        if (logo != null && !logo.isEmpty()) {
            propertyLogoSheratonResortFrenchGrayUncoatedCMYK = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
            attributes.add( new Attribute( 57,
                    "PropertyLogoSheratonResortFrenchGrayUncoatedCMYK",
                    "MEDIA",
                    propertyLogoSheratonResortFrenchGrayUncoatedCMYK ) );
        }
        logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "FrenchGray_U_pms", TransferringAssetStatus.DONE);
        String propertyLogoSheratonResortFrenchGrayUncoatedPMS = "";
        if (logo != null && !logo.isEmpty()) {
            propertyLogoSheratonResortFrenchGrayUncoatedPMS = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
            attributes.add( new Attribute( 58,
                    "PropertyLogoSheratonResortFrenchGrayUncoatedPMS",
                    "MEDIA",
                    propertyLogoSheratonResortFrenchGrayUncoatedPMS ) );
        }
        logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "Tungsten_C_cmyk", TransferringAssetStatus.DONE);
        String propertyLogoSheratonTungstenCoatedCMYK = "";
        if (logo != null && !logo.isEmpty()) {
            propertyLogoSheratonTungstenCoatedCMYK = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
            attributes.add( new Attribute( 59,
                    "PropertyLogoSheratonTungstenCoatedCMYK",
                    "MEDIA",
                    propertyLogoSheratonTungstenCoatedCMYK ) );
        }
        logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "Tungsten_C_pms", TransferringAssetStatus.DONE);
        String propertyLogoSheratonTungstenCoatedPMS = "";
        if (logo != null && !logo.isEmpty()) {
            propertyLogoSheratonTungstenCoatedPMS = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
            attributes.add( new Attribute( 60,
                    "PropertyLogoSheratonTungstenCoatedPMS",
                    "MEDIA",
                    propertyLogoSheratonTungstenCoatedPMS ) );
        }
        logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "Tungsten_rgb", TransferringAssetStatus.DONE);
        String propertyLogoSheratonTungstenRGB = "";
        if (logo != null && !logo.isEmpty()) {
            propertyLogoSheratonTungstenRGB = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
            attributes.add( new Attribute( 61,
                    "PropertyLogoSheratonTungstenRGB",
                    "MEDIA",
                    propertyLogoSheratonTungstenRGB ) );
        }
        logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "Tungsten_U_cmyk", TransferringAssetStatus.DONE);
        String propertyLogoSheratonTungstenUncoatedCMYK = "";
        if (logo != null && !logo.isEmpty()) {
            propertyLogoSheratonTungstenUncoatedCMYK = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
            attributes.add( new Attribute( 62,
                    "PropertyLogoSheratonTungstenUncoatedCMYK",
                    "MEDIA",
                    propertyLogoSheratonTungstenUncoatedCMYK ) );
        }
        logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "Tungsten_U_pms", TransferringAssetStatus.DONE);
        String propertyLogoSheratonTungstenUncoatedPMS = "";
        if (logo != null && !logo.isEmpty()) {
            propertyLogoSheratonTungstenUncoatedPMS = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
            attributes.add( new Attribute( 63,
                    "PropertyLogoSheratonTungstenUncoatedPMS",
                    "MEDIA",
                    propertyLogoSheratonTungstenUncoatedPMS ) );
        }
        logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "usna", TransferringAssetStatus.DONE);
        String propertyLogoUSNavy = "";
        if (logo != null && !logo.isEmpty()) {
            propertyLogoUSNavy = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
            attributes.add( new Attribute( 64,
                    "PropertyLogoUSNavy",
                    "MEDIA",
                    propertyLogoUSNavy ) );
        }

        attributes.add( new Attribute( 1,
                "AFFILIATE_NAME",
                "TEXT",
                malPropertyEntity.getName() ) );
        attributes.add( new Attribute( 2,
                "AFFILIATE_CODE",
                "TEXT",
                malPropertyEntity.getPropertyId() ) );


        //row[0] = propertyVariant.getStructureName();
        //row[1] = malPropertyEntity.getPropertyId();
        //row[2] = malPropertyEntity.getPropertyId() + " - " + malPropertyEntity.getName();
        //row[3] = "";
        //row[4] = MALPropertyStatus.OBSERVED.equals( malPropertyEntity.getMalPropertyStatus() ) ? "1" : "0";
        //row[5] = malPropertyEntity.getName();
        //row[6] = malPropertyEntity.getPropertyId();
        //row[7] = malPropertyEntity.getState();
        attributes.add( new Attribute( 3,
                "PropertyState",
                "TEXT",
                malPropertyEntity.getState() ) );
        //row[8] = malPropertyEntity.getAddress2();
        //row[9] = malPropertyEntity.getAddress();
        attributes.add( new Attribute( 34,
                "CombinedAddress - Address 1 - Address 2",
                "RICHTEXT",
                combinedAddressField ) );
        attributes.add( new Attribute( 4,
                "ADDRESS",
                "TEXT",
                malPropertyEntity.getAddress() ) );
        attributes.add( new Attribute( 5,
                "STREET",
                "TEXT",
                malPropertyEntity.getAddress2() ) );
        //row[10] = malPropertyEntity.getZip();
        attributes.add( new Attribute( 6,
                "ZIP",
                "TEXT",
                malPropertyEntity.getZip() ) );
        //row[11] = malPropertyEntity.getCity();
        attributes.add( new Attribute( 7,
                "CITY",
                "TEXT",
                malPropertyEntity.getCity() ) );
        //row[12] = malPropertyEntity.getCountry();
        attributes.add( new Attribute( 8,
                "COUNTRY",
                "TEXT",
                malPropertyEntity.getCountry() ) );
        //row[13] = malPropertyEntity.getUrl();
        attributes.add( new Attribute( 9,
                "PropertyURL",
                "TEXT",
                malPropertyEntity.getUrl() ) );
        //row[14] = malPropertyEntity.getTelephone();
        attributes.add( new Attribute( 10,
                "PropertyTelephone",
                "TEXT",
                malPropertyEntity.getTelephone() ) );
        //row[15] = malPropertyEntity.getBrand();
        //row[16] = malPropertyEntity.getParentBrand();
        //row[17] = malPropertyEntity.getLatitude();
        attributes.add( new Attribute( 13,
                "Latitude",
                "TEXT",
                malPropertyEntity.getLatitude() ) );
        //row[18] = malPropertyEntity.getLongitude();
        attributes.add( new Attribute( 14,
                "Longitude",
                "TEXT",
                malPropertyEntity.getLongitude() ) );
        //row[19] = "";
        //row[20] = "";
        //row[21] = "";
        //row[22] = "";
        //row[23] = "";
        //row[24] = "";
        //row[25] = "";
        //row[26] = "";
        //row[27] = "";
        //row[28] = propertyLogo1c;
        //row[29] = propertyLogo4c;
        //row[30] = propertyLogo4cb;
        //row[31] = propertyLogo1cBlack;
        //row[32] = propertyLogoPMS;
        //row[33] = propertyLogoPMSC;
        //row[34] = propertyLogo4cc;
        //row[35] = propertyLogokod;
        //row[36] = "";
        //row[37] = "";
        //row[38] = addressField01;
        //row[39] = addressField02;
        //row[40] = addressField03;
        //row[41] = addressField04;
        //row[42] = addressField05;
        //row[43] = malPropertyEntity.getPropertyId();
        //rows.add(row);
        addRow(sheet, propertyVariant.getStructureName(), malPropertyEntity.getPropertyId(), malPropertyEntity.getName(), gsonWithNulls.toJson(attributes), null);
    }

    private String digestCombinedAddressField( final MALPropertyVariant propertyVariant, final MALPropertyEntity malPropertyEntity ) {
        final String[] fields = propertyVariant.getFieldsArray();
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
        return addressField01 + addressField02 + addressField03 + addressField04 +addressField05;
    }

    private void createStructuresSheet( final WritableWorkbook workbook ) throws Exception {

        final WritableSheet sheet = workbook.createSheet("structures", 0);

        final WritableCellFormat headerFormat = new WritableCellFormat();
        final WritableFont font = new WritableFont(WritableFont.ARIAL, 16, WritableFont.NO_BOLD);
        headerFormat.setFont(font);

        int colIndex = 0;
        int rowIndex = 0;
        for (final String column : STRUCTURES_COLUMN_NAMES) {
            Label headerLabel = new Label(colIndex++, rowIndex, column, headerFormat);
            sheet.addCell(headerLabel);
        }

        for( final MALPropertyVariant malPropertyVariant : assetStructures.getPropertyVariants().values() ) {
            //parent
            ++rowIndex;
            colIndex = 0;
            Label headerLabel = new Label(colIndex++, rowIndex, malPropertyVariant.getStructureName(), headerFormat);
            sheet.addCell(headerLabel);
            headerLabel = new Label(colIndex++, rowIndex, "{\"default\":\"" + malPropertyVariant.getStructureName() + "\"}", headerFormat);
            sheet.addCell(headerLabel);
            headerLabel = new Label(colIndex++, rowIndex, "DEFAULT", headerFormat);
            sheet.addCell(headerLabel);
            colIndex++;
            colIndex++;
            headerLabel = new Label(colIndex++, rowIndex, "MULTI_OBJECT_AFFILIATE_STRUCTURE", headerFormat);
            sheet.addCell(headerLabel);
            colIndex++;

            headerLabel = new Label(colIndex++, rowIndex, "[{\"number\":3,\"name\":\"PropertyState\",\"label\":\"Property State\",\"comment\":\"\",\"order\":-1,\"type\":\"TEXT\",\"props\":null},{\"number\":9,\"name\":\"PropertyURL\",\"label\":\"Property URL\",\"comment\":\"\",\"order\":-1,\"type\":\"TEXT\",\"props\":null},{\"number\":10,\"name\":\"PropertyTelephone\",\"label\":\"Property Telephone\",\"comment\":\"\",\"order\":-1,\"type\":\"TEXT\",\"props\":null},{\"number\":11,\"name\":\"PropertyFacsimile\",\"label\":\"Property Facsimile\",\"comment\":\"\",\"order\":-1,\"type\":\"TEXT\",\"props\":null},{\"number\":13,\"name\":\"Latitude\",\"label\":\"Latitude\",\"comment\":\"\",\"order\":-1,\"type\":\"TEXT\",\"props\":null},{\"number\":14,\"name\":\"Longitude\",\"label\":\"Longitude\",\"comment\":\"\",\"order\":-1,\"type\":\"TEXT\",\"props\":null},{\"number\":24,\"name\":\"PropertyLogo1c\",\"label\":\"Property Logo 1c\",\"comment\":\"\",\"order\":-1,\"type\":\"MEDIA\",\"props\":null},{\"number\":25,\"name\":\"PropertyLogo4c\",\"label\":\"Property Logo 4c\",\"comment\":\"\",\"order\":-1,\"type\":\"MEDIA\",\"props\":null},{\"number\":26,\"name\":\"PropertyLogo4cb\",\"label\":\"Property Logo 4c-b\",\"comment\":\"\",\"order\":-1,\"type\":\"MEDIA\",\"props\":null},{\"number\":27,\"name\":\"PropertyLogo1cblack\",\"label\":\"Property Logo 1c-black\",\"comment\":\"\",\"order\":-1,\"type\":\"MEDIA\",\"props\":null},{\"number\":28,\"name\":\"PropertyLogopms\",\"label\":\"Property Logo pms\",\"comment\":\"\",\"order\":-1,\"type\":\"MEDIA\",\"props\":null},{\"number\":29,\"name\":\"PropertyLogopmsc\",\"label\":\"Property Logo pms-c\",\"comment\":\"\",\"order\":-1,\"type\":\"MEDIA\",\"props\":null},{\"number\":30,\"name\":\"PropertyLogo4cc\",\"label\":\"Property Logo 4c-c\",\"comment\":\"\",\"order\":-1,\"type\":\"MEDIA\",\"props\":null},{\"number\":31,\"name\":\"PropertyLogokod\",\"label\":\"Property Logo ko-d\",\"comment\":\"\",\"order\":-1,\"type\":\"MEDIA\",\"props\":null},{\"number\":32,\"name\":\"PropertyLogoblackK\",\"label\":\"Property Logo black-K\",\"comment\":\"\",\"order\":-1,\"type\":\"MEDIA\",\"props\":null},{\"number\":33,\"name\":\"PropertyLogo4cK\",\"label\":\"Property Logo 4c-K\",\"comment\":\"\",\"order\":-1,\"type\":\"MEDIA\",\"props\":null},{\"number\":34,\"name\":\"CombinedAddress\",\"label\":\"Combined Address\",\"comment\":\"\",\"order\":-1,\"type\":\"RICHTEXT\",\"props\":null},{\"number\":39,\"name\":\"PropertyLogoSheratonBlack\",\"label\":\"Property Logo Sheraton Black\",\"comment\":\"\",\"order\":0,\"type\":\"MEDIA\",\"props\":null},{\"number\":40,\"name\":\"PropertyLogoSheratonDusk\",\"label\":\"Property Logo Sheraton Dusk\",\"comment\":\"\",\"order\":0,\"type\":\"MEDIA\",\"props\":null},{\"number\":41,\"name\":\"PropertyLogoSheratonGraphiteCodedCMYK\",\"label\":\"Property Logo Sheraton Graphite Coded CMYK\",\"comment\":\"\",\"order\":0,\"type\":\"MEDIA\",\"props\":null},{\"number\":42,\"name\":\"PropertyLogoSheratonGraphiteCodedPMS\",\"label\":\"Property Logo Sheraton Graphite Coded PMS\",\"comment\":\"\",\"order\":0,\"type\":\"MEDIA\",\"props\":null},{\"number\":43,\"name\":\"PropertyLogoSheratonGraphiteRGB\",\"label\":\"Property Logo Sheraton Graphite RGB\",\"comment\":\"\",\"order\":0,\"type\":\"MEDIA\",\"props\":null},{\"number\":44,\"name\":\"PropertyLogoSheratonGraphiteUncoatedCMYK\",\"label\":\"Property Logo Sheraton Graphite Uncoated CMYK\",\"comment\":\"\",\"order\":0,\"type\":\"MEDIA\",\"props\":null},{\"number\":45,\"name\":\"PropertyLogoSheratonGraphiteUncoatedPMS\",\"label\":\"Property Logo Sheraton Graphite Uncoated PMS\",\"comment\":\"\",\"order\":0,\"type\":\"MEDIA\",\"props\":null},{\"number\":46,\"name\":\"PropertyLogoSheratonIvory\",\"label\":\"Property Logo Sheraton Ivory\",\"comment\":\"\",\"order\":0,\"type\":\"MEDIA\",\"props\":null},{\"number\":47,\"name\":\"PropertyLogoSheratonKnockout\",\"label\":\"Property Logo Sheraton Knockout\",\"comment\":\"\",\"order\":0,\"type\":\"MEDIA\",\"props\":null},{\"number\":48,\"name\":\"PropertyLogoSheratonLogoSpecs\",\"label\":\"Property Logo Sheraton Logo Specs\",\"comment\":\"\",\"order\":0,\"type\":\"MEDIA\",\"props\":null},{\"number\":49,\"name\":\"PropertyLogoSheratonOysterCoatedCMYK\",\"label\":\"Property Logo Sheraton Oyster Coated CMYK\",\"comment\":\"\",\"order\":0,\"type\":\"MEDIA\",\"props\":null},{\"number\":50,\"name\":\"PropertyLogoSheratonOysterCoatedPMS\",\"label\":\"Property Logo Sheraton Oyster Coated PMS\",\"comment\":\"\",\"order\":0,\"type\":\"MEDIA\",\"props\":null},{\"number\":51,\"name\":\"PropertyLogoSheratonOysterRGB\",\"label\":\"Property Logo Sheraton Oyster RGB\",\"comment\":\"\",\"order\":0,\"type\":\"MEDIA\",\"props\":null},{\"number\":52,\"name\":\"PropertyLogoSheratonOysterUncoatedCMYK\",\"label\":\"Property Logo Sheraton Oyster Uncoated CMYK\",\"comment\":\"\",\"order\":0,\"type\":\"MEDIA\",\"props\":null},{\"number\":53,\"name\":\"PropertyLogoSheratonOysterUncoatedPMS\",\"label\":\"Property Logo Sheraton Oyster Uncoated PMS\",\"comment\":\"\",\"order\":0,\"type\":\"MEDIA\",\"props\":null},{\"number\":54,\"name\":\"PropertyLogoSheratonResortFrenchGrayCoatedCMYK\",\"label\":\"Property Logo Sheraton Resort French Gray Coated CMYK\",\"comment\":\"\",\"order\":0,\"type\":\"MEDIA\",\"props\":null},{\"number\":55,\"name\":\"PropertyLogoSheratonResortFrenchGrayCoatedPMS\",\"label\":\"Property Logo Sheraton Resort French Gray Coated PMS\",\"comment\":\"\",\"order\":0,\"type\":\"MEDIA\",\"props\":null},{\"number\":56,\"name\":\"PropertyLogoSheratonResortFrenchGrayRGB\",\"label\":\"Property Logo Sheraton Resort French Gray RGB\",\"comment\":\"\",\"order\":0,\"type\":\"MEDIA\",\"props\":null},{\"number\":57,\"name\":\"PropertyLogoSheratonResortFrenchGrayUncoatedCMYK\",\"label\":\"Property Logo Sheraton Resort French Gray Uncoated CMYK\",\"comment\":\"\",\"order\":0,\"type\":\"MEDIA\",\"props\":null},{\"number\":58,\"name\":\"PropertyLogoSheratonResortFrenchGrayUncoatedPMS\",\"label\":\"Property Logo Sheraton Resort French Gray Uncoated PMS\",\"comment\":\"\",\"order\":0,\"type\":\"MEDIA\",\"props\":null},{\"number\":59,\"name\":\"PropertyLogoSheratonTungstenCoatedCMYK\",\"label\":\"Property Logo Sheraton Tungsten Coated CMYK\",\"comment\":\"\",\"order\":0,\"type\":\"MEDIA\",\"props\":null},{\"number\":60,\"name\":\"PropertyLogoSheratonTungstenCoatedPMS\",\"label\":\"Property Logo Sheraton Tungsten Coated PMS\",\"comment\":\"\",\"order\":0,\"type\":\"MEDIA\",\"props\":null},{\"number\":61,\"name\":\"PropertyLogoSheratonTungstenRGB\",\"label\":\"Property Logo Sheraton Tungsten RGB\",\"comment\":\"\",\"order\":0,\"type\":\"MEDIA\",\"props\":null},{\"number\":62,\"name\":\"PropertyLogoSheratonTungstenUncoatedCMYK\",\"label\":\"Property Logo Sheraton Tungsten Uncoated CMYK\",\"comment\":\"\",\"order\":0,\"type\":\"MEDIA\",\"props\":null},{\"number\":63,\"name\":\"PropertyLogoSheratonTungstenUncoatedPMS\",\"label\":\"Property Logo Sheraton Tungsten Uncoated PMS\",\"comment\":\"\",\"order\":0,\"type\":\"MEDIA\",\"props\":null},{\"number\":64,\"name\":\"PropertyLogoUSNavy\",\"label\":\"Property Logo US Navy\",\"comment\":\"\",\"order\":0,\"type\":\"MEDIA\",\"props\":null},{\"number\":2,\"name\":\"AFFILIATES_CODE\",\"label\":\"Property Number\",\"comment\":\"\",\"order\":1,\"type\":\"TEXT\",\"props\":null},{\"number\":1,\"name\":\"AFFILIATE_NAME\",\"label\":\"Property Name\",\"comment\":\"\",\"order\":2,\"type\":\"TEXT\",\"props\":null},{\"number\":4,\"name\":\"ADDRESS\",\"label\":\"Property Address\",\"comment\":\"\",\"order\":4,\"type\":\"TEXT\",\"props\":null},{\"number\":5,\"name\":\"STREET\",\"label\":\"Property Street\",\"comment\":\"\",\"order\":5,\"type\":\"TEXT\",\"props\":null},{\"number\":6,\"name\":\"ZIP\",\"label\":\"Property Zip Code\",\"comment\":\"\",\"order\":6,\"type\":\"TEXT\",\"props\":null},{\"number\":7,\"name\":\"CITY\",\"label\":\"Property City\",\"comment\":\"\",\"order\":7,\"type\":\"TEXT\",\"props\":null},{\"number\":8,\"name\":\"COUNTRY\",\"label\":\"Property Country\",\"comment\":\"\",\"order\":8,\"type\":\"TEXT\",\"props\":null},{\"number\":35,\"name\":\"CombinedAddressAddress1\",\"label\":\"CombinedAddress - Address 1\",\"comment\":\"\",\"order\":-1,\"type\":\"RICHTEXT\",\"props\":null},{\"number\":36,\"name\":\"CombinedAddressAddress1Bold\",\"label\":\"CombinedAddress - Address 1 - Bold\",\"comment\":\"\",\"order\":-1,\"type\":\"RICHTEXT\",\"props\":null},{\"number\":37,\"name\":\"CombinedAddressAddress11stand2ndLineBold\",\"label\":\"CombinedAddress - Address 1 - 1st and 2nd Line Bold\",\"comment\":\"\",\"order\":-1,\"type\":\"RICHTEXT\",\"props\":null}]", headerFormat);
            sheet.addCell(headerLabel);

            // substructure floor plans
            rowIndex = createSubStructure(sheet, headerFormat, rowIndex, malPropertyVariant, malPropertyVariant.getSubNameFloorPlans(), "[{\"number\":24,\"name\":\"Floorplan1\",\"label\":\"Floorplan 1\",\"comment\":\"\",\"order\":-1,\"type\":\"MEDIA\",\"props\":null},{\"number\":25,\"name\":\"Floorplan2\",\"label\":\"Floorplan 2\",\"comment\":\"\",\"order\":-1,\"type\":\"MEDIA\",\"props\":null},{\"number\":26,\"name\":\"Floorplan3\",\"label\":\"Floorplan 3\",\"comment\":\"\",\"order\":-1,\"type\":\"MEDIA\",\"props\":null},{\"number\":27,\"name\":\"Floorplan4\",\"label\":\"Floorplan 4\",\"comment\":\"\",\"order\":-1,\"type\":\"MEDIA\",\"props\":null},{\"number\":28,\"name\":\"Floorplan5\",\"label\":\"Floorplan 5\",\"comment\":\"\",\"order\":-1,\"type\":\"MEDIA\",\"props\":null}]");
            // substructure maps
            rowIndex = createSubStructure(sheet, headerFormat, rowIndex, malPropertyVariant, malPropertyVariant.getSubNameMaps(), "[{\"number\":24,\"name\":\"Map1\",\"label\":\"Map 1\",\"comment\":\"\",\"order\":-1,\"type\":\"MEDIA\",\"props\":null},{\"number\":25,\"name\":\"Map2\",\"label\":\"Map 2\",\"comment\":\"\",\"order\":-1,\"type\":\"MEDIA\",\"props\":null},{\"number\":26,\"name\":\"Map3\",\"label\":\"Map 3\",\"comment\":\"\",\"order\":-1,\"type\":\"MEDIA\",\"props\":null},{\"number\":27,\"name\":\"Map4\",\"label\":\"Map 4\",\"comment\":\"\",\"order\":-1,\"type\":\"MEDIA\",\"props\":null},{\"number\":28,\"name\":\"Map5\",\"label\":\"Map 5\",\"comment\":\"\",\"order\":-1,\"type\":\"MEDIA\",\"props\":null}]");
            // substructure images
            rowIndex = createSubStructure(sheet, headerFormat, rowIndex, malPropertyVariant, malPropertyVariant.getSubName(), "[{\"number\":24,\"name\":\"Image1\",\"label\":\"Image 1\",\"comment\":\"\",\"order\":-1,\"type\":\"MEDIA\",\"props\":null},{\"number\":25,\"name\":\"Image2\",\"label\":\"Image 2\",\"comment\":\"\",\"order\":-1,\"type\":\"MEDIA\",\"props\":null},{\"number\":26,\"name\":\"Image3\",\"label\":\"Image 3\",\"comment\":\"\",\"order\":-1,\"type\":\"MEDIA\",\"props\":null},{\"number\":27,\"name\":\"Image4\",\"label\":\"Image 4\",\"comment\":\"\",\"order\":-1,\"type\":\"MEDIA\",\"props\":null},{\"number\":28,\"name\":\"Image5\",\"label\":\"Image 5\",\"comment\":\"\",\"order\":-1,\"type\":\"MEDIA\",\"props\":null}]");
        }
    }

    private int createSubStructure(WritableSheet sheet, WritableCellFormat headerFormat, int rowIndex, MALPropertyVariant malPropertyVariant, String subStructureName, String s) throws WriteException {
        if (subStructureName != null) {
            ++rowIndex;
            int colIndex = 0;
            Label headerLabel = new Label(colIndex++, rowIndex, subStructureName, headerFormat);
            sheet.addCell(headerLabel);
            headerLabel = new Label(colIndex++, rowIndex, "{\"default\":\"" + subStructureName + "\"}", headerFormat);
            sheet.addCell(headerLabel);
            headerLabel = new Label(colIndex++, rowIndex, "DEFAULT", headerFormat);
            sheet.addCell(headerLabel);
            headerLabel = new Label(colIndex++, rowIndex, malPropertyVariant.getStructureName(), headerFormat);
            sheet.addCell(headerLabel);
            colIndex++;
            headerLabel = new Label(colIndex++, rowIndex, "NON_AFFILIATE_STRUCTURE", headerFormat);
            sheet.addCell(headerLabel);
            colIndex++;

            headerLabel = new Label(colIndex++, rowIndex, s, headerFormat);
            sheet.addCell(headerLabel);

        }
        return rowIndex;
    }

    public class AttributeShort {
        private int number;

        private String attributeName;

        private String value;

        private String type;

        public AttributeShort() {}

        public AttributeShort( final int number, final String attributeName, final String value, final String type ) {
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

        private String type;

        private String value;

        public Attribute() {}

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

    public class Props {

        private Integer cheditor_id;

        public Props() {}

        public Props( final Integer cheditor_id ) {
            this.cheditor_id = cheditor_id;
        }

        public Integer getCheditor_id() {
            return cheditor_id;
        }

        public void setCheditor_id(Integer cheditor_id) {
            this.cheditor_id = cheditor_id;
        }
    }
}
