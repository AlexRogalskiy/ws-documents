package com.sensiblemetrics.api.ws.commons.property;

import com.sensiblemetrics.api.ws.commons.constraint.NullOrNotEmpty;
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

import static com.sensiblemetrics.api.ws.commons.property.PropertySettings.PROPERTY_DELIMITER;

@Data
@Validated
@Accessors(chain = true)
@ConfigurationProperties(prefix = WsInfoServiceProperty.PROPERTY_PREFIX, ignoreInvalidFields = true)
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
@Description("SensibleMetrics Commons Api Status Info configuration properties")
public class WsInfoServiceProperty {
    /**
     * Default info service property prefix
     */
    public static final String PROPERTY_PREFIX = WsApiStatusProperty.PROPERTY_PREFIX + PROPERTY_DELIMITER + "node.info";

    /**
     * Info service settings
     */
    @Valid
    @NestedConfigurationProperty
    @NotNull(message = "{property.api-status.node.info.settings.notNull}")
    private WsInfoServiceProperty.InfoSettings settings = new InfoSettings();

    /**
     * Info service parameter names
     */
    @Valid
    @NestedConfigurationProperty
    @NotNull(message = "{property.api-status.node.info.parameter-names.notNull}")
    private WsInfoServiceProperty.InfoParameterNames parameterNames = new InfoParameterNames();

    /**
     * Info service metrics
     */
    @Valid
    @NestedConfigurationProperty
    @NotNull(message = "{property.api-status.node.info.metrics.notNull}")
    private WsInfoServiceProperty.InfoMetrics metrics = new InfoMetrics();

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
        private static final Map<String, String> DEFAULT_METRIC_ENTRIES = Map.of("process-uptime", PROCESS_UPTIME_METRIC, "logback-events", LOGBACK_EVENTS_METRIC);

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
