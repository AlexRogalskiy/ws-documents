package com.sensiblemetrics.api.ws.document.generator.property;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Description;
import org.springframework.context.annotation.Role;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Data
@Validated
@Accessors(chain = true)
@ConfigurationProperties(prefix = DocumentTemplateProperty.PROPERTY_PREFIX, ignoreInvalidFields = true)
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
@Description("SensibleMetrics Document Generator Web Service template configuration properties")
public class DocumentTemplateProperty {
    /**
     * Default endpoints property prefix
     */
    public static final String PROPERTY_PREFIX = "ws-document.template";

    /**
     * Default template name pattern
     */
    @NotBlank(message = "{property.ws-document.template.name-pattern.notBlank}")
    private String namePattern;
}
