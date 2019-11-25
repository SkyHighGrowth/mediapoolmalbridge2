package MediaPoolMalBridge.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailSenderConfig {

    @Bean
    public JavaMailSender getJavaMailSender(@Value( "${mail.hostname}" ) final String hostName,
                                            @Value( "${mail.port}") final int port,
                                            @Value( "${mail.username}") final String username,
                                            @Value( "${mail.password}") final String password,
                                            @Value( "${mail.transport.protocol}" ) final String protocol,
                                            @Value( "${mail.smtp.auth}" ) final String smtpAuth,
                                            @Value( "${mail.smtp.starttls.enable}") final String starttlsEnable,
                                            @Value( "${mail.debug}") final String debug )
    {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost( hostName );
        mailSender.setPort( port );

        mailSender.setUsername( username );
        mailSender.setPassword( password );

        Properties props = mailSender.getJavaMailProperties();
        props.put( "mail.transport.protocol", protocol );
        props.put( "mail.smtp.auth", smtpAuth );
        props.put( "mail.smtp.starttls.enable", starttlsEnable );
        props.put( "mail.debug", debug );

        return mailSender;
    }
}
