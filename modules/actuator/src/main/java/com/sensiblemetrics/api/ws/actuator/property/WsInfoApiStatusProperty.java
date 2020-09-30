package com.sensiblemetrics.api.ws.actuator.property;

import com.sensiblemetrics.api.ws.commons.constraint.NullOrNotEmpty;
import com.sensiblemetrics.api.ws.commons.helper.MapBuilder;
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
import javax.validation.constraints.NotNull;
import java.util.Map;

import static com.sensiblemetrics.api.ws.commons.property.PropertySettings.DEFAULT_PROPERTY_DELIMITER;

@Data
@Validated
@Accessors(chain = true)
@ConfigurationProperties(prefix = WsInfoApiStatusProperty.PROPERTY_PREFIX, ignoreInvalidFields = true)
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
@Description("SensibleMetrics WebDocs Info Api Status configuration properties")
public class WsInfoApiStatusProperty {
    /**
     * Default info api status property prefix
     */
    public static final String PROPERTY_PREFIX = WsApiStatusProperty.PROPERTY_PREFIX + DEFAULT_PROPERTY_DELIMITER + "node.info";

    /**
     * Info settings
     */
    @Valid
    @NestedConfigurationProperty
    @NotNull(message = "{property.api-status.node.info.settings.notNull}")
    private WsInfoApiStatusProperty.InfoSettings settings = new InfoSettings();

    /**
     * Info parameter names
     */
    @Valid
    @NestedConfigurationProperty
    @NotNull(message = "{property.api-status.node.info.parameter-names.notNull}")
    private WsInfoApiStatusProperty.InfoParameterNames parameterNames = new InfoParameterNames();

    /**
     * Info metrics
     */
    @Valid
    @NestedConfigurationProperty
    @NotNull(message = "{property.api-status.node.info.metrics.notNull}")
    private WsInfoApiStatusProperty.InfoMetrics metrics = new InfoMetrics();

    @Data
    @Validated
    @Accessors(chain = true)
    public static class InfoMetrics {
        /**
         * Default process uptime metric
         */
        public static final String PROCESS_UPTIME_METRIC = "process.uptime";
        /**
         * Default logback events metric
         */
        public static final String LOGBACK_EVENTS_METRIC = "logback.events";

        /**
         * Default {@link Map} collection of metric entries
         */
        private static final Map<String, String> DEFAULT_METRIC_ENTRIES = MapBuilder
                .map(String.class, String.class)
                .entry("process-uptime", PROCESS_UPTIME_METRIC)
                .entry("logback-events", LOGBACK_EVENTS_METRIC)
                .build();

        /**
         * Default {@link Map} collection of metrics
         */
        @Valid
        @NullOrNotEmpty(message = "{property.api-status.node.info.metrics.entries.nullOrNotEmpty}")
        private Map<@NotBlank String, @NotBlank String> entries = DEFAULT_METRIC_ENTRIES;
    }

    @Data
    @Validated
    @Accessors(chain = true)
    public static class InfoSettings {
        /**
         * Default uptime duration format setting
         */
        @NotBlank(message = "{property.api-status.node.info.settings.duration-format.notBlank}")
        private String durationFormat = "d.HH:mm:ss.SSS";

        /**
         * Default error description setting
         */
        @NotBlank(message = "{property.api-status.node.info.settings.error-description.notBlank}")
        private String errorDescription = "NONE";
    }

    @Data
    @Validated
    @Accessors(chain = true)
    public static class InfoParameterNames {
        /**
         * Default server name
         */
        @NotBlank(message = "{property.api-status.node.info.parameters.server-name.notBlank}")
        private String serverName = "serverName";

        /**
         * Default server uptime name
         */
        @NotBlank(message = "{property.api-status.node.info.parameters.uptime-name.notBlank}")
        private String serverUptimeName = "uptime";

        /**
         * Default build number name
         */
        @NotBlank(message = "{property.api-status.node.info.parameters.build-number-name.notBlank}")
        private String buildNumberName = "buildNumber";

        /**
         * Default application state name
         */
        @NotBlank(message = "{property.api-status.node.info.parameters.state-name.notBlank}")
        private String stateName = "appState";

        /**
         * Default application status name
         */
        @NotBlank(message = "{property.api-status.node.info.parameters.started-status-name.notBlank}")
        private String startedStatusName = "isStarted";

        /**
         * Default application errors counter name
         */
        @NotBlank(message = "{property.api-status.node.info.parameters.errors-counter-name.notBlank}")
        private String errorsCounterName = "errors";

        /**
         * Default application error description name
         */
        @NotBlank(message = "{property.api-status.node.info.parameters.error-description-name.notBlank}")
        private String errorDescriptionName = "errorDescription";
    }
}
