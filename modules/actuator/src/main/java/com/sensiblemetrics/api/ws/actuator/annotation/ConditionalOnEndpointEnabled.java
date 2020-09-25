package com.sensiblemetrics.api.ws.actuator.annotation;

import org.springframework.context.annotation.Conditional;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({
        ElementType.TYPE,
        ElementType.METHOD
})
@Conditional(OnPropertyEnabledEndpointElementCondition.class)
public @interface ConditionalOnEndpointEnabled {
    /**
     * Returns {@link String} value
     *
     * @return {@link String} value
     */
    String value();
}
