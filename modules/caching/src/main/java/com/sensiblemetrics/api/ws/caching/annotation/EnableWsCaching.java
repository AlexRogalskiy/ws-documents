package com.sensiblemetrics.api.ws.caching.annotation;

import com.sensiblemetrics.api.ws.caching.configuration.WsCachingConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Import(WsCachingConfiguration.class)
public @interface EnableWsCaching {
}
