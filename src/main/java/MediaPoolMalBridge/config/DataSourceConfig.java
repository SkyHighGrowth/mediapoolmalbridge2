package MediaPoolMalBridge.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
public class DataSourceConfig {

    private AppConfig appConfig;

    public DataSourceConfig( final AppConfig appConfig )
    {
        this.appConfig = appConfig;
    }

    @Bean( "DataSource" )
    @Profile( {"dev", "!production"})
    public DataSource getDataSourceDev()
    {
        Properties dsProps = new Properties();
        dsProps.put("url", appConfig.getDatasourceUrlDev());
        dsProps.put("user", appConfig.getDatasourceUsernameDev());
        dsProps.put("password", appConfig.getDatasourcePasswordDev());

        Properties configProps = new Properties();
        configProps.put("maximumPoolSize",appConfig.getHikariMaximumPoolSize());
        configProps.put("connectionTimeout", appConfig.getHikariConnectionTimeout());
        configProps.put("idleTimeout", appConfig.getHikariIdleTimeout());
        configProps.put("dataSourceProperties", dsProps);
        configProps.put( "jdbcUrl", appConfig.getDatasourceUrlDev());

        final HikariConfig hikariConfig = new HikariConfig( configProps );
        return new HikariDataSource( hikariConfig );

    }

    @Bean( "DataSource" )
    @Profile( {"production", "!dev"} )
    public DataSource getDataSourceProduction()
    {
        Properties dsProps = new Properties();
        dsProps.put("url", appConfig.getDatasourceUrlProduction());
        dsProps.put("user", appConfig.getDatasourceUsernameProduction());
        dsProps.put("password", appConfig.getDatasourcePasswordProduction());

        Properties configProps = new Properties();
        configProps.put("maximumPoolSize",appConfig.getHikariMaximumPoolSize());
        configProps.put("connectionTimeout", appConfig.getHikariConnectionTimeout());
        configProps.put("idleTimeout", appConfig.getHikariIdleTimeout());
        configProps.put("dataSourceProperties", dsProps);
        configProps.put( "jdbcUrl", appConfig.getDatasourceUrlProduction());

        final HikariConfig hikariConfig = new HikariConfig( configProps );
        return new HikariDataSource( hikariConfig );
    }
}
