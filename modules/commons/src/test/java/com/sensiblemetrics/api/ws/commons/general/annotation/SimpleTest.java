package com.sensiblemetrics.api.ws.commons.general.annotation;

import java.lang.annotation.*;

@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@EnableBootstrapTest
public @interface SimpleTest {
}
