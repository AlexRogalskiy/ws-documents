package com.sensiblemetrics.api.ws.actuator.property;

import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.time.DurationMin;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.convert.DurationFormat;
import org.springframework.boot.convert.DurationStyle;
import org.springframework.boot.convert.DurationUnit;
import org.springframework.context.annotation.Description;
import org.springframework.context.annotation.Role;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

import static com.sensiblemetrics.api.ws.commons.property.PropertySettings.DEFAULT_PROPERTY_DELIMITER;

@Data
@Validated
@Accessors(chain = true)
@ConfigurationProperties(
    prefix = WsGracefulShutdownProperty.PROPERTY_PREFIX,
    ignoreInvalidFields = true)
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
@Description("SensibleMetrics Web Service Graceful Shutdown configuration properties")
public class WsGracefulShutdownProperty {
  /** Default graceful shutdown property prefix */
  public static final String PROPERTY_PREFIX =
      WsApiStatusProperty.PROPERTY_PREFIX + DEFAULT_PROPERTY_DELIMITER + "shutdown";

  /**
   * Milliseconds to wait before /internal/health is starting to respond with server errors, after
   * shutdown signal is retrieved.
   *
   * <p>Default: {@code api-status.shutdown.indicateErrorAfter = 5000}
   */
  @DurationUnit(ChronoUnit.MILLIS)
  @DurationFormat(DurationStyle.SIMPLE)
  @DurationMin(message = "{property.api-status.shutdown.indicate-error-after.min}")
  @NotNull(message = "{property.api-status.shutdown.indicate-error-after.notNull}")
  private Duration indicateErrorAfter = Duration.ofMillis(5000);

  /**
   * Milliseconds to respond /internal/health checks with server errors, before actually shutting
   * down the application.
   *
   * <p>Default: api-status.shutdown.phaseOutAfter = 20000
   */
  @DurationUnit(ChronoUnit.MILLIS)
  @DurationFormat(DurationStyle.SIMPLE)
  @DurationMin(millis = 100, message = "{property.api-status.shutdown.phase-out-after.min}")
  @NotNull(message = "{property.api-status.shutdown.phase-out-after.notNull}")
  private Duration phaseOutAfter = Duration.ofMillis(20000);

  /**
   * Enable/disable graceful shutdown configuration ({@code false} by default)
   *
   * <p>Disabling the shutdown is especially required in test cases, where you do not want to wait
   * for 25sec. to stop a test server instance.
   */
  private boolean enabled = false;
}
