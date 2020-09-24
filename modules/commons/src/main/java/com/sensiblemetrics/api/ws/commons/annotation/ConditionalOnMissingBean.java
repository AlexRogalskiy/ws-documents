package com.sensiblemetrics.api.ws.commons.annotation;

import org.springframework.context.annotation.Conditional;

import java.lang.annotation.*;

@Documented
@Target({
        ElementType.TYPE,
        ElementType.METHOD
})
@Retention(RetentionPolicy.RUNTIME)
@Conditional(OnMissingBeanCondition.class)
public @interface ConditionalOnMissingBean {
}
