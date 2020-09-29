package com.sensiblemetrics.api.ws.document.generator.property;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Description;
import org.springframework.context.annotation.Role;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

import static com.sensiblemetrics.api.ws.commons.property.PropertySettings.DEFAULT_PROPERTY_DELIMITER;
import static com.sensiblemetrics.api.ws.commons.property.PropertySettings.DEFAULT_PROPERTY_PREFIX;

@Data
@Validated
@Accessors(chain = true)
@ConfigurationProperties(
    prefix = WsDocumentTemplateProperty.PROPERTY_PREFIX,
    ignoreInvalidFields = true)
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
@Description(
    "SensibleMetrics Document Generator Web Service Document Template configuration properties")
public class WsDocumentTemplateProperty {
  /** Default document template property prefix */
  public static final String PROPERTY_PREFIX =
      DEFAULT_PROPERTY_PREFIX + DEFAULT_PROPERTY_DELIMITER + "template";

  /** Default template name pattern */
  @NotBlank(message = "{property.template.name-pattern.notBlank}")
  private String namePattern;
}
