package com.sensiblemetrics.api.ws.logger.aspect;

import com.sensiblemetrics.api.ws.logger.annotation.LoggingInfo;
import com.sensiblemetrics.api.ws.logger.property.WsLoggingProperty;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Description;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import static com.sensiblemetrics.api.ws.commons.property.PropertySettings.DEFAULT_PROPERTY_DELIMITER;

@Slf4j
@Aspect
@ConditionalOnProperty(prefix = LoggingAspect.PROPERTY_PREFIX, value = "enabled", havingValue = "true", matchIfMissing = true)
@Description("Web Service Logging aspect configuration")
public class LoggingAspect {
    /**
     * Default logging property prefix
     */
    public static final String PROPERTY_PREFIX = WsLoggingProperty.Handlers.PROPERTY_PREFIX + DEFAULT_PROPERTY_DELIMITER + "log-data";

    /**
     * Pointcut that matches {@link Repository}, {@link Service}, {@link RestController} and {@link Controller} components
     */
    @Pointcut("within(@org.springframework.stereotype.Repository *)" +
            " || within(@org.springframework.stereotype.Service *)" +
            " || within(@org.springframework.web.bind.annotation.RestController *)" +
            " || within(@org.springframework.stereotype.Controller *)")
    public void componentPointcut() {
    }

    /**
     * Pointcut that matches {@link LoggingInfo}
     */
    @Pointcut("@annotation(com.sensiblemetrics.api.ws.logger.annotation.LoggingInfo)" +
            "|| @within(com.sensiblemetrics.api.ws.logger.annotation.LoggingInfo)")
    public void loggingInfoPointcut() {
    }

    /**
     * Advice that logs methods throwing exceptions
     *
     * @param joinPoint join point for advice
     * @param e         exception
     */
    @AfterThrowing(pointcut = "(componentPointcut() || loggingInfoPointcut())", throwing = "e")
    public void logAfterThrowing(final JoinPoint joinPoint,
                                 final Throwable e) {
        this.logExecution(joinPoint);
        if (log.isErrorEnabled()) {
            log.error("Method error >>> {}.{}() with cause = '{}' and exception = '{}'", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName(), e.getCause() != null ? e.getCause() : "NULL", e.getMessage(), e);
        }
    }

    /**
     * Advice that logs when a method is entered and exited
     *
     * @param joinPoint - initial input {@link ProceedingJoinPoint} for advice
     * @return {@link Object} result
     * @throws Throwable throws IllegalArgumentExceptions
     */
    @Around("(componentPointcut() || loggingInfoPointcut())")
    public Object logAround(final ProceedingJoinPoint joinPoint) throws Throwable {
        this.logExecution(joinPoint);
        if (log.isInfoEnabled()) {
            log.info("Method enter >>> {}.{}() with argument[s] = {}", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));
        }
        try {
            final Object result = joinPoint.proceed();
            if (log.isInfoEnabled()) {
                log.info("Method exit >>> {}.{}() with result = {}", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName(), result);
            }
            return result;
        } catch (IllegalArgumentException e) {
            log.error("Method error >>> {} in {}.{}()", Arrays.toString(joinPoint.getArgs()), joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
            throw e;
        }
    }

    /**
     * Provides logging information on method execution by input {@link ProceedingJoinPoint}
     *
     * @param joinPoint - initial input {@link ProceedingJoinPoint} to operate by
     */
    private void logExecution(final JoinPoint joinPoint) {
        final MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        final LoggingInfo methodLoggingInfoAnnotation = methodSignature.getMethod().getAnnotation(LoggingInfo.class);
        final LoggingInfo classLoggingInfoAnnotation = joinPoint.getTarget().getClass().getAnnotation(LoggingInfo.class);

        Stream.of(methodLoggingInfoAnnotation, classLoggingInfoAnnotation)
                .filter(Objects::nonNull)
                .filter(annotation -> annotation.value().isVerbose())
                .findAny()
                .ifPresent(v -> {
                    if (log.isInfoEnabled()) {
                        log.info("Joint point info >>> {}", joinPoint.toLongString());
                    }
                });

        this.logRequestAttributes(joinPoint);
    }

    /**
     * Provides logging information on request attributes by input {@link ProceedingJoinPoint}
     *
     * @param joinPoint - initial input {@link ProceedingJoinPoint} to operate by
     */
    private void logRequestAttributes(final JoinPoint joinPoint) {
        final ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        log.info(">>> Request attributes: url: {}, method: {}, remote address: {}, method: {}, arguments: {}",
                this.getRequest(requestAttributes)
                        .map(HttpServletRequest::getRequestURL)
                        .map(StringBuffer::toString)
                        .orElse(null),
                this.getRequest(requestAttributes)
                        .map(HttpServletRequest::getMethod)
                        .orElse(null),
                this.getRequest(requestAttributes)
                        .map(HttpServletRequest::getRemoteAddr)
                        .orElse(null),
                joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName(),
                Arrays.toString(joinPoint.getArgs())
        );
    }

    private Optional<HttpServletRequest> getRequest(final ServletRequestAttributes requestAttributes) {
        return Optional.ofNullable(requestAttributes).map(ServletRequestAttributes::getRequest);
    }
}
