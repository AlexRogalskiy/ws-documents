package com.sensiblemetrics.api.ws.commons.property;

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

@Data
@Validated
@Accessors(chain = true)
@ConfigurationProperties(prefix = WsMetricsProperty.PROPERTY_PREFIX, ignoreInvalidFields = true)
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
@Description("Paragon MicroServices Commons Metrics configuration properties")
public class WsMetricsProperty {
    /**
     * Default metrics property prefix
     */
    public static final String PROPERTY_PREFIX = "ws-metrics";
    /**
     * Default {@link List} of {@link String} simple common tags
     */
    private static final List<String> DEFAULT_SIMPLE_COMMON_TAGS = Lists.newArrayList("scope", "WebDocs");

    /**
     * Default naming convention
     */
    @NotNull(message = "{property.ws-metrics.naming-convention.notNull}")
    private NamingConvention namingConvention = NamingConvention.snakeCase;

    /**
     * Default {@link Map} collection of {@link MeterProperty} entries
     */
    @Valid
    @NullOrNotEmpty(message = "{property.ws-metrics.meters.nullOrNotEmpty}")
    private Map<@NotBlank String, @NotNull MeterProperty> meters;

    /**
     * Default {@link MeterProperty} property
     */
    @Valid
    @NestedConfigurationProperty
    @NotNull(message = "{property.ws-metrics.defaults.notNull}")
    private MeterProperty defaults = new MeterProperty();

    /**
     * MultiMeter configuration properties
     */
    @Data
    @Validated
    @Accessors(chain = true)
    public static class MeterProperty {
        /**
         * Default consumer
         */
        @NullOrNotBlank(message = "{property.ws-metrics.meter.consumer.nullOrNotBlank}")
        private String consumer;

        /**
         * Default description
         */
        @NullOrNotBlank(message = "{property.ws-metrics.meter.description.nullOrNotBlank}")
        private String description;

        /**
         * Default tags
         */
        @Valid
        @NullOrNotEmpty(message = "{property.ws-metrics.meter.tags.nullOrNotEmpty}")
        private List<@NotNull Tag> tags;

        /**
         * Default long task tags
         */
        @Valid
        @NullOrNotEmpty(message = "{property.ws-metrics.meter.long-task-tags.nullOrNotEmpty}")
        private List<@NotNull Tag> longTaskTags;

        /**
         * Default base unit
         */
        @NullOrNotBlank(message = "{property.ws-metrics.meter.base-unit.nullOrNotBlank}")
        private String baseUnit;

        /**
         * Default simple tags
         */
        @Valid
        @NotEmpty(message = "{property.ws-metrics.meter.simple-tags.notEmpty}")
        private List<@NotEmpty String> simpleTags = DEFAULT_SIMPLE_COMMON_TAGS;

        /**
         * Enable/disable meter ({@code true} by default)
         */
        private boolean enabled = true;
    }

    /**
     * Enable/disable metrics configuration ({@code true} by default)
     */
    private boolean enabled = true;
}
