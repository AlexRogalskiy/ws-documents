package com.sensiblemetrics.api.ws.commons.configuration;

import com.sensiblemetrics.api.ws.commons.property.WsRouteProperty;
import lombok.RequiredArgsConstructor;
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
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;

import java.util.List;
import java.util.function.Function;

import static org.springframework.util.StringUtils.toStringArray;

@EnableWs
@Configuration
@Import(WsEndpointConfigurerAdapter.class)
@ConditionalOnProperty(prefix = WsRouteProperty.PROPERTY_PREFIX, name = "enabled", havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties(WsRouteProperty.class)
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
@Description("SensibleMetrics Commons Web Service Addressing configuration")
public abstract class WsAddressingConfiguration {

    @Configuration
    @RequiredArgsConstructor
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    @Description("SensibleMetrics Commons Web Service Registration configuration")
    public static class WsRegistrationConfiguration extends WsConfigurerAdapter {
        private final ApplicationContext applicationContext;
        private final WsRouteProperty property;

        @Bean
        @ConditionalOnMissingBean
        @Description("Default message dispatcher servlet configuration bean")
        public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet() {
            final MessageDispatcherServlet servlet = new MessageDispatcherServlet();
            servlet.setApplicationContext(this.applicationContext);
            servlet.setTransformWsdlLocations(this.property.isTransformWsdlLocations());
            servlet.setEnableLoggingRequestDetails(this.property.isEnableLoggingRequests());
            servlet.setTransformSchemaLocations(this.property.isTransformSchemaLocations());
            servlet.setNamespace(this.property.getNamespace());
            return new ServletRegistrationBean<>(servlet, toStringArray(this.property.getUrlMappings()));
        }

        @Bean
        @ConditionalOnMissingBean
        @Description("Default WS-endpoint factory bean")
        public Function<String, WsRouteProperty.WsEndpoint> endpointBeanFactory() {
            return name -> this.property.getEndpoints().get(name);
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
