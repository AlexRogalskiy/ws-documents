package com.sensiblemetrics.api.ws.admin.configuration;

import com.sensiblemetrics.api.ws.admin.property.WsAdminServerProperty;
import de.codecentric.boot.admin.server.config.AdminServerProperties;
import de.codecentric.boot.admin.server.web.client.InstanceExchangeFilterFunction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.*;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;
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

    /**
     * Default bean naming conventions
     */
    public static final String AUDIT_LOG_BEAN_NAME = "auditLog";

    @Bean(AUDIT_LOG_BEAN_NAME)
    @ConditionalOnMissingBean(name = AUDIT_LOG_BEAN_NAME)
    @Description("Audit log configuration bean")
    public InstanceExchangeFilterFunction auditLog() {
        return (instance, request, next) -> next.exchange(request).doOnSubscribe((s) -> {
            if (HttpMethod.DELETE.equals(request.method()) || HttpMethod.POST.equals(request.method())) {
                log.info(DEFAULT_CONFIGURATION_MARKER, "{} for {} on {}", request.method(), instance.getId(), request.url());
            }
        });
    }

    @Profile("admin-secure")
    @Configuration(proxyBeanMethods = false)
    @EnableConfigurationProperties(AdminServerProperties.class)
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    @Description("WebDocs Authentication Admin Server Web Security configuration adapter")
    public static class AuthorityAdminServerSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

        private final String adminContextPath;

        public AuthorityAdminServerSecurityConfigurerAdapter(final AdminServerProperties adminServerProperties) {
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

    @Profile("admin-secure-db")
    @Configuration(proxyBeanMethods = false)
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    @Description("WebDocs Database Authentication Admin Server Web Security configuration")
    public static class DataBaseAdminServerSecurityConfiguration {

        @Bean
        @ConditionalOnMissingBean
        @Description("Web Security password encoder bean")
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }

        @Bean
        @ConditionalOnMissingBean
        @Description("Web Security persistent token repository bean")
        public PersistentTokenRepository tokenRepository(@Qualifier("dataSource") final DataSource dataSource) {
            final JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
            jdbcTokenRepository.setDataSource(dataSource);
            return jdbcTokenRepository;
        }

        @Profile("admin-secure-db")
        @EnableWebSecurity
        @RequiredArgsConstructor
        @Configuration(proxyBeanMethods = false)
        @EnableConfigurationProperties(AdminServerProperties.class)
        @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
        @Description("Authentication Admin Server Web Security configuration adapter")
        public static class AuthorityAdminServerSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

            private final DataSource dataSource;
            private final PasswordEncoder passwordEncoder;
            private final PersistentTokenRepository tokenRepository;
            private final AdminServerProperties adminServerProperties;

            @Override
            protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
                auth.jdbcAuthentication()
                    .dataSource(this.dataSource)
                    .usersByUsernameQuery("select username, password, status"
                        + " from users where username=?")
                    .authoritiesByUsernameQuery("select username, authority "
                        + "from authorities where username=?")
                    .passwordEncoder(this.passwordEncoder);
            }

            @Override
            protected void configure(final HttpSecurity http) throws Exception {
                http.authorizeRequests(authorizeRequests -> authorizeRequests.anyRequest().permitAll())
                    .csrf(csrf -> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                        .ignoringRequestMatchers(
                            new AntPathRequestMatcher(this.adminServerProperties.getContextPath() + "/instances",
                                HttpMethod.POST.toString()),
                            new AntPathRequestMatcher(this.adminServerProperties.getContextPath() + "/instances/*",
                                HttpMethod.DELETE.toString()),
                            new AntPathRequestMatcher(this.adminServerProperties.getContextPath() + "/actuator/**")
                        )
                    )
                    .rememberMe().rememberMeParameter("remember-me").tokenRepository(this.tokenRepository);
            }
        }
    }

    @Profile("admin-insecure")
    @Configuration(proxyBeanMethods = false)
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    @Description("WebDocs No-Authentication Admin Server Web Security configuration adapter")
    public static class NoAuthorityAdminServerSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(final HttpSecurity http) throws Exception {
            http.csrf().disable().authorizeRequests().anyRequest().permitAll();
        }
    }
}
