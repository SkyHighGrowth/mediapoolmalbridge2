package MediaPoolMalBridge.persistence.repository.schedule;

import MediaPoolMalBridge.persistence.entity.schedule.ServiceEntity;
import org.springframework.data.repository.CrudRepository;

public interface ServiceRepository extends CrudRepository<ServiceEntity, Long> {
}
