package com.brandmaker.mediapoolmalbridge.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

/**
 * Datasource configuration bean
 */
@Profile("standalone")
@Configuration
public class DataSourceConfig {

    /**
     * dev profile
     *
     * @param appConfig {@link AppConfig}
     * @return datasource for qa
     */
    @Bean("DataSource")
    @Profile({"standalone", "dev", "!production"})
    public DataSource getDataSourceDev(final AppConfig appConfig) {
        AppConfigData appConfigData = appConfig.getAppConfigData();
        final HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setUsername(appConfigData.getDatasourceUsernameDev());
        hikariConfig.setPassword(appConfigData.getDatasourcePasswordDev());
        hikariConfig.setJdbcUrl(appConfigData.getDatasourceUrlDev());
        hikariConfig.setAutoCommit(appConfigData.isHikariAutoCommit());
        hikariConfig.setMaximumPoolSize(appConfigData.getHikariMaximumPoolSize());
        hikariConfig.setConnectionTimeout(appConfigData.getHikariConnectionTimeout());
        hikariConfig.setIdleTimeout(appConfigData.getHikariIdleTimeout());
        return new HikariDataSource(hikariConfig);

    }

    /**
     * production profile
     *
     * @param appConfig {@link AppConfig}
     * @return datasource for production
     */
    @Bean("DataSource")
    @Profile({"standalone", "production", "!dev"})
    public DataSource getDataSourceProduction(final AppConfig appConfig) {
        AppConfigData appConfigData = appConfig.getAppConfigData();
        final HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setUsername(appConfigData.getDatasourceUsernameProduction());
        hikariConfig.setPassword(appConfigData.getDatasourcePasswordProduction());
        hikariConfig.setJdbcUrl(appConfigData.getDatasourceUrlProduction());
        hikariConfig.setAutoCommit(appConfigData.isHikariAutoCommit());
        hikariConfig.setMaximumPoolSize(appConfigData.getHikariMaximumPoolSize());
        hikariConfig.setConnectionTimeout(appConfigData.getHikariConnectionTimeout());
        hikariConfig.setIdleTimeout(appConfigData.getHikariIdleTimeout());
        return new HikariDataSource(hikariConfig);
    }

}
