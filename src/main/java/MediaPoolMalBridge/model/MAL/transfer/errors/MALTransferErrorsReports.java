package MediaPoolMalBridge.model.MAL.transfer.errors;

import MediaPoolMalBridge.clients.rest.RestResponse;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class MALTransferErrorsReports extends ConcurrentHashMap<String, RestResponse> {
}
