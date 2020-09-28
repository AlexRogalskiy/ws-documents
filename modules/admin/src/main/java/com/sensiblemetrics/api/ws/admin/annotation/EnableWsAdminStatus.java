package com.sensiblemetrics.api.ws.admin.annotation;

import com.sensiblemetrics.api.ws.admin.configuration.WsAdminConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Import(WsAdminConfiguration.class)
public @interface EnableWsAdminStatus {
}
