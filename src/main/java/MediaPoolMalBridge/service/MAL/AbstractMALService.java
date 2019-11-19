package MediaPoolMalBridge.service.MAL;

import MediaPoolMalBridge.persistence.repository.MAL.MALAssetRepository;
import MediaPoolMalBridge.persistence.repository.MAL.MALPropertyRepository;
import MediaPoolMalBridge.persistence.repository.ReportsRepository;
import MediaPoolMalBridge.service.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AbstractMALService extends AbstractService {

    @Autowired
    protected ReportsRepository reportsRepository;

    @Autowired
    protected MALAssetRepository malAssetRepository;

    @Autowired
    protected MALPropertyRepository malPropertyRepository;
}
