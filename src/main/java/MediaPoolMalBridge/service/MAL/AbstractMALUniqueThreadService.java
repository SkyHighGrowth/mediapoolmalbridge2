package MediaPoolMalBridge.service.MAL;

import MediaPoolMalBridge.persistence.repository.MAL.MALPropertyRepository;
import MediaPoolMalBridge.service.AbstractUniqueThreadService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Class that implements common fields and properties for MAL server unique thread services
 */
public abstract class AbstractMALUniqueThreadService extends AbstractUniqueThreadService {

    @Autowired
    protected MALPropertyRepository malPropertyRepository;
}
