package MediaPoolMalBridge.persistence.repository.Bridge.schedule;

import MediaPoolMalBridge.persistence.entity.Bridge.schedule.JobEntity;
import org.springframework.data.repository.CrudRepository;

public interface JobRepository extends CrudRepository<JobEntity, Long> {
}
