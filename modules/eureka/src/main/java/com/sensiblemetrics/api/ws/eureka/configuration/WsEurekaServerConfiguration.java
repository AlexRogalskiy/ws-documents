package com.sensiblemetrics.api.ws.eureka.configuration;

import com.sensiblemetrics.api.ws.eureka.property.WsEurekaServerProperty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.context.annotation.Role;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Slf4j
@Configuration
@EnableConfigurationProperties(WsEurekaServerProperty.class)
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
@Description("SensibleMetrics WebDocs Eureka Server configuration")
public abstract class WsEurekaServerConfiguration {

    /**
     * Actuator {@link WebSecurityConfigurerAdapter} implementation
     */
    @Configuration(proxyBeanMethods = false)
    @RequiredArgsConstructor
    @ConditionalOnProperty(prefix = WsEurekaServerProperty.PROPERTY_PREFIX, value = "enabled", havingValue = "true")
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    @Description("Authentication Actuator Web Security configuration adapter")
    public static class AuthActuatorSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
        private final WsEurekaServerProperty property;

        /**
         * {@inheritDoc}
         *
         * @see WebSecurityConfigurerAdapter
         */
        @Override
        protected void configure(final HttpSecurity http) throws Exception {
            http.csrf().disable()
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .httpBasic();
//            this.property.getEndpoints()
//                .forEach((key, value) -> {
//                        try {
//                            http.requestMatcher(EndpointRequest.to(value.getNamesAsArray()))
//                                .authorizeRequests(requests -> requests.anyRequest().hasAnyRole(value.getRolesAsArray()));
//                            log.info("Adding security for actuator path: {} with roles: {}", value.getNames(), value.getRoles());
//                        } catch (Exception e) {
//                            log.error("Cannot process actuator endpoints configuration, message: {}", e.getMessage());
//                            throw new SecurityConfigurationException(e);
//                        }
//                    }
//                );
        }
    }

    /**
     * Actuator empty {@link WebSecurityConfigurerAdapter} implementation
     */
    @Configuration(proxyBeanMethods = false)
    @ConditionalOnProperty(prefix = WsEurekaServerProperty.PROPERTY_PREFIX, value = "enabled", havingValue = "false", matchIfMissing = true)
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    @Description("NoAuthentication Actuator Web Security configuration adapter")
    public static class NoAuthActuatorSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
        /**
         * {@inheritDoc}
         *
         * @see WebSecurityConfigurerAdapter
         */
        @Override
        protected void configure(final HttpSecurity http) throws Exception {
            http.authorizeRequests().anyRequest().permitAll();
        }
    }
}
