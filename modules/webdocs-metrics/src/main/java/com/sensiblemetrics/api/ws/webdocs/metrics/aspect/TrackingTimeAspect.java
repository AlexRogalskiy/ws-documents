package com.sensiblemetrics.api.ws.webdocs.metrics.aspect;

import com.sensiblemetrics.api.ws.webdocs.metrics.annotation.TrackingTime;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.StopWatch;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Description;
import org.springframework.lang.Nullable;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

@Slf4j
@Aspect
@Description("Web Service Tracking time aspect configuration")
public class TrackingTimeAspect {

  /** Pointcut that matches {@link TrackingTime} */
  @Pointcut("@annotation(annotation) || @within(annotation)")
  public void trackingTimePointcut(final TrackingTime annotation) {}

  /**
   * Advice that logs when a method is entered and exited
   *
   * @param joinPoint - initial input {@link ProceedingJoinPoint} for advice
   * @return {@link Object} result
   * @throws Throwable throws IllegalArgumentExceptions
   */
  @Nullable
  @Around(value = "trackingTimePointcut(annotation)", argNames = "joinPoint,annotation")
  public Object around(final ProceedingJoinPoint joinPoint, final TrackingTime annotation)
      throws Throwable {
    final StopWatch stopWatch = new StopWatch();
    stopWatch.start();
    final Object returnProceed = joinPoint.proceed();
    stopWatch.stop();

    log.info(
        "Method time tracking >>> point: {} with time rate: {}",
        joinPoint,
        millisToShortDHMS(stopWatch.getTime()));
    log.info(
        "Method time tracking >>> class: {}, method: {}, args: {}, execTime: {}",
        joinPoint.getTarget().getClass().getName(),
        joinPoint.getSignature().getName(),
        (joinPoint.getArgs() == null || joinPoint.getArgs().length == 0)
            ? "None"
            : Arrays.toString(joinPoint.getArgs()),
        stopWatch.getTime() + " millis");
    log.info(
        "Method time tracking >>> point: {} with time rate: {}",
        joinPoint,
        MILLISECONDS.convert(stopWatch.getTime(), annotation.timeUnit()));
    return returnProceed;
  }

  public static String millisToShortDHMS(final long duration) {
    final long days = TimeUnit.MILLISECONDS.toDays(duration);
    final long hours =
        TimeUnit.MILLISECONDS.toHours(duration)
            - TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(duration));
    final long minutes =
        TimeUnit.MILLISECONDS.toMinutes(duration)
            - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(duration));
    final long seconds =
        TimeUnit.MILLISECONDS.toSeconds(duration)
            - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration));

    return (days == 0)
        ? String.format("%02d:%02d:%02d", hours, minutes, seconds)
        : String.format("%dd%02d:%02d:%02d", days, hours, minutes, seconds);
  }
}
