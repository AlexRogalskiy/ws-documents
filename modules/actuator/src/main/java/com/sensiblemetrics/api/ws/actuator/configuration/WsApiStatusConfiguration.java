package com.sensiblemetrics.api.ws.actuator.configuration;

import com.sensiblemetrics.api.ws.actuator.indicator.WsGracefulShutdownHealthIndicator;
import com.sensiblemetrics.api.ws.actuator.indicator.WsStatefulHealthIndicator;
import com.sensiblemetrics.api.ws.actuator.indicator.WsTaskSchedulerHealthIndicator;
import com.sensiblemetrics.api.ws.actuator.indicator.WsApiStatusInfoContributor;
import com.sensiblemetrics.api.ws.actuator.property.WsApiStatusProperty;
import com.sensiblemetrics.api.ws.actuator.property.WsGracefulShutdownProperty;
import com.sensiblemetrics.api.ws.actuator.property.WsInfoApiStatusProperty;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.*;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
@Import(WsApiStatusInfoContributor.class)
@ConditionalOnProperty(prefix = WsApiStatusProperty.PROPERTY_PREFIX, value = "enabled", havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties({
        WsGracefulShutdownProperty.class,
        WsApiStatusProperty.class,
        WsInfoApiStatusProperty.class
})
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
@Description("SensibleMetrics WebDocs Api Status configuration")
public abstract class WsApiStatusConfiguration {

    @Bean
    @ConditionalOnBean(WsGracefulShutdownProperty.class)
    @ConditionalOnClass(WsGracefulShutdownHealthIndicator.class)
    @Description("Actuator graceful shutdown health indicator configuration bean")
    @ConditionalOnProperty(prefix = WsGracefulShutdownProperty.PROPERTY_PREFIX, name = "enabled", havingValue = "true")
    public WsGracefulShutdownHealthIndicator gracefulShutdownHealthIndicator(final WsGracefulShutdownProperty property) {
        return new WsGracefulShutdownHealthIndicator(property);
    }

    @Bean
    @ConditionalOnBean(ThreadPoolTaskScheduler.class)
    @ConditionalOnClass(WsTaskSchedulerHealthIndicator.class)
    @Description("Actuator task scheduler health indicator configuration bean")
    public WsTaskSchedulerHealthIndicator taskSchedulerHealthIndicator(final ThreadPoolTaskScheduler taskScheduler) {
        return new WsTaskSchedulerHealthIndicator(taskScheduler);
    }

    @Bean
    @ConditionalOnClass(WsStatefulHealthIndicator.class)
    @Description("Actuator stateful health indicator configuration bean")
    public WsStatefulHealthIndicator statefulHealthIndicator() {
        return new WsStatefulHealthIndicator();
    }
}
