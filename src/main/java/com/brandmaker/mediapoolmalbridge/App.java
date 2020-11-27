package com.brandmaker.mediapoolmalbridge;


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
        String[] appProfiles;
        if( !hasProfiles ) {
            String[] argProfiles = new String[ args.length + 1 ];
            System.arraycopy(args, 0, argProfiles, 0, args.length);
            argProfiles[ args.length ] = "-Dspring.profiles.active=" + profiles;
            appProfiles = argProfiles;
        } else {
            appProfiles = args;
        }
        SpringApplication.run(App.class, appProfiles);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(App.class);
    }
}