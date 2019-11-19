package MediaPoolMalBridge.service.BrandMaker;

import MediaPoolMalBridge.persistence.repository.BM.BMAssetRepository;
import MediaPoolMalBridge.persistence.repository.ReportsRepository;
import MediaPoolMalBridge.service.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;

public class AbstractBMService extends AbstractService {

    @Autowired
    protected ReportsRepository reportsRepository;

    @Autowired
    protected BMAssetRepository bmAssetRepository;
}
