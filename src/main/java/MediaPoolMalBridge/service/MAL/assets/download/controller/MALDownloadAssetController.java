package MediaPoolMalBridge.service.MAL.assets.download.controller;

import MediaPoolMalBridge.clients.MAL.asset.client.model.MALGetAssetsRequest;
import MediaPoolMalBridge.persistence.entity.MAL.MALAssetEntity;
import MediaPoolMalBridge.persistence.entity.enums.asset.TransferringAssetStatus;
import MediaPoolMalBridge.persistence.repository.MAL.MALAssetRepository;
import MediaPoolMalBridge.service.MAL.assets.download.MALDownloadAssetService;
import MediaPoolMalBridge.tasks.TaskExecutorWrapper;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Profile("dev")
public class MALDownloadAssetController {

    private final MALDownloadAssetService malDownloadAssetService;
    private final TaskExecutorWrapper taskExecutorWrapper;
    private final MALGetAssetsDev malGetAssetsDev;
    private final MALAssetRepository malAssetRepository;

    public MALDownloadAssetController(final MALDownloadAssetService malDownloadAssetService,
                                      final TaskExecutorWrapper taskExecutorWrapper,
                                      final MALGetAssetsDev malGetAssetsDev,
                                      final MALAssetRepository malAssetRepository) {
        this.malDownloadAssetService = malDownloadAssetService;
        this.taskExecutorWrapper = taskExecutorWrapper;
        this.malGetAssetsDev = malGetAssetsDev;
        this.malAssetRepository = malAssetRepository;
    }

    @PostMapping(value = "/service/mal/downloadAssets", consumes = "application/json")
    public List<MALAssetEntity> getAssets(@RequestBody final MALGetAssetsRequest request) {
        malGetAssetsDev.downloadModifiedAssets(request);
        final List<MALAssetEntity> malAssetEntities = malAssetRepository.findByTransferringAssetStatus(TransferringAssetStatus.MAL_CREATED);
        malAssetEntities.forEach( malAssetEntity ->
                taskExecutorWrapper.getTaskExecutor()
                        .execute(() -> malDownloadAssetService.downloadMALAsset(malAssetEntity) ) );
        return malAssetEntities;
    }
}
