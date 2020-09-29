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
    prefix = WsDocumentStorageProperty.PROPERTY_PREFIX,
    ignoreInvalidFields = true)
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
@Description(
    "SensibleMetrics Document Generator Web Service Document Storage configuration properties")
public class WsDocumentStorageProperty {
  /** Default document storage property prefix */
  public static final String PROPERTY_PREFIX =
      DEFAULT_PROPERTY_PREFIX + DEFAULT_PROPERTY_DELIMITER + "storage";

  /** Default archive base path */
  @NotBlank(message = "{property.storage.base-path.notBlank}")
  private String basePath;

  /** Default file name prefix */
  @NotBlank(message = "{property.storage.file-name-prefix.notBlank}")
  private String fileNamePrefix = "document-";

  /** Default file extension */
  @NotBlank(message = "{property.storage.file-extension.notBlank}")
  private String fileExtension = "docx";

  /** Default posix file permissions */
  @NotBlank(message = "{property.storage.posix-file-permissions.notBlank}")
  private String posixFilePermissions = "rwxrwxrwx";
}
