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
@ConfigurationProperties(prefix = DocumentArchiveProperty.PROPERTY_PREFIX, ignoreInvalidFields = true)
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
@Description("SensibleMetrics Document Generator Web Service archive configuration properties")
public class DocumentArchiveProperty {
    /**
     * Default endpoints property prefix
     */
    public static final String PROPERTY_PREFIX = "ws-document.archive";

    /**
     * Default archive base path
     */
    @NotBlank(message = "{property.ws-document.archive.base-path.notBlank}")
    private String basePath = "/storage";

    /**
     * Default archive name prefix
     */
    @NotBlank(message = "{property.ws-document.archive.name-prefix.notBlank}")
    private String namePrefix = "document-";

    /**
     * Default posix file permissions
     */
    @NotBlank(message = "{property.ws-document.archive.posix-file-permissions.notBlank}")
    private String posixFilePermissions = "rwxrwxrwx";
}
