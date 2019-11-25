package MediaPoolMalBridge.persistence.repository.schedule;

import MediaPoolMalBridge.persistence.entity.schedule.JobEntity;
import org.springframework.data.repository.CrudRepository;

public interface JobRepository extends CrudRepository<JobEntity, Long> {
}
