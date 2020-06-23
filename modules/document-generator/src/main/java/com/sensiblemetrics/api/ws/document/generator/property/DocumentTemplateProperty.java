package com.sensiblemetrics.api.ws.document.generator.property;

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
    private String namePattern = "templates/invoice.docx";

    /**
     * Default template marker mappings
     */
    @Valid
    @NotNull(message = "{property.ws-document.template.marker-mappings.notnull}")
    private MarkerMappings markerMappings = new MarkerMappings();

    @Data
    @Validated
    @Accessors(chain = true)
    public static class MarkerMappings {
        @Valid
        @NotNull(message = "{property.ws-document.template.marker-mappings.id.notNull}")
        private Marker id;

        @Valid
        @NotNull(message = "{property.ws-document.template.marker-mappings.company.notNull}")
        private Marker company;

        @Valid
        @NotNull(message = "{property.ws-document.template.marker-mappings.partner.notNull}")
        private Marker partner;

        @Valid
        @NotNull(message = "{property.ws-document.template.marker-mappings.product.notNull}")
        private Marker product;

        @Valid
        @NotNull(message = "{property.ws-document.template.marker-mappings.amount.notNull}")
        private Marker amount;

        @Valid
        @NotNull(message = "{property.ws-document.template.marker-mappings.price.notNull}")
        private Marker price;

        @Valid
        @NotNull(message = "{property.ws-document.template.marker-mappings.data.notNull}")
        private Marker data;

        @Valid
        @NotNull(message = "{property.ws-document.template.marker-mappings.date.notNull}")
        private Marker date;

        @Valid
        @NotNull(message = "{property.ws-document.template.marker-mappings.total.notNull}")
        private Marker total;
    }

    @Data
    @Validated
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Marker {
        @NotBlank(message = "{property.ws-document.template.marker.name.notBlank}")
        private String name;
    }
}
