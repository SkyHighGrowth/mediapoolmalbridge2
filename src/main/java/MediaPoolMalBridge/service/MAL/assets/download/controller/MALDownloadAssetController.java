package MediaPoolMalBridge.service.MAL.assets.download.controller;

import MediaPoolMalBridge.clients.MAL.asset.client.model.MALGetAssetsRequest;
import MediaPoolMalBridge.persistence.entity.MAL.MALAssetEntity;
import MediaPoolMalBridge.persistence.entity.enums.asset.TransferringAssetStatus;
import MediaPoolMalBridge.persistence.repository.MAL.MALAssetRepository;
import MediaPoolMalBridge.service.MAL.assets.download.MALDownloadAssetService;
import MediaPoolMalBridge.service.MAL.assets.download.MALFireDownloadAssetsService;
import MediaPoolMalBridge.tasks.TaskExecutorWrapper;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Profile("dev")
public class MALDownloadAssetController {

    private final MALDownloadAssetService malDownloadAssetService;
    private final MALFireDownloadAssetsService malFireDownloadAssetsService;
    private final TaskExecutorWrapper taskExecutorWrapper;
    private final MALGetAssetsDev malGetAssetsDev;
    private final MALAssetRepository malAssetRepository;

    public MALDownloadAssetController(final MALDownloadAssetService malDownloadAssetService,
                                      final MALFireDownloadAssetsService malFireDownloadAssetsService,
                                      final TaskExecutorWrapper taskExecutorWrapper,
                                      final MALGetAssetsDev malGetAssetsDev,
                                      final MALAssetRepository malAssetRepository) {
        this.malDownloadAssetService = malDownloadAssetService;
        this.malFireDownloadAssetsService = malFireDownloadAssetsService;
        this.taskExecutorWrapper = taskExecutorWrapper;
        this.malGetAssetsDev = malGetAssetsDev;
        this.malAssetRepository = malAssetRepository;
    }

    @PostMapping(value = "/service/mal/downloadAssets", consumes = "application/json")
    public String getAssets(@RequestBody final MALGetAssetsRequest request) {
        malGetAssetsDev.start(request);
        boolean condition = true;
        for (int page = 0; condition; ++page) {
            final Slice<MALAssetEntity> malAssetEntities = malAssetRepository.findAllByTransferringAssetStatusOrTransferringAssetStatus(TransferringAssetStatus.MAL_UPDATED,
                    TransferringAssetStatus.MAL_CREATED, PageRequest.of(page, 1000));
            condition = malAssetEntities.hasNext();
            malAssetEntities.forEach( malAssetEntity -> {
                while( true ) {
                    if( taskExecutorWrapper.getTaskExecutor().getThreadPoolExecutor().getQueue().size() > 9000 ) {
                        try {
                            synchronized( malDownloadAssetService ) {
                                malDownloadAssetService.wait(10000);
                            }
                        } catch ( final InterruptedException e ) {
                            throw new RuntimeException();
                        }
                    } else {
                        break;
                    }
                }
                taskExecutorWrapper.getTaskExecutor().execute(() -> malDownloadAssetService.start(malAssetEntity) ); } ); }
        return "hello";
    }

    @GetMapping( "/service/mal/firedownload" )
    public void fireDownload()
    {
        malFireDownloadAssetsService.start();
    }
}
