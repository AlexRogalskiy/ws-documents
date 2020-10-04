package com.sensiblemetrics.api.ws.validation.aspect;

import com.sensiblemetrics.api.ws.validation.annotation.ValidateInput;
import com.sensiblemetrics.api.ws.validation.exception.DataValidationException;
import com.sensiblemetrics.api.ws.validation.management.ValidatorRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Description;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Objects;

@Slf4j
@Aspect
@RequiredArgsConstructor
@Description("WebService method parameters validation aspect configuration")
public class MethodParamsValidationAspect {

    private final ValidatorRegistry registry;

    /**
     * Pointcut that matches {@link ValidateInput}
     */
    @Pointcut("@annotation(com.sensiblemetrics.api.ws.validation.annotation.ValidateInput)" +
        "|| @within(com.sensiblemetrics.api.ws.validation.annotation.ValidateInput)")
    public void validateInputPointcut() {
    }

    @Before(value = "validateInputPointcut()")
    public void doBefore(final JoinPoint point) {
        final Annotation[][] paramAnnotations = ((MethodSignature) point.getSignature()).getMethod().getParameterAnnotations();
        for (int i = 0; i < paramAnnotations.length; i++) {
            for (final Annotation annotation : paramAnnotations[i]) {
                if (annotation.annotationType() == ValidateInput.class) {
                    final Object arg = point.getArgs()[i];
                    if (Objects.isNull(arg)) continue;
                    this.validate(arg);
                }
            }
        }
    }

    private void validate(final Object arg) {
        final List<Validator> validatorList = this.registry.getValidatorsForObject(arg);
        for (final Validator validator : validatorList) {
            final BindingResult errors = new BeanPropertyBindingResult(arg, arg.getClass().getSimpleName());
            validator.validate(arg, errors);
            if (errors.hasErrors()) {
                DataValidationException.throwInvalidData(errors);
            }
        }
    }
}
