package MediaPoolMalBridge.persistence.repository.Bridge.schedule;

import MediaPoolMalBridge.persistence.entity.Bridge.schedule.ServiceEntity;
import org.springframework.data.repository.CrudRepository;

public interface ServiceRepository extends CrudRepository<ServiceEntity, Long> {
}
