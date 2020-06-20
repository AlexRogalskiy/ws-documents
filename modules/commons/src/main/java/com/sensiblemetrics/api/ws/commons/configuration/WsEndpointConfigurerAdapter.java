package com.sensiblemetrics.api.ws.commons.configuration;

import com.sensiblemetrics.api.ws.commons.property.WsEndpointProperty;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Description;
import org.springframework.context.annotation.Role;
import org.springframework.stereotype.Component;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

import static com.sensiblemetrics.api.ws.commons.utils.ServiceUtils.findInClasspath;

@Component
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
@Description("SensibleMetrics Commons WS configurer adapter")
public class WsEndpointConfigurerAdapter {

    /**
     * Returns {@link DefaultWsdl11Definition} by input {@link XsdSchema} and {@link WsEndpointProperty.WsEndpoint} properties
     *
     * @param xsdSchema initial input {@link XsdSchema} to operate by
     * @param endpoint  initial input {@link WsEndpointProperty.WsEndpoint} properties to operate by
     * @return new {@link DefaultWsdl11Definition} instance
     */
    public DefaultWsdl11Definition createWsdl11Definition(final XsdSchema xsdSchema,
                                                          final WsEndpointProperty.WsEndpoint endpoint) {
        final DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName(endpoint.getPortTypeName());
        wsdl11Definition.setServiceName(endpoint.getServiceName());
        wsdl11Definition.setLocationUri(endpoint.getLocationUri());
        wsdl11Definition.setTargetNamespace(endpoint.getTargetNamespace());
        wsdl11Definition.setSchema(xsdSchema);
        return wsdl11Definition;
    }

    /**
     * Returns simple {@link XsdSchema} by input {@link WsEndpointProperty.WsEndpoint} properties
     *
     * @param endpoint initial input {@link WsEndpointProperty.WsEndpoint} properties to operate by
     * @return new simple {@link XsdSchema} instance
     */
    public XsdSchema createSimpleXsdSchema(final WsEndpointProperty.WsEndpoint endpoint) {
        return new SimpleXsdSchema(findInClasspath(endpoint.getResourcePattern()));
    }
}
