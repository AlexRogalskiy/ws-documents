package com.sensiblemetrics.api.ws.commons.configuration;

import com.sensiblemetrics.api.ws.commons.annotation.ConditionalOnMissingBean;
import com.sensiblemetrics.api.ws.commons.aspect.LoggingAspect;
import com.sensiblemetrics.api.ws.commons.aspect.TrackingTimeAspect;
import com.sensiblemetrics.api.ws.commons.property.WsLoggingProperty;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.*;

@Configuration
@Import({
        LoggingAspect.class,
        TrackingTimeAspect.class
})
@EnableAspectJAutoProxy(proxyTargetClass = true)
@ConditionalOnProperty(prefix = WsLoggingProperty.PROPERTY_PREFIX, value = "enabled", havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties(WsLoggingProperty.class)
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
@Description("SensibleMetrics Commons Web Service Logging configuration")
public abstract class WsLoggingConfiguration {

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnBean(WsLoggingProperty.class)
    @ConditionalOnClass(LogHeadersToMdcFilter.class)
    @ConditionalOnProperty(prefix = WsLoggingProperty.Headers.PROPERTY_PREFIX, name = "enabled", havingValue = "true")
    @Description("MDC filter headers logging configuration bean")
    public LogHeadersToMdcFilter logHeadersToMDCFilter(final WsLoggingProperty property) {
        return new LogHeadersToMdcFilter(property);
    }
}
