package MediaPoolMalBridge.service.Bridge.sendmail.controller;

import MediaPoolMalBridge.service.Bridge.sendmail.BridgeSendMailSchedulerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BridgeSendMailController {

    private final BridgeSendMailSchedulerService bridgeSendMailSchedulerService;

    public BridgeSendMailController(final BridgeSendMailSchedulerService bridgeSendMailSchedulerService)
    {
        this.bridgeSendMailSchedulerService = bridgeSendMailSchedulerService;
    }

    @GetMapping( "/service/app/sendMail" )
    public void sendMail()
    {
        bridgeSendMailSchedulerService.scheduled();
    }
}
