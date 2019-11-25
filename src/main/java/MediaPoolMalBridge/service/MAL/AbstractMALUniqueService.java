package MediaPoolMalBridge.service.MAL;

import MediaPoolMalBridge.persistence.repository.MAL.MALAssetRepository;
import MediaPoolMalBridge.persistence.repository.MAL.MALPropertyRepository;
import MediaPoolMalBridge.service.AbstractUniqueService;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractMALUniqueService extends AbstractUniqueService {

    @Autowired
    protected MALAssetRepository malAssetRepository;

    @Autowired
    protected MALPropertyRepository malPropertyRepository;
}
