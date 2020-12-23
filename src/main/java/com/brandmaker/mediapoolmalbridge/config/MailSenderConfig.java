package com.brandmaker.mediapoolmalbridge.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

/**
 * mail service bean
 */
@Configuration
public class MailSenderConfig {

    private final AppConfig appConfig;

    public MailSenderConfig(final AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        AppConfigData appConfigData = appConfig.getAppConfigData();
        mailSender.setHost(appConfigData.getMailHostname());
        mailSender.setPort(appConfigData.getMailPort());

        mailSender.setUsername(appConfigData.getMailUsername());
        mailSender.setPassword(appConfigData.getMailPassword());

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", appConfigData.getMailTransportProtocol());
        props.put("mail.smtp.auth", String.valueOf(appConfigData.isMailSmtpAuth()));
        props.put("mail.smtp.starttls.enable", String.valueOf(appConfigData.isMailSmtpStarttlsEnable()));
        props.put("mail.debug", String.valueOf(appConfigData.isMailDebug()));

        return mailSender;
    }
}
