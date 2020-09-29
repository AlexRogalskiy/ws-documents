package com.sensiblemetrics.api.ws.security.configuration;

import com.sensiblemetrics.api.ws.security.property.WsSecurityProperty;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.context.annotation.Role;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableConfigurationProperties(WsSecurityProperty.class)
@ConditionalOnProperty(
    prefix = WsSecurityProperty.PROPERTY_PREFIX,
    name = "enabled",
    havingValue = "true",
    matchIfMissing = true)
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
@Description("SensibleMetrics Commons Web Service Security configuration")
public abstract class WsSecurityConfiguration {

  /** SSL {@link WebSecurityConfigurerAdapter} implementation */
  @Configuration(proxyBeanMethods = false)
  @ConditionalOnProperty(
      prefix = WsSecurityProperty.SSL_PROPERTY_PREFIX,
      value = "enabled",
      havingValue = "false",
      matchIfMissing = true)
  @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
  @Description("SSL Web Security configuration adapter")
  public static class SslWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
      http.requiresChannel().anyRequest().requiresSecure();
    }
  }
}
