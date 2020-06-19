package com.sensiblemetrics.api.ws.document.uploader.configuration;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.context.annotation.Role;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

import static com.sensiblemetrics.api.ws.document.uploader.utils.ServiceUtils.findInClasspath;

@EnableWs
@Configuration
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
@Description("SensibleMetrics Document uploader Web Service configuration")
public class WebServiceConfiguration extends WsConfigurerAdapter {
    /**
     * Default wsdl definition bean naming convention
     */
    public static final String DOCUMENT_WSDL_DEFINITION_BEAN_NAME = "documentWsdlDefinitionBean";

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnBean(ApplicationContext.class)
    @Description("Default message dispatcher servlet configuration bean")
    public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(final ApplicationContext applicationContext) {
        final MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean<>(servlet, "/ws/*");
    }

    @Bean(name = DOCUMENT_WSDL_DEFINITION_BEAN_NAME)
    @ConditionalOnMissingBean(name = DOCUMENT_WSDL_DEFINITION_BEAN_NAME)
    @ConditionalOnBean({
            XsdSchema.class
    })
    @Description("Default document XSD-schema configuration bean")
    public DefaultWsdl11Definition defaultWsdl11Definition(final XsdSchema documentSchema) {
        final DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("DocumentPort");
        wsdl11Definition.setServiceName("DocumentService");
        wsdl11Definition.setLocationUri("/ws");
        wsdl11Definition.setTargetNamespace("http://spring.io/guides/gs-producing-web-service");
        wsdl11Definition.setSchema(documentSchema);
        return wsdl11Definition;
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnClass(SimpleXsdSchema.class)
    @Description("Default document XSD-schema configuration bean")
    public XsdSchema documentSchema() {
        return new SimpleXsdSchema(findInClasspath("xsd/document.xsd"));
    }
}
