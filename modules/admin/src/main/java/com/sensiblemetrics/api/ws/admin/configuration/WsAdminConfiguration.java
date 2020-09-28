package com.sensiblemetrics.api.ws.admin.configuration;

import com.sensiblemetrics.api.ws.admin.property.WsAdminProperty;
import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
import de.codecentric.boot.admin.server.notify.CompositeNotifier;
import de.codecentric.boot.admin.server.notify.LoggingNotifier;
import de.codecentric.boot.admin.server.notify.Notifier;
import de.codecentric.boot.admin.server.notify.RemindingNotifier;
import de.codecentric.boot.admin.server.notify.filter.FilteringNotifier;
import io.github.mweirauch.micrometer.jvm.extras.ProcessThreadMetrics;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.context.annotation.Role;

import java.time.Duration;
import java.util.Collections;
import java.util.List;

@Slf4j
@Configuration
@ConditionalOnProperty(prefix = WsAdminProperty.PROPERTY_PREFIX, value = "enabled", havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties(WsAdminProperty.class)
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
@Description("SensibleMetrics Web Service Admin configuration")
public abstract class WsAdminConfiguration {

    @Configuration
    @RequiredArgsConstructor
    @ConditionalOnProperty(prefix = WsAdminProperty.Handlers.NOTIFICATION_PROPERTY_PREFIX, value = "enabled", havingValue = "true", matchIfMissing = true)
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    @Description("SensibleMetrics Web Service Admin Notifier configuration")
    public static class AdminNotificationConfiguration {
        /**
         * Default admin bean naming conventions
         */
        public static final String ADMIN_LOGGING_NOTIFIER_BEAN_NAME = "loggingNotifier";
        public static final String ADMIN_FILTERING_NOTIFIER_BEAN_NAME = "filteringNotifier";
        public static final String ADMIN_REMINDING_NOTIFIER_BEAN_NAME = "remindingNotifier";

        private final InstanceRepository repository;
        private final ObjectProvider<List<Notifier>> otherNotifiers;

        @Bean(ADMIN_LOGGING_NOTIFIER_BEAN_NAME)
        @ConditionalOnClass(ProcessThreadMetrics.class)
        @Description("Admin logging notifier configuration bean")
        public LoggingNotifier loggerNotifier() {
            return new LoggingNotifier(this.repository);
        }

        @Bean(ADMIN_FILTERING_NOTIFIER_BEAN_NAME)
        @ConditionalOnClass(ProcessThreadMetrics.class)
        @Description("Admin filtering notifier configuration bean")
        public FilteringNotifier filteringNotifier() {
            final CompositeNotifier delegate = new CompositeNotifier(this.otherNotifiers.getIfAvailable(Collections::emptyList));
            return new FilteringNotifier(delegate, this.repository);
        }


        @Bean(value = ADMIN_REMINDING_NOTIFIER_BEAN_NAME, initMethod = "start", destroyMethod = "stop")
        @ConditionalOnBean(FilteringNotifier.class)
        @Description("Admin reminding notifier configuration bean")
        public RemindingNotifier remindingNotifier(final FilteringNotifier filteringNotifier) {
            final RemindingNotifier notifier = new RemindingNotifier(filteringNotifier, this.repository);
            notifier.setReminderPeriod(Duration.ofMinutes(10));
            notifier.setCheckReminderInverval(Duration.ofSeconds(10));
            return notifier;
        }
    }
}
