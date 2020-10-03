package com.sensiblemetrics.api.ws.admin.configuration;

import com.sensiblemetrics.api.ws.admin.property.WsAdminServerProperty;
import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
import de.codecentric.boot.admin.server.notify.CompositeNotifier;
import de.codecentric.boot.admin.server.notify.LoggingNotifier;
import de.codecentric.boot.admin.server.notify.Notifier;
import de.codecentric.boot.admin.server.notify.RemindingNotifier;
import de.codecentric.boot.admin.server.notify.filter.FilteringNotifier;
import io.github.mweirauch.micrometer.jvm.extras.ProcessThreadMetrics;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
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
     * Default admin bean naming conventions
     */
    public static final String ADMIN_LOGGING_NOTIFIER_BEAN_NAME = "loggingNotifier";
    public static final String ADMIN_FILTERING_NOTIFIER_BEAN_NAME = "filteringNotifier";
    public static final String ADMIN_REMINDING_NOTIFIER_BEAN_NAME = "remindingNotifier";

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnProperty(prefix = LoggingNotifierConfiguration.PROPERTY_PREFIX, value = "enabled", havingValue = "true")
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    @Description("Logging Admin Server notifier configuration")
    public static class LoggingNotifierConfiguration {
        /**
         * Default property prefix
         */
        public static final String PROPERTY_PREFIX = WsAdminServerProperty.Handlers.PROPERTY_PREFIX + DEFAULT_PROPERTY_DELIMITER + "logging-notifier";

        @Bean(ADMIN_LOGGING_NOTIFIER_BEAN_NAME)
        @ConditionalOnClass(ProcessThreadMetrics.class)
        @Description("Admin logging notifier configuration bean")
        public LoggingNotifier loggerNotifier(final InstanceRepository repository) {
            return new LoggingNotifier(repository);
        }
    }

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnProperty(prefix = FilteringNotifierConfiguration.PROPERTY_PREFIX, value = "enabled", havingValue = "true")
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    @Description("Filtering Admin Server notifier configuration")
    public static class FilteringNotifierConfiguration {
        /**
         * Default property prefix
         */
        public static final String PROPERTY_PREFIX = WsAdminServerProperty.Handlers.PROPERTY_PREFIX + DEFAULT_PROPERTY_DELIMITER + "filtering-notifier";

        @Bean(ADMIN_FILTERING_NOTIFIER_BEAN_NAME)
        @ConditionalOnClass(ProcessThreadMetrics.class)
        @Description("Admin filtering notifier configuration bean")
        public FilteringNotifier filteringNotifier(final ObjectProvider<List<Notifier>> otherNotifiers,
                                                   final InstanceRepository repository) {
            final CompositeNotifier delegate = new CompositeNotifier(otherNotifiers.getIfAvailable(Collections::emptyList));
            return new FilteringNotifier(delegate, repository);
        }
    }

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnProperty(prefix = RemindingNotifierConfiguration.PROPERTY_PREFIX, value = "enabled", havingValue = "true")
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    @Description("Reminding Admin Server notifier configuration")
    public static class RemindingNotifierConfiguration {
        /**
         * Default property prefix
         */
        public static final String PROPERTY_PREFIX = WsAdminServerProperty.Handlers.PROPERTY_PREFIX + DEFAULT_PROPERTY_DELIMITER + "reminding-notifier";

        @Bean(value = ADMIN_REMINDING_NOTIFIER_BEAN_NAME, initMethod = "start", destroyMethod = "stop")
        @ConditionalOnBean(FilteringNotifier.class)
        @Description("Admin reminding notifier configuration bean")
        public RemindingNotifier remindingNotifier(final FilteringNotifier filteringNotifier,
                                                   final InstanceRepository repository) {
            final RemindingNotifier notifier = new RemindingNotifier(filteringNotifier, repository);
            notifier.setReminderPeriod(Duration.ofMinutes(10));
            notifier.setCheckReminderInverval(Duration.ofSeconds(10));
            return notifier;
        }
    }
}
