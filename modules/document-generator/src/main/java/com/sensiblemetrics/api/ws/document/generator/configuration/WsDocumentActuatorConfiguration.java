package com.sensiblemetrics.api.ws.document.generator.configuration;

import com.sensiblemetrics.api.ws.document.generator.metrics.DataSourceStatusProbe;
import io.micrometer.core.instrument.config.MeterFilter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.context.annotation.Role;

import javax.sql.DataSource;

import static com.sensiblemetrics.api.ws.document.generator.configuration.WsDocumentDataSourceConfiguration.DEFAULT_DATASOURCE_BEAN;

@Configuration
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
@Description("SensibleMetrics Document Web Service Actuator configuration")
public class WsDocumentActuatorConfiguration {

    @Bean
    @Description("Actuator datasource status probe bean")
    public DataSourceStatusProbe dataSourceStatusProbe(@Qualifier(DEFAULT_DATASOURCE_BEAN) final DataSource dataSource) {
        return new DataSourceStatusProbe(dataSource);
    }

    @Bean
    @Description("Actuator meter filter configuration bean")
    public MeterFilter excludeTomcatFilter() {
        return MeterFilter.denyNameStartsWith("test");
    }
}
