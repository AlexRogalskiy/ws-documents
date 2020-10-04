package com.sensiblemetrics.api.ws.admin.configuration;

import com.sensiblemetrics.api.ws.admin.property.WsAdminServerProperty;
import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
import de.codecentric.boot.admin.server.notify.CompositeNotifier;
import de.codecentric.boot.admin.server.notify.LoggingNotifier;
import de.codecentric.boot.admin.server.notify.Notifier;
import de.codecentric.boot.admin.server.notify.RemindingNotifier;
import de.codecentric.boot.admin.server.notify.filter.FilteringNotifier;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.context.annotation.Role;

import java.time.Duration;
import java.util.Collections;
import java.util.List;

import static com.sensiblemetrics.api.ws.commons.property.PropertySettings.DEFAULT_PROPERTY_DELIMITER;

@Configuration
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = WsAdminServerProperty.Handlers.PROPERTY_PREFIX, value = "enabled", havingValue = "true")
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
@Description("SensibleMetrics WebDocs Admin Server Notifier configuration")
public abstract class WsAdminServerNotifierConfiguration {
    /**
     * Default bean naming conventions
     */
    public static final String LOGGING_NOTIFIER_BEAN_NAME = "loggingNotifier";
    public static final String FILTERING_NOTIFIER_BEAN_NAME = "filteringNotifier";
    public static final String REMINDING_NOTIFIER_BEAN_NAME = "remindingNotifier";
    public static final String METRICS_NOTIFIER_BEAN_NAME = "metricsNotifier";

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnProperty(prefix = LoggingNotifierConfiguration.PROPERTY_PREFIX, value = "enabled", havingValue = "true")
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    @Description("Admin Server logging notifier configuration")
    public static class LoggingNotifierConfiguration {
        /**
         * Default property prefix
         */
        public static final String PROPERTY_PREFIX = WsAdminServerProperty.Handlers.PROPERTY_PREFIX + DEFAULT_PROPERTY_DELIMITER + "logging-notifier";

        @Bean(LOGGING_NOTIFIER_BEAN_NAME)
        @ConditionalOnMissingBean(name = LOGGING_NOTIFIER_BEAN_NAME)
        @Description("Logging notifier configuration bean")
        public LoggingNotifier loggingNotifier(final InstanceRepository repository) {
            return new LoggingNotifier(repository);
        }
    }

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnProperty(prefix = FilteringNotifierConfiguration.PROPERTY_PREFIX, value = "enabled", havingValue = "true")
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    @Description("Admin Server filtering notifier configuration")
    public static class FilteringNotifierConfiguration {
        /**
         * Default property prefix
         */
        public static final String PROPERTY_PREFIX = WsAdminServerProperty.Handlers.PROPERTY_PREFIX + DEFAULT_PROPERTY_DELIMITER + "filtering-notifier";

        @Bean(FILTERING_NOTIFIER_BEAN_NAME)
        @ConditionalOnMissingBean(name = FILTERING_NOTIFIER_BEAN_NAME)
        @Description("Filtering notifier configuration bean")
        public FilteringNotifier filteringNotifier(final ObjectProvider<List<Notifier>> otherNotifiers,
                                                   final InstanceRepository repository) {
            final CompositeNotifier delegate = new CompositeNotifier(otherNotifiers.getIfAvailable(Collections::emptyList));
            return new FilteringNotifier(delegate, repository);
        }
    }

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnProperty(prefix = RemindingNotifierConfiguration.PROPERTY_PREFIX, value = "enabled", havingValue = "true")
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    @Description("Admin Server reminding notifier configuration")
    public static class RemindingNotifierConfiguration {
        /**
         * Default property prefix
         */
        public static final String PROPERTY_PREFIX = WsAdminServerProperty.Handlers.PROPERTY_PREFIX + DEFAULT_PROPERTY_DELIMITER + "reminding-notifier";

        @Bean(value = REMINDING_NOTIFIER_BEAN_NAME, initMethod = "start", destroyMethod = "stop")
        @ConditionalOnBean(FilteringNotifier.class)
        @ConditionalOnMissingBean(name = REMINDING_NOTIFIER_BEAN_NAME)
        @Description("Reminding notifier configuration bean")
        public RemindingNotifier remindingNotifier(final FilteringNotifier filteringNotifier,
                                                   final InstanceRepository repository) {
            final RemindingNotifier notifier = new RemindingNotifier(filteringNotifier, repository);
            notifier.setReminderPeriod(Duration.ofMinutes(10));
            notifier.setCheckReminderInverval(Duration.ofSeconds(10));
            return notifier;
        }
    }

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnProperty(prefix = RemindingNotifierConfiguration.PROPERTY_PREFIX, value = "enabled", havingValue = "true")
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    @Description("Admin Server metrics notifier configuration")
    public static class MetricsNotifierConfiguration {
        /**
         * Default property prefix
         */
        public static final String PROPERTY_PREFIX = WsAdminServerProperty.Handlers.PROPERTY_PREFIX + DEFAULT_PROPERTY_DELIMITER + "metrics-notifier";

        @Bean(value = METRICS_NOTIFIER_BEAN_NAME)
        @ConditionalOnMissingBean(name = METRICS_NOTIFIER_BEAN_NAME)
        @Description("Metrics notifier configuration bean")
        public Notifier metricsNotifier(final InstanceRepository repository) {
            return new MetricsNotifier(repository);
        }
    }
}
