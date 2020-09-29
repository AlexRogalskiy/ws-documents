package com.sensiblemetrics.api.ws.router.configuration;

import com.sensiblemetrics.api.ws.router.property.WsAddressingProperty;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;

import java.util.List;

@Configuration
@Import(WsEndpointConfigurerAdapter.class)
@ConditionalOnProperty(
    prefix = WsAddressingProperty.PROPERTY_PREFIX,
    name = "enabled",
    havingValue = "true",
    matchIfMissing = true)
@EnableConfigurationProperties(WsAddressingProperty.class)
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
@Description("SensibleMetrics Web Service Router Addressing configuration")
public abstract class WsAddressingConfiguration {

  @Configuration
  @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
  @Description("SensibleMetrics Commons Web Service Registration configuration")
  public static class WsRegistrationConfiguration extends WsConfigurerAdapter {
    /** Default bean naming conventions */
    public static final String MESSAGE_DISPATCHER_SERVLET_CUSTOMIZER_BEAN_NAME =
        "messageDispatcherServletCustomizer";

    public static final String CONTEXT_MESSAGE_DISPATCHER_SERVLET_CUSTOMIZER_BEAN_NAME =
        "applicationContextMessageDispatcherServletCustomizer";
    public static final String ENDPOINT_CONFIGURATION_PROVIDER_BEAN_NAME =
        "endpointConfigurationProvider";

    @Bean
    @ConditionalOnMissingBean
    @Description("Default message dispatcher servlet registration bean")
    public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(
        final WsAddressingProperty property,
        final ObjectProvider<List<MessageDispatcherServletCustomizer>> customizerList) {
      final MessageDispatcherServlet dispatcherServlet = new MessageDispatcherServlet();
      customizerList.ifAvailable(
          customizers -> customizers.forEach(c -> c.customize(dispatcherServlet)));
      return new ServletRegistrationBean<>(dispatcherServlet, property.getUrlMappingsAsArray());
    }

    @Bean(MESSAGE_DISPATCHER_SERVLET_CUSTOMIZER_BEAN_NAME)
    @ConditionalOnMissingBean(name = MESSAGE_DISPATCHER_SERVLET_CUSTOMIZER_BEAN_NAME)
    @Description("Default message dispatcher servlet customizer bean")
    public MessageDispatcherServletCustomizer messageDispatcherServletCustomizer(
        final WsAddressingProperty property) {
      return dispatcherServlet -> {
        dispatcherServlet.setTransformWsdlLocations(property.isTransformWsdlLocations());
        dispatcherServlet.setEnableLoggingRequestDetails(property.isEnableLoggingRequests());
        dispatcherServlet.setTransformSchemaLocations(property.isTransformSchemaLocations());
        dispatcherServlet.setNamespace(property.getNamespace());
      };
    }

    @Bean(CONTEXT_MESSAGE_DISPATCHER_SERVLET_CUSTOMIZER_BEAN_NAME)
    @ConditionalOnMissingBean(name = CONTEXT_MESSAGE_DISPATCHER_SERVLET_CUSTOMIZER_BEAN_NAME)
    @Description("Application context message dispatcher servlet customizer bean")
    public MessageDispatcherServletCustomizer applicationContextMessageDispatcherServletCustomizer(
        final ApplicationContext applicationContext) {
      return dispatcherServlet -> dispatcherServlet.setApplicationContext(applicationContext);
    }

    @Bean(ENDPOINT_CONFIGURATION_PROVIDER_BEAN_NAME)
    @ConditionalOnMissingBean(name = ENDPOINT_CONFIGURATION_PROVIDER_BEAN_NAME)
    @Description("Default WS-endpoint factory bean")
    public EndpointConfigurationProvider endpointConfigurationProvider(
        final WsAddressingProperty property) {
      return name -> property.getEndpoints().get(name);
    }
  }

  @Bean
  @ConditionalOnMissingBean
  @ConditionalOnClass(DefaultConversionService.class)
  @Description("Default conversion service bean")
  public ConversionService conversionService(final List<Converter<?, ?>> converters) {
    final DefaultConversionService defaultConversionService = new DefaultConversionService();
    converters.forEach(defaultConversionService::addConverter);
    return defaultConversionService;
  }
}
