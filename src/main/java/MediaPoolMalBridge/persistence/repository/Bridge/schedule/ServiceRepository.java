package MediaPoolMalBridge.persistence.repository.Bridge.schedule;

import MediaPoolMalBridge.persistence.entity.Bridge.schedule.ServiceEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Repository class for {@link ServiceEntity}
 */
public interface ServiceRepository extends PagingAndSortingRepository<ServiceEntity, Long> {
}
