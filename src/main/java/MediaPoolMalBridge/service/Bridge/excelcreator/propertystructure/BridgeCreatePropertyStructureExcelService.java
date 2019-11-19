package MediaPoolMalBridge.service.Bridge.excelcreator.propertystructure;

import MediaPoolMalBridge.model.MAL.MALAssetStructures;
import MediaPoolMalBridge.persistence.entity.enums.asset.TransferringBMConnectionAssetStatus;
import MediaPoolMalBridge.persistence.entity.BM.BMAssetEntity;
import MediaPoolMalBridge.persistence.entity.MAL.MALPropertyEntity;
import MediaPoolMalBridge.service.Bridge.excelcreator.AbstractBridgeExcelService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BridgeCreatePropertyStructureExcelService extends AbstractBridgeExcelService {

    private MALAssetStructures malAssetStructures;

    public BridgeCreatePropertyStructureExcelService( final MALAssetStructures malAssetStructures )
    {
        this.malAssetStructures = malAssetStructures;
    }

    public void createPropertyStructuresExcelFiles()
    {
        MALAssetStructures.getPropertyVariants().values().forEach(
                propertyVariant -> {
                    final String[] fields = propertyVariant.getFieldsArray();
                    final String brandName = malAssetStructures.getBrands().get( propertyVariant.getBrandId() );
                    if( StringUtils.isBlank( brandName ) ) {
                        return;
                    }
                    final List<String[]> rows = new ArrayList<>();
                    final List<MALPropertyEntity> malPropertyEntities = malPropertyRepository.findByBrand( brandName );
                    malPropertyEntities.forEach( malPropertyEntity -> {
                        String addressField01 = propertyVariant.getAddressField01();
                        String addressField02 = propertyVariant.getAddressField02();
                        String addressField03 = propertyVariant.getAddressField03();
                        String addressField04 = propertyVariant.getAddressField04();
                        String addressField05 = propertyVariant.getAddressField05();
                        for (final String field : fields) {
                            final String replacement;
                            switch( field ) {
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
                            if( StringUtils.isNotBlank( addressField01 ) ) {
                                addressField01 = addressField01.replace("{" + field + "}", replacement);
                            }
                            if( StringUtils.isNotBlank( addressField02 ) ) {
                                addressField02 = addressField02.replace("{" + field + "}", replacement);
                            }
                            if( StringUtils.isNotBlank( addressField03 ) ) {
                                addressField03 = addressField03.replace("{" + field + "}", replacement);
                            }
                            if( StringUtils.isNotBlank( addressField04 ) ) {
                                addressField04 = addressField04.replace("{" + field + "}", replacement);
                            }
                            if( StringUtils.isNotBlank( addressField05 ) ) {
                                addressField05 = addressField05.replace("{" + field + "}", replacement);
                            }
                        }
                        List<BMAssetEntity> logo = bmAssetRepository.findAssetDetails( malPropertyEntity.getPropertyId(), "2", "ko", TransferringBMConnectionAssetStatus.DONE );
                        String propertyLogo1c = "";
                        if( logo != null && !logo.isEmpty() ) {
                            propertyLogo1c = "[MD5_HASH=" + logo.get( 0 ).getBmHash() + ";MEDIA_GUID=" + logo.get( 0 ).getAssetId() + ";]";
                        }
                        logo = bmAssetRepository.findAssetDetails( malPropertyEntity.getPropertyId(), "2", "cmyk", TransferringBMConnectionAssetStatus.DONE );
                        String propertyLogo4c = "";
                        if( logo != null && !logo.isEmpty() ) {
                            propertyLogo4c = "[MD5_HASH=" + logo.get( 0 ).getBmHash() + ";MEDIA_GUID=" + logo.get( 0 ).getAssetId() + ";]";
                        }
                        logo = bmAssetRepository.findAssetDetails( malPropertyEntity.getPropertyId(), "2", "cmyk-B", TransferringBMConnectionAssetStatus.DONE );
                        String propertyLogo4cb = "";
                        if( logo != null && !logo.isEmpty() ) {
                            propertyLogo4cb = "[MD5_HASH=" + logo.get( 0 ).getBmHash() + ";MEDIA_GUID=" + logo.get( 0 ).getAssetId() + ";]";
                        }
                        logo = bmAssetRepository.findAssetDetails( malPropertyEntity.getPropertyId(), "2", "k", TransferringBMConnectionAssetStatus.DONE );
                        String propertyLogolcBlack = "";
                        if( logo != null && !logo.isEmpty() ) {
                            propertyLogolcBlack = "[MD5_HASH=" + logo.get( 0 ).getBmHash() + ";MEDIA_GUID=" + logo.get( 0 ).getAssetId() + ";]";
                        }
                        logo = bmAssetRepository.findAssetDetails( malPropertyEntity.getPropertyId(), "2", "pms", TransferringBMConnectionAssetStatus.DONE );
                        String propertyLogoPMS = "";
                        if( logo != null && !logo.isEmpty() ) {
                            propertyLogoPMS = "[MD5_HASH=" + logo.get( 0 ).getBmHash() + ";MEDIA_GUID=" + logo.get( 0 ).getAssetId() + ";]";
                        }
                        logo = bmAssetRepository.findAssetDetails( malPropertyEntity.getPropertyId(), "2", "pms-c", TransferringBMConnectionAssetStatus.DONE );
                        String propertyLogoPMSC = "";
                        if( logo != null && !logo.isEmpty() ) {
                            propertyLogoPMSC = "[MD5_HASH=" + logo.get( 0 ).getBmHash() + ";MEDIA_GUID=" + logo.get( 0 ).getAssetId() + ";]";
                        }
                        logo = bmAssetRepository.findAssetDetails( malPropertyEntity.getPropertyId(), "2", "cmyk-C", TransferringBMConnectionAssetStatus.DONE );
                        String propertyLogo4cc = "";
                        if( logo != null && !logo.isEmpty() ) {
                            propertyLogo4cc = "[MD5_HASH=" + logo.get( 0 ).getBmHash() + ";MEDIA_GUID=" + logo.get( 0 ).getAssetId() + ";]";
                        }
                        logo = bmAssetRepository.findAssetDetails( malPropertyEntity.getPropertyId(), "2", "ko-D", TransferringBMConnectionAssetStatus.DONE );
                        String propertyLogolcd = "";
                        if( logo != null && !logo.isEmpty() ) {
                            propertyLogolcd = "[MD5_HASH=" + logo.get( 0 ).getBmHash() + ";MEDIA_GUID=" + logo.get( 0 ).getAssetId() + ";]";
                        }

                        String[] row = new String[ 45 ];
                        row[ 0 ] = propertyVariant.getStructureName();
                        row[ 1 ] = malPropertyEntity.getPropertyId();
                        row[ 2 ] = malPropertyEntity.getPropertyId() + " - " + malPropertyEntity.getName();
                        row[ 3 ] = "";
                        row[ 4 ] = String.valueOf( malPropertyEntity.getStatus() );
                        row[ 5 ] = malPropertyEntity.getName();
                        row[ 6 ] = malPropertyEntity.getPropertyId();
                        row[ 7 ] = malPropertyEntity.getState();
                        row[ 8 ] = malPropertyEntity.getState();
                        row[ 9 ] = malPropertyEntity.getAddress2();
                        row[ 10 ] = malPropertyEntity.getAddress();
                        row[ 11 ] = malPropertyEntity.getZip();
                        row[ 12 ] = malPropertyEntity.getCity();
                        row[ 13 ] = malPropertyEntity.getCountry();
                        row[ 14 ] = malPropertyEntity.getUrl();
                        row[ 15 ] = malPropertyEntity.getTelephone();
                        row[ 16 ] = malPropertyEntity.getBrand();
                        row[ 17 ] = malPropertyEntity.getParentBrand();
                        row[ 18 ] = malPropertyEntity.getLatitude();
                        row[ 19 ] = malPropertyEntity.getLongitude();
                        row[ 20 ] = "";
                        row[ 21 ] = "";
                        row[ 22 ] = "";
                        row[ 23 ] = "";
                        row[ 24 ] = "";
                        row[ 25 ] = "";
                        row[ 26 ] = "";
                        row[ 27 ] = "";
                        row[ 28 ] = "";
                        row[ 29 ] = propertyLogo1c;
                        row[ 30 ] = propertyLogo4c;
                        row[ 31 ] = propertyLogo4cb;
                        row[ 32 ] = propertyLogolcBlack;
                        row[ 33 ] = propertyLogoPMS;
                        row[ 34 ] = propertyLogoPMSC;
                        row[ 35 ] = propertyLogo4cc;
                        row[ 36 ] = propertyLogolcd;
                        row[ 37 ] = "";
                        row[ 38 ] = "";
                        row[ 39 ] = addressField01;
                        row[ 40 ] = addressField02;
                        row[ 41 ] = addressField03;
                        row[ 42 ] = addressField04;
                        row[ 43 ] = addressField05;
                        row[ 44 ] = malPropertyEntity.getPropertyId();
                        rows.add( row );

                        List<BMAssetEntity> bmAssetEntities = bmAssetRepository.findPropertyAssets( malPropertyEntity.getPropertyId(), "1" );
                        if( bmAssetEntities != null && !bmAssetEntities.isEmpty() ) {
                            bmAssetEntities.forEach( bmAssetEntity ->
                                addRow( rows, bmAssetEntity, propertyVariant.getSubName(), malPropertyEntity.getPropertyId() ) );
                        }

                        final String[] mapColors = new String[ 3 ];
                        mapColors[ 0 ] = "Mat";
                        mapColors[ 1 ] = "Mla";
                        mapColors[ 2 ] = "Mrm";
                        for( final String mapColor : mapColors ) {
                            bmAssetEntities = bmAssetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "5", mapColor, TransferringBMConnectionAssetStatus.DONE);
                            if (bmAssetEntities != null && !bmAssetEntities.isEmpty()) {
                                bmAssetEntities.forEach(bmAssetEntity ->
                                    addRow( rows, bmAssetEntity, propertyVariant.getSubNameMaps(), malPropertyEntity.getPropertyId() ) );
                            }
                        }

                        final String[] floorTypes = new String[ 2 ];
                        floorTypes[ 0 ] = "Fmt";
                        floorTypes[ 1 ] = "Fgr";
                        for( final String floorType : floorTypes ) {
                            bmAssetEntities = bmAssetRepository.findAssetDetails(malPropertyEntity.getPropertyId(), "5", floorType, TransferringBMConnectionAssetStatus.DONE);
                            if (bmAssetEntities != null && !bmAssetEntities.isEmpty()) {
                                bmAssetEntities.forEach(bmAssetEntity ->
                                    addRow( rows, bmAssetEntity, propertyVariant.getSubNameFloorPlans(), malPropertyEntity.getPropertyId() ) );
                            }
                        }
                    } );

                    writeToFile( propertyVariant.getStructureName(), rows );
                } );
    }

    private void addRow( final List<String[]> rows, final BMAssetEntity bmAssetEntity, final String subName, final String propertyId )
    {
        final String[] row_ = new String[45];
        row_[0] = subName;
        row_[1] = bmAssetEntity.getMalAssetId();
        row_[2] = bmAssetEntity.getMalAssetId() + " - " + bmAssetEntity.getCaption();
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
        row_[28] = "[MD5_HASH=" + bmAssetEntity.getBmHash() + ";MEDIA_GUID=" + bmAssetEntity.getAssetId() + ";]";
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
        row_[43] = "";
        row_[44] = propertyId;
        rows.add(row_);
    }
}
