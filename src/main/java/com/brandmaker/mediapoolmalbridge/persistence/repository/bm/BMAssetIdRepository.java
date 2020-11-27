package com.brandmaker.mediapoolmalbridge.persistence.repository.bm;

import com.brandmaker.mediapoolmalbridge.persistence.entity.bm.BMAssetIdEntity;
import org.springframework.data.repository.CrudRepository;

/**
 * Repository class for {@link BMAssetIdEntity}
 */
public interface BMAssetIdRepository extends CrudRepository<BMAssetIdEntity, Long> {
}
