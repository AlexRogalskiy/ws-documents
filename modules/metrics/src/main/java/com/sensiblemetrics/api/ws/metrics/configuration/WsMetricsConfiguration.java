package com.sensiblemetrics.api.ws.metrics.configuration;

import com.sensiblemetrics.api.ws.commons.helper.OptionalConsumer;
import com.sensiblemetrics.api.ws.metrics.aspect.TrackingTimeAspect;
import com.sensiblemetrics.api.ws.metrics.property.WsMetricsProperty;
import io.github.mweirauch.micrometer.jvm.extras.ProcessMemoryMetrics;
import io.github.mweirauch.micrometer.jvm.extras.ProcessThreadMetrics;
import io.micrometer.core.instrument.ImmutableTag;
import io.micrometer.core.instrument.LongTaskTimer;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.binder.MeterBinder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.boot.actuate.metrics.web.servlet.WebMvcTagsContributor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.*;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
@Import(TrackingTimeAspect.class)
@ConditionalOnProperty(prefix = WsMetricsProperty.PROPERTY_PREFIX, value = "enabled", havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties(WsMetricsProperty.class)
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
@Description("SensibleMetrics Commons Web Service Metrics configuration")
public abstract class WsMetricsConfiguration {
    /**
     * Default metrics bean naming conventions
     */
    public static final String MEMORY_METRICS_METER_BINDER_BEAN_NAME = "processMemoryMetrics";
    public static final String THREAD_METER_BINDER_BEAN_NAME = "processThreadMetrics";
    public static final String METER_REGISTRY_COMMON_TAGS_CUSTOMIZER_BEAN_NAME = "metricsCommonTagsCustomizer";
    public static final String METER_REGISTRY_NAMING_CONVENTION_CUSTOMIZER_BEAN_NAME = "namingConventionCustomizer";
    public static final String METER_REGISTRY_WEB_MVC_TAGS_CONTRIBUTOR_BEAN_NAME = "webMvcTagsContributor";

    @Bean(MEMORY_METRICS_METER_BINDER_BEAN_NAME)
    @ConditionalOnClass(ProcessMemoryMetrics.class)
    @Description("Process memory metrics configuration bean")
    public MeterBinder processMemoryMetrics() {
        return new ProcessMemoryMetrics();
    }

    @Bean(THREAD_METER_BINDER_BEAN_NAME)
    @ConditionalOnClass(ProcessThreadMetrics.class)
    @Description("Process thread metrics configuration bean")
    public MeterBinder processThreadMetrics() {
        return new ProcessThreadMetrics();
    }

    @Bean(METER_REGISTRY_NAMING_CONVENTION_CUSTOMIZER_BEAN_NAME)
    @ConditionalOnBean(WsMetricsProperty.class)
    @Description("Metrics naming convention customizer bean")
    public MeterRegistryCustomizer<MeterRegistry> metricsNamingConventionCustomizer(final WsMetricsProperty metricsProperty) {
        return registry -> registry.config().namingConvention(metricsProperty.getNamingConvention());
    }

    @Bean(METER_REGISTRY_COMMON_TAGS_CUSTOMIZER_BEAN_NAME)
    @ConditionalOnBean(WsMetricsProperty.class)
    @Description("Metrics common tags customizer bean")
    public MeterRegistryCustomizer<MeterRegistry> metricsCommonTagsCustomizer(final WsMetricsProperty metricsProperty) {
        return registry -> OptionalConsumer.of(metricsProperty.getDefaults().getTags())
                .ifPresent(value -> registry.config().commonTags(value))
                .ifNotPresent(() -> registry.config().commonTags(metricsProperty.getDefaults().getSimpleTags().toArray(new String[0])));
    }

    @Bean(METER_REGISTRY_WEB_MVC_TAGS_CONTRIBUTOR_BEAN_NAME)
    @ConditionalOnBean(WsMetricsProperty.class)
    @Description("Metrics Web MVC tags contributor bean")
    public WebMvcTagsContributor webMvcTagsContributor(final WsMetricsProperty metricsProperty) {
        return new CustomWebMvcTagsContributor(metricsProperty);
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
            return ((Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE))
                    .entrySet()
                    .stream()
                    .map(entry -> new ImmutableTag(entry.getKey(), entry.getValue()))
                    .collect(Collectors.toList());
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
