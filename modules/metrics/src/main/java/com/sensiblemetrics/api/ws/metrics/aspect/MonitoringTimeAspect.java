package com.sensiblemetrics.api.ws.metrics.aspect;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.StopWatch;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Description;

import java.util.concurrent.TimeUnit;

@Slf4j
@Aspect
@RequiredArgsConstructor
@Description("Web Service Monitoring Component aspect configuration")
public class MonitoringTimeAspect {
  /** Tags status markers */
  private static final String SUCCESS = "SUCCESS";

  private static final String ERROR = "ERROR";
  /** Tags naming conventions */
  private static final String TAG_COMPONENT_CLASS = "componentClass";

  private static final String TAG_EXCEPTION_CLASS = "exceptionClass";
  private static final String TAG_METHOD_NAME = "methodName";
  private static final String TAG_OUTCOME = "outcome";
  private static final String TAG_TYPE = "componentType";
  /** Tags component naming conventions */
  private static final String TAG_VALUE_SERVICE_TYPE = "service";

  private static final String TAG_VALUE_COMPONENT_TYPE = "component";
  private static final String TAG_VALUE_REPOSITORY_TYPE = "repository";
  private static final String TAG_VALUE_CONTROLLER_TYPE = "controller";
  private static final String TAG_VALUE_STEREOTYPE_TYPE = "stereotype";
  /** Meters component naming conventions */
  private static final String METER_COMPONENT_TIMER = "component.invocation.timer";

  private static final String METER_COMPONENT_COUNTER = "component.invocation.counter";
  private static final String METER_COMPONENT_EXCEPTION_COUNTER =
      "component.invocation.exception.counter";

  private final MeterRegistry registry;

  @Pointcut(
      "@annotation(com.sensiblemetrics.api.ws.metrics.annotation.MonitoredComponent) "
          + "|| @within(com.sensiblemetrics.api.ws.metrics.annotation.MonitoredComponent)")
  public void monitoredComponentPointcut() {}

  @Pointcut(
      "@annotation(com.sensiblemetrics.api.ws.metrics.annotation.MonitoredService) "
          + "|| @within(com.sensiblemetrics.api.ws.metrics.annotation.MonitoredService)")
  public void monitoredServicePointcut() {}

  @Pointcut(
      "@annotation(com.sensiblemetrics.api.ws.metrics.annotation.MonitoredRepository) "
          + "|| @within(com.sensiblemetrics.api.ws.metrics.annotation.MonitoredRepository)")
  public void monitoredRepositoryPointcut() {}

  @Pointcut(
      "(@annotation(com.sensiblemetrics.api.ws.metrics.annotation.MonitoredComponent) ||"
          + " @within(com.sensiblemetrics.api.ws.metrics.annotation.MonitoredComponent))||"
          + " (@annotation(com.sensiblemetrics.api.ws.metrics.annotation.MonitoredRestController)"
          + " || @within(com.sensiblemetrics.api.ws.metrics.annotation.MonitoredRestController))")
  public void monitoredControllerPointcut() {}

  @Pointcut(
      "monitoredComponentPointcut()"
          + " || monitoredServicePointcut()"
          + " || monitoredRepositoryPointcut()"
          + " || monitoredControllerPointcut()")
  public void monitoredStereotypePointcut() {}

  @Around("monitoredComponentPointcut()")
  public Object componentResponseTimeAdvice(final ProceedingJoinPoint joinPoint) throws Throwable {
    log.info("Pointcut target: {}", joinPoint.getTarget().toString());
    return this.monitorResponseTime(joinPoint, TAG_VALUE_COMPONENT_TYPE);
  }

  @Around("monitoredServicePointcut()")
  public Object serviceResponseTimeAdvice(final ProceedingJoinPoint joinPoint) throws Throwable {
    log.info("Pointcut target: {}", joinPoint.getTarget().toString());
    return this.monitorResponseTime(joinPoint, TAG_VALUE_SERVICE_TYPE);
  }

  @Around("monitoredRepositoryPointcut()")
  public Object repositoryResponseTimeAdvice(final ProceedingJoinPoint joinPoint) throws Throwable {
    log.info("Pointcut target: {}", joinPoint.getTarget().toString());
    return this.monitorResponseTime(joinPoint, TAG_VALUE_REPOSITORY_TYPE);
  }

  @Around("monitoredControllerPointcut()")
  public Object controllerResponseTimeAdvice(final ProceedingJoinPoint joinPoint) throws Throwable {
    log.info("Pointcut target: {}", joinPoint.getTarget().toString());
    return this.monitorResponseTime(joinPoint, TAG_VALUE_CONTROLLER_TYPE);
  }

  @AfterThrowing(pointcut = "monitoredStereotypePointcut()", throwing = "ex")
  public void serviceResponseTimeExceptionAdvice(final JoinPoint joinPoint, final Exception ex) {
    this.monitorException(joinPoint, ex, TAG_VALUE_STEREOTYPE_TYPE);
  }

  private Object monitorResponseTime(final ProceedingJoinPoint joinPoint, final String type)
      throws Throwable {
    final StopWatch stopWatch = new StopWatch();
    stopWatch.start();
    final Object obj = joinPoint.proceed();
    stopWatch.stop();

    final String serviceClass = this.getClassName(joinPoint.getThis().getClass().getName());
    final String methodName = joinPoint.getSignature().getName();

    final Timer timer =
        this.registry.timer(
            METER_COMPONENT_TIMER,
            TAG_COMPONENT_CLASS,
            serviceClass,
            TAG_METHOD_NAME,
            methodName,
            TAG_OUTCOME,
            SUCCESS,
            TAG_TYPE,
            type);
    timer.record(stopWatch.getNanoTime(), TimeUnit.NANOSECONDS);

    this.registry
        .counter(
            METER_COMPONENT_COUNTER,
            TAG_COMPONENT_CLASS,
            serviceClass,
            TAG_METHOD_NAME,
            methodName,
            TAG_OUTCOME,
            SUCCESS,
            TAG_TYPE,
            type)
        .increment();
    return obj;
  }

  private void monitorException(final JoinPoint joinPoint, final Exception ex, final String type) {
    final String serviceClass = this.getClassName(joinPoint.getThis().getClass().getName());
    final String methodName = joinPoint.getSignature().getName();
    this.registry
        .counter(
            METER_COMPONENT_EXCEPTION_COUNTER,
            TAG_EXCEPTION_CLASS,
            ex.getClass().getName(),
            TAG_COMPONENT_CLASS,
            serviceClass,
            TAG_METHOD_NAME,
            methodName,
            TAG_OUTCOME,
            ERROR,
            TAG_TYPE,
            type)
        .increment();
  }

  private String getClassName(final String fullName) {
    String serviceClass = fullName;
    final int first = serviceClass.lastIndexOf('.');
    final int second = serviceClass.indexOf('$');

    if (first > 0 && second > first) {
      serviceClass = serviceClass.substring(first + 1, second);
    }
    return serviceClass;
  }
}
