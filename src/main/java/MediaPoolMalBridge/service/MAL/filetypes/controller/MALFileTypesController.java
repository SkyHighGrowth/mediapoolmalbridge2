package MediaPoolMalBridge.service.MAL.filetypes.controller;

import MediaPoolMalBridge.service.MAL.filetypes.MALGetFileTypesSchedulerService;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Profile( "dev" )
public class MALFileTypesController {

    private final MALGetFileTypesSchedulerService getFileTypesSchedulerService;

    public MALFileTypesController(final MALGetFileTypesSchedulerService getFileTypesSchedulerService )
    {
        this.getFileTypesSchedulerService = getFileTypesSchedulerService;
    }

    @GetMapping( "/mal/fileTypes/download" )
    public void setFileType()
    {
        getFileTypesSchedulerService.downloadFileTypes();
    }
}
