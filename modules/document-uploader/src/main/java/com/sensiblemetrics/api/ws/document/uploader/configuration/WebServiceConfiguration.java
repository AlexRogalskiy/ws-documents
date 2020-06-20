package com.sensiblemetrics.api.ws.document.uploader.configuration;

import com.sensiblemetrics.api.ws.commons.configuration.WsEndpointConfigurerAdapter;
import com.sensiblemetrics.api.ws.commons.property.WsEndpointProperty;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.context.annotation.Role;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

import static com.sensiblemetrics.api.ws.commons.utils.ServiceUtils.findInClasspath;

@Configuration
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
@Description("SensibleMetrics Web Service configuration")
public class WebServiceConfiguration {

    @Configuration
    @RequiredArgsConstructor
    @EnableConfigurationProperties(WsEndpointProperty.class)
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    @Description("SensibleMetrics Document Web Service configuration")
    public static class WsServiceConfiguration extends WsConfigurerAdapter {
        /**
         * Default document definition bean naming conventions
         */
        public static final String DOCUMENT_WSDL_DEFINITION_BEAN_NAME = "documentWsdlDefinitionBean";
        public static final String DOCUMENT_WS_ENDPOINT_BEAN_NAME = "documentWsEndpoint";
        public static final String DOCUMENT_WS_ENDPOINT_KEY = "document";

        private final WsEndpointConfigurerAdapter endpointConfigurerAdapter;

        @Bean(name = DOCUMENT_WSDL_DEFINITION_BEAN_NAME)
        @ConditionalOnMissingBean(name = DOCUMENT_WSDL_DEFINITION_BEAN_NAME)
        @ConditionalOnBean(XsdSchema.class)
        @Description("Default document XSD-schema configuration bean")
        public DefaultWsdl11Definition defaultWsdl11Definition(final XsdSchema documentSchema,
                                                               final WsEndpointProperty.WsEndpoint documentWsEndpoint) {
            return this.endpointConfigurerAdapter.createWsdl11Definition(documentSchema, documentWsEndpoint);
        }

        @Bean
        @ConditionalOnMissingBean
        @ConditionalOnClass(SimpleXsdSchema.class)
        @Description("Default document XSD-schema configuration bean")
        public XsdSchema documentSchema(final WsEndpointProperty.WsEndpoint documentWsEndpoint) {
            return new SimpleXsdSchema(findInClasspath(documentWsEndpoint.getResourcePattern()));
        }

        @Bean(name = DOCUMENT_WS_ENDPOINT_BEAN_NAME)
        @ConditionalOnMissingBean(name = DOCUMENT_WS_ENDPOINT_BEAN_NAME)
        @ConditionalOnBean(XsdSchema.class)
        @Description("Default document WS-endpoint configuration bean")
        public WsEndpointProperty.WsEndpoint documentWsEndpoint(final WsEndpointProperty property) {
            return property.getEntries().get(DOCUMENT_WS_ENDPOINT_KEY);
        }
    }
}
