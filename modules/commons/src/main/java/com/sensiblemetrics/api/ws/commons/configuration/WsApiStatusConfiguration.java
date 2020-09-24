package com.sensiblemetrics.api.ws.commons.configuration;

import com.sensiblemetrics.api.ws.commons.indicator.GracefulShutdownHealthIndicator;
import com.sensiblemetrics.api.ws.commons.indicator.StatefulHealthIndicator;
import com.sensiblemetrics.api.ws.commons.indicator.TaskSchedulerHealthIndicator;
import com.sensiblemetrics.api.ws.commons.indicator.WsApiStatusInfoContributor;
import com.sensiblemetrics.api.ws.commons.property.WsApiStatusProperty;
import com.sensiblemetrics.api.ws.commons.property.WsGracefulShutdownProperty;
import com.sensiblemetrics.api.ws.commons.property.WsInfoServiceProperty;
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
        WsInfoServiceProperty.class
})
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
@Description("SensibleMetrics Commons Web Service Api status configuration")
public abstract class WsApiStatusConfiguration {

    @Bean
    @ConditionalOnBean(WsGracefulShutdownProperty.class)
    @ConditionalOnClass(GracefulShutdownHealthIndicator.class)
    @Description("Actuator graceful shutdown health indicator bean")
    @ConditionalOnProperty(prefix = WsGracefulShutdownProperty.PROPERTY_PREFIX, name = "enabled", havingValue = "true")
    public GracefulShutdownHealthIndicator gracefulShutdownHealthIndicator(final WsGracefulShutdownProperty property) {
        return new GracefulShutdownHealthIndicator(property);
    }

    @Bean
    @ConditionalOnBean(ThreadPoolTaskScheduler.class)
    @ConditionalOnClass(TaskSchedulerHealthIndicator.class)
    @Description("Actuator task scheduler health indicator bean")
    public TaskSchedulerHealthIndicator taskSchedulerHealthIndicator(final ThreadPoolTaskScheduler taskScheduler) {
        return new TaskSchedulerHealthIndicator(taskScheduler);
    }

    @Bean
    @ConditionalOnClass(StatefulHealthIndicator.class)
    @Description("Actuator stateful health indicator bean")
    public StatefulHealthIndicator statefulHealthIndicator() {
        return new StatefulHealthIndicator();
    }
}
