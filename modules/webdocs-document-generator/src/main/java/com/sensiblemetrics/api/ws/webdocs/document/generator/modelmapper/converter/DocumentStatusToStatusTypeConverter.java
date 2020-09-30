package com.sensiblemetrics.api.ws.webdocs.document.generator.modelmapper.converter;

import com.sensiblemetrics.api.ws.webdocs.document.generator.enumeration.StatusType;
import com.sensiblemetrics.api.ws.webdocs.document.generator.generated.DocumentStatus;
import org.apache.commons.lang3.EnumUtils;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;

/** {@link DocumentStatus} to {@link StatusType} {@link Converter} implementation */
@Component
public class DocumentStatusToStatusTypeConverter implements Converter<DocumentStatus, StatusType> {
  /**
   * Returns converted {@link StatusType} from {@link DocumentStatus} by input {@link
   * MappingContext}
   *
   * @param context - initial input {@link MappingContext} to convert from
   * @return converted {@link StatusType}
   */
  @Override
  public StatusType convert(final MappingContext<DocumentStatus, StatusType> context) {
    return this.convertToStatusType(context.getSource());
  }

  /**
   * Returns {@link StatusType} by input {@link DocumentStatus}
   *
   * @param status initial input {@link DocumentStatus} to operate by
   * @return new {@link StatusType} enumeration
   */
  private StatusType convertToStatusType(final DocumentStatus status) {
    return EnumUtils.getEnumIgnoreCase(StatusType.class, status.name());
  }
}
