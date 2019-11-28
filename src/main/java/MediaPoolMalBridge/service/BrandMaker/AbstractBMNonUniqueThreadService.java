package MediaPoolMalBridge.service.BrandMaker;

import MediaPoolMalBridge.clients.BrandMaker.model.response.AbstractBMResponse;
import MediaPoolMalBridge.persistence.entity.Bridge.ReportsEntity;
import MediaPoolMalBridge.persistence.entity.enums.ReportTo;
import MediaPoolMalBridge.persistence.entity.enums.ReportType;
import MediaPoolMalBridge.persistence.repository.BM.BMAssetRepository;
import MediaPoolMalBridge.service.AbstractNonUniqueThreadService;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractBMNonUniqueThreadService<RUN_ARGUMENT> extends AbstractNonUniqueThreadService<RUN_ARGUMENT> {

    @Autowired
    protected BMAssetRepository bmAssetRepository;

    protected void reportErrorOnResponse(final String bmAssetId, final AbstractBMResponse abstractBMResponse) {
        final String message = String.format("Can not perform operation [%s] for asset with id [%s], with error message [%s] and warnings [%s]", getClass().getName(), bmAssetId, abstractBMResponse.getErrorAsString(), abstractBMResponse.getWarningsAsString());
        final ReportsEntity reportsEntity = new ReportsEntity( ReportType.ERROR, getClass().getName(), message, ReportTo.BM, null, null, null );
        reportsRepository.save( reportsEntity );
    }
}
