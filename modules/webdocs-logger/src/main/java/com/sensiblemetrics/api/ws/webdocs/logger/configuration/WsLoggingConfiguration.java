package com.sensiblemetrics.api.ws.webdocs.logger.configuration;

import com.sensiblemetrics.api.ws.webdocs.logger.aspect.ReportingDataAspect;
import com.sensiblemetrics.api.ws.webdocs.logger.handler.LogHeadersToMdcFilter;
import com.sensiblemetrics.api.ws.webdocs.logger.property.WsLoggingProperty;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.*;

@Configuration
@Import(ReportingDataAspect.class)
@EnableAspectJAutoProxy(proxyTargetClass = true)
@ConditionalOnProperty(
    prefix = WsLoggingProperty.PROPERTY_PREFIX,
    value = "enabled",
    havingValue = "true",
    matchIfMissing = true)
@EnableConfigurationProperties(WsLoggingProperty.class)
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
@Description("SensibleMetrics Web Service Logging configuration")
public abstract class WsLoggingConfiguration {

  @Bean
  @ConditionalOnBean(WsLoggingProperty.class)
  @ConditionalOnClass(LogHeadersToMdcFilter.class)
  @ConditionalOnProperty(
      prefix = WsLoggingProperty.Handlers.HEADERS_PROPERTY_PREFIX,
      name = "enabled",
      havingValue = "true")
  @Description("MDC filter headers logging configuration bean")
  public LogHeadersToMdcFilter logHeadersToMDCFilter(final WsLoggingProperty property) {
    return new LogHeadersToMdcFilter(property.getHandlers().getHeaders());
  }
}
