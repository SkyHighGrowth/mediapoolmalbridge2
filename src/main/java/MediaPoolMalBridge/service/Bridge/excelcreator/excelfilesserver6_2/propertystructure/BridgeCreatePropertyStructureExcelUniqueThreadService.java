package MediaPoolMalBridge.service.Bridge.excelcreator.excelfilesserver6_2.propertystructure;

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
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Service which creates excel files according to brands
 */
@Service
public class BridgeCreatePropertyStructureExcelUniqueThreadService extends AbstractBridgeUniqueExcelService {

    private final MALAssetStructures assetStructures;

    public BridgeCreatePropertyStructureExcelUniqueThreadService(final MALAssetStructures assetStructures )
    {
        this.assetStructures = assetStructures;
    }

    @Override
    protected void run()
    {
        assetStructures.getPropertyVariants().values().forEach(
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

                            List<AssetEntity> assetEntities = assetRepository.findPropertyAssets(malPropertyEntity.getPropertyId(), "1", TransferringAssetStatus.DONE, getMidnightBridgeLookInThePast());
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
    }

    private void addRow( final List<String[]> rows, final AssetEntity assetEntity, final String subName, final String propertyId )
    {
        final String[] row_ = new String[44];
        row_[0] = subName;
        row_[1] = assetEntity.getMalAssetId();
        row_[2] = assetEntity.getMalAssetId() + " - " + assetEntity.getCaption();
        row_[3] = propertyId;
        row_[4] = "1";
        row_[5] = "";
        row_[6] = "";
        row_[7] = "";
        row_[8] = "";
        row_[9] = "";
        row_[10] = "";
        row_[11] = "";
        row_[12] = "";
        row_[13] = "";
        row_[14] = "";
        row_[15] = "";
        row_[16] = "";
        row_[17] = "";
        row_[18] = "";
        row_[19] = "";
        row_[20] = "";
        row_[21] = "";
        row_[22] = "";
        row_[23] = "";
        row_[24] = "";
        row_[25] = "";
        row_[26] = "";
        row_[27] = "";
        row_[28] = "[MD5_HASH=" + assetEntity.getBmMd5Hash() + ";MEDIA_GUID=" + assetEntity.getBmAssetId() + ";]";
        row_[29] = "";
        row_[30] = "";
        row_[31] = "";
        row_[32] = "";
        row_[33] = "";
        row_[34] = "";
        row_[35] = "";
        row_[36] = "";
        row_[37] = "";
        row_[38] = "";
        row_[39] = "";
        row_[40] = "";
        row_[41] = "";
        row_[42] = "";
        row_[43] = propertyId;
        rows.add(row_);
    }

    private void addHeaderForPorpertyVariant(final List<String[]> rows, final MALPropertyVariant propertyVariant) {
        final String[] row = new String[45];
    }
}
