package com.brandmaker.mediapoolmalbridge.config;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Application configuration data which is collected from
 */
@Component("AppConfigData")
public class AppConfigData {

    @Value("${bridge.jscp.usesftp}")
    private boolean useSftp;
    /**
     * sftp server username
     */
    @Value("${bridge.jscp.username.dev:root}")
    private String sftpUsername;
    /**
     * sftp server password
     */
    @Value("${bridge.jscp.password.dev:password}")
    private String sftpPassword;
    /**
     * sftp hostname
     */
    @Value("${bridge.jscp.hostname.dev:147.91.27.84}")
    private String sftpHostname;
    /**
     * sftp port
     */
    @Value("${bridge.jscp.port.dev:22}")
    private int sftpPort;
    /**
     * sftp public key, for example ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABAQDXlGvJ+ncOhx8r97QFsjDJzZffY20puG7/Lql5wxXqv3AKFBciaBjEzVeLxE60dgLSOLJLyfjijKXyhH+PlISlFuXN3V/w8bJZw46bGPUJQJR+3R0pHV7SSza6qhoJVVQKV+xNs0hL85yLEyMGK38Rg1Mu2a3ciJlrDrIN8qtG6xLu8jylVLUbAGjMOl4b9rzTzi3Q6OooLJrwWB29Xxs4uaY9IbsWAEKGLD3T4AB2GE2Sw6ZUsRxsuyUYOKNj4vZn3km7sQV8fTWxb+TsDcuPHf1vYNGE27AVc3C2sO1J6H4ZaXLl6F4bBssLCrq6upcLW4OWMv9tEq66sQAJJuxn
     */
    @Value("${server.ssh.public.key.dev:ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABAQDXlGvJ+ncOhx8r97QFsjDJzZffY20puG7/Lql5wxXqv3AKFBciaBjEzVeLxE60dgLSOLJLyfjijKXyhH+PlISlFuXN3V/w8bJZw46bGPUJQJR+3R0pHV7SSza6qhoJVVQKV+xNs0hL85yLEyMGK38Rg1Mu2a3ciJlrDrIN8qtG6xLu8jylVLUbAGjMOl4b9rzTzi3Q6OooLJrwWB29Xxs4uaY9IbsWAEKGLD3T4AB2GE2Sw6ZUsRxsuyUYOKNj4vZn3km7sQV8fTWxb+TsDcuPHf1vYNGE27AVc3C2sO1J6H4ZaXLl6F4bBssLCrq6upcLW4OWMv9tEq66sQAJJuxn}")
    private String sftpPublicKey;

    /**
     * MAL host name
     */
    @Value("${mal.hostname:https://api.starwoodassetlibrary.com/}")
    private String malHostname;
    /**
     * MAL login name
     */
    @Value("${mal.login:robert.scholten@brandmaker.com}")
    private String malLogin;
    /**
     * MAL api key
     */
    @Value("${mal.apiKey:4ee54a12d9e6f1d4a535248142856a3e}")
    private String malApiKey;

    /**
     * Mediapool username for dev profile
     */
    @Value("${mediapool.username.dev:tsupport.de}")
    private String mediapoolUsernameDev;
    /**
     * Medaipool password for dev profile
     */
    @Value("${mediapool.password.dev:de!SuPPort$4}")
    private String mediapoolPasswordDev;
    /**
     * Mediapool server url for dev profile
     */
    @Value("${mediapool.url.dev:https://qamarriott.brandmakerinc.com/webservices/MediaPool/}")
    private String mediapoolUrlDev;
    /**
     * Mediapool theme server url for dev profile
     */
    @Value("${theme.url.dev:https://qamarriott.brandmakerinc.com/webservices/Theme/}")
    private String themeUrlDev;

    /**
     * Mediapool username production profile
     */
    @Value("${mediapool.username.production:tsupport.de}")
    private String mediapoolUsernameProduction;
    /**
     * Mediapool passoword production profile
     */
    @Value("${mediapool.password.production:de!SuPPort$4}")
    private String mediapoolPasswordProduction;
    /**
     * Mediapool server url production profile
     */
    @Value("${mediapool.url.production:https://qamarriott.brandmakerinc.com/webservices/MediaPool/}")
    private String mediapoolUrlProduction;
    /**
     * Mediapool server url production profile
     */
    @Value("${theme.url.production:https://qamarriott.brandmakerinc.com/webservices/Theme/}")
    private String themeUrlProduction;

    /**
     * scheduler pool size
     */
    @Value("${threadscheduler.pool.size:20}")
    private int threadschedulerPoolSize;
    /**
     * executor pool size
     */
    @Value("${malthreadexecutor.pool.size:100}")
    private int malThreadexecutorPoolSize;
    /**
     * executor waiting queue size
     */
    @Value("${malthreadexecutor.queue.size:10000}")
    private int malThreadexecutorQueueSize;
    /**
     * executor queue maximal allowed length
     */
    @Value("${malthreadexecutor.queue.length.max:9000}")
    private int malThreadexecutorQueueLengthMax;
    /**
     * executor pool size
     */
    @Value("${bmthreadexecutor.pool.size:10}")
    private int bmThreadexecutorPoolSize;
    /**
     * executor waiting queue size
     */
    @Value("${bmthreadexecutor.queue.size:10000}")
    private int bmThreadexecutorQueueSize;
    /**
     * executor queue maximal allowed length
     */
    @Value("${bmthreadexecutor.queue.length.max:9000}")
    private int bmThreadexecutorQueueLengthMax;

    /**
     * mail server name
     */
    @Value("${mail.hostname:smtp.gmail.com}")
    private String mailHostname;
    /**
     * mail server port
     */
    @Value("${mail.port:587}")
    private int mailPort;
    /**
     * mail server username
     */
    @Value("${mail.username:coacoacoa}")
    private String mailUsername;
    /**
     * mail server password
     */
    @Value("${mail.password:password}")
    private String mailPassword;
    /**
     * mail server transport protocol
     */
    @Value("${mail.transport.protocol:smtp}")
    private String mailTransportProtocol;
    /**
     * mail server smtp authorization
     */
    @Value("${mail.smtp.auth:true}")
    private boolean mailSmtpAuth;
    /**
     * mail server server start tls enabled
     */
    @Value("${mail.smtp.starttls.enable:true}")
    private boolean mailSmtpStarttlsEnable;
    /**
     * mail server debug output
     */
    @Value("${mail.debug:true}")
    private boolean mailDebug;
    /**
     * Mediapool mail address
     */
    @Value("${mail.bm.address:bm@bm.com}")
    private String mailBmAddress;
    /**
     * MAL mail address
     */
    @Value("${mail.mal.address:mal@mal.com}")
    private String mailMalAddress;

    /**
     * maximum allowed repetition of being in the same state
     */
    @Value("${asset.state.repetition.max:4}")
    private int assetStateRepetitionMax;
    /**
     * datetime used to execute queries on MAL server is today minus number of days given by this field
     */
    @Value("${mal.look.in.the.past.days:14}")
    private int malLookInThePastDays;
    /**
     * maximal number of days downloaded file will live on disc after which it will be deleted
     * regardless of the fact it has been uploaded or not
     */
    @Value("${bridge.asset.maximallivingdaysofassetondisc:10}")
    private int assetFileMaximalLivingDaysOnDisc;
    /**
     * datetime used to look in the past in the database table expressed in days
     */
    @Value("${bridge.look.in.the.past.days:2}")
    private int bridgeLookInThePastDays;
    /**
     * After asset updated timestamp becomes smaller then lower biund of the time window
     * in which asset for transitions are looked up, BridgeDatabaseAssetResolver will update
     * their updated timestamp if transferring asset status is not DONE or ERROR
     * and will be given a chance to perform transitions
     */
    @Value("${bridge.resolver.window.days:1}")
    private int bridgeResolverWindow;
    /**
     * number of days after which rows are deleted from database
     */
    @Value("${bridge.deletedatabaserows.window.days:31}")
    private int bridgeDatabaseRowsDeleterDays;
    /**
     * database querries page size
     */
    @Value("${bridge.database.page.size:2000}")
    private int databasePageSize;
    /**
     * MAL server querries page size
     */
    @Value("${mal.page.size:2000}")
    private int malPageSize;

    /**
     * Mediapool metadata exchange scheduler cron expression
     */
    @Value("${scheduler.bmexchange.cron.expression:0 0 */1 * * *}")
    private String bmExchangeSchedulerCronExpression;
    /**
     * Mediapool upload asset scheduler cron expression
     */
    @Value("${scheduler.bmupload.cron.expression:0 0 */1 * * *}")
    private String bmUploadSchedulerCronExpression;
    /**
     * Mediapool get theme id cron expression
     */
    @Value("${scheduler.bmgetthemeid.cron.expression:0 0 */1 * * *}")
    private String bmGetThemeIdCronExpression;
    /**
     *
     */
    @Value("${scheduler.bridgeexchangeassets.cron.expression:0 0 */1 * * *}")
    private String bridgeExchangeAssetsCronExpression;
    /**
     * delete temporary files scheduler cron expression
     */
    @Value("${scheduler.bridgedeletefiles.cron.expression:0 0 */1 * * *}")
    private String bridgeDeleteFileCronExpression;
    /**
     * delete rows in database tables that are created in the past,
     * past is described bridgeDatabaseRowsDeleterDays
     */
    @Value("${scheduler.bridgedeletedatabaserows.cron.expression:0 0 0 */1 * *}")
    private String bridgeDatabaseRowsDeleterCronExpression;
    /**
     * excel file creation scheduler cron expression
     */
    @Value("${scheduler.bridgecreateexcelfiles.cron.expression:0 30 23 * * *}")
    private String bridgeExcelFilesCronExpression;
    /**
     * upload excel file scheduler cron expression
     */
    @Value("${scheduler.bridgeuploadexcelfiles.cron.expression:0 0 0 * * *}")
    private String bridgeUploadExcelFilesCronExpression;
    /**
     * theme transfer scheduler cron expression
     */
    @Value("${scheduler.bridgetransfertheme.cron.expression:0 0 */1 * * *}")
    private String bridgeTransferThemeCronExpression;
    /**
     * send mail scheduler cron expression
     */
    @Value("${scheduler.bridgesendmail.cron.expression: 0 0 0 * * *}")
    private String bridgeSendMailCronExpression;
    /**
     * Asset on boarding cron expression
     */
    @Value("${scheduler.bridgeassetonboarding.cron.expression:0 10 * * * *}")
    private String bridgeAssetOnBoardingCronExpression;

    /**
     * Asset resolver cron expression
     */
    @Value("${scheduler.bridgeassetresolver.cron.expression:0 10 * * * *}")
    private String bridgeAssetResolverCronExpression;
    /**
     * MAL server collect assets scheduler cron expression
     */
    @Value("${scheduler.malasset.cron.expression: 0 0 */1 * * *}")
    private String malAssetCronExpression;
    /**
     * MAL server collect new assets scheduler cron expression
     */
    @Value("${scheduler.malnewasset.cron.expression: 0 0 */1 * * *}")
    private String malNewAssetCronExpression;
    /**
     * MAL server download files scheduler cron expression
     */
    @Value("${scheduler.maldownloadasset.cron.expression: 0 0 */1 * * *}")
    private String malDownloadAssetCronExpression;
    /**
     * MAL asset structure scheduler cron expression
     */
    @Value("${scheduler.malassetstructures.cron.expression:0 0 */1 * * *}")
    private String malAssetStructureCronExpression;
    /**
     * MAL properties scheduler cron expression
     */
    @Value("${scheduler.malproperties.cron.expression:0 0 */1 * * *}")
    private String malPropertiesCronExpression;

    /**
     * datasource url for dev profile
     */
    @Value("${spring.datasource.url.dev}")
    private String datasourceUrlDev;
    /**
     * datasource username for dev profile
     */
    @Value("${database.user.dev}")
    private String datasourceUsernameDev;
    /**
     * datasource password for dev profile
     */
    @Value("${database.password.dev}")
    private String datasourcePasswordDev;
    /**
     * datasource url for production profile
     */
    @Value("${spring.datasource.url.production:jdbc:mysql://localhost:3306/maltobmbridge?zeroDateTimeBehavior=CONVERT_TO_NULL&serverTimezone=UTC&sessionVariables=sql_mode='NO_ENGINE_SUBSTITUTION'&jdbcCompliantTruncation=false&autoReconnect=true}")
    private String datasourceUrlProduction;
    /**
     * datasource username for production profile
     */
    @Value("${database.user.production}")
    private String datasourceUsernameProduction;
    /**
     * datasource password for production profile
     */
    @Value("${database.password.production}")
    private String datasourcePasswordProduction;

    /**
     * hibernate dialect
     */
    @Value("${spring.jpa.properties.hibernate.dialect:org.hibernate.dialect.MySQL55Dialect}")
    private String hibernateDialect;
    /**
     * logs sql
     */
    @Value("${spring.jpa.show-sql:false}")
    private boolean hibernateShowSql;
    /**
     * will update database on boot
     */
    @Value("${spring.jpa.properties.hibernate.hbm2ddl.auto:update}")
    private String hibernateHbm2dlAuto;

    /**
     * database pool connection timeout
     */
    @Value("${spring.datasource.hikari.connection-timeout:30000}")
    private int hikariConnectionTimeout;
    /**
     * database idle timeout
     */
    @Value("${spring.datasource.hikari.idle-timeout:600000}")
    private int hikariIdleTimeout;
    /**
     * database pool max life time
     */
    @Value("${spring.datasource.hikari.max-lifetime:1800000}")
    private int hikariMaxLifeTime;
    /**
     * database pool auto commit
     */
    @Value("${spring.datasource.hikari.auto-commit:false}")
    private boolean hikariAutoCommit;
    /**
     * database pool maximum pool size
     */
    @Value("${spring.datasource.hikari.maximum-pool-size:10000}")
    private int hikariMaximumPoolSize;

    @Value("#{${mal.priorities}}")
    private Map<Integer, List<String>> malPriorities;

    @JsonIgnore
    private String malPrioritiesString;

    @Value("${working.directory}")
    private String workingDirectory;

    @Value("#{${included.asset.types}}")
    private Map<String, String> includedAssetTypes;

    @JsonIgnore
    private String includedAssetTypesString;

    @Value("${bridge.disable.absolutedelete}")
    private boolean disableAbsoluteDelete;

    @Value("${bridge.excel.donotcreateexcelfiles}")
    private boolean doNotCreateExcelFiles;

    @Value("${fail.save.category.name}")
    private String failSaveCategoryName;

    @Value("${interval.filter.enable}")
    private boolean intervalFilterEnable;

    @Value("${filter.start.date}")
    private String filterStartDate;

    @Value("${filter.end.date}")
    private String filterEndDate;

    @Value("${file.max.records}")
    private int fileMaxRecords;

    @Value("${file.formats.order}")
    private List<String> fileFormatsOrder;

    @Value("${download.folder.size.limit}")
    private long downloadFolderSizeLimit;

    @Value("${filter.only.mal.properties}")
    private List<String> filterOnlyMalProperties;

    @Value("${filter.only.asset.type}")
    private List<String> filterOnlyAssetType;

    @Value("${filter.only.color.ids}")
    private List<String> filterOnlyColorIds;

    public boolean isUseSftp() {
        return useSftp;
    }

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

    public int getMalThreadexecutorPoolSize() {
        return malThreadexecutorPoolSize;
    }

    public int getMalThreadexecutorQueueSize() {
        return malThreadexecutorQueueSize;
    }

    public int getMalThreadexecutorQueueLengthMax() {
        return malThreadexecutorQueueLengthMax;
    }

    public int getBmThreadexecutorPoolSize() {
        return bmThreadexecutorPoolSize;
    }

    public int getBmThreadexecutorQueueSize() {
        return bmThreadexecutorQueueSize;
    }

    public int getBmThreadexecutorQueueLengthMax() {
        return bmThreadexecutorQueueLengthMax;
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

    public int getBridgeResolverWindow() {
        return bridgeResolverWindow;
    }

    public int getBridgeDatabaseRowsDeleterDays() {
        return bridgeDatabaseRowsDeleterDays;
    }

    public int getAssetFileMaximalLivingDaysOnDisc() {
        return assetFileMaximalLivingDaysOnDisc;
    }

    public int getDatabasePageSize() {
        return databasePageSize;
    }

    public int getMalPageSize() {
        return malPageSize;
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

    public String getBmGetThemeIdCronExpression() {
        return bmGetThemeIdCronExpression;
    }

    public String getBridgeExchangeAssetsCronExpression() {
        return bridgeExchangeAssetsCronExpression;
    }

    public String getBridgeDeleteFileCronExpression() {
        return bridgeDeleteFileCronExpression;
    }

    public String getBridgeDatabaseRowsDeleterCronExpression() {
        return bridgeDatabaseRowsDeleterCronExpression;
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

    public String getBridgeAssetOnBoardingCronExpression() {
        return bridgeAssetOnBoardingCronExpression;
    }

    public String getBridgeAssetResolverCronExpression() {
        return bridgeAssetResolverCronExpression;
    }

    public String getMalAssetCronExpression() {
        return malAssetCronExpression;
    }

    public String getMalDownloadAssetCronExpression() {
        return malDownloadAssetCronExpression;
    }

    public String getMalAssetStructureCronExpression() {
        return malAssetStructureCronExpression;
    }

    public String getMalPropertiesCronExpression() {
        return malPropertiesCronExpression;
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

    public Map<Integer, List<String>> getMalPriorities() {
        return malPriorities;
    }

    public String getWorkingDirectory() {
        return workingDirectory;
    }

    public Map<String, String> getIncludedAssetTypes() {
        return includedAssetTypes;
    }

    public boolean isDisableAbsoluteDelete() {
        return disableAbsoluteDelete;
    }

    public boolean isDoNotCreateExcelFiles() {
        return doNotCreateExcelFiles;
    }

    public String getFailSaveCategoryName() {
        return failSaveCategoryName;
    }

    public boolean isIntervalFilterEnable() {
        return intervalFilterEnable;
    }

    public String getFilterStartDate() {
        return filterStartDate;
    }

    public String getFilterEndDate() {
        return filterEndDate;
    }

    public int getFileMaxRecords() {
        return fileMaxRecords;
    }

    public List<String> getFileFormatsOrder() {
        return fileFormatsOrder;
    }

    public long getDownloadFolderSizeLimit() {
        return downloadFolderSizeLimit;
    }

    public List<String> getFilterOnlyMalProperties() {
        return filterOnlyMalProperties;
    }

    public List<String> getFilterOnlyAssetType() {
        return filterOnlyAssetType;
    }

    public List<String> getFilterOnlyColorIds() {
        return filterOnlyColorIds;
    }

    public String getIncludedAssetTypesString() {
        return includedAssetTypesString;
    }

    public String getMalPrioritiesString() {
        return malPrioritiesString;
    }

    public void setUseSftp(boolean useSftp) {
        this.useSftp = useSftp;
    }

    public void setSftpUsername(String sftpUsername) {
        this.sftpUsername = sftpUsername;
    }

    public void setSftpPassword(String sftpPassword) {
        this.sftpPassword = sftpPassword;
    }

    public void setSftpHostname(String sftpHostname) {
        this.sftpHostname = sftpHostname;
    }

    public void setSftpPort(int sftpPort) {
        this.sftpPort = sftpPort;
    }

    public void setSftpPublicKey(String sftpPublicKey) {
        this.sftpPublicKey = sftpPublicKey;
    }

    public void setMalHostname(String malHostname) {
        this.malHostname = malHostname;
    }

    public void setMalLogin(String malLogin) {
        this.malLogin = malLogin;
    }

    public void setMalApiKey(String malApiKey) {
        this.malApiKey = malApiKey;
    }

    public void setMediapoolUsernameDev(String mediapoolUsernameDev) {
        this.mediapoolUsernameDev = mediapoolUsernameDev;
    }

    public void setMediapoolPasswordDev(String mediapoolPasswordDev) {
        this.mediapoolPasswordDev = mediapoolPasswordDev;
    }

    public void setMediapoolUrlDev(String mediapoolUrlDev) {
        this.mediapoolUrlDev = mediapoolUrlDev;
    }

    public void setThemeUrlDev(String themeUrlDev) {
        this.themeUrlDev = themeUrlDev;
    }

    public void setMediapoolUsernameProduction(String mediapoolUsernameProduction) {
        this.mediapoolUsernameProduction = mediapoolUsernameProduction;
    }

    public void setMediapoolPasswordProduction(String mediapoolPasswordProduction) {
        this.mediapoolPasswordProduction = mediapoolPasswordProduction;
    }

    public void setMediapoolUrlProduction(String mediapoolUrlProduction) {
        this.mediapoolUrlProduction = mediapoolUrlProduction;
    }

    public void setThemeUrlProduction(String themeUrlProduction) {
        this.themeUrlProduction = themeUrlProduction;
    }

    public void setThreadschedulerPoolSize(int threadschedulerPoolSize) {
        this.threadschedulerPoolSize = threadschedulerPoolSize;
    }

    public void setMalThreadexecutorPoolSize(int malThreadexecutorPoolSize) {
        this.malThreadexecutorPoolSize = malThreadexecutorPoolSize;
    }

    public void setMalThreadexecutorQueueSize(int malThreadexecutorQueueSize) {
        this.malThreadexecutorQueueSize = malThreadexecutorQueueSize;
    }

    public void setMalThreadexecutorQueueLengthMax(int malThreadexecutorQueueLengthMax) {
        this.malThreadexecutorQueueLengthMax = malThreadexecutorQueueLengthMax;
    }

    public void setBmThreadexecutorPoolSize(int bmThreadexecutorPoolSize) {
        this.bmThreadexecutorPoolSize = bmThreadexecutorPoolSize;
    }

    public void setBmThreadexecutorQueueSize(int bmThreadexecutorQueueSize) {
        this.bmThreadexecutorQueueSize = bmThreadexecutorQueueSize;
    }

    public void setBmThreadexecutorQueueLengthMax(int bmThreadexecutorQueueLengthMax) {
        this.bmThreadexecutorQueueLengthMax = bmThreadexecutorQueueLengthMax;
    }

    public void setMailHostname(String mailHostname) {
        this.mailHostname = mailHostname;
    }

    public void setMailPort(int mailPort) {
        this.mailPort = mailPort;
    }

    public void setMailUsername(String mailUsername) {
        this.mailUsername = mailUsername;
    }

    public void setMailPassword(String mailPassword) {
        this.mailPassword = mailPassword;
    }

    public void setMailTransportProtocol(String mailTransportProtocol) {
        this.mailTransportProtocol = mailTransportProtocol;
    }

    public void setMailSmtpAuth(boolean mailSmtpAuth) {
        this.mailSmtpAuth = mailSmtpAuth;
    }

    public void setMailSmtpStarttlsEnable(boolean mailSmtpStarttlsEnable) {
        this.mailSmtpStarttlsEnable = mailSmtpStarttlsEnable;
    }

    public void setMailDebug(boolean mailDebug) {
        this.mailDebug = mailDebug;
    }

    public void setMailBmAddress(String mailBmAddress) {
        this.mailBmAddress = mailBmAddress;
    }

    public void setMailMalAddress(String mailMalAddress) {
        this.mailMalAddress = mailMalAddress;
    }

    public void setAssetStateRepetitionMax(int assetStateRepetitionMax) {
        this.assetStateRepetitionMax = assetStateRepetitionMax;
    }

    public void setMalLookInThePastDays(int malLookInThePastDays) {
        this.malLookInThePastDays = malLookInThePastDays;
    }

    public void setAssetFileMaximalLivingDaysOnDisc(int assetFileMaximalLivingDaysOnDisc) {
        this.assetFileMaximalLivingDaysOnDisc = assetFileMaximalLivingDaysOnDisc;
    }

    public void setBridgeLookInThePastDays(int bridgeLookInThePastDays) {
        this.bridgeLookInThePastDays = bridgeLookInThePastDays;
    }

    public void setBridgeResolverWindow(int bridgeResolverWindow) {
        this.bridgeResolverWindow = bridgeResolverWindow;
    }

    public void setBridgeDatabaseRowsDeleterDays(int bridgeDatabaseRowsDeleterDays) {
        this.bridgeDatabaseRowsDeleterDays = bridgeDatabaseRowsDeleterDays;
    }

    public void setDatabasePageSize(int databasePageSize) {
        this.databasePageSize = databasePageSize;
    }

    public void setMalPageSize(int malPageSize) {
        this.malPageSize = malPageSize;
    }

    public void setBmExchangeSchedulerCronExpression(String bmExchangeSchedulerCronExpression) {
        this.bmExchangeSchedulerCronExpression = bmExchangeSchedulerCronExpression;
    }

    public void setBmUploadSchedulerCronExpression(String bmUploadSchedulerCronExpression) {
        this.bmUploadSchedulerCronExpression = bmUploadSchedulerCronExpression;
    }

    public void setBmGetThemeIdCronExpression(String bmGetThemeIdCronExpression) {
        this.bmGetThemeIdCronExpression = bmGetThemeIdCronExpression;
    }

    public void setBridgeExchangeAssetsCronExpression(String bridgeExchangeAssetsCronExpression) {
        this.bridgeExchangeAssetsCronExpression = bridgeExchangeAssetsCronExpression;
    }

    public void setBridgeDeleteFileCronExpression(String bridgeDeleteFileCronExpression) {
        this.bridgeDeleteFileCronExpression = bridgeDeleteFileCronExpression;
    }

    public void setBridgeDatabaseRowsDeleterCronExpression(String bridgeDatabaseRowsDeleterCronExpression) {
        this.bridgeDatabaseRowsDeleterCronExpression = bridgeDatabaseRowsDeleterCronExpression;
    }

    public void setBridgeExcelFilesCronExpression(String bridgeExcelFilesCronExpression) {
        this.bridgeExcelFilesCronExpression = bridgeExcelFilesCronExpression;
    }

    public void setBridgeUploadExcelFilesCronExpression(String bridgeUploadExcelFilesCronExpression) {
        this.bridgeUploadExcelFilesCronExpression = bridgeUploadExcelFilesCronExpression;
    }

    public void setBridgeTransferThemeCronExpression(String bridgeTransferThemeCronExpression) {
        this.bridgeTransferThemeCronExpression = bridgeTransferThemeCronExpression;
    }

    public void setBridgeSendMailCronExpression(String bridgeSendMailCronExpression) {
        this.bridgeSendMailCronExpression = bridgeSendMailCronExpression;
    }

    public void setBridgeAssetOnBoardingCronExpression(String bridgeAssetOnBoardingCronExpression) {
        this.bridgeAssetOnBoardingCronExpression = bridgeAssetOnBoardingCronExpression;
    }

    public void setBridgeAssetResolverCronExpression(String bridgeAssetResolverCronExpression) {
        this.bridgeAssetResolverCronExpression = bridgeAssetResolverCronExpression;
    }

    public void setMalAssetCronExpression(String malAssetCronExpression) {
        this.malAssetCronExpression = malAssetCronExpression;
    }

    public void setMalDownloadAssetCronExpression(String malDownloadAssetCronExpression) {
        this.malDownloadAssetCronExpression = malDownloadAssetCronExpression;
    }

    public void setMalAssetStructureCronExpression(String malAssetStructureCronExpression) {
        this.malAssetStructureCronExpression = malAssetStructureCronExpression;
    }

    public void setMalPropertiesCronExpression(String malPropertiesCronExpression) {
        this.malPropertiesCronExpression = malPropertiesCronExpression;
    }

    public void setDatasourceUrlDev(String datasourceUrlDev) {
        this.datasourceUrlDev = datasourceUrlDev;
    }

    public void setDatasourceUsernameDev(String datasourceUsernameDev) {
        this.datasourceUsernameDev = datasourceUsernameDev;
    }

    public void setDatasourcePasswordDev(String datasourcePasswordDev) {
        this.datasourcePasswordDev = datasourcePasswordDev;
    }

    public void setDatasourceUrlProduction(String datasourceUrlProduction) {
        this.datasourceUrlProduction = datasourceUrlProduction;
    }

    public void setDatasourceUsernameProduction(String datasourceUsernameProduction) {
        this.datasourceUsernameProduction = datasourceUsernameProduction;
    }

    public void setDatasourcePasswordProduction(String datasourcePasswordProduction) {
        this.datasourcePasswordProduction = datasourcePasswordProduction;
    }

    public void setHibernateDialect(String hibernateDialect) {
        this.hibernateDialect = hibernateDialect;
    }

    public void setHibernateShowSql(boolean hibernateShowSql) {
        this.hibernateShowSql = hibernateShowSql;
    }

    public void setHibernateHbm2dlAuto(String hibernateHbm2dlAuto) {
        this.hibernateHbm2dlAuto = hibernateHbm2dlAuto;
    }

    public void setHikariConnectionTimeout(int hikariConnectionTimeout) {
        this.hikariConnectionTimeout = hikariConnectionTimeout;
    }

    public void setHikariIdleTimeout(int hikariIdleTimeout) {
        this.hikariIdleTimeout = hikariIdleTimeout;
    }

    public void setHikariMaxLifeTime(int hikariMaxLifeTime) {
        this.hikariMaxLifeTime = hikariMaxLifeTime;
    }

    public void setHikariAutoCommit(boolean hikariAutoCommit) {
        this.hikariAutoCommit = hikariAutoCommit;
    }

    public void setHikariMaximumPoolSize(int hikariMaximumPoolSize) {
        this.hikariMaximumPoolSize = hikariMaximumPoolSize;
    }

    public void setMalPriorities(Map<Integer, List<String>> malPriorities) {
        this.malPriorities = malPriorities;
    }

    public void setWorkingDirectory(String workingDirectory) {
        this.workingDirectory = workingDirectory;
    }

    public void setIncludedAssetTypes(Map<String, String> includedAssetTypes) {
        this.includedAssetTypes = includedAssetTypes;
    }

    public void setDisableAbsoluteDelete(boolean disableAbsoluteDelete) {
        this.disableAbsoluteDelete = disableAbsoluteDelete;
    }

    public void setDoNotCreateExcelFiles(boolean doNotCreateExcelFiles) {
        this.doNotCreateExcelFiles = doNotCreateExcelFiles;
    }

    public void setFailSaveCategoryName(String failSaveCategoryName) {
        this.failSaveCategoryName = failSaveCategoryName;
    }

    public void setIntervalFilterEnable(boolean intervalFilterEnable) {
        this.intervalFilterEnable = intervalFilterEnable;
    }

    public void setFilterStartDate(String filterStartDate) {
        this.filterStartDate = filterStartDate;
    }

    public void setFilterEndDate(String filterEndDate) {
        this.filterEndDate = filterEndDate;
    }

    public void setFileMaxRecords(int fileMaxRecords) {
        this.fileMaxRecords = fileMaxRecords;
    }

    public void setFileFormatsOrder(List<String> fileFormatsOrder) {
        this.fileFormatsOrder = fileFormatsOrder;
    }

    public void setDownloadFolderSizeLimit(long downloadFolderSizeLimit) {
        this.downloadFolderSizeLimit = downloadFolderSizeLimit;
    }

    public void setFilterOnlyMalProperties(List<String> filterOnlyMalProperties) {
        this.filterOnlyMalProperties = filterOnlyMalProperties;
    }

    public void setFilterOnlyAssetType(List<String> filterOnlyAssetType) {
        this.filterOnlyAssetType = filterOnlyAssetType;
    }

    public void setFilterOnlyColorIds(List<String> filterOnlyColorIds) {
        this.filterOnlyColorIds = filterOnlyColorIds;
    }

    public void setIncludedAssetTypesString(String includedAssetTypesString) {
        this.includedAssetTypesString = includedAssetTypesString;
    }

    public void setMalPrioritiesString(String malPrioritiesString) {
        this.malPrioritiesString = malPrioritiesString;
    }

    public String getMalNewAssetCronExpression() {
        return malNewAssetCronExpression;
    }

    public void setMalNewAssetCronExpression(String malNewAssetCronExpression) {
        this.malNewAssetCronExpression = malNewAssetCronExpression;
    }
}
