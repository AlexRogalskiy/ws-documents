package com.sensiblemetrics.api.ws.document.generator.modelmapper.converter;

import com.sensiblemetrics.api.ws.document.generator.generated.OperationStatus;
import com.sensiblemetrics.api.ws.document.generator.model.domain.FileInfo;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;

/** {@link FileInfo} to {@link OperationStatus} {@link Converter} implementation */
@Component
public class FileInfoToOperationStatusConverter implements Converter<FileInfo, OperationStatus> {
  /**
   * Returns converted {@link OperationStatus} from {@link FileInfo} by input {@link MappingContext}
   *
   * @param context - initial input {@link MappingContext} to convert from
   * @return converted {@link OperationStatus}
   */
  @Override
  public OperationStatus convert(final MappingContext<FileInfo, OperationStatus> context) {
    return context.getSource().isExists() ? OperationStatus.SUCCESS : OperationStatus.FAIL;
  }
}
