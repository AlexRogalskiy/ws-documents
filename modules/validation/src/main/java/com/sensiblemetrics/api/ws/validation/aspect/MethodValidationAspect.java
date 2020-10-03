package com.sensiblemetrics.api.ws.validation.aspect;

import com.sensiblemetrics.api.ws.validation.annotation.ValidateIn;
import com.sensiblemetrics.api.ws.validation.annotation.ValidateOut;
import com.sensiblemetrics.api.ws.validation.management.ThrowableValidator;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.Description;

import javax.validation.Validator;
import java.util.Optional;

import static com.sensiblemetrics.api.ws.commons.utils.ServiceUtils.streamOf;

@Aspect
@RequiredArgsConstructor
@Description("WebService method validation aspect configuration")
public class MethodValidationAspect {

    private final ThrowableValidator validator;

    /**
     * Pointcut that matches {@link ValidateIn} annotation
     */
    @Before("@annotation(com.sensiblemetrics.api.ws.validation.annotation.ValidateIn)")
    public void validateInputData(final JoinPoint joinPoint) {
        this.doValidation(joinPoint.getArgs());
    }

    /**
     * Pointcut that matches {@link ValidateOut} annotation
     */
    @AfterReturning(pointcut = "@annotation(com.sensiblemetrics.api.ws.validation.annotation.ValidateOut)", returning = "result")
    public void validateOutputData(final Object result) {
        this.doValidation(result);
    }

    /**
     * Validates input array of {@link Object}s by custom validation rules
     *
     * @param targets - initial input array of {@link Object}s
     */
    private void doValidation(final Object... targets) {
        streamOf(targets)
            .forEach(target -> {
                if ((target instanceof Optional)) {
                    ((Optional<?>) target).ifPresent(this::validate);
                } else {
                    this.validate(target);
                }
            });
    }

    /**
     * Validates input {@link Object} by current {@link Validator} instance
     *
     * @param target - initial input {@link Object} to validate
     */
    private void validate(final Object target) {
        this.validator.validate(target);
    }
}
