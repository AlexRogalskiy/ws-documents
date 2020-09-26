package com.sensiblemetrics.api.ws.document.generator.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.SearchStrategy;
import org.springframework.boot.autoconfigure.jmx.ParentAwareNamingStrategy;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.context.annotation.Role;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jmx.export.annotation.AnnotationJmxAttributeSource;
import org.springframework.jmx.export.naming.ObjectNamingStrategy;
import org.springframework.orm.jpa.JpaDialect;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.persistenceunit.DefaultPersistenceUnitManager;
import org.springframework.orm.jpa.persistenceunit.PersistenceUnitManager;
import org.springframework.orm.jpa.support.PersistenceAnnotationBeanPostProcessor;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.UUID;

import static org.apache.commons.lang3.StringUtils.join;
import static org.springframework.util.StringUtils.arrayToDelimitedString;

@Slf4j
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories("com.sensiblemetrics.api.ws.document.generator.repository")
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
@Description("SensibleMetrics Document Web Service DataSource configuration")
public class WsDocumentDataSourceConfiguration {
    /**
     * Default bean naming conventions
     */
    public static final String DEFAULT_DATASOURCE_BEAN = "dataSource";
    public static final String DEFAULT_TRANSACTION_MANAGER_BEAN = "transactionManager";
    public static final String DEFAULT_ENTITY_MANAGER_FACTORY_BEAN = "entityManagerFactory";

    /**
     * Default packages to scan
     */
    private static final String[] DEFAULT_PACKAGES_TO_SCAN = {
            "com.sensiblemetrics.api.ws.document.generator.model.entity"
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
    @ConditionalOnMissingBean(value = JpaVendorAdapter.class)
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
    @ConditionalOnMissingBean(value = JpaDialect.class)
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
    @ConditionalOnMissingBean(value = PersistenceExceptionTranslationPostProcessor.class)
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
    @ConditionalOnMissingBean(value = PersistenceUnitManager.class)
    @Description("DataSource persistence unit manager bean")
    public PersistenceUnitManager persistenceUnitManager(@Qualifier(DEFAULT_DATASOURCE_BEAN) final DataSource dataSource) {
        final DefaultPersistenceUnitManager manager = new DefaultPersistenceUnitManager();
        manager.setDefaultDataSource(dataSource);
        manager.setPackagesToScan(DEFAULT_PACKAGES_TO_SCAN);
        manager.setDefaultPersistenceUnitName(DEFAULT_PERSISTENCE_UNIT_NAME);
        return manager;
    }

    @Bean(DEFAULT_TRANSACTION_MANAGER_BEAN)
    @Description("DataSource Jpa transaction manager bean")
    public PlatformTransactionManager transactionManager(@Qualifier(DEFAULT_ENTITY_MANAGER_FACTORY_BEAN) final EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
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
    @Description("DataSource object naming strategy bean")
    public ParentAwareNamingStrategy objectNamingStrategy() {
        final ParentAwareNamingStrategy namingStrategy = new ParentAwareNamingStrategy(new AnnotationJmxAttributeSource());
        namingStrategy.setDefaultDomain(join(DEFAULT_DOMAIN_NAME_PREFIX, UUID.randomUUID().toString()));
        return namingStrategy;
    }

    @Bean(DEFAULT_ENTITY_MANAGER_FACTORY_BEAN)
    @Description("DataSource entity manager factory bean")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(final EntityManagerFactoryBuilder builder,
                                                                       final JpaProperties jpaProperties,
                                                                       @Qualifier(DEFAULT_DATASOURCE_BEAN) final DataSource dataSource) {
        log.info(
                "DB [{}] <Default> with JPA properties: \n\n{}\n",
                jpaProperties.getDatabase(),
                arrayToDelimitedString(jpaProperties.getProperties().entrySet().toArray(), System.lineSeparator())
        );
        return builder
                .dataSource(dataSource)
                .properties(jpaProperties.getProperties())
                .packages(DEFAULT_PACKAGES_TO_SCAN)
                .persistenceUnit(DEFAULT_PERSISTENCE_UNIT_NAME)
                .build();
    }
}
