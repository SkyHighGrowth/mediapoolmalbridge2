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
import java.util.List;

@Service
public class BridgeCreateExcelXSSFFileUniqueThreadService extends AbstractBridgeUniqueExcelService {

    protected static final String[] STRUCTURES_COLUMN_NAMES = {"List ID * (no editable)",
            "List name *", "Type * (no editable)", "ID of Parent list", "Name of default entry",
            "Affiliate type", "Replace deleted entries", "Show preview for list entry", "Attributes"};

    protected static final String[] OBJECTS_COLUMN_NAMES = {"List ID * (no editable)",
            "List Entry ID * (no editable)", "Display name *", "Name of parent entry",
            "State *", "Affiliate", "Attributes"};

    private static final Gson gsonWithNulls = new GsonBuilder().serializeNulls().create();

    private MALAssetStructures assetStructures;

    private int rowIndex = 0;

    public BridgeCreateExcelXSSFFileUniqueThreadService(final MALAssetStructures assetStructures )
    {
        this.assetStructures = assetStructures;
    }

    @Override
    protected void run()
    {
        writeToFile( "DataStructures.xlsx" );
    }

    private void writeToFile(final String fileName) {
        try {
            final Workbook workbook = new XSSFWorkbook();
            createStructuresSheet( workbook );
            createObjectsSheet( workbook );
            final OutputStream fileOutputStream = new FileOutputStream( new File(appConfig.getExcelDir() + fileName) );
            workbook.write( fileOutputStream );
            fileOutputStream.close();
            workbook.close();
        } catch (final Exception e) {
            final String message = String.format("Can not create file [%s]", fileName);
            final ReportsEntity reportsEntity = new ReportsEntity(ReportType.ERROR, getClass().getName(), null, message, ReportTo.BM, null, null, null);
            reportsRepository.save(reportsEntity);
            logger.error(message, e);
        }
    }

    private void createObjectsSheet( final Workbook workbook ) throws Exception {

        final Sheet sheet = workbook.createSheet( "objects" );

        final Font font = workbook.createFont();
        font.setBold( true );
        font.setFontHeightInPoints( (short) 16 );
        final CellStyle headerFormat = workbook.createCellStyle();
        headerFormat.setFont( font );

        int colIndex = 0;
        rowIndex = 0;
        Row row = sheet.createRow( rowIndex++ );
        for (final String column : OBJECTS_COLUMN_NAMES) {
            Cell cell = row.createCell( colIndex++ );
            cell.setCellValue( column );
            cell.setCellStyle( headerFormat );
        }

        MALAssetStructures.getPropertyVariants().values().forEach(
                propertyVariant -> {
                    //logger.error( "CRESTING OBJECTS VARIANTS {}", (new Gson()).toJson( propertyVariant ) );
                    try {
                        final String brandName = assetStructures.getBrands().get(propertyVariant.getBrandId());
                        if (StringUtils.isBlank(brandName)) {
                            return;
                        }
                        //final List<MALPropertyEntity> malPropertyEntities = malPropertyRepository.findByBrandAndUpdatedIsAfter(brandName, getMidnightBridgeLookInThePast());
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

    private void digestFloorTypes(final Sheet sheet, final MALPropertyVariant propertyVariant, final MALPropertyEntity malPropertyEntity ) throws Exception {
        final String[] floorTypes = new String[2];
        floorTypes[0] = "Fmt";
        floorTypes[1] = "Fgr";
        int order = 1;
        for (final String floorType : floorTypes) {
            final List<AssetEntity> assetEntities = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "5", floorType, TransferringAssetStatus.DONE, getMidnightBridgeLookInThePast());
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

    private void digestMaps( final Sheet sheet, final MALPropertyVariant propertyVariant, final MALPropertyEntity malPropertyEntity ) throws Exception {
        final String[] mapColors = new String[3];
        mapColors[0] = "Mat";
        mapColors[1] = "Mla";
        mapColors[2] = "Mrm";
        int order = 1;
        for (final String mapColor : mapColors) {
            final List<AssetEntity> assetEntities = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "5", mapColor, TransferringAssetStatus.DONE, getMidnightBridgeLookInThePast());
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

    private void digestAssets( final Sheet sheet, final MALPropertyVariant propertyVariant, final MALPropertyEntity malPropertyEntity ) throws Exception {
        final List<AssetEntity> assetEntities = assetRepository.findPropertyAssets(malPropertyEntity.getPropertyId(), "1", TransferringAssetStatus.DONE, getMidnightBridgeLookInThePast());
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

    private void addRow( final Sheet sheet, final String subName, final String propertyId, final String name, final String jsonedAttributes, final String malAssetId ) throws Exception
    {
        Row row = sheet.createRow( rowIndex++ );
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
            row.createCell( colIndex++ ).setCellValue( subName );
            row.createCell( colIndex++ ).setCellValue( malAssetId );
            row.createCell( colIndex++ ).setCellValue( "{\"default\":\"" + malAssetId + " - " + name + "\"}" );
            row.createCell( colIndex++ ).setCellValue( propertyId );
            row.createCell( colIndex++ ).setCellValue( "EDIT_AND_ADD" );
            row.createCell( colIndex++ ).setCellValue( "" );
            row.createCell( colIndex++ ).setCellValue( jsonedAttributes );
        } else {
            row.createCell( colIndex++ ).setCellValue( subName );
            row.createCell( colIndex++ ).setCellValue( propertyId );
            row.createCell( colIndex++ ).setCellValue( "{\"default\":\"" + propertyId + " - " + name + "\"}" );
            row.createCell( colIndex++ ).setCellValue( "" );
            row.createCell( colIndex++ ).setCellValue( "EDIT_AND_ADD" );
            row.createCell( colIndex++ ).setCellValue( "" );
            row.createCell( colIndex++ ).setCellValue( jsonedAttributes );
        }
    }

    private void digestLogos( final Sheet sheet, MALPropertyVariant propertyVariant, final MALPropertyEntity malPropertyEntity, final String combinedAddressField ) throws Exception {
        final List<Attribute> attributes = new ArrayList<>();
        List<AssetEntity> logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "ko", TransferringAssetStatus.DONE, getMidnightBridgeLookInThePast());
        String propertyLogo1c = "";
        if (logo != null && !logo.isEmpty()) {
            propertyLogo1c = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
            attributes.add( new Attribute( 24,
                    "PropertyLogo1c",
                    "Property Logo 1c",
                    "",
                    -1,
                    "MEDIA",
                    null,
                    propertyLogo1c ) );
        }
        logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "cmyk", TransferringAssetStatus.DONE, getMidnightBridgeLookInThePast());
        String propertyLogo4c = "";
        if (logo != null && !logo.isEmpty()) {
            propertyLogo4c = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
            attributes.add( new Attribute( 25,
                    "PropertyLogo4c",
                    "Property Logo 4c",
                    "",
                    -1,
                    "MEDIA",
                    null,
                    propertyLogo4c ) );
        }
        logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "cmyk-B", TransferringAssetStatus.DONE, getMidnightBridgeLookInThePast());
        String propertyLogocmykb = "";
        if (logo != null && !logo.isEmpty()) {
            propertyLogocmykb = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
            attributes.add( new Attribute( 26,
                    "PropertyLogo4c-b",
                    "Property Logo 4c-b",
                    "",
                    -1,
                    "MEDIA",
                    null,
                    propertyLogocmykb ) );
        }
        logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "k", TransferringAssetStatus.DONE, getMidnightBridgeLookInThePast());
        String propertyLogo1cBlack = "";
        if (logo != null && !logo.isEmpty()) {
            propertyLogo1cBlack = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
            attributes.add( new Attribute( 27,
                    "PropertyLogo1cblack",
                    "Property Logo 1c-black",
                    "",
                    -1,
                    "MEDIA",
                    null,
                    propertyLogo1cBlack ) );
        }
        logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "pms", TransferringAssetStatus.DONE, getMidnightBridgeLookInThePast());
        String propertyLogoPMS = "";
        if (logo != null && !logo.isEmpty()) {
            propertyLogoPMS = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
            attributes.add( new Attribute( 28,
                    "PropertyLogopms",
                    "Property Logo pms",
                    "",
                    -1,
                    "MEDIA",
                    null,
                    propertyLogoPMS ) );
        }
        logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "pms-C", TransferringAssetStatus.DONE, getMidnightBridgeLookInThePast());
        String propertyLogoPMSC = "";
        if (logo != null && !logo.isEmpty()) {
            propertyLogoPMSC = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
            attributes.add( new Attribute( 29,
                    "PropertyLogopmsc",
                    "Property Logo pms-c",
                    "",
                    -1,
                    "MEDIA",
                    null,
                    propertyLogoPMSC ) );
        }
        logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "cmyk-C", TransferringAssetStatus.DONE, getMidnightBridgeLookInThePast());
        String propertyLogocmykC = "";
        if (logo != null && !logo.isEmpty()) {
            propertyLogocmykC = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
            attributes.add( new Attribute( 30,
                    "PropertyLogo4cc",
                    "Property Logo 4c-c",
                    "",
                    -1,
                    "MEDIA",
                    null,
                    propertyLogocmykC ) );
        }
        logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "ko-D", TransferringAssetStatus.DONE, getMidnightBridgeLookInThePast());
        String propertyLogokoD = "";
        if (logo != null && !logo.isEmpty()) {
            propertyLogokoD = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
            attributes.add( new Attribute( 31,
                    "PropertyLogokod",
                    "Property Logo ko-d",
                    "",
                    -1,
                    "MEDIA",
                    null,
                    propertyLogokoD ) );
        }

        //this should be checked particularly
        logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "k", TransferringAssetStatus.DONE, getMidnightBridgeLookInThePast());
        String propertyLogoBlackK = "";
        if (logo != null && !logo.isEmpty()) {
            propertyLogoBlackK = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
            attributes.add( new Attribute( 32,
                    "PropertyLogoblackK",
                    "Property Logo black-K",
                    "",
                    -1,
                    "MEDIA",
                    null,
                    propertyLogoBlackK ) );
        }
        logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "cmyk-K", TransferringAssetStatus.DONE, getMidnightBridgeLookInThePast());
        String propertyLogo4cK = "";
        if (logo != null && !logo.isEmpty()) {
            propertyLogo4cK = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
            attributes.add( new Attribute( 33,
                    "PropertyLogo4cK",
                    "Property Logo 4c-K",
                    "",
                    -1,
                    "MEDIA",
                    null,
                    propertyLogoBlackK ) );
        }
        logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "Sheraton Black logo-1", TransferringAssetStatus.DONE, getMidnightBridgeLookInThePast());
        String propertyLogoSheratonBlack = "";
        if (logo != null && !logo.isEmpty()) {
            propertyLogoSheratonBlack = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
            attributes.add( new Attribute( 39,
                    "PropertyLogoSheratonBlack",
                    "Property Logo Sheraton Black",
                    "",
                    -1,
                    "MEDIA",
                    null,
                    propertyLogoSheratonBlack ) );
        }
        logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "dusk", TransferringAssetStatus.DONE, getMidnightBridgeLookInThePast());
        String propertyLogoSheratonDusk = "";
        if (logo != null && !logo.isEmpty()) {
            propertyLogoSheratonDusk = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
            attributes.add( new Attribute( 40,
                    "propertyLogoSheratonDusk",
                    "property Logo Sheraton Dusk",
                    "",
                    -1,
                    "MEDIA",
                    null,
                    propertyLogoSheratonDusk ) );
        }
        logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "Graphite_C_cmyk", TransferringAssetStatus.DONE, getMidnightBridgeLookInThePast());
        String propertyLogoSheratonGraphiteCodedCMYK = "";
        if (logo != null && !logo.isEmpty()) {
            propertyLogoSheratonGraphiteCodedCMYK = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
            attributes.add( new Attribute( 40,
                    "PropertyLogoSheratonGraphiteCodedCMYK",
                    "Property Logo Sheraton Graphite Coded CMYK",
                    "",
                    -1,
                    "MEDIA",
                    null,
                    propertyLogoSheratonGraphiteCodedCMYK ) );
        }
        logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "Graphite_C_pms", TransferringAssetStatus.DONE, getMidnightBridgeLookInThePast());
        String propertyLogoSheratonGraphiteCodedPMS = "";
        if (logo != null && !logo.isEmpty()) {
            propertyLogoSheratonGraphiteCodedPMS = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
            attributes.add( new Attribute( 42,
                    "PropertyLogoSheratonGraphiteCodedPMS",
                    "Property Logo Sheraton Graphite Coded PMS",
                    "",
                    -1,
                    "MEDIA",
                    null,
                    propertyLogoSheratonGraphiteCodedPMS ) );
        }
        logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "Graphite_rgb", TransferringAssetStatus.DONE, getMidnightBridgeLookInThePast());
        String propertyLogoSheratonGraphiteRGB = "";
        if (logo != null && !logo.isEmpty()) {
            propertyLogoSheratonGraphiteRGB = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
            attributes.add( new Attribute( 43,
                    "PropertyLogoSheratonGraphiteRGB",
                    "Property Logo Sheraton Graphite RGB",
                    "",
                    -1,
                    "MEDIA",
                    null,
                    propertyLogoSheratonGraphiteRGB ) );
        }
        logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "Graphite_U_cmyk", TransferringAssetStatus.DONE, getMidnightBridgeLookInThePast());
        String propertyLogoSheratonGraphiteUncoatedCMYK = "";
        if (logo != null && !logo.isEmpty()) {
            propertyLogoSheratonGraphiteUncoatedCMYK = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
            attributes.add( new Attribute( 44,
                    "PropertyLogoSheratonGraphiteUncoatedCMYK",
                    "Property Logo Sheraton Graphite Uncoated CMYK",
                    "",
                    -1,
                    "MEDIA",
                    null,
                    propertyLogoSheratonGraphiteUncoatedCMYK ) );
        }
        logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "Graphite_U_pms", TransferringAssetStatus.DONE, getMidnightBridgeLookInThePast());
        String propertyLogoSheratonGraphiteUncoatedPMS = "";
        if (logo != null && !logo.isEmpty()) {
            propertyLogoSheratonGraphiteUncoatedPMS = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
            attributes.add( new Attribute( 45,
                    "PropertyLogoSheratonGraphiteUncoatedPMS",
                    "Property Logo Sheraton Graphite Uncoated PMS",
                    "",
                    -1,
                    "MEDIA",
                    null,
                    propertyLogoSheratonGraphiteUncoatedPMS ) );
        }
        logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "ivory", TransferringAssetStatus.DONE, getMidnightBridgeLookInThePast());
        String propertyLogoSheratonIvory = "";
        if (logo != null && !logo.isEmpty()) {
            propertyLogoSheratonIvory = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
            attributes.add( new Attribute( 46,
                    "PropertyLogoSheratonIvory",
                    "Property Logo Sheraton Ivory",
                    "",
                    -1,
                    "MEDIA",
                    null,
                    propertyLogoSheratonIvory ) );
        }
        logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "Knockout", TransferringAssetStatus.DONE, getMidnightBridgeLookInThePast());
        String propertyLogoSheratonKnockout = "";
        if (logo != null && !logo.isEmpty()) {
            propertyLogoSheratonKnockout = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
            attributes.add( new Attribute( 47,
                    "PropertyLogoSheratonKnockout",
                    "Property Logo Sheraton Knockout",
                    "",
                    -1,
                    "MEDIA",
                    null,
                    propertyLogoSheratonKnockout ) );
        }
        logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "Logo_Specs", TransferringAssetStatus.DONE, getMidnightBridgeLookInThePast());
        String propertyLogoSheratonLogoSpecs = "";
        if (logo != null && !logo.isEmpty()) {
            propertyLogoSheratonLogoSpecs = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
            attributes.add( new Attribute( 47,
                    "PropertyLogoSheratonLogoSpecs",
                    "Property Logo Sheraton Logo Specs",
                    "",
                    -1,
                    "MEDIA",
                    null,
                    propertyLogoSheratonLogoSpecs ) );
        }
        logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "Logo_Specs", TransferringAssetStatus.DONE, getMidnightBridgeLookInThePast());
        String propertyLogoSheratonOysterCoatedCMYK = "";
        if (logo != null && !logo.isEmpty()) {
            propertyLogoSheratonOysterCoatedCMYK = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
            attributes.add( new Attribute( 49,
                    "PropertyLogoSheratonOysterCoatedCMYK",
                    "Property Logo Sheraton Oyster Coated CMYK",
                    "",
                    0,
                    "MEDIA",
                    null,
                    propertyLogoSheratonOysterCoatedCMYK ) );
        }
        logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "Oyster_C_pms", TransferringAssetStatus.DONE, getMidnightBridgeLookInThePast());
        String propertyLogoSheratonOysterCoatedPMS = "";
        if (logo != null && !logo.isEmpty()) {
            propertyLogoSheratonOysterCoatedPMS = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
            attributes.add( new Attribute( 50,
                    "PropertyLogoSheratonOysterCoatedPMS",
                    "Property Logo Sheraton Oyster Coated PMS",
                    "",
                    0,
                    "MEDIA",
                    null,
                    propertyLogoSheratonOysterCoatedPMS ) );
        }
        logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "Oyster_rgb", TransferringAssetStatus.DONE, getMidnightBridgeLookInThePast());
        String propertyLogoSheratonOysterRGB = "";
        if (logo != null && !logo.isEmpty()) {
            propertyLogoSheratonOysterRGB = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
            attributes.add( new Attribute( 51,
                    "PropertyLogoSheratonOysterRGB",
                    "Property Logo Sheraton Oyster RGB",
                    "",
                    0,
                    "MEDIA",
                    null,
                    propertyLogoSheratonOysterRGB ) );
        }
        logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "Oyster_U_cmyk", TransferringAssetStatus.DONE, getMidnightBridgeLookInThePast());
        String propertyLogoSheratonOysterUncoatedCMYK = "";
        if (logo != null && !logo.isEmpty()) {
            propertyLogoSheratonOysterUncoatedCMYK = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
            attributes.add( new Attribute( 52,
                    "PropertyLogoSheratonOysterUncoatedCMYK",
                    "Property Logo Sheraton Oyster Uncoated CMYK",
                    "",
                    0,
                    "MEDIA",
                    null,
                    propertyLogoSheratonOysterUncoatedCMYK ) );
        }
        logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "Oyster_U_pms", TransferringAssetStatus.DONE, getMidnightBridgeLookInThePast());
        String propertyLogoSheratonOysterUncoatedPMS = "";
        if (logo != null && !logo.isEmpty()) {
            propertyLogoSheratonOysterUncoatedPMS = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
            attributes.add( new Attribute( 53,
                    "PropertyLogoSheratonOysterUncoatedPMS",
                    "Property Logo Sheraton Oyster Uncoated PMS",
                    "",
                    0,
                    "MEDIA",
                    null,
                    propertyLogoSheratonOysterUncoatedPMS ) );
        }
        logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "FrenchGray_C_cmyk", TransferringAssetStatus.DONE, getMidnightBridgeLookInThePast());
        String propertyLogoSheratonResortFrenchGrayCoatedCMYK = "";
        if (logo != null && !logo.isEmpty()) {
            propertyLogoSheratonResortFrenchGrayCoatedCMYK = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
            attributes.add( new Attribute( 54,
                    "PropertyLogoSheratonResortFrenchGrayCoatedCMYK",
                    "Property Logo Sheraton Resort French Gray Coated CMYK",
                    "",
                    0,
                    "MEDIA",
                    null,
                    propertyLogoSheratonResortFrenchGrayCoatedCMYK ) );
        }
        logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "FrenchGray_C_pms", TransferringAssetStatus.DONE, getMidnightBridgeLookInThePast());
        String propertyLogoSheratonResortFrenchGrayCoatedPMS = "";
        if (logo != null && !logo.isEmpty()) {
            propertyLogoSheratonResortFrenchGrayCoatedPMS = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
            attributes.add( new Attribute( 55,
                    "PropertyLogoSheratonResortFrenchGrayCoatedPMS",
                    "Property Logo Sheraton Resort French Gray Coated PMS",
                    "",
                    0,
                    "MEDIA",
                    null,
                    propertyLogoSheratonResortFrenchGrayCoatedPMS ) );
        }
        logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "FrenchGray_rgb", TransferringAssetStatus.DONE, getMidnightBridgeLookInThePast());
        String propertyLogoSheratonResortFrenchGrayRGB = "";
        if (logo != null && !logo.isEmpty()) {
            propertyLogoSheratonResortFrenchGrayRGB = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
            attributes.add( new Attribute( 56,
                    "PropertyLogoSheratonResortFrenchGrayRGB",
                    "Property Logo Sheraton Resort French Gray RGB",
                    "",
                    0,
                    "MEDIA",
                    null,
                    propertyLogoSheratonResortFrenchGrayRGB ) );
        }
        logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "FrenchGray_U_cmyk", TransferringAssetStatus.DONE, getMidnightBridgeLookInThePast());
        String propertyLogoSheratonResortFrenchGrayUncoatedCMYK = "";
        if (logo != null && !logo.isEmpty()) {
            propertyLogoSheratonResortFrenchGrayUncoatedCMYK = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
            attributes.add( new Attribute( 57,
                    "PropertyLogoSheratonResortFrenchGrayUncoatedCMYK",
                    "Property Logo Sheraton Resort French Gray Uncoated CMYK",
                    "",
                    0,
                    "MEDIA",
                    null,
                    propertyLogoSheratonResortFrenchGrayUncoatedCMYK ) );
        }
        logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "FrenchGray_U_pms", TransferringAssetStatus.DONE, getMidnightBridgeLookInThePast());
        String propertyLogoSheratonResortFrenchGrayUncoatedPMS = "";
        if (logo != null && !logo.isEmpty()) {
            propertyLogoSheratonResortFrenchGrayUncoatedPMS = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
            attributes.add( new Attribute( 58,
                    "PropertyLogoSheratonResortFrenchGrayUncoatedPMS",
                    "Property Logo Sheraton Resort French Gray Uncoated PMS",
                    "",
                    0,
                    "MEDIA",
                    null,
                    propertyLogoSheratonResortFrenchGrayUncoatedPMS ) );
        }
        logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "Tungsten_C_cmyk", TransferringAssetStatus.DONE, getMidnightBridgeLookInThePast());
        String propertyLogoSheratonTungstenCoatedCMYK = "";
        if (logo != null && !logo.isEmpty()) {
            propertyLogoSheratonTungstenCoatedCMYK = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
            attributes.add( new Attribute( 59,
                    "PropertyLogoSheratonTungstenCoatedCMYK",
                    "Property Logo Sheraton Tungsten Coated CMYK",
                    "",
                    0,
                    "MEDIA",
                    null,
                    propertyLogoSheratonTungstenCoatedCMYK ) );
        }
        logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "Tungsten_C_pms", TransferringAssetStatus.DONE, getMidnightBridgeLookInThePast());
        String propertyLogoSheratonTungstenCoatedPMS = "";
        if (logo != null && !logo.isEmpty()) {
            propertyLogoSheratonTungstenCoatedPMS = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
            attributes.add( new Attribute( 60,
                    "PropertyLogoSheratonTungstenCoatedPMS",
                    "Property Logo Sheraton Tungsten Coated PMS",
                    "",
                    0,
                    "MEDIA",
                    null,
                    propertyLogoSheratonTungstenCoatedPMS ) );
        }
        logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "Tungsten_rgb", TransferringAssetStatus.DONE, getMidnightBridgeLookInThePast());
        String propertyLogoSheratonTungstenRGB = "";
        if (logo != null && !logo.isEmpty()) {
            propertyLogoSheratonTungstenRGB = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
            attributes.add( new Attribute( 61,
                    "PropertyLogoSheratonTungstenRGB",
                    "Property Logo Sheraton Tungsten RGB",
                    "",
                    0,
                    "MEDIA",
                    null,
                    propertyLogoSheratonTungstenRGB ) );
        }
        logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "Tungsten_U_cmyk", TransferringAssetStatus.DONE, getMidnightBridgeLookInThePast());
        String propertyLogoSheratonTungstenUncoatedCMYK = "";
        if (logo != null && !logo.isEmpty()) {
            propertyLogoSheratonTungstenUncoatedCMYK = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
            attributes.add( new Attribute( 62,
                    "PropertyLogoSheratonTungstenUncoatedCMYK",
                    "Property Logo Sheraton Tungsten Uncoated CMYK",
                    "",
                    0,
                    "MEDIA",
                    null,
                    propertyLogoSheratonTungstenUncoatedCMYK ) );
        }
        logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "Tungsten_U_pms", TransferringAssetStatus.DONE, getMidnightBridgeLookInThePast());
        String propertyLogoSheratonTungstenUncoatedPMS = "";
        if (logo != null && !logo.isEmpty()) {
            propertyLogoSheratonTungstenUncoatedPMS = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
            attributes.add( new Attribute( 63,
                    "PropertyLogoSheratonTungstenUncoatedPMS",
                    "Property Logo Sheraton Tungsten Uncoated PMS",
                    "",
                    0,
                    "MEDIA",
                    null,
                    propertyLogoSheratonTungstenUncoatedPMS ) );
        }
        logo = assetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "2", "usna", TransferringAssetStatus.DONE, getMidnightBridgeLookInThePast());
        String propertyLogoUSNavy = "";
        if (logo != null && !logo.isEmpty()) {
            propertyLogoUSNavy = "[MD5_HASH=" + logo.get(0).getBmMd5Hash() + ";MEDIA_GUID=" + logo.get(0).getBmAssetId() + ";]";
            attributes.add( new Attribute( 64,
                    "PropertyLogoUSNavy",
                    "Property Logo US Navy",
                    "",
                    0,
                    "MEDIA",
                    null,
                    propertyLogoUSNavy ) );
        }

        attributes.add( new Attribute( 1,
                "AFFILIATE_NAME",
                "Property Name",
                "",
                2,
                "TEXT",
                null,
                malPropertyEntity.getName() ) );
        attributes.add( new Attribute( 2,
                "AFFILIATE_CODE",
                "Property Number",
                "",
                1,
                "TEXT",
                null,
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
                "Property State",
                "",
                3,
                "TEXT",
                null,
                malPropertyEntity.getState() ) );
        //row[8] = malPropertyEntity.getAddress2();
        //row[9] = malPropertyEntity.getAddress();
        attributes.add( new Attribute( 34,
                "CombinedAddress - Address 1 - Address 2",
                "CombinedAddress - Address 1 - Address 2",
                "",
                -1,
                "RICHTEXT",
                new Props( 128 ),
                combinedAddressField ) );
        attributes.add( new Attribute( 4,
                "ADDRESS",
                "Property Address",
                "",
                4,
                "TEXT",
                null,
                malPropertyEntity.getAddress() ) );
        attributes.add( new Attribute( 5,
                "STREET",
                "Property Street",
                "",
                5,
                "TEXT",
                null,
                malPropertyEntity.getAddress2() ) );
        //row[10] = malPropertyEntity.getZip();
        attributes.add( new Attribute( 6,
                "ZIP",
                "Property Zip Code",
                "",
                6,
                "TEXT",
                null,
                malPropertyEntity.getZip() ) );
        //row[11] = malPropertyEntity.getCity();
        attributes.add( new Attribute( 7,
                "CITY",
                "Property City",
                "",
                7,
                "TEXT",
                null,
                malPropertyEntity.getCity() ) );
        //row[12] = malPropertyEntity.getCountry();
        attributes.add( new Attribute( 8,
                "COUNTRY",
                "Property Country",
                "",
                8,
                "TEXT",
                null,
                malPropertyEntity.getCountry() ) );
        //row[13] = malPropertyEntity.getUrl();
        attributes.add( new Attribute( 9,
                "PropertyURL",
                "Property URL",
                "",
                -1,
                "TEXT",
                null,
                malPropertyEntity.getUrl() ) );
        //row[14] = malPropertyEntity.getTelephone();
        attributes.add( new Attribute( 10,
                "PropertyTelephone",
                "Property Telephone",
                "",
                -1,
                "TEXT",
                null,
                malPropertyEntity.getTelephone() ) );
        //row[15] = malPropertyEntity.getBrand();
        //row[16] = malPropertyEntity.getParentBrand();
        //row[17] = malPropertyEntity.getLatitude();
        attributes.add( new Attribute( 13,
                "Latitude",
                "Latitude",
                "",
                -1,
                "TEXT",
                null,
                malPropertyEntity.getLatitude() ) );
        //row[18] = malPropertyEntity.getLongitude();
        attributes.add( new Attribute( 14,
                "Longitude",
                "Longitude",
                "",
                -1,
                "TEXT",
                null,
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

    private void createStructuresSheet( final Workbook workbook ) throws Exception {

        final Sheet sheet = workbook.createSheet("structures");

        final Font font = workbook.createFont();
        font.setBold( true );
        font.setFontHeightInPoints( (short) 16 );
        final CellStyle headerFormat = workbook.createCellStyle();
        headerFormat.setFont( font );

        int colIndex = 0;
        int rowIndex = 0;
        Row row = sheet.createRow( rowIndex++ );
        for (final String column : STRUCTURES_COLUMN_NAMES) {
            Cell cell = row.createCell( colIndex++ );
            cell.setCellValue( column );
            cell.setCellStyle( headerFormat );
        }

        for( final MALPropertyVariant malPropertyVariant : MALAssetStructures.getPropertyVariants().values() ) {
            //parent
            row = sheet.createRow( rowIndex++ );

            colIndex = 0;

            row.createCell( colIndex++ ).setCellValue( malPropertyVariant.getStructureName() );

            row.createCell( colIndex++ ).setCellValue( "{\"default\":\"" + malPropertyVariant.getStructureName() + "\"}" );

            row.createCell( colIndex++ ).setCellValue( "DEFAULT" );

            //no parent
            //headerLabel = new Label(colIndex++, rowIndex, "DEFAULT", headerFormat);
            //sheet.addCell(headerLabel);
            colIndex++;
            colIndex++;

            row.createCell( colIndex++ ).setCellValue( "MULTI_OBJECT_AFFILIATE_STRUCTURE" );

            row.createCell( colIndex++ ).setCellValue( "false" );

            colIndex++;

            /*List<Attribute> attributes = new ArrayList<>();
            attributes.add(new Attribute(1, "AFFILIATE_NAME", "Affiliate Name", null, 2, "TEXT", null, malPropertyVariant.getStructureName()));
            attributes.add(new Attribute(2, "AFFILIATE_CODE", "Affiliate Code", null, 1, "TEXT", null, malPropertyVariant.getId()));
            attributes.add(new Attribute(3, "DEPARTMENT", "Department", null, 3, "TEXT", null, null));
            attributes.add(new Attribute(4, "ADDRESS", "Address", null, 4, "TEXT", null, null));
            attributes.add(new Attribute(5, "STREET", "Street", null, 5, "TEXT", null, null));
            attributes.add(new Attribute(6, "ZIP", "Zip Code", null, 6, "TEXT", null, null));
            attributes.add(new Attribute(7, "CITY", "City", null, 7, "TEXT", null, null));
            attributes.add(new Attribute(8, "COUNTRY", "Country", null, 8, "TEXT", null, null));*/

            row.createCell( colIndex++ ).setCellValue( "[{\"number\":3,\"name\":\"PropertyState\",\"label\":\"Property State\",\"comment\":\"\",\"order\":-1,\"type\":\"TEXT\",\"props\":null},{\"number\":9,\"name\":\"PropertyURL\",\"label\":\"Property URL\",\"comment\":\"\",\"order\":-1,\"type\":\"TEXT\",\"props\":null},{\"number\":10,\"name\":\"PropertyTelephone\",\"label\":\"Property Telephone\",\"comment\":\"\",\"order\":-1,\"type\":\"TEXT\",\"props\":null},{\"number\":11,\"name\":\"PropertyFacsimile\",\"label\":\"Property Facsimile\",\"comment\":\"\",\"order\":-1,\"type\":\"TEXT\",\"props\":null},{\"number\":13,\"name\":\"Latitude\",\"label\":\"Latitude\",\"comment\":\"\",\"order\":-1,\"type\":\"TEXT\",\"props\":null},{\"number\":14,\"name\":\"Longitude\",\"label\":\"Longitude\",\"comment\":\"\",\"order\":-1,\"type\":\"TEXT\",\"props\":null},{\"number\":24,\"name\":\"PropertyLogo1c\",\"label\":\"Property Logo 1c\",\"comment\":\"\",\"order\":-1,\"type\":\"MEDIA\",\"props\":null},{\"number\":25,\"name\":\"PropertyLogo4c\",\"label\":\"Property Logo 4c\",\"comment\":\"\",\"order\":-1,\"type\":\"MEDIA\",\"props\":null},{\"number\":26,\"name\":\"PropertyLogo4cb\",\"label\":\"Property Logo 4c-b\",\"comment\":\"\",\"order\":-1,\"type\":\"MEDIA\",\"props\":null},{\"number\":27,\"name\":\"PropertyLogo1cblack\",\"label\":\"Property Logo 1c-black\",\"comment\":\"\",\"order\":-1,\"type\":\"MEDIA\",\"props\":null},{\"number\":28,\"name\":\"PropertyLogopms\",\"label\":\"Property Logo pms\",\"comment\":\"\",\"order\":-1,\"type\":\"MEDIA\",\"props\":null},{\"number\":29,\"name\":\"PropertyLogopmsc\",\"label\":\"Property Logo pms-c\",\"comment\":\"\",\"order\":-1,\"type\":\"MEDIA\",\"props\":null},{\"number\":30,\"name\":\"PropertyLogo4cc\",\"label\":\"Property Logo 4c-c\",\"comment\":\"\",\"order\":-1,\"type\":\"MEDIA\",\"props\":null},{\"number\":31,\"name\":\"PropertyLogokod\",\"label\":\"Property Logo ko-d\",\"comment\":\"\",\"order\":-1,\"type\":\"MEDIA\",\"props\":null},{\"number\":32,\"name\":\"PropertyLogoblackK\",\"label\":\"Property Logo black-K\",\"comment\":\"\",\"order\":-1,\"type\":\"MEDIA\",\"props\":null},{\"number\":33,\"name\":\"PropertyLogo4cK\",\"label\":\"Property Logo 4c-K\",\"comment\":\"\",\"order\":-1,\"type\":\"MEDIA\",\"props\":null},{\"number\":34,\"name\":\"CombinedAddress\",\"label\":\"Combined Address\",\"comment\":\"\",\"order\":-1,\"type\":\"RICHTEXT\",\"props\":null},{\"number\":39,\"name\":\"PropertyLogoSheratonBlack\",\"label\":\"Property Logo Sheraton Black\",\"comment\":\"\",\"order\":0,\"type\":\"MEDIA\",\"props\":null},{\"number\":40,\"name\":\"PropertyLogoSheratonDusk\",\"label\":\"Property Logo Sheraton Dusk\",\"comment\":\"\",\"order\":0,\"type\":\"MEDIA\",\"props\":null},{\"number\":41,\"name\":\"PropertyLogoSheratonGraphiteCodedCMYK\",\"label\":\"Property Logo Sheraton Graphite Coded CMYK\",\"comment\":\"\",\"order\":0,\"type\":\"MEDIA\",\"props\":null},{\"number\":42,\"name\":\"PropertyLogoSheratonGraphiteCodedPMS\",\"label\":\"Property Logo Sheraton Graphite Coded PMS\",\"comment\":\"\",\"order\":0,\"type\":\"MEDIA\",\"props\":null},{\"number\":43,\"name\":\"PropertyLogoSheratonGraphiteRGB\",\"label\":\"Property Logo Sheraton Graphite RGB\",\"comment\":\"\",\"order\":0,\"type\":\"MEDIA\",\"props\":null},{\"number\":44,\"name\":\"PropertyLogoSheratonGraphiteUncoatedCMYK\",\"label\":\"Property Logo Sheraton Graphite Uncoated CMYK\",\"comment\":\"\",\"order\":0,\"type\":\"MEDIA\",\"props\":null},{\"number\":45,\"name\":\"PropertyLogoSheratonGraphiteUncoatedPMS\",\"label\":\"Property Logo Sheraton Graphite Uncoated PMS\",\"comment\":\"\",\"order\":0,\"type\":\"MEDIA\",\"props\":null},{\"number\":46,\"name\":\"PropertyLogoSheratonIvory\",\"label\":\"Property Logo Sheraton Ivory\",\"comment\":\"\",\"order\":0,\"type\":\"MEDIA\",\"props\":null},{\"number\":47,\"name\":\"PropertyLogoSheratonKnockout\",\"label\":\"Property Logo Sheraton Knockout\",\"comment\":\"\",\"order\":0,\"type\":\"MEDIA\",\"props\":null},{\"number\":48,\"name\":\"PropertyLogoSheratonLogoSpecs\",\"label\":\"Property Logo Sheraton Logo Specs\",\"comment\":\"\",\"order\":0,\"type\":\"MEDIA\",\"props\":null},{\"number\":49,\"name\":\"PropertyLogoSheratonOysterCoatedCMYK\",\"label\":\"Property Logo Sheraton Oyster Coated CMYK\",\"comment\":\"\",\"order\":0,\"type\":\"MEDIA\",\"props\":null},{\"number\":50,\"name\":\"PropertyLogoSheratonOysterCoatedPMS\",\"label\":\"Property Logo Sheraton Oyster Coated PMS\",\"comment\":\"\",\"order\":0,\"type\":\"MEDIA\",\"props\":null},{\"number\":51,\"name\":\"PropertyLogoSheratonOysterRGB\",\"label\":\"Property Logo Sheraton Oyster RGB\",\"comment\":\"\",\"order\":0,\"type\":\"MEDIA\",\"props\":null},{\"number\":52,\"name\":\"PropertyLogoSheratonOysterUncoatedCMYK\",\"label\":\"Property Logo Sheraton Oyster Uncoated CMYK\",\"comment\":\"\",\"order\":0,\"type\":\"MEDIA\",\"props\":null},{\"number\":53,\"name\":\"PropertyLogoSheratonOysterUncoatedPMS\",\"label\":\"Property Logo Sheraton Oyster Uncoated PMS\",\"comment\":\"\",\"order\":0,\"type\":\"MEDIA\",\"props\":null},{\"number\":54,\"name\":\"PropertyLogoSheratonResortFrenchGrayCoatedCMYK\",\"label\":\"Property Logo Sheraton Resort French Gray Coated CMYK\",\"comment\":\"\",\"order\":0,\"type\":\"MEDIA\",\"props\":null},{\"number\":55,\"name\":\"PropertyLogoSheratonResortFrenchGrayCoatedPMS\",\"label\":\"Property Logo Sheraton Resort French Gray Coated PMS\",\"comment\":\"\",\"order\":0,\"type\":\"MEDIA\",\"props\":null},{\"number\":56,\"name\":\"PropertyLogoSheratonResortFrenchGrayRGB\",\"label\":\"Property Logo Sheraton Resort French Gray RGB\",\"comment\":\"\",\"order\":0,\"type\":\"MEDIA\",\"props\":null},{\"number\":57,\"name\":\"PropertyLogoSheratonResortFrenchGrayUncoatedCMYK\",\"label\":\"Property Logo Sheraton Resort French Gray Uncoated CMYK\",\"comment\":\"\",\"order\":0,\"type\":\"MEDIA\",\"props\":null},{\"number\":58,\"name\":\"PropertyLogoSheratonResortFrenchGrayUncoatedPMS\",\"label\":\"Property Logo Sheraton Resort French Gray Uncoated PMS\",\"comment\":\"\",\"order\":0,\"type\":\"MEDIA\",\"props\":null},{\"number\":59,\"name\":\"PropertyLogoSheratonTungstenCoatedCMYK\",\"label\":\"Property Logo Sheraton Tungsten Coated CMYK\",\"comment\":\"\",\"order\":0,\"type\":\"MEDIA\",\"props\":null},{\"number\":60,\"name\":\"PropertyLogoSheratonTungstenCoatedPMS\",\"label\":\"Property Logo Sheraton Tungsten Coated PMS\",\"comment\":\"\",\"order\":0,\"type\":\"MEDIA\",\"props\":null},{\"number\":61,\"name\":\"PropertyLogoSheratonTungstenRGB\",\"label\":\"Property Logo Sheraton Tungsten RGB\",\"comment\":\"\",\"order\":0,\"type\":\"MEDIA\",\"props\":null},{\"number\":62,\"name\":\"PropertyLogoSheratonTungstenUncoatedCMYK\",\"label\":\"Property Logo Sheraton Tungsten Uncoated CMYK\",\"comment\":\"\",\"order\":0,\"type\":\"MEDIA\",\"props\":null},{\"number\":63,\"name\":\"PropertyLogoSheratonTungstenUncoatedPMS\",\"label\":\"Property Logo Sheraton Tungsten Uncoated PMS\",\"comment\":\"\",\"order\":0,\"type\":\"MEDIA\",\"props\":null},{\"number\":64,\"name\":\"PropertyLogoUSNavy\",\"label\":\"Property Logo US Navy\",\"comment\":\"\",\"order\":0,\"type\":\"MEDIA\",\"props\":null},{\"number\":2,\"name\":\"AFFILIATES_CODE\",\"label\":\"Property Number\",\"comment\":\"\",\"order\":1,\"type\":\"TEXT\",\"props\":null},{\"number\":1,\"name\":\"AFFILIATE_NAME\",\"label\":\"Property Name\",\"comment\":\"\",\"order\":2,\"type\":\"TEXT\",\"props\":null},{\"number\":4,\"name\":\"ADDRESS\",\"label\":\"Property Address\",\"comment\":\"\",\"order\":4,\"type\":\"TEXT\",\"props\":null},{\"number\":5,\"name\":\"STREET\",\"label\":\"Property Street\",\"comment\":\"\",\"order\":5,\"type\":\"TEXT\",\"props\":null},{\"number\":6,\"name\":\"ZIP\",\"label\":\"Property Zip Code\",\"comment\":\"\",\"order\":6,\"type\":\"TEXT\",\"props\":null},{\"number\":7,\"name\":\"CITY\",\"label\":\"Property City\",\"comment\":\"\",\"order\":7,\"type\":\"TEXT\",\"props\":null},{\"number\":8,\"name\":\"COUNTRY\",\"label\":\"Property Country\",\"comment\":\"\",\"order\":8,\"type\":\"TEXT\",\"props\":null}]" );

            //floorplans
            row = sheet.createRow( rowIndex++ );

            colIndex = 0;

            row.createCell( colIndex++ ).setCellValue( malPropertyVariant.getSubNameFloorPlans() );

            row.createCell( colIndex++ ).setCellValue( "{\"default\":\"" + malPropertyVariant.getSubNameFloorPlans() + "\"}" );

            row.createCell( colIndex++ ).setCellValue( "DEFAULT" );

            row.createCell( colIndex++ ).setCellValue( malPropertyVariant.getStructureName() );

            colIndex++;

            row.createCell( colIndex++ ).setCellValue( "NON_AFFILIATE_STRUCTURE" );

            row.createCell( colIndex++ ).setCellValue( "false" );

            colIndex++;

            /*attributes = new ArrayList<>();
            attributes.add(new Attribute(1, "AFFILIATE_NAME", "Affiliate Name", null, 2, "TEXT", null, malPropertyVariant.getStructureName()));
            attributes.add(new Attribute(2, "AFFILIATE_CODE", "Affiliate Code", null, 1, "TEXT", null, malPropertyVariant.getId()));
            attributes.add(new Attribute(3, "DEPARTMENT", "Department", null, 3, "TEXT", null, null));
            attributes.add(new Attribute(4, "ADDRESS", "Address", null, 4, "TEXT", null, null));
            attributes.add(new Attribute(5, "STREET", "Street", null, 5, "TEXT", null, null));
            attributes.add(new Attribute(6, "ZIP", "Zip Code", null, 6, "TEXT", null, null));
            attributes.add(new Attribute(7, "CITY", "City", null, 7, "TEXT", null, null));
            attributes.add(new Attribute(8, "COUNTRY", "Country", null, 8, "TEXT", null, null));*/

            row.createCell( colIndex++ ).setCellValue( "[{\"number\":24,\"name\":\"Floorplan1\",\"label\":\"Floorplan 1\",\"comment\":\"\",\"order\":-1,\"type\":\"MEDIA\",\"props\":null},{\"number\":25,\"name\":\"Floorplan2\",\"label\":\"Floorplan 2\",\"comment\":\"\",\"order\":-1,\"type\":\"MEDIA\",\"props\":null},{\"number\":26,\"name\":\"Floorplan3\",\"label\":\"Floorplan 3\",\"comment\":\"\",\"order\":-1,\"type\":\"MEDIA\",\"props\":null},{\"number\":27,\"name\":\"Floorplan4\",\"label\":\"Floorplan 4\",\"comment\":\"\",\"order\":-1,\"type\":\"MEDIA\",\"props\":null},{\"number\":28,\"name\":\"Floorplan5\",\"label\":\"Floorplan 5\",\"comment\":\"\",\"order\":-1,\"type\":\"MEDIA\",\"props\":null}]" );

            //maps
            row = sheet.createRow( rowIndex++ );

            colIndex = 0;

            row.createCell( colIndex++ ).setCellValue( malPropertyVariant.getSubNameMaps() );

            row.createCell( colIndex++ ).setCellValue( "{\"default\":\"" + malPropertyVariant.getSubNameMaps() + "\"}" );

            row.createCell( colIndex++ ).setCellValue( "DEFAULT" );

            row.createCell( colIndex++ ).setCellValue( malPropertyVariant.getStructureName() );

            colIndex++;

            row.createCell( colIndex++ ).setCellValue( "NON_AFFILIATE_STRUCTURE" );

            row.createCell( colIndex++ ).setCellValue( "false" );

            colIndex++;

            /*attributes = new ArrayList<>();
            attributes.add(new Attribute(1, "AFFILIATE_NAME", "Affiliate Name", null, 2, "TEXT", null, malPropertyVariant.getStructureName()));
            attributes.add(new Attribute(2, "AFFILIATE_CODE", "Affiliate Code", null, 1, "TEXT", null, malPropertyVariant.getId()));
            attributes.add(new Attribute(3, "DEPARTMENT", "Department", null, 3, "TEXT", null, null));
            attributes.add(new Attribute(4, "ADDRESS", "Address", null, 4, "TEXT", null, null));
            attributes.add(new Attribute(5, "STREET", "Street", null, 5, "TEXT", null, null));
            attributes.add(new Attribute(6, "ZIP", "Zip Code", null, 6, "TEXT", null, null));
            attributes.add(new Attribute(7, "CITY", "City", null, 7, "TEXT", null, null));
            attributes.add(new Attribute(8, "COUNTRY", "Country", null, 8, "TEXT", null, null));*/

            row.createCell( colIndex++ ).setCellValue( "[{\"number\":24,\"name\":\"Map1\",\"label\":\"Map 1\",\"comment\":\"\",\"order\":-1,\"type\":\"MEDIA\",\"props\":null},{\"number\":25,\"name\":\"Map2\",\"label\":\"Map 2\",\"comment\":\"\",\"order\":-1,\"type\":\"MEDIA\",\"props\":null},{\"number\":26,\"name\":\"Map3\",\"label\":\"Map 3\",\"comment\":\"\",\"order\":-1,\"type\":\"MEDIA\",\"props\":null},{\"number\":27,\"name\":\"Map4\",\"label\":\"Map 4\",\"comment\":\"\",\"order\":-1,\"type\":\"MEDIA\",\"props\":null},{\"number\":28,\"name\":\"Map5\",\"label\":\"Map 5\",\"comment\":\"\",\"order\":-1,\"type\":\"MEDIA\",\"props\":null}]" );

            //images
            row = sheet.createRow( rowIndex++ );

            colIndex = 0;

            row.createCell( colIndex++ ).setCellValue( malPropertyVariant.getSubName() );

            row.createCell( colIndex++ ).setCellValue( "{\"default\":\"" + malPropertyVariant.getSubName() + "\"}" );

            row.createCell( colIndex++ ).setCellValue( "DEFAULT" );

            row.createCell( colIndex++ ).setCellValue( malPropertyVariant.getStructureName() );

            colIndex++;

            row.createCell( colIndex++ ).setCellValue( "NON_AFFILIATE_STRUCTURE" );

            row.createCell( colIndex++ ).setCellValue( "false" );

            colIndex++;

            /*attributes = new ArrayList<>();
            attributes.add(new Attribute(1, "AFFILIATE_NAME", "Affiliate Name", null, 2, "TEXT", null, malPropertyVariant.getStructureName()));
            attributes.add(new Attribute(2, "AFFILIATE_CODE", "Affiliate Code", null, 1, "TEXT", null, malPropertyVariant.getId()));
            attributes.add(new Attribute(3, "DEPARTMENT", "Department", null, 3, "TEXT", null, null));
            attributes.add(new Attribute(4, "ADDRESS", "Address", null, 4, "TEXT", null, null));
            attributes.add(new Attribute(5, "STREET", "Street", null, 5, "TEXT", null, null));
            attributes.add(new Attribute(6, "ZIP", "Zip Code", null, 6, "TEXT", null, null));
            attributes.add(new Attribute(7, "CITY", "City", null, 7, "TEXT", null, null));
            attributes.add(new Attribute(8, "COUNTRY", "Country", null, 8, "TEXT", null, null));*/

            row.createCell( colIndex++ ).setCellValue( "[{\"number\":24,\"name\":\"Image1\",\"label\":\"Image 1\",\"comment\":\"\",\"order\":-1,\"type\":\"MEDIA\",\"props\":null},{\"number\":25,\"name\":\"Image2\",\"label\":\"Image 2\",\"comment\":\"\",\"order\":-1,\"type\":\"MEDIA\",\"props\":null},{\"number\":26,\"name\":\"Image3\",\"label\":\"Image 3\",\"comment\":\"\",\"order\":-1,\"type\":\"MEDIA\",\"props\":null},{\"number\":27,\"name\":\"Image4\",\"label\":\"Image 4\",\"comment\":\"\",\"order\":-1,\"type\":\"MEDIA\",\"props\":null},{\"number\":28,\"name\":\"Image5\",\"label\":\"Image 5\",\"comment\":\"\",\"order\":-1,\"type\":\"MEDIA\",\"props\":null}]" );
        }
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

        private String name;

        private String label;

        private String comment;

        private int order;

        private String type;

        private Props props;

        private String value;

        public Attribute() {}

        public Attribute(final int number, final String name, final String label, final String comment, final int order, final String type, final Props props, final String value ) {
            this.number = number;
            this.name = name;
            this.label = label;
            this.comment = comment;
            this.order = order;
            this.type = type;
            this.props = props;
            this.value = value;
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

        public Props() {}

        public Props( final Integer ckeditor_id ) {
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
