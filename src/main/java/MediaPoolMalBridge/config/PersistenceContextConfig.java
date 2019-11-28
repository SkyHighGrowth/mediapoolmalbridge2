package MediaPoolMalBridge.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
class PersistenceContextConfig {

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(final DataSource dataSource,
                                                                          final AppConfig appConfig) {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource);
        entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        entityManagerFactoryBean.setPackagesToScan("MediaPoolMalBridge.persistence");

        Properties jpaProperties = new Properties();

        jpaProperties.put("hibernate.dialect", appConfig.getHibernateDialect());
        jpaProperties.put("hibernate.hbm2ddl.auto", appConfig.getHibernateHbm2dlAuto() );

        jpaProperties.put("hibernate.show_sql", appConfig.isHibernateShowSql());
        jpaProperties.put("hibernate.format_sql", true);

        entityManagerFactoryBean.setJpaProperties(jpaProperties);

        return entityManagerFactoryBean;
    }
}