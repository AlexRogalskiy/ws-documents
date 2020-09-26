package com.sensiblemetrics.api.ws.document.generator.configuration;

import com.sensiblemetrics.api.ws.document.generator.metrics.DataSourceStatusProbe;
import com.sensiblemetrics.api.ws.metrics.property.WsMetricsProperty;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.config.MeterFilter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.context.annotation.Role;

import javax.sql.DataSource;
import java.util.List;

import static com.sensiblemetrics.api.ws.document.generator.configuration.WsDocumentDataSourceConfiguration.DEFAULT_DATASOURCE_BEAN;

@Configuration
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
@Description("SensibleMetrics Document Web Service Actuator configuration")
public class WsDocumentActuatorConfiguration {

    @Bean
    @Description("Actuator datasource status probe bean")
    @ConditionalOnProperty(prefix = WsMetricsProperty.MeterProperty.METER_PROPERTY_PREFIX, name = "datasource")
    public DataSourceStatusProbe dataSourceStatusProbe(@Qualifier(DEFAULT_DATASOURCE_BEAN) final DataSource dataSource,
                                                       final WsMetricsProperty metricsProperty) {
        final WsMetricsProperty.MeterProperty meterProperty = metricsProperty.getMeters().get("datasource");
        final String name = meterProperty.getName();
        final String description = meterProperty.getDescription();
        final List<Tag> tags = meterProperty.getTags();
        return new DataSourceStatusProbe(dataSource, name, description, tags);
    }

    @Bean
    @Description("Actuator meter filter configuration bean")
    public MeterFilter excludeTomcatMeterFilter() {
        return MeterFilter.denyNameStartsWith("test");
    }
}
