package com.sensiblemetrics.api.ws.document.generator.configuration;

import com.sensiblemetrics.api.ws.commons.configuration.WsEndpointConfigurerAdapter;
import com.sensiblemetrics.api.ws.commons.property.EndpointConfigurationProvider;
import com.sensiblemetrics.api.ws.commons.property.WsRouteProperty;
import com.sensiblemetrics.api.ws.document.generator.property.DocumentStorageProperty;
import com.sensiblemetrics.api.ws.document.generator.property.DocumentTemplateFormatProperty;
import com.sensiblemetrics.api.ws.document.generator.property.DocumentTemplateProperty;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.convention.NameTokenizers;
import org.modelmapper.convention.NamingConventions;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.context.annotation.Role;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.XsdSchema;

import java.util.List;

@Configuration
@EnableConfigurationProperties({
        DocumentTemplateProperty.class,
        DocumentTemplateFormatProperty.class,
        DocumentStorageProperty.class
})
@Description("SensibleMetrics Web Service configuration")
public class WsServiceConfiguration {

    @Bean
    @Description("Default model mapper configuration bean")
    public ModelMapper modelMapper(final List<Converter<?, ?>> converters,
                                   final List<PropertyMap<?, ?>> propertyMaps) {
        final ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE)
                .setMethodAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PUBLIC)
                .setSourceNamingConvention(NamingConventions.JAVABEANS_MUTATOR)
                .setSourceNamingConvention(NamingConventions.JAVABEANS_ACCESSOR)
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setSourceNameTokenizer(NameTokenizers.CAMEL_CASE)
                .setDestinationNameTokenizer(NameTokenizers.CAMEL_CASE)
                .setAmbiguityIgnored(true)
                .setSkipNullEnabled(true)
                .setFieldMatchingEnabled(true)
                .setFullTypeMatchingRequired(true)
                .setImplicitMappingEnabled(true);
        converters.forEach(modelMapper::addConverter);
        propertyMaps.forEach(modelMapper::addMappings);
        return modelMapper;
    }

    @Configuration
    @RequiredArgsConstructor
    @EnableConfigurationProperties(WsRouteProperty.class)
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    @Description("SensibleMetrics Document Web Service configuration")
    public static class WsDocumentConfiguration {
        /**
         * Default document definition bean naming conventions
         */
        public static final String DOCUMENT_WSDL_DEFINITION_BEAN_NAME = "documents";
        public static final String DOCUMENT_WS_XSD_SCHEMA_BEAN_NAME = "documentWsXsdSchemaBean";
        public static final String DOCUMENT_WS_ENDPOINT_BEAN_NAME = "documentWsEndpointBean";
        /**
         * Default document property key
         */
        public static final String DOCUMENT_WS_ENDPOINT_KEY = "document";

        private final WsEndpointConfigurerAdapter endpointConfigurerAdapter;
        private final EndpointConfigurationProvider endpointConfigurationProvider;

        @Bean(name = DOCUMENT_WSDL_DEFINITION_BEAN_NAME)
        @Description("Default document WSDL definition configuration bean")
        public DefaultWsdl11Definition defaultWsdl11Definition(@Qualifier(DOCUMENT_WS_XSD_SCHEMA_BEAN_NAME) final XsdSchema xsdSchema,
                                                               @Qualifier(DOCUMENT_WS_ENDPOINT_BEAN_NAME) final WsRouteProperty.WsEndpoint endpoint) {
            return this.endpointConfigurerAdapter.createWsdl11Definition(xsdSchema, endpoint);
        }

        @Bean(name = DOCUMENT_WS_XSD_SCHEMA_BEAN_NAME)
        @Description("Default document XSD-schema configuration bean")
        public XsdSchema documentSchema(@Qualifier(DOCUMENT_WS_ENDPOINT_BEAN_NAME) final WsRouteProperty.WsEndpoint endpoint) {
            return this.endpointConfigurerAdapter.createSimpleXsdSchema(endpoint);
        }

        @Bean(name = DOCUMENT_WS_ENDPOINT_BEAN_NAME)
        @Description("Default document WS-endpoint property configuration bean")
        public WsRouteProperty.WsEndpoint documentWsEndpoint(final WsRouteProperty property) {
            return this.endpointConfigurationProvider.getOrThrow(DOCUMENT_WS_ENDPOINT_KEY);
        }
    }
}
