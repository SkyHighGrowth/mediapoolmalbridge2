package MediaPoolMalBridge.service.Bridge.databasenormalizer;

import MediaPoolMalBridge.constants.Constants;
import MediaPoolMalBridge.persistence.entity.Bridge.AssetEntity;
import MediaPoolMalBridge.persistence.entity.Bridge.UploadedFileEntity;
import MediaPoolMalBridge.persistence.entity.MAL.MALPropertyEntity;
import MediaPoolMalBridge.persistence.entity.enums.asset.TransferringAssetStatus;
import MediaPoolMalBridge.persistence.repository.Bridge.AssetRepository;
import MediaPoolMalBridge.persistence.repository.Bridge.UploadedFileRepository;
import MediaPoolMalBridge.persistence.repository.MAL.MALPropertyRepository;
import MediaPoolMalBridge.service.AbstractService;
import MediaPoolMalBridge.service.Bridge.databasenormalizer.model.Properties;
import MediaPoolMalBridge.service.Bridge.databasenormalizer.model.TransformPropertyToMALPropertyEntity;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileReader;
import java.time.LocalDateTime;

@Service( "BridgeDatabaseNormalizerService" )
public class BridgeDatabaseNormalizerService extends AbstractService {

    private AssetRepository assetRepository;

    private MALPropertyRepository malPropertyRepository;

    private UploadedFileRepository uploadedFileRepository;

    public BridgeDatabaseNormalizerService(final AssetRepository assetRepository,
                                           final MALPropertyRepository malPropertyRepository,
                                           final UploadedFileRepository uploadedFileRepository) {
        this.assetRepository = assetRepository;
        this.malPropertyRepository = malPropertyRepository;
        this.uploadedFileRepository = uploadedFileRepository;
    }

    @PostConstruct
    public void init() {
        normalize( getMidnight(), TransferringAssetStatus.FILE_DOWNLOADING, TransferringAssetStatus.ASSET_OBSERVED);
        normalize( getMidnight(), TransferringAssetStatus.FILE_UPLOADING, TransferringAssetStatus.FILE_DOWNLOADED );
        normalize( getMidnight(), TransferringAssetStatus.METADATA_UPLOADING, TransferringAssetStatus.FILE_UPLOADED );
        normalize( getMidnight(), TransferringAssetStatus.METADATA_DOWNLOADING, TransferringAssetStatus.METADATA_UPLOADED );
        normalize( getMidnight(), TransferringAssetStatus.GETTING_BM_ASSET_ID,  TransferringAssetStatus.GET_BM_ASSET_ID );
        importProperties();
    }

    private void normalize(final LocalDateTime midnight, final TransferringAssetStatus from, TransferringAssetStatus to )
    {
        boolean condition = true;
        for(int page = 0; condition; ++page ) {
            Slice<AssetEntity> assetEntities = assetRepository.findAllByTransferringAssetStatusAndUpdatedIsAfter(from, midnight, PageRequest.of(page, 200));
            condition = assetEntities.hasNext();
            normalizePage( assetEntities, to );
        }
    }

    @Transactional
    protected void normalizePage( final Slice<AssetEntity> assetEntities, final TransferringAssetStatus to )
    {
        assetEntities.forEach(assetEntity -> {
            assetEntity.setTransferringAssetStatus( to );
            assetRepository.save( assetEntity );
            if( TransferringAssetStatus.ASSET_OBSERVED.equals( assetEntity.getTransferringAssetStatus() ) ) {
                uploadedFileRepository.save(new UploadedFileEntity(assetEntity.getFileNameOnDisc()));
            }
        });
    }

    private void importProperties() {
        final String filePath = System.getProperty("user.home") + File.separator + Constants.APPLICATION_DIR + File.separator + "strarwood_api.properties.json";
        final File file = new File( filePath );
        if( file.exists() ) {
            FileReader reader = null;
            try {
                reader = new FileReader(file);
                final Properties properties = (new Gson()).fromJson(new JsonReader(reader), Properties.class);
                reader.close();
                logger.info( "Properties data loaded" );

                final TransformPropertyToMALPropertyEntity transformer = new TransformPropertyToMALPropertyEntity();
                properties.getProperties().forEach( property -> {
                    if( malPropertyRepository.findByPropertyId( String.valueOf( property.getPropertyId() ) ).isPresent() ) {
                        return;
                    }
                    final MALPropertyEntity malProperty = transformer.transform( property );
                    malPropertyRepository.save( malProperty );
                });
            } catch (final Exception e) {
                logger.error( "Fatal: Can not parse application.properties.json", e );
                throw new RuntimeException();
            } finally {
                if( reader != null )
                {
                    try {
                        reader.close();
                    } catch( final Exception e )
                    {

                    }
                }
            }
        } else {
            logger.error( "Can not load properties file" );
        }
    }
}
