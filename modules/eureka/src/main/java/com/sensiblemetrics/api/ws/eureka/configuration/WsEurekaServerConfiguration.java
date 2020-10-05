package com.sensiblemetrics.api.ws.eureka.configuration;

import com.sensiblemetrics.api.ws.eureka.property.WsEurekaServerProperty;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Role;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@ConditionalOnProperty(prefix = WsEurekaServerProperty.PROPERTY_PREFIX, value = "enabled", havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties(WsEurekaServerProperty.class)
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
@Description("SensibleMetrics WebDocs Eureka Server configuration")
public abstract class WsEurekaServerConfiguration {

    /**
     * Actuator {@link WebSecurityConfigurerAdapter} implementation
     */
    @Profile("eureka-secure")
    @Configuration(proxyBeanMethods = false)
    @RequiredArgsConstructor
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    @Description("WebDocs Authentication Actuator Web Security configuration adapter")
    public static class AuthorityActuatorSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
        private final WsEurekaServerProperty property;

        /**
         * {@inheritDoc}
         *
         * @see WebSecurityConfigurerAdapter
         */
        @Override
        protected void configure(final HttpSecurity http) throws Exception {
            http.csrf().ignoringAntMatchers("/eureka/**")
                .and()
                .authorizeRequests()
                .antMatchers("/actuator/**").permitAll()
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
    @Profile("eureka-insecure")
    @Configuration(proxyBeanMethods = false)
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    @Description("WebDocs No-Authentication Actuator Web Security configuration adapter")
    public static class NoAuthorityActuatorSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
        /**
         * {@inheritDoc}
         *
         * @see WebSecurityConfigurerAdapter
         */
        @Override
        protected void configure(final HttpSecurity http) throws Exception {
            http.csrf().ignoringAntMatchers("/eureka/**")
                .and()
                .authorizeRequests()
                .anyRequest().permitAll();
        }
    }
}
