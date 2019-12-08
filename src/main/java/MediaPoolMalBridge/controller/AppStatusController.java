package MediaPoolMalBridge.controller;

import MediaPoolMalBridge.controller.model.ThreadPoolsStatuses;
import MediaPoolMalBridge.model.BrandMaker.theme.BMThemes;
import MediaPoolMalBridge.model.MAL.MALAssetStructures;
import MediaPoolMalBridge.model.MAL.kits.MALKits;
import MediaPoolMalBridge.persistence.entity.Bridge.ReportsEntity;
import MediaPoolMalBridge.persistence.entity.Bridge.schedule.JobEntity;
import MediaPoolMalBridge.persistence.entity.Bridge.schedule.ServiceEntity;
import MediaPoolMalBridge.persistence.entity.enums.asset.TransferringAssetStatus;
import MediaPoolMalBridge.persistence.repository.Bridge.AssetRepository;
import MediaPoolMalBridge.persistence.repository.Bridge.ReportsRepository;
import MediaPoolMalBridge.persistence.repository.Bridge.schedule.JobRepository;
import MediaPoolMalBridge.persistence.repository.Bridge.schedule.ServiceRepository;
import MediaPoolMalBridge.tasks.TaskExecutorWrapper;
import MediaPoolMalBridge.tasks.TaskSchedulerWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Cotroller which exposes application status, available only in dev profile
 */
@RestController
@Profile("dev")
public class AppStatusController {

    private static Logger logger = LoggerFactory.getLogger(AppStatusController.class);

    private final MALKits malKits;
    private final BMThemes bmThemes;
    private final MALAssetStructures assetStructures;
    @Qualifier( "MALThreadExcutorWrapper" )
    private TaskExecutorWrapper malTaskExecutorWrapper;
    @Qualifier( "BMThreadExcutorWrapper" )
    private TaskExecutorWrapper bmTaskExecutorWrapper;
    private TaskSchedulerWrapper taskSchedulerWrapper;
    private final JobRepository jobRepository;
    private final ServiceRepository serviceRepository;
    private final ReportsRepository reportsRepository;
    private final AssetRepository assetRepository;
    private final ObjectMapper objectMapper;

    public AppStatusController(final MALKits malKits,
                               final BMThemes bmThemes,
                               final MALAssetStructures assetStructures,
                               @Qualifier( "MALTaskExecutorWrapper" )
                               final TaskExecutorWrapper malTaskExecutorWrapper,
                               @Qualifier( "BMTaskExecutorWrapper" )
                               final TaskExecutorWrapper bmTaskExecutorWrapper,
                               final TaskSchedulerWrapper taskSchedulerWrapper,
                               final JobRepository jobRepository,
                               final ServiceRepository serviceRepository,
                               final ReportsRepository reportsRepository,
                               final AssetRepository assetRepository,
                               final ObjectMapper objectMapper) {
        this.malKits = malKits;
        this.bmThemes = bmThemes;
        this.assetStructures = assetStructures;
        this.malTaskExecutorWrapper = malTaskExecutorWrapper;
        this.bmTaskExecutorWrapper = bmTaskExecutorWrapper;
        this.taskSchedulerWrapper = taskSchedulerWrapper;
        this.jobRepository = jobRepository;
        this.serviceRepository = serviceRepository;
        this.reportsRepository = reportsRepository;
        this.assetRepository = assetRepository;
        this.objectMapper = objectMapper;
    }

    /**
     * returns file types downloaded from MAL server
     * @return
     */
    @GetMapping("/appStatus/mal/fileTypes")
    public Map<String, String> getFileTypes() {
        return assetStructures.getFileTypes();
    }

    /**
     * returns kits downloaded from MAL server
     * @return
     */
    @GetMapping("/appStatus/mal/kits")
    public MALKits getKits() {
        return malKits;
    }

    /**
     * returns themes downloaded from Mediapool server
     * @return
     */
    @GetMapping("/appStatus/bm/themes")
    public BMThemes getThemes() {
        return bmThemes;
    }

    /**
     * returns brands downloaded from MAL server
     * @return
     */
    @GetMapping("/appStatus/mal/brands")
    public Map<String, String> getBrands() {
        return assetStructures.getBrands();
    }

    /**
     * returns collections downloaded from MAL server
     * @return
     */
    @GetMapping("/appStatus/mal/collections")
    public Map<String, String> getCollections() {
        return assetStructures.getCollections();
    }

    /**
     * returns colors downlaoded from MAL server
     * @return
     */
    @GetMapping("/appStatus/mal/colors")
    public Map<String, String> getColors() {
        return assetStructures.getColors();
    }

    /**
     * returns destionations downloaded from MAL server
     * @return
     */
    @GetMapping("/appStatus/mal/destinations")
    public Map<String, String> getMalDestinations() {
        return assetStructures.getDestinations();
    }

    /**
     * returns subjects downloaded from MAL server
     * @return
     */
    @GetMapping("/appStatus/mal/subjects")
    public Map<String, String> getSubjects() {
        return assetStructures.getSubjects();
    }

    /**
     * returns asset types downloaded from MAL server
     * @return
     */
    @GetMapping("/appStatus/mal/assetTypes")
    public Map<String, String> getMalAssetTypes() {
        return assetStructures.getAssetTypes();
    }

    /**
     * returns property types downaloaded from MAL server
     * @return
     */
    @GetMapping("/appStatus/app/propertyTypes")
    public Map<String, String> getPropertyTypes() {
        return assetStructures.getPropertyTypes();
    }

    /**
     * returns queue size from MAL server
     * @return
     */
    @GetMapping("/appStatus/app/getThreadPoolsInfo" )
    public ThreadPoolsStatuses getMalQueueSize()
    {
        return new ThreadPoolsStatuses( taskSchedulerWrapper.getTaskScheduler().getPoolSize(),
                taskSchedulerWrapper.getTaskScheduler().getScheduledThreadPoolExecutor().getQueue().size(),
                taskSchedulerWrapper.getTaskScheduler().getActiveCount(),
                malTaskExecutorWrapper.getTaskExecutor().getMaxPoolSize(),
                malTaskExecutorWrapper.getTaskExecutor().getPoolSize(),
                malTaskExecutorWrapper.getQueueSize(),
                malTaskExecutorWrapper.getMaximalQueueSize(),
                malTaskExecutorWrapper.getTaskExecutor().getActiveCount(),
                malTaskExecutorWrapper.getLockCount(),
                bmTaskExecutorWrapper.getTaskExecutor().getMaxPoolSize(),
                bmTaskExecutorWrapper.getTaskExecutor().getPoolSize(),
                bmTaskExecutorWrapper.getQueueSize(),
                bmTaskExecutorWrapper.getMaximalQueueSize(),
                bmTaskExecutorWrapper.getTaskExecutor().getActiveCount(),
                bmTaskExecutorWrapper.getLockCount() );
    }

    /**
     * Returns paged job scheduled with page size 200
     * @param page
     * @return
     */
    @GetMapping( "/appStatus/app/jobsScheduled/{page}")
    public Page<JobEntity> getJobs(@PathVariable final int page)
    {
        return jobRepository.findAll( PageRequest.of( page, 200, Sort.by( Sort.Direction.DESC, "created") ) );
    }

    /**
     * Returns paged service executed with page size 200
     * @param page
     * @return
     */
    @GetMapping( "/appStatus/app/serviceExecution/{page}")
    public Page<ServiceEntity> getServices(@PathVariable final int page)
    {
        return serviceRepository.findAll( PageRequest.of( page, 200, Sort.by( Sort.Direction.DESC, "created" ) ) );
    }

    /**
     * Returns paged reports with page size 200
     * @param page
     * @return
     */
    @GetMapping( "/appStatus/app/reports/{page}" )
    public Page<ReportsEntity> getReports(@PathVariable final int page)
    {
        return reportsRepository.findAll( PageRequest.of( page, 200, Sort.by( Sort.Direction.DESC, "created") ) );
    }

    /**
     * Returns assets which has updated timestamp if before from and after to with given transferring asset status
     * @param from
     * @param to
     * @param transferringAssetStatus
     * @return
     */
    @GetMapping( "/appStatus/app/assets/" )
    public String getAssets(@RequestParam( "from" ) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) final LocalDateTime from,
                                       @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @RequestParam( "to" ) final LocalDateTime to,
                                       @RequestParam( "transferringAssetStatus" ) TransferringAssetStatus transferringAssetStatus) {
        try {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(
                    assetRepository.findAllByUpdatedIsAfterAndUpdatedIsBeforeAndTransferringAssetStatus(
                            from, to, transferringAssetStatus, Sort.by(Sort.Direction.DESC, "updated")));
        } catch( final Exception e ) {
            return "Can not serialize database response " + e.getMessage();
        }
    }
}
