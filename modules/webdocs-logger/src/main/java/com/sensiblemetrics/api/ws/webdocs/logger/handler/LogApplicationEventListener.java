package com.sensiblemetrics.api.ws.webdocs.logger.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.boot.actuate.audit.listener.AuditApplicationEvent;
import org.springframework.boot.context.event.*;
import org.springframework.context.event.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Slf4j
@Component
public class LogApplicationEventListener {

  /**
   * Handles input {@link AuditApplicationEvent}
   *
   * @param event - initial input {@link AuditApplicationEvent} to handle
   */
  @Async
  @EventListener
  public void onApplicationAuditEvent(final AuditApplicationEvent event) {
    final AuditEvent auditEvent = event.getAuditEvent();
    log.info(
        "Handling [{}] >>> timestamp: [{}], principal: [{}], type: [{}], data: {}, source: [{}]",
        event.getClass().getSimpleName(),
        auditEvent.getTimestamp(),
        auditEvent.getPrincipal(),
        auditEvent.getType(),
        auditEvent.getData(),
        event.getSource().getClass().getSimpleName());
  }

  /**
   * Handles input {@link SpringApplicationEvent}
   *
   * @param event - initial input {@link SpringApplicationEvent} to handle
   */
  @Async
  @EventListener({
    ApplicationStartedEvent.class,
    ApplicationReadyEvent.class,
    ApplicationFailedEvent.class,
    ApplicationContextInitializedEvent.class,
    ApplicationEnvironmentPreparedEvent.class
  })
  public void onSpringApplicationEvent(final SpringApplicationEvent event) {
    this.logEvent(event);
  }

  /**
   * Handles input {@link ApplicationContextEvent}
   *
   * @param event - initial input {@link ApplicationContextEvent} to handle
   */
  @Async
  @EventListener({
    ContextStartedEvent.class,
    ContextRefreshedEvent.class,
    ContextStoppedEvent.class,
    ContextClosedEvent.class,
  })
  public void onApplicationContextEvent(final ApplicationContextEvent event) {
    this.logEvent(event);
  }

  /**
   * Describes input {@link SpringApplicationEvent} by {@code log} instance
   *
   * @param event - initial input {@link SpringApplicationEvent} to handle
   */
  private void logEvent(final SpringApplicationEvent event) {
    log.info(
        "Handling [{}] >>> timestamp: [{}], args: {}, source: [{}]",
        event.getClass().getSimpleName(),
        Instant.ofEpochMilli(event.getTimestamp()),
        event.getArgs(),
        event.getSource().getClass().getSimpleName());
  }

  /**
   * Describes input {@link ApplicationContextEvent} by {@code log} instance
   *
   * @param event - initial input {@link ApplicationContextEvent} to handle
   */
  private void logEvent(final ApplicationContextEvent event) {
    log.info(
        "Handling [{}] >>> timestamp: [{}], source: [{}]",
        event.getClass().getSimpleName(),
        Instant.ofEpochMilli(event.getTimestamp()),
        event.getSource().getClass().getSimpleName());
  }
}
