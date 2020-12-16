package com.brandmaker.mediapoolmalbridge.controller;

import com.brandmaker.mediapoolmalbridge.config.AppConfig;
import com.brandmaker.mediapoolmalbridge.config.MalPriorities;
import com.brandmaker.mediapoolmalbridge.controller.model.ThreadPoolsStatuses;
import com.brandmaker.mediapoolmalbridge.model.brandmaker.theme.BMThemes;
import com.brandmaker.mediapoolmalbridge.model.mal.MALAssetStructures;
import com.brandmaker.mediapoolmalbridge.model.mal.kits.MALKits;
import com.brandmaker.mediapoolmalbridge.persistence.entity.bridge.AssetEntity;
import com.brandmaker.mediapoolmalbridge.persistence.entity.bridge.ReportsEntity;
import com.brandmaker.mediapoolmalbridge.persistence.entity.bridge.schedule.JobEntity;
import com.brandmaker.mediapoolmalbridge.persistence.entity.bridge.schedule.ServiceEntity;
import com.brandmaker.mediapoolmalbridge.persistence.entity.enums.asset.TransferringAssetStatus;
import com.brandmaker.mediapoolmalbridge.persistence.repository.bridge.AssetRepository;
import com.brandmaker.mediapoolmalbridge.persistence.repository.bridge.ReportsRepository;
import com.brandmaker.mediapoolmalbridge.persistence.repository.bridge.schedule.JobRepository;
import com.brandmaker.mediapoolmalbridge.persistence.repository.bridge.schedule.ServiceRepository;
import com.brandmaker.mediapoolmalbridge.tasks.TaskExecutorWrapper;
import com.brandmaker.mediapoolmalbridge.tasks.TaskPriorityExecutorWrapper;
import com.brandmaker.mediapoolmalbridge.tasks.TaskSchedulerWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

/**
 * Cotroller which exposes application status, available only in dev profile
 */
@RestController
@Profile({"enable controllers"})
public class AppStatusController {

    public static final String CREATED = "created";
    private final AppConfig appConfig;
    private final MALKits malKits;
    private final BMThemes bmThemes;
    private final MALAssetStructures assetStructures;
    private final TaskExecutorWrapper malTaskExecutorWrapper;
    private final TaskExecutorWrapper bmTaskExecutorWrapper;
    private final TaskPriorityExecutorWrapper bmTaskPriorityExecutorWrapper;
    private final TaskSchedulerWrapper taskSchedulerWrapper;
    private final JobRepository jobRepository;
    private final ServiceRepository serviceRepository;
    private final ReportsRepository reportsRepository;
    private final AssetRepository assetRepository;
    private final ObjectMapper objectMapper;
    private final MalPriorities malPriorities;

    public AppStatusController( final AppConfig appConfig,
                                final MALKits malKits,
                                final BMThemes bmThemes,
                                final MALAssetStructures assetStructures,
                                @Qualifier( "MALTaskExecutorWrapper" )
                                final TaskExecutorWrapper malTaskExecutorWrapper,
                                @Qualifier( "BMTaskExecutorWrapper" )
                                final TaskExecutorWrapper bmTaskExecutorWrapper,
                                @Qualifier( "BMTaskPriorityExecutorWrapper" )
                                final TaskPriorityExecutorWrapper bmTaskPriorityExecutorWrapper,
                                final TaskSchedulerWrapper taskSchedulerWrapper,
                                final JobRepository jobRepository,
                                final ServiceRepository serviceRepository,
                                final ReportsRepository reportsRepository,
                                final AssetRepository assetRepository,
                                final MalPriorities malPriorities,
                                final ObjectMapper objectMapper) {
        this.appConfig = appConfig;
        this.malKits = malKits;
        this.bmThemes = bmThemes;
        this.assetStructures = assetStructures;
        this.malTaskExecutorWrapper = malTaskExecutorWrapper;
        this.bmTaskExecutorWrapper = bmTaskExecutorWrapper;
        this.taskSchedulerWrapper = taskSchedulerWrapper;
        this.bmTaskPriorityExecutorWrapper = bmTaskPriorityExecutorWrapper;
        this.jobRepository = jobRepository;
        this.serviceRepository = serviceRepository;
        this.reportsRepository = reportsRepository;
        this.assetRepository = assetRepository;
        this.malPriorities = malPriorities;
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

    @GetMapping("/appStatus/mal/priorities")
    public String getMalPriorities() {
        try {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(malPriorities);
        } catch( final Exception e ) {
            return "Can not serialize MalPriorities " + e.getMessage();
        }
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
                bmTaskExecutorWrapper.getLockCount(),
                bmTaskPriorityExecutorWrapper.getTaskExecutor().getMaxPoolSize(),
                bmTaskPriorityExecutorWrapper.getTaskExecutor().getPoolSize(),
                bmTaskPriorityExecutorWrapper.getQueueSize(),
                bmTaskPriorityExecutorWrapper.getMaximalQueueSize(),
                bmTaskPriorityExecutorWrapper.getTaskExecutor().getActiveCount(),
                bmTaskPriorityExecutorWrapper.getLockCount() );
    }

    /**
     * Returns paged job scheduled with page size 200
     * @param page
     * @return
     */
    @GetMapping( "/appStatus/app/jobsScheduled/{page}")
    public Page<JobEntity> getJobs(@PathVariable final int page)
    {
        return jobRepository.findAll( PageRequest.of( page, 200, Sort.by( Sort.Direction.DESC, CREATED) ) );
    }

    /**
     * Returns paged service executed with page size 200
     * @param page
     * @return
     */
    @GetMapping( "/appStatus/app/serviceExecution/{page}")
    public Page<ServiceEntity> getServices(@PathVariable final int page)
    {
        return serviceRepository.findAll( PageRequest.of( page, 200, Sort.by( Sort.Direction.DESC, CREATED ) ) );
    }

    /**
     * Returns paged reports with page size 200
     * @param page
     * @return
     */
    @GetMapping( "/appStatus/app/reports/{page}" )
    public Page<ReportsEntity> getReports(@PathVariable final int page)
    {
        return reportsRepository.findAll( PageRequest.of( page, 200, Sort.by( Sort.Direction.DESC, CREATED) ) );
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

    @GetMapping( value = "/appStatus/app/appConfigData" , produces = MediaType.APPLICATION_JSON_VALUE)
    public String getAppConfig() {
        try {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString( appConfig.getAppConfigData() );
        } catch ( final Exception e ) {
            return "Can not serialize appConfigData" + e.getMessage();
        }
    }

    @GetMapping( "/appStatus/calculateBmHash/{id}")
    public String calculateBmHash(@PathVariable final Long id) {
        final Optional<AssetEntity> assetEntity = assetRepository.findById( id );
        if( assetEntity.isPresent() ) {
            final File file = new File(appConfig.getTempDir() + File.separator + assetEntity.get().getFileNameOnDisc());
            try {
                final MessageDigest messageDigest = MessageDigest.getInstance("MD5");
                messageDigest.update(Files.readAllBytes(file.toPath()));
                return "hash is [" + Base64.encodeBase64String( messageDigest.digest() ) + "]";
            } catch (final Exception e) {
                return "Exception " + e.getMessage();
            }
        }
        else {
            return "No such asset";
        }
    }
}
