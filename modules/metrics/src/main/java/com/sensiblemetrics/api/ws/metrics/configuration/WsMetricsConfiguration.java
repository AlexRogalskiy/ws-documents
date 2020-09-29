package com.sensiblemetrics.api.ws.metrics.configuration;

import com.sensiblemetrics.api.ws.commons.helper.OptionalConsumer;
import com.sensiblemetrics.api.ws.metrics.aspect.MonitoringTimeAspect;
import com.sensiblemetrics.api.ws.metrics.aspect.TrackingTimeAspect;
import com.sensiblemetrics.api.ws.metrics.meter.DataSourceStatusMeterBinder;
import com.sensiblemetrics.api.ws.metrics.property.WsMetricsProperty;
import io.github.mweirauch.micrometer.jvm.extras.ProcessMemoryMetrics;
import io.github.mweirauch.micrometer.jvm.extras.ProcessThreadMetrics;
import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.LongTaskTimer;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.binder.MeterBinder;
import io.micrometer.core.instrument.config.MeterFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.boot.actuate.metrics.web.servlet.WebMvcTagsContributor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.*;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Configuration
@EnableAspectJAutoProxy
@Import({WsMetricsConfigurerAdapter.class, WsMetricsHealthStatusExporterConfiguration.class})
@ConditionalOnProperty(prefix = WsMetricsProperty.PROPERTY_PREFIX, value = "enabled", havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties(WsMetricsProperty.class)
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
@Description("SensibleMetrics Web Service Metrics configuration")
public abstract class WsMetricsConfiguration {
    /**
     * Default metrics bean naming conventions
     */
    public static final String MEMORY_METRICS_METER_BINDER_BEAN_NAME = "processMemoryMetrics";
    public static final String PROCESS_THREAD_METER_BINDER_BEAN_NAME = "processThreadMetrics";
    public static final String METER_REGISTRY_COMMON_TAGS_CUSTOMIZER_BEAN_NAME = "metricsCommonTagsCustomizer";
    public static final String METER_REGISTRY_NAMING_CONVENTION_CUSTOMIZER_BEAN_NAME = "namingConventionCustomizer";
    public static final String METER_REGISTRY_WEB_MVC_TAGS_CONTRIBUTOR_BEAN_NAME = "webMvcTagsContributor";
    public static final String METER_REGISTRY_DATASOURCE_METER_PROBE_BEAN_NAME = "dataSourceStatusProbe";
    public static final String METER_REGISTRY_TIMED_ASPECT_BEAN_NAME = "timedAspect";
    public static final String METER_REGISTRY_MONITORING_TIME_ASPECT_BEAN_NAME = "monitoringTimeAspect";
    public static final String METER_REGISTRY_TRACKING_TIME_ASPECT_BEAN_NAME = "trackingTimeAspect";
    public static final String METER_REGISTRY_INCLUDE_FILTER_BEAN_NAME = "includeMeterFilter";
    public static final String METER_REGISTRY_EXCLUDE_FILTER_BEAN_NAME = "excludeMeterFilter";

    @Bean(MEMORY_METRICS_METER_BINDER_BEAN_NAME)
    @ConditionalOnMissingBean(name = MEMORY_METRICS_METER_BINDER_BEAN_NAME)
    @ConditionalOnClass(ProcessMemoryMetrics.class)
    @Description("Process memory metrics configuration bean")
    public MeterBinder processMemoryMetrics() {
        return new ProcessMemoryMetrics();
    }

    @Bean(PROCESS_THREAD_METER_BINDER_BEAN_NAME)
    @ConditionalOnMissingBean(name = PROCESS_THREAD_METER_BINDER_BEAN_NAME)
    @ConditionalOnClass(ProcessThreadMetrics.class)
    @Description("Process thread metrics configuration bean")
    public MeterBinder processThreadMetrics() {
        return new ProcessThreadMetrics();
    }

    @Bean(METER_REGISTRY_NAMING_CONVENTION_CUSTOMIZER_BEAN_NAME)
    @ConditionalOnMissingBean(name = METER_REGISTRY_NAMING_CONVENTION_CUSTOMIZER_BEAN_NAME)
    @Description("Metrics naming convention customizer bean")
    public MeterRegistryCustomizer<MeterRegistry> metricsNamingConventionCustomizer(final WsMetricsProperty metricsProperty) {
        return registry -> registry.config().namingConvention(metricsProperty.getNamingConvention());
    }

    @Bean(METER_REGISTRY_COMMON_TAGS_CUSTOMIZER_BEAN_NAME)
    @ConditionalOnMissingBean(name = METER_REGISTRY_COMMON_TAGS_CUSTOMIZER_BEAN_NAME)
    @Description("Metrics common tags customizer bean")
    public MeterRegistryCustomizer<MeterRegistry> metricsCommonTagsCustomizer(final WsMetricsProperty metricsProperty) {
        return registry -> OptionalConsumer.of(metricsProperty.getDefaults().getTags())
                .ifPresent(value -> registry.config().commonTags(value))
                .ifNotPresent(() -> registry.config().commonTags(metricsProperty.getDefaults().getSimpleTagsAsArray()));
    }

    @Bean(METER_REGISTRY_WEB_MVC_TAGS_CONTRIBUTOR_BEAN_NAME)
    @ConditionalOnMissingBean(name = METER_REGISTRY_WEB_MVC_TAGS_CONTRIBUTOR_BEAN_NAME)
    @Description("Metrics Web MVC tags contributor bean")
    public WebMvcTagsContributor webMvcTagsContributor(final WsMetricsProperty metricsProperty) {
        return new CustomWebMvcTagsContributor(metricsProperty);
    }

    @Bean(METER_REGISTRY_TIMED_ASPECT_BEAN_NAME)
    @ConditionalOnMissingBean(name = METER_REGISTRY_TIMED_ASPECT_BEAN_NAME)
    @Description("Metrics timed aspect bean")
    public TimedAspect timedAspect(final MeterRegistry registry) {
        return new TimedAspect(registry);
    }

    @Bean(METER_REGISTRY_MONITORING_TIME_ASPECT_BEAN_NAME)
    @ConditionalOnMissingBean(name = METER_REGISTRY_MONITORING_TIME_ASPECT_BEAN_NAME)
    @Description("Metrics monitoring time aspect bean")
    @ConditionalOnProperty(prefix = WsMetricsProperty.Handlers.MONITORING_TIME_PROPERTY_PREFIX, value = "enabled", havingValue = "true")
    public MonitoringTimeAspect monitoringTimeAspect(final MeterRegistry registry) {
        return new MonitoringTimeAspect(registry);
    }

    @Bean(METER_REGISTRY_TRACKING_TIME_ASPECT_BEAN_NAME)
    @ConditionalOnMissingBean(name = METER_REGISTRY_TRACKING_TIME_ASPECT_BEAN_NAME)
    @Description("Tracking time aspect bean")
    @ConditionalOnProperty(prefix = WsMetricsProperty.Handlers.TRACKING_TIME_PROPERTY_PREFIX, value = "enabled", havingValue = "true")
    public TrackingTimeAspect trackingTimeAspect() {
        return new TrackingTimeAspect();
    }

    @Bean(METER_REGISTRY_INCLUDE_FILTER_BEAN_NAME)
    @ConditionalOnMissingBean(name = METER_REGISTRY_INCLUDE_FILTER_BEAN_NAME)
    @Description("Actuator include meter filter configuration bean")
    public MeterFilter includeMeterFilter(final WsMetricsConfigurerAdapter configurerAdapter,
                                          final WsMetricsProperty metricsProperty) {
        return MeterFilter.accept(configurerAdapter.createMeterTagPredicate(metricsProperty.getPatterns().getInclude()));
    }

    @Bean(METER_REGISTRY_EXCLUDE_FILTER_BEAN_NAME)
    @ConditionalOnMissingBean(name = METER_REGISTRY_EXCLUDE_FILTER_BEAN_NAME)
    @Description("Actuator exclude meter filter configuration bean")
    public MeterFilter excludeMeterFilter(final WsMetricsConfigurerAdapter configurerAdapter,
                                          final WsMetricsProperty metricsProperty) {
        return MeterFilter.deny(configurerAdapter.createMeterTagPredicate(metricsProperty.getPatterns().getExclude()));
    }

    @Bean(METER_REGISTRY_DATASOURCE_METER_PROBE_BEAN_NAME)
    @ConditionalOnMissingBean(name = METER_REGISTRY_DATASOURCE_METER_PROBE_BEAN_NAME)
    @Description("Actuator datasource status probe configuration bean")
    @ConditionalOnProperty(prefix = WsMetricsProperty.MeterProperty.METER_PROPERTY_PREFIX, name = "datasource")
    public Function<DataSource, DataSourceStatusMeterBinder> dataSourceStatusProbe(final WsMetricsProperty metricsProperty) {
        return dataSource -> {
            final WsMetricsProperty.MeterProperty meterProperty = metricsProperty.getMeters().get("datasource");
            final String name = meterProperty.getName();
            final String description = meterProperty.getDescription();
            final List<Tag> tags = meterProperty.getTags();
            return new DataSourceStatusMeterBinder(dataSource, name, description, tags);
        };
    }

    @RequiredArgsConstructor
    public static class CustomWebMvcTagsContributor implements WebMvcTagsContributor {
        private final WsMetricsProperty metricsProperty;

        /**
         * Provides tags to be associated with metrics for the given {@code request} and
         * {@code response} exchange.
         *
         * @param request   the request
         * @param response  the response
         * @param handler   the handler for the request or {@code null} if the handler is
         *                  unknown
         * @param exception the current exception, if any
         * @return tags to associate with metrics for the request and response exchange
         */
        @Override
        @SuppressWarnings("unchecked")
        public Iterable<Tag> getTags(final HttpServletRequest request,
                                     final HttpServletResponse response,
                                     final Object handler,
                                     final Throwable exception) {
            return Optional.ofNullable(request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE))
                    .map(v -> (Map<String, String>) v)
                    .map(Map::entrySet)
                    .map(Collection::stream)
                    .map(this.mapToList())
                    .orElseGet(Collections::emptyList);
        }

        /**
         * Returns {@link Function} mapper of {@link Stream} of {@link Map.Entry} to {@link List} of {@link Tag}s
         *
         * @return {@link List} of {@link Tag}s
         */
        private Function<Stream<Map.Entry<String, String>>, List<Tag>> mapToList() {
            return entry -> entry.map(v -> Tag.of(v.getKey(), v.getValue())).collect(Collectors.toList());
        }

        /**
         * Provides tags to be used by {@link LongTaskTimer long task timers}.
         *
         * @param request the HTTP request
         * @param handler the handler for the request or {@code null} if the handler is
         *                unknown
         * @return tags to associate with metrics recorded for the request
         */
        @Override
        public Iterable<Tag> getLongRequestTags(final HttpServletRequest request,
                                                final Object handler) {
            return this.metricsProperty.getDefaults().getLongTaskTags();
        }
    }
}
