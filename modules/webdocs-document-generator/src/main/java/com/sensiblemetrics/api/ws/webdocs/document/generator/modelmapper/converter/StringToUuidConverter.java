package com.sensiblemetrics.api.ws.webdocs.document.generator.modelmapper.converter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

import java.util.UUID;

/** {@link String} to {@link UUID} {@link Converter} implementation */
@Slf4j
@Component
@RequiredArgsConstructor
public class StringToUuidConverter implements Converter<String, UUID> {
  private final ConversionService conversionService;

  /**
   * Returns converted {@link UUID} from {@link String} by input {@link MappingContext}
   *
   * @param context - initial input {@link MappingContext} to convert from
   * @return converted {@link UUID}
   */
  @Override
  public UUID convert(final MappingContext<String, UUID> context) {
    try {
      return this.conversionService.convert(context.getSource(), UUID.class);
    } catch (RuntimeException ex) {
      log.error("Cannot convert input source: {} to UUID format", context.getSource(), ex);
      return null;
    }
  }
}
