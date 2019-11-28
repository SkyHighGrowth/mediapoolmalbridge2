package MediaPoolMalBridge.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailSenderConfig {

    private AppConfig appConfig;

    public MailSenderConfig(final AppConfig appConfig)
    {
        this.appConfig = appConfig;
    }

    @Bean
    public JavaMailSender getJavaMailSender()
    {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost( appConfig.getMailHostname() );
        mailSender.setPort( appConfig.getMailPort() );

        mailSender.setUsername( appConfig.getMailUsername() );
        mailSender.setPassword( appConfig.getMailPassword() );

        Properties props = mailSender.getJavaMailProperties();
        props.put( "mail.transport.protocol", appConfig.getMailTransportProtocol() );
        props.put( "mail.smtp.auth", String.valueOf( appConfig.isMailSmtpAuth() ) );
        props.put( "mail.smtp.starttls.enable", String.valueOf( appConfig.isMailSmtpStarttlsEnable() ) );
        props.put( "mail.debug", String.valueOf( appConfig.isMailDebug() ) );

        return mailSender;
    }
}
