package MediaPoolMalBridge.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

/**
 * Datasource configuration bean
 */
@Configuration
public class DataSourceConfig {

    /**
     * dev profile
     * @param appConfig
     * @return
     */
    @Bean( "DataSource" )
    @Profile( {"dev", "!production"})
    public DataSource getDataSourceDev(final AppConfig appConfig)
    {
        final HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setUsername( appConfig.getDatasourceUsernameDev() );
        hikariConfig.setPassword( appConfig.getDatasourcePasswordDev() );
        hikariConfig.setJdbcUrl( appConfig.getDatasourceUrlDev() );
        hikariConfig.setAutoCommit( appConfig.isHikariAutoCommit() );
        hikariConfig.setMaximumPoolSize( appConfig.getHikariMaximumPoolSize() );
        hikariConfig.setConnectionTimeout( appConfig.getHikariConnectionTimeout() );
        hikariConfig.setIdleTimeout( appConfig.getHikariIdleTimeout() );
        return new HikariDataSource( hikariConfig );

    }

    /**
     * production profile
     * @param appConfig
     * @return
     */
    @Bean( "DataSource" )
    @Profile( {"production", "!dev"} )
    public DataSource getDataSourceProduction(final AppConfig appConfig)
    {
        final HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setUsername( appConfig.getDatasourceUsernameProduction() );
        hikariConfig.setPassword( appConfig.getDatasourcePasswordProduction() );
        hikariConfig.setJdbcUrl( appConfig.getDatasourceUrlProduction() );
        hikariConfig.setAutoCommit( appConfig.isHikariAutoCommit() );
        hikariConfig.setMaximumPoolSize( appConfig.getHikariMaximumPoolSize() );
        hikariConfig.setConnectionTimeout( appConfig.getHikariConnectionTimeout() );
        hikariConfig.setIdleTimeout( appConfig.getHikariIdleTimeout() );
        return new HikariDataSource( hikariConfig );
    }
}
