package com.brandmaker.mediapoolmalbridge.persistence.repository.bridge.schedule;

import com.brandmaker.mediapoolmalbridge.persistence.entity.bridge.schedule.JobEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository class for {@link JobEntity}
 */
public interface JobRepository extends PagingAndSortingRepository<JobEntity, Long> {

    @Transactional
    @Modifying
    @Query("delete from JobEntity je " +
            "where je.created < :dateTime ")
    void deleteRowsInThePast(final LocalDateTime dateTime);

    @Query("from JobEntity as je " +
            "where je.jobState = 'JOB_START' and je.created > :dateTime and je.jobName is not null")
    List<JobEntity> findAllRunningJobs(LocalDateTime dateTime);
}
