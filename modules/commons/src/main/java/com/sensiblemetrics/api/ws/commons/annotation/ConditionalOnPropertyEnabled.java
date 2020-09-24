package com.sensiblemetrics.api.ws.commons.annotation;

import org.springframework.context.annotation.Conditional;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({
        ElementType.TYPE,
        ElementType.METHOD
})
@Conditional(OnPropertyEnabledCondition.class)
public @interface ConditionalOnPropertyEnabled {
    /**
     * Returns {@link String} value
     *
     * @return {@link String} value
     */
    String value();
}
