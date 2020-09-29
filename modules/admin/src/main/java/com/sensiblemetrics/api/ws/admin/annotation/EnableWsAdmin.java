package com.sensiblemetrics.api.ws.admin.annotation;

import com.sensiblemetrics.api.ws.admin.configuration.WsAdminConfiguration;
import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@EnableDiscoveryClient
@EnableAdminServer
@Import(WsAdminConfiguration.class)
public @interface EnableWsAdmin {
    /**
     * Returns {@code boolean} flag, whether to autoregister client on startup
     *
     * @return true - if client autoregister is enabled, false - otherwise
     */
    @AliasFor(annotation = EnableDiscoveryClient.class, attribute = "autoRegister")
    boolean autoRegister() default true;
}
