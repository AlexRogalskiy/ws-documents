package com.sensiblemetrics.api.ws.commons.configuration;

import com.sensiblemetrics.api.ws.commons.exception.SecurityConfigurationException;
import com.sensiblemetrics.api.ws.commons.property.WsActuatorSecurityProperty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.context.annotation.Role;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Slf4j
@Configuration
@EnableConfigurationProperties(WsActuatorSecurityProperty.class)
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
@Description("SensibleMetrics Commons Web Service Actuator Security configuration")
public abstract class WsActuatorSecurityConfiguration {

    /**
     * Actuator {@link WebSecurityConfigurerAdapter} adapter implementation
     */
    @Configuration(proxyBeanMethods = false)
    @RequiredArgsConstructor
    @ConditionalOnProperty(prefix = WsActuatorSecurityProperty.PROPERTY_PREFIX, value = "enabled", havingValue = "true")
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    @Description("Role-based actuator web security configurer adapter")
    public static class RoleBasedActuatorSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
        private final WsActuatorSecurityProperty property;

        /**
         * {@inheritDoc}
         *
         * @see WebSecurityConfigurerAdapter
         */
        @Override
        protected void configure(final HttpSecurity http) {
            this.property.getEndpoints().forEach(
                    (key, value) -> {
                        try {
                            http
                                    .requestMatcher(EndpointRequest.to(value.getNames().toArray(new String[0])))
                                    .authorizeRequests(requests -> requests.anyRequest().hasAnyRole(value.getRoles().toArray(new String[0])));
                            log.info("Adding security for actuator path: {} with roles: {}", value.getNames(), value.getRoles());
                        } catch (Exception e) {
                            throw new SecurityConfigurationException(e);
                        }
                    }
            );
        }
    }

    /**
     * Actuator empty {@link WebSecurityConfigurerAdapter} adapter implementation
     */
    @Configuration(proxyBeanMethods = false)
    @ConditionalOnProperty(prefix = WsActuatorSecurityProperty.PROPERTY_PREFIX, value = "enabled", havingValue = "false", matchIfMissing = true)
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    @Description("Empty actuator web security configurer adapter")
    public static class EmptyActuatorSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
        /**
         * {@inheritDoc}
         *
         * @see WebSecurityConfigurerAdapter
         */
        @Override
        protected void configure(final HttpSecurity http) throws Exception {
            http.requestMatcher(EndpointRequest.toAnyEndpoint())
                    .authorizeRequests((requests) ->
                            requests.anyRequest().permitAll()
                    );
        }
    }
}
