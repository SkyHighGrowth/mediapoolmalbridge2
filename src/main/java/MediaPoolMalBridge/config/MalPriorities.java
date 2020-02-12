package MediaPoolMalBridge.config;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class MalPriorities {

    private int smallestPriority;

    private final Map<String, Integer> marshaCodeToPriority = new HashMap<>();

    public MalPriorities( final AppConfig appConfig ) {

        final Map<Integer, List<String>> malPriorities = appConfig.getMalPriorities();

        smallestPriority = malPriorities.size() + 1000;
        malPriorities.entrySet().forEach( x -> {
            x.getValue().forEach( y -> marshaCodeToPriority.put( y, x.getKey() ) );
        } );
    }

    public int getMalPriority( final String marshaCode ) {
        return marshaCodeToPriority.computeIfAbsent( marshaCode, ( mC ) -> smallestPriority );
    }
}
