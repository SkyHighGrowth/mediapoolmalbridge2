package MediaPoolMalBridge.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfig {

    @Bean( name = "ObjectMapper" )
    public ObjectMapper getObjectMapper()
    {
        return new ObjectMapper();
    }
}
