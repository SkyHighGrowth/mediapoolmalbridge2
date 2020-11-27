package com.brandmaker.mediapoolmalbridge.persistence.repository.bridge;

import com.brandmaker.mediapoolmalbridge.persistence.entity.bridge.AssetJsonedValuesEntity;
import org.springframework.data.repository.CrudRepository;

public interface AssetJsonedValuesRepository extends CrudRepository<AssetJsonedValuesEntity, Long> {

    AssetJsonedValuesEntity findAssetJsonedValuesEntitiesById(long id);
}
