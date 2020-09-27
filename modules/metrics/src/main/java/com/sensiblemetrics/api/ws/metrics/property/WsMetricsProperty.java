package com.sensiblemetrics.api.ws.metrics.property;

import com.google.common.collect.Lists;
import com.sensiblemetrics.api.ws.commons.constraint.NullOrNotBlank;
import com.sensiblemetrics.api.ws.commons.constraint.NullOrNotEmpty;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.config.NamingConvention;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Description;
import org.springframework.context.annotation.Role;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.sensiblemetrics.api.ws.commons.property.PropertySettings.DEFAULT_PROPERTY_DELIMITER;
import static com.sensiblemetrics.api.ws.commons.property.PropertySettings.DEFAULT_PROPERTY_PREFIX;

@Data
@Validated
@Accessors(chain = true)
@ConfigurationProperties(prefix = WsMetricsProperty.PROPERTY_PREFIX, ignoreInvalidFields = true)
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
@Description("SensibleMetrics Commons Web Service Metrics configuration properties")
public class WsMetricsProperty {
    /**
     * Default metrics property prefix
     */
    public static final String PROPERTY_PREFIX = DEFAULT_PROPERTY_PREFIX + DEFAULT_PROPERTY_DELIMITER + "metrics";
    /**
     * Default {@link List} of {@link String} simple common tags
     */
    private static final List<String> DEFAULT_SIMPLE_COMMON_TAGS = Lists.newArrayList("scope", "webdocs");

    /**
     * Default metrics handlers
     */
    @Valid
    @NestedConfigurationProperty
    @NotNull(message = "{property.metrics.handlers.notNull}")
    private Handlers handlers = new Handlers();

    /**
     * Default metrics patterns
     */
    @Valid
    @NestedConfigurationProperty
    @NotNull(message = "{property.metrics.patterns.notNull}")
    private Patterns patterns = new Patterns();

    /**
     * Default naming convention
     */
    @NotNull(message = "{property.metrics.naming-convention.notNull}")
    private NamingConvention namingConvention = NamingConvention.snakeCase;

    /**
     * Default {@link Map} collection of {@link MeterProperty} entries
     */
    @Valid
    @NullOrNotEmpty(message = "{property.metrics.meters.nullOrNotEmpty}")
    private Map<@NotBlank String, @NotNull MeterProperty> meters;

    /**
     * Default {@link MeterProperty} property
     */
    @Valid
    @NestedConfigurationProperty
    @NotNull(message = "{property.metrics.defaults.notNull}")
    private MeterProperty defaults = new MeterProperty();

    /**
     * MultiMeter configuration properties
     */
    @Data
    @Validated
    @Accessors(chain = true)
    public static class MeterProperty {
        /**
         * Default metrics property prefix
         */
        public static final String METER_PROPERTY_PREFIX = PROPERTY_PREFIX + DEFAULT_PROPERTY_DELIMITER + "meters";

        /**
         * Default name
         */
        @NullOrNotBlank(message = "{property.metrics.meter.name.nullOrNotBlank}")
        private String name;

        /**
         * Default description
         */
        @NullOrNotBlank(message = "{property.metrics.meter.description.nullOrNotBlank}")
        private String description;

        /**
         * Default tags
         */
        @Valid
        @NullOrNotEmpty(message = "{property.metrics.meter.tags.nullOrNotEmpty}")
        private List<@NotNull Tag> tags;

        /**
         * Default long task tags
         */
        @Valid
        @NullOrNotEmpty(message = "{property.metrics.meter.long-task-tags.nullOrNotEmpty}")
        private List<@NotNull Tag> longTaskTags;

        /**
         * Default base unit
         */
        @NullOrNotBlank(message = "{property.metrics.meter.base-unit.nullOrNotBlank}")
        private String baseUnit;

        /**
         * Default simple tags
         */
        @Valid
        @NotEmpty(message = "{property.metrics.meter.simple-tags.notEmpty}")
        private List<@NotEmpty String> simpleTags = DEFAULT_SIMPLE_COMMON_TAGS;

        /**
         * Enable/disable meter ({@code true} by default)
         */
        private boolean enabled = true;

        /**
         * Returns {@link String} array of tags
         *
         * @return {@link String} array of tags
         */
        public String[] getSimpleTagsAsArray() {
            return this.simpleTags.toArray(new String[0]);
        }
    }

    /**
     * Metrics patterns configuration properties
     */
    @Data
    @Validated
    @Accessors(chain = true)
    public static class Patterns {
        /**
         * Default handlers property prefix
         */
        public static final String PROPERTY_PREFIX = WsMetricsProperty.PROPERTY_PREFIX + DEFAULT_PROPERTY_DELIMITER + "patterns";

        /**
         * Default {@link Set} collection of {@link String} patterns to include
         */
        @Valid
        @NullOrNotEmpty(message = "{property.metrics.patterns.include.nullOrNotEmpty}")
        private Set<@NotBlank String> include;

        /**
         * Default {@link Set} collection of {@link String} patterns to exclude
         */
        @Valid
        @NullOrNotEmpty(message = "{property.metrics.patterns.exclude.nullOrNotEmpty}")
        private Set<@NotBlank String> exclude;
    }

    /**
     * Metrics handlers configuration properties
     */
    @Data
    @Validated
    @Accessors(chain = true)
    public static class Handlers {
        /**
         * Default handlers property prefix
         */
        public static final String PROPERTY_PREFIX = WsMetricsProperty.PROPERTY_PREFIX + DEFAULT_PROPERTY_DELIMITER + "handlers";
        public static final String TRACKING_TIME_PROPERTY_PREFIX = PROPERTY_PREFIX + DEFAULT_PROPERTY_DELIMITER + "tracking-time";
        public static final String MONITORING_TIME_PROPERTY_PREFIX = PROPERTY_PREFIX + DEFAULT_PROPERTY_DELIMITER + "monitoring-time";

        /**
         * Tracking time handler
         */
        @Valid
        @NestedConfigurationProperty
        @NotNull(message = "{property.metrics.handlers.tracking-time.notNull}")
        private Handler trackingTime = new Handler();

        /**
         * Monitoring counter/time handler
         */
        @Valid
        @NestedConfigurationProperty
        @NotNull(message = "{property.metrics.handlers.monitoring-time.notNull}")
        private Handler monitoringTime = new Handler();
    }

    /**
     * Metrics handler configuration properties
     */
    @Data
    @Validated
    @Accessors(chain = true)
    public static class Handler {
        /**
         * Enable/disable handler ({@code true} by default)
         */
        private boolean enabled = true;
    }

    /**
     * Enable/disable metrics configuration ({@code true} by default)
     */
    private boolean enabled = true;
}
