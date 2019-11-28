package MediaPoolMalBridge.service.MAL;

import MediaPoolMalBridge.persistence.repository.MAL.MALAssetRepository;
import MediaPoolMalBridge.persistence.repository.MAL.MALPropertyRepository;
import MediaPoolMalBridge.service.AbstractUniqueThreadService;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractMALUniqueThreadService extends AbstractUniqueThreadService {

    @Autowired
    protected MALAssetRepository malAssetRepository;

    @Autowired
    protected MALPropertyRepository malPropertyRepository;
}
