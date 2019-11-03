package MediaPoolMalBridge.controller;

import MediaPoolMalBridge.clients.MAL.properties.client.MALGetPropertiesClient;
import com.google.gson.Gson;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    private final MALGetPropertiesClient client;

    public HelloController(final MALGetPropertiesClient client) {
        this.client = client;
    }

    @RequestMapping("/")
    public String index() {
        return (new Gson()).toJson(client.download("20000"));
    }
}
