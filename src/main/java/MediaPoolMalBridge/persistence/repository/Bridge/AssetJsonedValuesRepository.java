package MediaPoolMalBridge.persistence.repository.Bridge;

import MediaPoolMalBridge.persistence.entity.Bridge.AssetEntity;
import MediaPoolMalBridge.persistence.entity.Bridge.AssetJsonedValuesEntity;
import org.springframework.data.repository.CrudRepository;

public interface AssetJsonedValuesRepository extends CrudRepository<AssetJsonedValuesEntity, Long> {

    AssetJsonedValuesEntity findByAssetEntity(final AssetEntity assetEntity );
}
