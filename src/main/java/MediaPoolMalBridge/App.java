package MediaPoolMalBridge;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class App extends SpringBootServletInitializer {

    @Value( "${spring.profiles.active:standalone, dev, no scheduler}" )
    private static String profiles;

    public static void main(String[] args) {
        boolean hasProfiles = false;
        for( final String arg : args )
        {
            if( arg.contains( "-Dspring.profiles.active") ) {
                hasProfiles = true;
                break;
            }
        }
        String[] args__;
        if( !hasProfiles ) {
            String[] args_ = new String[ args.length + 1 ];
            for( int index = 0; index < args.length; ++index ) {
                args_[ index ] = args[ index ];
            }
            args_[ args.length ] = "-Dspring.profiles.active=" + profiles;
            args__ = args_;
        } else {
            args__ = args;
        }
        SpringApplication.run(App.class, args__);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(App.class);
    }
}

/*@SpringBootApplication
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

}*/


