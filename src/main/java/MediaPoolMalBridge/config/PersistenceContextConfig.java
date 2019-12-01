package MediaPoolMalBridge.config;

//@Configuration
//@EnableTransactionManagement
class PersistenceContextConfig {

    /*@Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(final DataSource dataSource,
                                                                       final AppConfig appConfig) {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource);
        entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        entityManagerFactoryBean.setPackagesToScan("MediaPoolMalBridge");

        Properties jpaProperties = new Properties();

        jpaProperties.put("hibernate.dialect", appConfig.getHibernateDialect());
        jpaProperties.put("hibernate.hbm2ddl.auto", appConfig.getHibernateHbm2dlAuto() );

        jpaProperties.put("hibernate.show_sql", appConfig.isHibernateShowSql());
        jpaProperties.put("hibernate.format_sql", true);

        entityManagerFactoryBean.setJpaProperties(jpaProperties);

        return entityManagerFactoryBean;
    }

    @Bean
    public PlatformTransactionManager transactionManager( final DataSource dataSource,
                                                          final AppConfig appConfig){
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory( entityManagerFactory( dataSource, appConfig ).getObject() );
        return transactionManager;
    }*/
}