package MediaPoolMalBridge.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AppConfigData {

    @Value( "${bridge.jscp.username.dev:root}")
    private String sftpUsername;
    @Value( "${bridge.jscp.password.dev:password}")
    private String sftpPassword;
    @Value( "${bridge.jscp.hostname.dev:147.91.27.84}")
    private String sftpHostname;
    @Value( "${bridge.jscp.port.dev:22}")
    private int sftpPort;
    @Value( "${server.ssh.public.key.dev:ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABAQDXlGvJ+ncOhx8r97QFsjDJzZffY20puG7/Lql5wxXqv3AKFBciaBjEzVeLxE60dgLSOLJLyfjijKXyhH+PlISlFuXN3V/w8bJZw46bGPUJQJR+3R0pHV7SSza6qhoJVVQKV+xNs0hL85yLEyMGK38Rg1Mu2a3ciJlrDrIN8qtG6xLu8jylVLUbAGjMOl4b9rzTzi3Q6OooLJrwWB29Xxs4uaY9IbsWAEKGLD3T4AB2GE2Sw6ZUsRxsuyUYOKNj4vZn3km7sQV8fTWxb+TsDcuPHf1vYNGE27AVc3C2sO1J6H4ZaXLl6F4bBssLCrq6upcLW4OWMv9tEq66sQAJJuxn}")
    private String sftpPublicKey;

    @Value( "${mal.hostname:https://api.starwoodassetlibrary.com/}" )
    private String malHostname;
    @Value( "${mal.login:robert.scholten@brandmaker.com}")
    private String malLogin;
    @Value( "${mal.apiKey:4ee54a12d9e6f1d4a535248142856a3e}")
    private String malApiKey;

    @Value( "${mediapool.username.dev:tsupport.de}")
    private String mediapoolUsernameDev;
    @Value( "${mediapool.password.dev:de!SuPPort$4}" )
    private String mediapoolPasswordDev;
    @Value( "${mediapool.url.dev:https://qamarriott.brandmakerinc.com/webservices/MediaPool/}" )
    private String mediapoolUrlDev;
    @Value( "${theme.url.dev:https://qamarriott.brandmakerinc.com/webservices/Theme/}" )
    private String themeUrlDev;

    @Value( "${mediapool.username.production:tsupport.de}")
    private String mediapoolUsernameProduction;
    @Value( "${mediapool.password.production:de!SuPPort$4}")
    private String mediapoolPasswordProduction;
    @Value( "${mediapool.url.production:https://qamarriott.brandmakerinc.com/webservices/MediaPool/}" )
    private String mediapoolUrlProduction;
    @Value( "${theme.url.production:https://qamarriott.brandmakerinc.com/webservices/Theme/}" )
    private String themeUrlProduction;

    @Value( "${threadscheduler.pool.size:20}" )
    private int threadschedulerPoolSize;
    @Value( "${threadexecutor.pool.size:100}" )
    private int threadexecutorPoolSize;
    @Value( "${threadexecutor.queue.size:10000}" )
    private int threadexecutorQueueSize;
    @Value( "${threadexecutor.queue.length.max:9000}" )
    private int threadexecutorQueueLengthMax;

    @Value( "${mail.hostname:smtp.gmail.com}" )
    private String mailHostname;
    @Value( "${mail.port:587}" )
    private int mailPort;
    @Value( "${mail.username:coacoacoa}" )
    private String mailUsername;
    @Value( "${mail.password:password}" )
    private String mailPassword;
    @Value( "${mail.transport.protocol:smtp}" )
    private String mailTransportProtocol;
    @Value( "${mail.smtp.auth:true}" )
    private boolean mailSmtpAuth;
    @Value( "${mail.smtp.starttls.enable:true}" )
    private boolean mailSmtpStarttlsEnable;
    @Value( "${mail.debug:true}" )
    private boolean mailDebug;
    @Value( "${mail.bm.address:bm@bm.com}" )
    private String mailBmAddress;
    @Value( "${mail.mal.address:mal@mal.com}" )
    private String mailMalAddress;

    @Value( "${asset.state.repetition.max:4}" )
    private int assetStateRepetitionMax;
    @Value( "${mal.look.in.the.past.days:14}" )
    private int malLookInThePastDays;
    @Value( "${bridge.look.in.the.past.days:2}")
    private int bridgeLookInThePastDays;

    @Value( "${scheduler.bmexchange.cron.expression:0 0 */1 * * *}" )
    private String bmExchangeSchedulerCronExpression;
    @Value( "${scheduler.bmupload.cron.expression:0 0 */1 * * *}" )
    private String bmUploadSchedulerCronExpression;
    @Value( "${scheduler.bridgeexchangeassets.cron.expression:0 0 */1 * * *}" )
    private String bridgeExchangeAssetsCronExpression;
    @Value( "${scheduler.bridgedeletefiles.cron.expression:0 0 */1 * * *}" )
    private String bridgeDeleteFileCronExpression;
    @Value( "${scheduler.bridgecreateexcelfiles.cron.expression:0 30 23 * * *}" )
    private String bridgeExcelFilesCronExpression;
    @Value( "${scheduler.bridgeuploadexcelfiles.cron.expression:0 0 0 * * *}" )
    private String bridgeUploadExcelFilesCronExpression;
    @Value( "${scheduler.bridgetransfertheme.cron.expression:0 0 */1 * * *}" )
    private String bridgeTransferThemeCronExpression;
    @Value( "${scheduler.bridgesendmail.cron.expression: 0 0 0 * * *}" )
    private String bridgeSendMailCronExpression;
    @Value( "${scheduler.malasset.cron.expression: 0 0 */1 * * *}" )
    private String assetCronExpression;
    @Value( "${scheduler.maldownloadasset.cron.expression: 0 0 */1 * * *}" )
    private String malDownloadAssetCronExpression;
    @Value( "${scheduler.malassetstructures.cron.expression:0 0 */1 * * *}" )
    private String assetStructureCronExpression;
    @Value( "${scheduler.malproperties.cron.expression:0 0 */1 * * *}" )
    private String malPropertiesCronExpression;
    @Value( "${scheduler.malgetphotos.cron.expression:0 0 */1 * * *}" )
    private String malGetPhotosCronExpression;
    @Value( "${scheduler.malphoto.cron.expression: 0 0 */1 * * *}" )
    private String malPhotoCronExpression;

    @Value( "${datasource.url.dev:jdbc:mysql://localhost:3306/maltobmbridge?zeroDateTimeBehavior=CONVERT_TO_NULL&serverTimezone=UTC&sessionVariables=sql_mode='NO_ENGINE_SUBSTITUTION'&jdbcCompliantTruncation=false&autoReconnect=true}")
    private String datasourceUrlDev;
    @Value( "${database.user.dev}" )
    private String datasourceUsernameDev;
    @Value( "${database.password.dev}" )
    private String datasourcePasswordDev;
    @Value( "${datasource.url.production:jdbc:mysql://localhost:3306/maltobmbridge?zeroDateTimeBehavior=CONVERT_TO_NULL&serverTimezone=UTC&sessionVariables=sql_mode='NO_ENGINE_SUBSTITUTION'&jdbcCompliantTruncation=false&autoReconnect=true}")
    private String datasourceUrlProduction;
    @Value( "${database.user.production}" )
    private String datasourceUsernameProduction;
    @Value( "${database.password.production}" )
    private String datasourcePasswordProduction;

    @Value( "${jpa.properties.hibernate.dialect:org.hibernate.dialect.MySQL55Dialect}" )
    private String hibernateDialect;
    @Value( "${jpa.show-sql:false}" )
    private boolean hibernateShowSql;
    @Value( "${jpa.properties.hibernate.hbm2ddl.auto:update}" )
    private String hibernateHbm2dlAuto;

    @Value( "${datasource.hikari.connectionTimeout:30000}")
    private int hikariConnectionTimeout;
    @Value( "${datasource.hikari.idleTimeout:600000}")
    private int hikariIdleTimeout;
    @Value( "${datasource.hikari.maxLifetime:1800000}")
    private int hikariMaxLifeTime;
    @Value( "${datasource.hikari.auto-commit:false}" )
    private boolean hikariAutoCommit;
    @Value( "${datasource.hikari.maximum-pool-size:10000}" )
    private int hikariMaximumPoolSize;


    public String getSftpUsername() {
        return sftpUsername;
    }

    public String getSftpPassword() {
        return sftpPassword;
    }

    public String getSftpHostname() {
        return sftpHostname;
    }

    public int getSftpPort() {
        return sftpPort;
    }

    public String getSftpPublicKey() {
        return sftpPublicKey;
    }

    public String getMalHostname() {
        return malHostname;
    }

    public String getMalLogin() {
        return malLogin;
    }

    public String getMalApiKey() {
        return malApiKey;
    }

    public String getMediapoolUsernameDev() {
        return mediapoolUsernameDev;
    }

    public String getMediapoolPasswordDev() {
        return mediapoolPasswordDev;
    }

    public String getMediapoolUrlDev() {
        return mediapoolUrlDev;
    }

    public String getThemeUrlDev() {
        return themeUrlDev;
    }

    public String getMediapoolUsernameProduction() {
        return mediapoolUsernameProduction;
    }

    public String getMediapoolPasswordProduction() {
        return mediapoolPasswordProduction;
    }

    public String getMediapoolUrlProduction() {
        return mediapoolUrlProduction;
    }

    public String getThemeUrlProduction() {
        return themeUrlProduction;
    }

    public int getThreadschedulerPoolSize() {
        return threadschedulerPoolSize;
    }

    public int getThreadexecutorPoolSize() {
        return threadexecutorPoolSize;
    }

    public int getThreadexecutorQueueSize() {
        return threadexecutorQueueSize;
    }

    public int getThreadexecutorQueueLengthMax() {
        return threadexecutorQueueLengthMax;
    }

    public String getMailHostname() {
        return mailHostname;
    }

    public int getMailPort() {
        return mailPort;
    }

    public String getMailUsername() {
        return mailUsername;
    }

    public String getMailPassword() {
        return mailPassword;
    }

    public String getMailTransportProtocol() {
        return mailTransportProtocol;
    }

    public boolean isMailSmtpAuth() {
        return mailSmtpAuth;
    }

    public boolean isMailSmtpStarttlsEnable() {
        return mailSmtpStarttlsEnable;
    }

    public boolean isMailDebug() {
        return mailDebug;
    }

    public String getMailBmAddress() {
        return mailBmAddress;
    }

    public String getMailMalAddress() {
        return mailMalAddress;
    }

    public int getAssetStateRepetitionMax() {
        return assetStateRepetitionMax;
    }

    public int getMalLookInThePastDays() {
        return malLookInThePastDays;
    }

    public int getBridgeLookInThePastDays() {
        return bridgeLookInThePastDays;
    }

    public String getBmExchangeSchedulerCronExpression() {
        return bmExchangeSchedulerCronExpression;
    }

    public String getBmUploadSchedulerCronExpression() {
        return bmUploadSchedulerCronExpression;
    }

    public String getBridgeExchangeAssetsCronExpression() {
        return bridgeExchangeAssetsCronExpression;
    }

    public String getBridgeDeleteFileCronExpression() {
        return bridgeDeleteFileCronExpression;
    }

    public String getBridgeExcelFilesCronExpression() {
        return bridgeExcelFilesCronExpression;
    }

    public String getBridgeUploadExcelFilesCronExpression() {
        return bridgeUploadExcelFilesCronExpression;
    }

    public String getBridgeTransferThemeCronExpression() {
        return bridgeTransferThemeCronExpression;
    }

    public String getBridgeSendMailCronExpression() {
        return bridgeSendMailCronExpression;
    }

    public String getMalAssetCronExpression() {
        return assetCronExpression;
    }

    public String getMalDownloadAssetCronExpression() {
        return malDownloadAssetCronExpression;
    }

    public String getMalAssetStructureCronExpression() {
        return assetStructureCronExpression;
    }

    public String getMalPropertiesCronExpression() {
        return malPropertiesCronExpression;
    }

    public String getMalGetPhotosCronExpression() {
        return malGetPhotosCronExpression;
    }

    public String getMalPhotoCronExpression() {
        return malPhotoCronExpression;
    }

    public String getDatasourceUrlDev() {
        return datasourceUrlDev;
    }

    public String getDatasourceUsernameDev() {
        return datasourceUsernameDev;
    }

    public String getDatasourcePasswordDev() {
        return datasourcePasswordDev;
    }

    public String getDatasourceUrlProduction() {
        return datasourceUrlProduction;
    }

    public String getDatasourceUsernameProduction() {
        return datasourceUsernameProduction;
    }

    public String getDatasourcePasswordProduction() {
        return datasourcePasswordProduction;
    }

    public String getHibernateDialect() {
        return hibernateDialect;
    }

    public boolean isHibernateShowSql() {
        return hibernateShowSql;
    }

    public String getHibernateHbm2dlAuto() {
        return hibernateHbm2dlAuto;
    }

    public int getHikariConnectionTimeout() {
        return hikariConnectionTimeout;
    }

    public int getHikariIdleTimeout() {
        return hikariIdleTimeout;
    }

    public int getHikariMaxLifeTime() {
        return hikariMaxLifeTime;
    }

    public boolean isHikariAutoCommit() {
        return hikariAutoCommit;
    }

    public int getHikariMaximumPoolSize() {
        return hikariMaximumPoolSize;
    }
}
