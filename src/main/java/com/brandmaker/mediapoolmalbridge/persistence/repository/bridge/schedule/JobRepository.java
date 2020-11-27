package com.brandmaker.mediapoolmalbridge.persistence.repository.bridge.schedule;

import com.brandmaker.mediapoolmalbridge.persistence.entity.bridge.schedule.JobEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * Repository class for {@link JobEntity}
 */
public interface JobRepository extends PagingAndSortingRepository<JobEntity, Long> {

    @Transactional
    @Modifying
    @Query( "delete from JobEntity je " +
            "where je.created < :dateTime ")
    void deleteRowsInThePast( final LocalDateTime dateTime );
}
