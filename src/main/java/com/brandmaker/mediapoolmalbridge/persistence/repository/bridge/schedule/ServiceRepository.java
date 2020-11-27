package com.brandmaker.mediapoolmalbridge.persistence.repository.bridge.schedule;

import com.brandmaker.mediapoolmalbridge.persistence.entity.bridge.schedule.ServiceEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * Repository class for {@link ServiceEntity}
 */
public interface ServiceRepository extends PagingAndSortingRepository<ServiceEntity, Long> {

    @Transactional
    @Modifying
    @Query( "delete from ServiceEntity se " +
            "where se.created < :dateTime ")
    void deleteRowsInThePast( final LocalDateTime dateTime );
}
