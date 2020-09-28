package com.sensiblemetrics.api.ws.caching.configuration;

import com.sensiblemetrics.api.ws.caching.property.WsCachingProperty;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.context.annotation.Role;

@Configuration
@EnableConfigurationProperties(WsCachingProperty.class)
@ConditionalOnProperty(prefix = WsCachingProperty.PROPERTY_PREFIX, name = "enabled", havingValue = "true", matchIfMissing = true)
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
@Description("SensibleMetrics Commons Web Service Caching configuration")
public abstract class WsCachingConfiguration {
}
