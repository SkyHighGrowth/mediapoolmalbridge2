package MediaPoolMalBridge.service.MAL;

import MediaPoolMalBridge.persistence.repository.MAL.MALAssetRepository;
import MediaPoolMalBridge.persistence.repository.MAL.MALPropertyRepository;
import MediaPoolMalBridge.service.AbstractNonUniqueService;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractMALNonUniqueService<RUN_ARGUMENT> extends AbstractNonUniqueService<RUN_ARGUMENT> {

    @Autowired
    protected MALAssetRepository malAssetRepository;

    @Autowired
    protected MALPropertyRepository malPropertyRepository;
}
