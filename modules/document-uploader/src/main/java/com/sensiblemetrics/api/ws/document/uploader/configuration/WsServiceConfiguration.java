package com.sensiblemetrics.api.ws.document.uploader.configuration;

import com.sensiblemetrics.api.ws.commons.annotation.ConditionalOnWsAddressingEnabled;
import com.sensiblemetrics.api.ws.commons.configuration.WsEndpointConfigurerAdapter;
import com.sensiblemetrics.api.ws.commons.property.WsEndpointProperty;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.context.annotation.Role;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.XsdSchema;

@Configuration
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
@Description("SensibleMetrics Web Service configuration")
public class WsServiceConfiguration {

    @Configuration
    @RequiredArgsConstructor
    @ConditionalOnWsAddressingEnabled
    @EnableConfigurationProperties(WsEndpointProperty.class)
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    @Description("SensibleMetrics Document Uploader Web Service configuration")
    public static class WsDocumentUploaderConfiguration extends WsConfigurerAdapter {
        /**
         * Default document definition bean naming conventions
         */
        public static final String DOCUMENT_WSDL_DEFINITION_BEAN_NAME = "documentWsdlDefinitionBean";
        public static final String DOCUMENT_WS_XSD_SCHEMA_BEAN_NAME = "documentWsXsdSchemaBean";
        public static final String DOCUMENT_WS_ENDPOINT_BEAN_NAME = "documentWsEndpointBean";
        /**
         * Default document property key
         */
        public static final String DOCUMENT_WS_ENDPOINT_KEY = "document";

        private final WsEndpointConfigurerAdapter endpointConfigurerAdapter;

        @Bean(name = DOCUMENT_WSDL_DEFINITION_BEAN_NAME)
        @Description("Default document WSDL definition configuration bean")
        public DefaultWsdl11Definition defaultWsdl11Definition(@Qualifier(DOCUMENT_WS_XSD_SCHEMA_BEAN_NAME) final XsdSchema xsdSchema,
                                                               @Qualifier(DOCUMENT_WS_ENDPOINT_BEAN_NAME) final WsEndpointProperty.WsEndpoint endpoint) {
            return this.endpointConfigurerAdapter.createWsdl11Definition(xsdSchema, endpoint);
        }

        @Bean(name = DOCUMENT_WS_XSD_SCHEMA_BEAN_NAME)
        @Description("Default document XSD-schema configuration bean")
        public XsdSchema documentSchema(@Qualifier(DOCUMENT_WS_ENDPOINT_BEAN_NAME) final WsEndpointProperty.WsEndpoint endpoint) {
            return this.endpointConfigurerAdapter.createSimpleXsdSchema(endpoint);
        }

        @Bean(name = DOCUMENT_WS_ENDPOINT_BEAN_NAME)
        @Description("Default document WS-endpoint property configuration bean")
        public WsEndpointProperty.WsEndpoint documentWsEndpoint(final WsEndpointProperty property) {
            return property.getEntries().get(DOCUMENT_WS_ENDPOINT_KEY);
        }
    }
}
