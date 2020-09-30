package com.sensiblemetrics.api.ws.webdocs.document.generator.property;

import com.sensiblemetrics.api.ws.webdocs.commons.constraint.NullOrNotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Description;
import org.springframework.context.annotation.Role;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Locale;

import static com.sensiblemetrics.api.ws.webdocs.commons.property.PropertySettings.DEFAULT_PROPERTY_DELIMITER;

@Data
@Validated
@Accessors(chain = true)
@ConfigurationProperties(
    prefix = WsDocumentTemplateFormatProperty.PROPERTY_PREFIX,
    ignoreInvalidFields = true)
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
@Description(
    "SensibleMetrics Document Generator Web Service Document Template Format configuration"
        + " properties")
public class WsDocumentTemplateFormatProperty {
  /** Default document template format property prefix */
  public static final String PROPERTY_PREFIX =
      WsDocumentTemplateProperty.PROPERTY_PREFIX + DEFAULT_PROPERTY_DELIMITER + "format";

  /** Default locale */
  @NotNull(message = "{property.template.format.locale.notnull}")
  private Locale locale = Locale.getDefault();

  /** Default pattern */
  @NotBlank(message = "{property.template.format.pattern.notBlank}")
  private String pattern = "%s";

  /** Default marker mappings */
  @Valid
  @NotNull(message = "{property.template.format.marker-mappings.notnull}")
  private MarkerMappings markerMappings = new MarkerMappings();

  @Data
  @Validated
  @Accessors(chain = true)
  public static class MarkerMappings {
    @Valid
    @NotNull(message = "{property.template.format.marker-mappings.id.notNull}")
    private Marker id;

    @Valid
    @NotNull(message = "{property.template.format.marker-mappings.company.notNull}")
    private Marker company;

    @Valid
    @NotNull(message = "{property.template.format.marker-mappings.partner.notNull}")
    private Marker partner;

    @Valid
    @NotNull(message = "{property.template.format.marker-mappings.product.notNull}")
    private Marker product;

    @Valid
    @NotNull(message = "{property.template.format.marker-mappings.amount.notNull}")
    private Marker amount;

    @Valid
    @NotNull(message = "{property.template.format.marker-mappings.price.notNull}")
    private Marker price;

    @Valid
    @NotNull(message = "{property.template.format.marker-mappings.data.notNull}")
    private Marker data;

    @Valid
    @NotNull(message = "{property.template.format.marker-mappings.date.notNull}")
    private Marker date;

    @Valid
    @NotNull(message = "{property.template.format.marker-mappings.total.notNull}")
    private Marker total;
  }

  @Data
  @Validated
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Marker {
    /** Marker name */
    @NotBlank(message = "{property.template.format.marker.name.notBlank}")
    private String name;
    /** Marker pattern */
    @NullOrNotBlank(message = "{property.template.format.marker.pattern.nullOrNotBlank}")
    private String pattern;
  }
}
