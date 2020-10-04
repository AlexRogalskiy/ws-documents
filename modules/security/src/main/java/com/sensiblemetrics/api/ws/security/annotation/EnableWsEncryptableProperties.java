package com.sensiblemetrics.api.ws.security.annotation;

import com.sensiblemetrics.api.ws.security.configuration.WsSecurityEncryptableConfiguration;
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@EnableEncryptableProperties
@Import(WsSecurityEncryptableConfiguration.class)
public @interface EnableWsEncryptableProperties {
}
