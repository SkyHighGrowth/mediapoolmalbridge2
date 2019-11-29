package MediaPoolMalBridge.service.MAL;

import MediaPoolMalBridge.persistence.repository.MAL.MALPropertyRepository;
import MediaPoolMalBridge.service.AbstractNonUniqueThreadService;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractMALNonUniqueThreadService<RUN_ARGUMENT> extends AbstractNonUniqueThreadService<RUN_ARGUMENT> {

    @Autowired
    protected MALPropertyRepository malPropertyRepository;
}
