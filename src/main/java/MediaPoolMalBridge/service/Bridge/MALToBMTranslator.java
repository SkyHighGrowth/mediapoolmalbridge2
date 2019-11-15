package MediaPoolMalBridge.service.Bridge;

import MediaPoolMalBridge.clients.BrandMaker.model.BMAsset;
import MediaPoolMalBridge.clients.MAL.model.MALAsset;
import MediaPoolMalBridge.persistence.entity.MALIdToBMIdEntity;
import MediaPoolMalBridge.persistence.repository.MALIdToBMIdRepository;
import com.brandmaker.webservices.mediapool.LanguageItem;
import com.brandmaker.webservices.mediapool.NameAndIdItem;
import com.brandmaker.webservices.mediapool.StructuredKeywords;
import com.brandmaker.webservices.mediapool.UploadMetadataArgument;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MALToBMTranslator {

    private MALIdToBMIdRepository malIdToBMIdRepository;

    public MALToBMTranslator(final MALIdToBMIdRepository malIdToBMIdRepository )
    {
        this.malIdToBMIdRepository = malIdToBMIdRepository;
    }

    public BMAsset translate(final MALAsset malAsset )
    {
        final BMAsset bmAsset = new BMAsset();
        final List<MALIdToBMIdEntity> malIdToBMIdEntities = malIdToBMIdRepository.findBySalId( malAsset.getMALAssetId() );
        if( !malIdToBMIdEntities.isEmpty() )
        {
            bmAsset.setAssetId( malIdToBMIdEntities.get( 0 ).getBmId() );
        }
        bmAsset.setTransferringAssetStatus( malAsset.getTransferringAssetStatus() );
        final UploadMetadataArgument uploadMetadataArgument = new UploadMetadataArgument();
        uploadMetadataArgument.setAddAssociations( false );
        uploadMetadataArgument.setVirtualDbName( "Standard" );
        uploadMetadataArgument.setShow( "SHOW_ALWAYS" );
        uploadMetadataArgument.setCreatorName( "Soap Api" );
        uploadMetadataArgument.setDesignationType( "3" );
        final StructuredKeywords structuredKeywords = new StructuredKeywords();
        final NameAndIdItem nameAndIdItem = new NameAndIdItem();
        /*nameAndIdItem.setId();
        nameAndIdItem.setName();
        'keyword' => array(
            'description' => utf8_encode($keywords),
            'langCode' => "EN"),*/
        structuredKeywords.getStructuredKeyword().add( nameAndIdItem );
        uploadMetadataArgument.setStructuredKeywords( structuredKeywords );

        /*'mediaTitle' => array(
            'description' => utf8_encode($title),
            'langCode' => "EN"),
            */
        LanguageItem languageItem = new LanguageItem();
        languageItem.setDescription( malAsset.getMALAssetId() );
        languageItem.setLangCode( "EN" );
        uploadMetadataArgument.getFreeField1().add( languageItem );
        languageItem = new LanguageItem();
        languageItem.setDescription( malAsset.getMalGetAsset().getAssociation() );
        languageItem.setLangCode( "EN" );
        uploadMetadataArgument.getFreeField2().add( languageItem );
        languageItem = new LanguageItem();
        languageItem.setDescription( malAsset.getMalGetAsset().getPropertyId() );
        languageItem.setLangCode( "EN" );
        uploadMetadataArgument.getFreeField3().add( languageItem );
        languageItem = new LanguageItem();
        languageItem.setDescription( malAsset.getMalGetAsset().getName() );
        languageItem.setLangCode( "EN" );
        uploadMetadataArgument.getFreeField4().add( languageItem );
        languageItem = new LanguageItem();
        languageItem.setDescription( String.valueOf( malAsset.getMalGetAsset().isLimitedRights() ) );
        languageItem.setLangCode( "EN" );
        uploadMetadataArgument.getFreeField5().add( languageItem );
        languageItem = new LanguageItem();
        languageItem.setDescription( String.valueOf( malAsset.getMalGetAsset().getUsageDescription() ) );
        languageItem.setLangCode( "EN" );
        uploadMetadataArgument.getFreeField6().add( languageItem );
        languageItem = new LanguageItem();
        languageItem.setDescription( String.valueOf( malAsset.getMalGetAsset().getInstructions() ) );
        languageItem.setLangCode( "EN" );
        uploadMetadataArgument.getFreeField7().add( languageItem );
        languageItem = new LanguageItem();
        languageItem.setDescription( String.valueOf( malAsset.getMalGetAsset().isRightsManaged() ) );
        languageItem.setLangCode( "EN" );
        uploadMetadataArgument.getFreeField7().add( languageItem );
        bmAsset.setUploadMetadataArgument( uploadMetadataArgument );
        return bmAsset;
    }
}
