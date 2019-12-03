package MediaPoolMalBridge.persistence.repository.Bridge.schedule;

import MediaPoolMalBridge.persistence.entity.Bridge.schedule.JobEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Repository class for {@link JobEntity}
 */
public interface JobRepository extends PagingAndSortingRepository<JobEntity, Long> {
}
