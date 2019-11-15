package MediaPoolMalBridge.model.MAL;

import MediaPoolMalBridge.clients.MAL.asset.client.model.MALGetAsset;
import MediaPoolMalBridge.clients.MAL.assetsunavailable.client.model.MALGetUnavailableAsset;
import MediaPoolMalBridge.clients.MAL.model.MALAsset;
import MediaPoolMalBridge.clients.MAL.propertyphotomodified.client.model.MALModifiedPropertyPhotoAsset;
import MediaPoolMalBridge.model.asset.TransferringAsset;
import MediaPoolMalBridge.model.asset.TransferringAssetStatus;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class MALAssetMap extends ConcurrentHashMap<String, TransferringAsset> {

    public void updateWithMALGetUnavailableAsset(final MALGetUnavailableAsset malGetUnavailableAsset)
    {
        TransferringAsset transferringAsset = get( malGetUnavailableAsset.getAssetId() );
        if( transferringAsset == null )
        {
            transferringAsset = MALAsset.fromMALGetUnavailableAsset( malGetUnavailableAsset );
        }
        transferringAsset.setTransferringAssetStatus( TransferringAssetStatus.MAL_DELETED );
        put( malGetUnavailableAsset.getAssetId(), transferringAsset );
    }

    public void updateWithMALGetAssetModified(final MALGetAsset malGetAsset)
    {
        TransferringAsset transferringAsset = get( malGetAsset.getAssetId() );
        if( transferringAsset == null )
        {
            transferringAsset = MALAsset.fromMALGetAsset( malGetAsset );
        }
        transferringAsset.setTransferringAssetStatus( TransferringAssetStatus.MAL_UPDATED );
        put( malGetAsset.getAssetId(), transferringAsset );
    }

    public void updateWithMALGetAssetCreated(final MALGetAsset malGetAsset)
    {
        TransferringAsset transferringAsset = get( malGetAsset.getAssetId() );
        if( transferringAsset == null )
        {
            transferringAsset = MALAsset.fromMALGetAsset( malGetAsset );
        }
        transferringAsset.setTransferringAssetStatus( TransferringAssetStatus.MAL_CREATED );
        put( malGetAsset.getAssetId(), transferringAsset );
    }

    public void updateWithMALGetModifiedPropertyPhoto(final MALModifiedPropertyPhotoAsset malModifiedPropertyPhotoAsset )
    {
        TransferringAsset transferringAsset = get( malModifiedPropertyPhotoAsset.getAssetId() );
        if( transferringAsset == null )
        {
            transferringAsset = MALAsset.fromMALGetModifiedPropertyPhotos( malModifiedPropertyPhotoAsset );
        }
        transferringAsset.setTransferringAssetStatus( TransferringAssetStatus.MAL_PHOTO_UPDATED );
        put( malModifiedPropertyPhotoAsset.getAssetId(), transferringAsset );
    }
}
