package com.sensiblemetrics.api.ws.commons.configuration;

import com.sensiblemetrics.api.ws.commons.property.WsEndpointProperty;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
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
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.transport.http.MessageDispatcherServlet;

import java.util.List;

import static org.springframework.util.StringUtils.toStringArray;

@EnableWs
@Configuration
@Import(WsEndpointConfigurerAdapter.class)
@ConditionalOnProperty(prefix = WsEndpointProperty.PROPERTY_PREFIX, name = "enabled", havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties(WsEndpointProperty.class)
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
@Description("SensibleMetrics Commons Web Service Addressing configuration")
public abstract class WsAddressingConfiguration {

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnBean(ApplicationContext.class)
    @Description("Default message dispatcher servlet configuration bean")
    public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(final ApplicationContext applicationContext,
                                                                                      final WsEndpointProperty property) {
        final MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(property.isTransformWsdlLocations());
        servlet.setEnableLoggingRequestDetails(property.isEnableLoggingRequests());
        servlet.setTransformSchemaLocations(property.isTransformSchemaLocations());
        servlet.setNamespace(property.getNamespace());
        return new ServletRegistrationBean<>(servlet, toStringArray(property.getUrlMappings()));
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnClass(DefaultConversionService.class)
    @Description("Conversion service bean")
    public ConversionService conversionService(final List<Converter<?, ?>> converters) {
        final DefaultConversionService defaultConversionService = new DefaultConversionService();
        converters.forEach(defaultConversionService::addConverter);
        return defaultConversionService;
    }
}
