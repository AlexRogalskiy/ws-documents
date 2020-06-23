package com.sensiblemetrics.api.ws.document.generator.configuration;

import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.SearchStrategy;
import org.springframework.boot.autoconfigure.jmx.ParentAwareNamingStrategy;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jmx.export.annotation.AnnotationJmxAttributeSource;
import org.springframework.jmx.export.naming.ObjectNamingStrategy;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaDialect;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.persistenceunit.DefaultPersistenceUnitManager;
import org.springframework.orm.jpa.persistenceunit.PersistenceUnitManager;
import org.springframework.orm.jpa.support.PersistenceAnnotationBeanPostProcessor;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;
import java.util.UUID;

import static org.apache.commons.lang3.StringUtils.join;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = {
                "com.sensiblemetrics.api.ws.document.generator.model",
                "com.sensiblemetrics.api.ws.document.generator.repository"
        }
)
@Description("SensibleMetrics Web Service DataSource configuration")
public class WsDataSourceConfiguration {

    /**
     * Default packages to scan
     */
    private static final String[] DEFAULT_PACKAGES_TO_SCAN = {
            "com.sensiblemetrics.api.ws.document.generator.model",
            "com.sensiblemetrics.api.ws.document.generator.repository"
    };

    /**
     * Default persistence unit name
     */
    private static final String DEFAULT_PERSISTENCE_UNIT_NAME = "local";
    /**
     * Default domain name prefix
     */
    private static final String DEFAULT_DOMAIN_NAME_PREFIX = "domain_";

    /**
     * Returns {@link JpaVendorAdapter}
     *
     * @return {@link JpaVendorAdapter}
     */
    @Bean
    @ConditionalOnMissingBean(type = "JpaVendorAdapter")
    @Description("DataSource hibernate JPA vendor bean")
    public JpaVendorAdapter jpaVendorAdapter() {
        return new HibernateJpaVendorAdapter();
    }

    /**
     * Returns {@link JpaDialect}
     *
     * @return {@link JpaDialect}
     */
    @Bean
    @ConditionalOnMissingBean(type = "JpaDialect")
    @Description("DataSource JPA dialect bean")
    public JpaDialect jpaDialect() {
        return new HibernateJpaDialect();
    }

    /**
     * Returns {@link PersistenceExceptionTranslationPostProcessor}
     *
     * @return {@link PersistenceExceptionTranslationPostProcessor}
     */
    @Bean
    @ConditionalOnMissingBean(type = "PersistenceExceptionTranslationPostProcessor")
    @Description("DataSource persistence exception translation post processor bean")
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    /**
     * Returns {@link PersistenceUnitManager} by input {@link DataSource} to configure by
     *
     * @param dataSource - initial input {@link DataSource} to configure by
     * @return {@link PersistenceUnitManager}
     */
    @Bean
    @ConditionalOnMissingBean(type = "PersistenceUnitManager")
    @Description("DataSource persistence unit manager bean")
    public PersistenceUnitManager persistenceUnitManager(final DataSource dataSource) {
        final DefaultPersistenceUnitManager manager = new DefaultPersistenceUnitManager();
        manager.setDefaultDataSource(dataSource);
        manager.setPackagesToScan(DEFAULT_PACKAGES_TO_SCAN);
        manager.setDefaultPersistenceUnitName(DEFAULT_PERSISTENCE_UNIT_NAME);
        return manager;
    }

    /**
     * Returns {@link HibernateTransactionManager} configuration by initial {@link LocalSessionFactoryBean} instance
     *
     * @param entityManagerFactory - initial {@link LocalSessionFactoryBean} instance
     * @return {@link HibernateTransactionManager} configuration
     */
    @Bean
    @Description("DataSource Hibernate transaction manager bean")
    public HibernateTransactionManager transactionManager(final LocalSessionFactoryBean entityManagerFactory) {
        final HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(entityManagerFactory.getObject());
        return transactionManager;
    }

    /**
     * Returns {@link BeanPostProcessor}
     *
     * @return {@link BeanPostProcessor}
     */
    @Bean
    @Description("DataSource persistence annotation post processor bean")
    public BeanPostProcessor postProcessor() {
        final PersistenceAnnotationBeanPostProcessor postProcessor = new PersistenceAnnotationBeanPostProcessor();
        postProcessor.setDefaultPersistenceUnitName(DEFAULT_PERSISTENCE_UNIT_NAME);
        return postProcessor;
    }

    /**
     * Returns {@link ParentAwareNamingStrategy} configuration
     *
     * @return {@link ParentAwareNamingStrategy} configuration
     */
    @Bean
    @ConditionalOnMissingBean(value = ObjectNamingStrategy.class, search = SearchStrategy.CURRENT)
    public ParentAwareNamingStrategy objectNamingStrategy() {
        final ParentAwareNamingStrategy namingStrategy = new ParentAwareNamingStrategy(new AnnotationJmxAttributeSource());
        namingStrategy.setDefaultDomain(join(DEFAULT_DOMAIN_NAME_PREFIX, UUID.randomUUID().toString()));
        return namingStrategy;
    }

    @Bean("entityManagerFactory")
    @Description("DataSource local session factory bean")
    public LocalSessionFactoryBean sessionFactory(final DataSource dataSource,
                                                  final JpaProperties jpaProperties) throws IOException {
        final LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setPackagesToScan(DEFAULT_PACKAGES_TO_SCAN);

        final Properties properties = new Properties();
        properties.putAll(jpaProperties.getProperties());
        sessionFactory.setHibernateProperties(properties);
        sessionFactory.afterPropertiesSet();
        return sessionFactory;
    }
}
