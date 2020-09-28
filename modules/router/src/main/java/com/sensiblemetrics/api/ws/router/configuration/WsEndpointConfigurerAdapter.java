package com.sensiblemetrics.api.ws.router.configuration;

import com.sensiblemetrics.api.ws.router.property.WsAddressingProperty;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Description;
import org.springframework.context.annotation.Role;
import org.springframework.stereotype.Component;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

import static com.sensiblemetrics.api.ws.commons.utils.ServiceUtils.findInClasspath;

@Component
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
@Description("SensibleMetrics Web Service Router configurer adapter")
public class WsEndpointConfigurerAdapter {

    /**
     * Returns {@link DefaultWsdl11Definition} by input {@link XsdSchema} and {@link WsAddressingProperty.WsEndpoint} properties
     *
     * @param xsdSchema initial input {@link XsdSchema} to operate by
     * @param endpoint  initial input {@link WsAddressingProperty.WsEndpoint} properties to operate by
     * @return new {@link DefaultWsdl11Definition} instance
     */
    public DefaultWsdl11Definition createWsdl11Definition(final XsdSchema xsdSchema,
                                                          final WsAddressingProperty.WsEndpoint endpoint) {
        final DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName(endpoint.getPortTypeName());
        wsdl11Definition.setServiceName(endpoint.getServiceName());
        wsdl11Definition.setLocationUri(endpoint.getLocationUri());
        wsdl11Definition.setTargetNamespace(endpoint.getTargetNamespace());
        wsdl11Definition.setSchema(xsdSchema);
        return wsdl11Definition;
    }

    /**
     * Returns simple {@link XsdSchema} by input {@link WsAddressingProperty.WsEndpoint} properties
     *
     * @param endpoint initial input {@link WsAddressingProperty.WsEndpoint} properties to operate by
     * @return new simple {@link XsdSchema} instance
     */
    public XsdSchema createSimpleXsdSchema(final WsAddressingProperty.WsEndpoint endpoint) {
        return new SimpleXsdSchema(findInClasspath(endpoint.getResourcePattern()));
    }

    /**
     * Returns new {@link MessageDispatcherServlet} instance
     *
     * @return {@link MessageDispatcherServlet}
     */
    public MessageDispatcherServlet createMessageDispatcherServlet(final WsAddressingProperty property) {
        final MessageDispatcherServlet dispatcherServlet = new MessageDispatcherServlet();
        dispatcherServlet.setTransformWsdlLocations(property.isTransformWsdlLocations());
        dispatcherServlet.setEnableLoggingRequestDetails(property.isEnableLoggingRequests());
        dispatcherServlet.setTransformSchemaLocations(property.isTransformSchemaLocations());
        dispatcherServlet.setNamespace(property.getNamespace());
        return dispatcherServlet;
    }
}
