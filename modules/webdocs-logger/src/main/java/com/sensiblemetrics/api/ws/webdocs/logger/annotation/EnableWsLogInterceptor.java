package com.sensiblemetrics.api.ws.webdocs.logger.annotation;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface EnableWsLogInterceptor {}
