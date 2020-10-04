package com.sensiblemetrics.api.ws.admin.configuration;

import com.sensiblemetrics.api.ws.admin.property.WsAdminServerProperty;
import com.sensiblemetrics.api.ws.commons.annotation.ConditionalOnMissingBean;
import de.codecentric.boot.admin.server.config.AdminServerProperties;
import de.codecentric.boot.admin.server.web.client.InstanceExchangeFilterFunction;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.*;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.UUID;

@Slf4j
@Configuration
@Import(WsAdminServerNotifierConfiguration.class)
@ConditionalOnProperty(prefix = WsAdminServerProperty.PROPERTY_PREFIX, value = "enabled", havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties(WsAdminServerProperty.class)
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
@Description("SensibleMetrics WebDocs Admin Server configuration")
public abstract class WsAdminServerConfiguration {
    /**
     * Default configuration {@link Marker} instance
     */
    private static final Marker DEFAULT_CONFIGURATION_MARKER = MarkerFactory.getMarker("WsAdminServerConfiguration");

    @Bean
    @ConditionalOnMissingBean
    @Description("Audit log configuration bean")
    public InstanceExchangeFilterFunction auditLog() {
        return (instance, request, next) -> next.exchange(request).doOnSubscribe((s) -> {
            if (HttpMethod.DELETE.equals(request.method()) || HttpMethod.POST.equals(request.method())) {
                log.info(DEFAULT_CONFIGURATION_MARKER, "{} for {} on {}", request.method(), instance.getId(), request.url());
            }
        });
    }

    @Profile("insecure")
    @Configuration(proxyBeanMethods = false)
    @EnableConfigurationProperties(AdminServerProperties.class)
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    @Description("Authority Admin Server Web Security configuration adapter")
    public static class AuthorityAdminServerSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
        private final String adminContextPath;

        public AuthorityAdminServerSecurityConfigurerAdapter(final AdminServerProperties adminServerProperties) {
            this.adminContextPath = adminServerProperties.getContextPath();
        }

        @Override
        protected void configure(final HttpSecurity http) throws Exception {
            http.authorizeRequests(authorizeRequests -> authorizeRequests.anyRequest().permitAll())
                .csrf(csrf -> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                    .ignoringRequestMatchers(
                        new AntPathRequestMatcher(this.adminContextPath + "/instances",
                            HttpMethod.POST.toString()),
                        new AntPathRequestMatcher(this.adminContextPath + "/instances/*",
                            HttpMethod.DELETE.toString()),
                        new AntPathRequestMatcher(this.adminContextPath + "/actuator/**")
                    )
                );
        }
    }

    @Profile("secure")
    @Configuration(proxyBeanMethods = false)
    @EnableConfigurationProperties(AdminServerProperties.class)
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    @Description("NoAuthority Admin Server Web Security configuration adapter")
    public static class NoAuthorityAdminServerSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
        private final String adminContextPath;

        public NoAuthorityAdminServerSecurityConfigurerAdapter(final AdminServerProperties adminServerProperties) {
            this.adminContextPath = adminServerProperties.getContextPath();
        }

        @Override
        protected void configure(final HttpSecurity http) throws Exception {
            http.authorizeRequests(authorizeRequests -> authorizeRequests
                .antMatchers(this.adminContextPath + "/assets/**").permitAll()
                .antMatchers(this.adminContextPath + "/login").permitAll().anyRequest().authenticated())
                .formLogin(formLogin -> formLogin.loginPage(this.adminContextPath + "/login").successHandler(this.authenticationSuccessHandler()))
                .logout(logout -> logout.logoutUrl(this.adminContextPath + "/logout"))
                .httpBasic(Customizer.withDefaults())
                .csrf(csrf -> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                    .ignoringRequestMatchers(
                        new AntPathRequestMatcher(this.adminContextPath + "/instances",
                            HttpMethod.POST.toString()),
                        new AntPathRequestMatcher(this.adminContextPath + "/instances/*",
                            HttpMethod.DELETE.toString()),
                        new AntPathRequestMatcher(this.adminContextPath + "/actuator/**")
                    )
                )
                .rememberMe()
                .key(UUID.randomUUID().toString())
                .tokenValiditySeconds(1209600);
        }

        private SavedRequestAwareAuthenticationSuccessHandler authenticationSuccessHandler() {
            final SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
            successHandler.setTargetUrlParameter("redirectTo");
            successHandler.setDefaultTargetUrl(this.adminContextPath + "/");
            return successHandler;
        }
    }
}
