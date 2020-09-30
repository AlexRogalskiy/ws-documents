package com.sensiblemetrics.api.ws.webdocs.document.generator.converter;

import com.google.common.collect.ImmutableMap;
import com.sensiblemetrics.api.ws.webdocs.document.generator.model.entity.DocumentEntity;
import com.sensiblemetrics.api.ws.webdocs.document.generator.property.WsDocumentTemplateFormatProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Map;

import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;

@Component
public class DocumentEntityToMapConverter
    implements Converter<DocumentEntity, Map<String, String>> {
  private final WsDocumentTemplateFormatProperty documentTemplateFormatProperty;

  @Lazy
  @Autowired
  public DocumentEntityToMapConverter(
      final WsDocumentTemplateFormatProperty documentTemplateFormatProperty) {
    this.documentTemplateFormatProperty = documentTemplateFormatProperty;
  }

  /**
   * Returns property {@link Map} by input {@link DocumentEntity} source
   *
   * @param source - initial input {@link DocumentEntity} source to convert from
   * @return property {@link Map}
   */
  @NonNull
  @Override
  public Map<String, String> convert(@NonNull final DocumentEntity source) {
    final WsDocumentTemplateFormatProperty.MarkerMappings mappings =
        this.documentTemplateFormatProperty.getMarkerMappings();

    final String docIdFormat = this.format(source.getId(), mappings.getId().getPattern());
    final String docCompanyFormat =
        this.format(source.getCompany(), mappings.getCompany().getPattern());
    final String docPartnerFormat =
        this.format(source.getPartner(), mappings.getPartner().getPattern());
    final String docProductFormat =
        this.format(source.getProduct(), mappings.getProduct().getPattern());
    final String docAmountFormat = this.formatAmount(source.getAmount());
    final String docPriceFormat = this.formatPrice(source.getPrice());
    final String docDataFormat = this.format(source.getData(), mappings.getData().getPattern());
    final String docDateFormat =
        this.formatDate(
            source.getCreatedDate().orElse(Instant.now()), mappings.getDate().getPattern());
    final String docTotalFormat =
        this.formatPrice(source.getPrice().multiply(BigDecimal.valueOf(source.getAmount())));

    return ImmutableMap.<String, String>builder()
        .put(mappings.getId().getName(), docIdFormat)
        .put(mappings.getCompany().getName(), docCompanyFormat)
        .put(mappings.getPartner().getName(), docPartnerFormat)
        .put(mappings.getProduct().getName(), docProductFormat)
        .put(mappings.getAmount().getName(), docAmountFormat)
        .put(mappings.getPrice().getName(), docPriceFormat)
        .put(mappings.getData().getName(), docDataFormat)
        .put(mappings.getDate().getName(), docDateFormat)
        .put(mappings.getTotal().getName(), docTotalFormat)
        .build();
  }

  private String getPattern(final String pattern) {
    return defaultIfNull(pattern, this.documentTemplateFormatProperty.getPattern());
  }

  private Locale getLocale() {
    return this.documentTemplateFormatProperty.getLocale();
  }

  private <T> String format(final T value, final String pattern) {
    return String.format(this.getLocale(), this.getPattern(pattern), value);
  }

  private String formatDate(final Instant date, final String datePattern) {
    final DateTimeFormatter dateTimeFormatter =
        DateTimeFormatter.ofPattern(datePattern)
            .withLocale(this.getLocale())
            .withZone(ZoneId.systemDefault());
    return dateTimeFormatter.format(date);
  }

  private String formatAmount(final Integer amount) {
    return NumberFormat.getIntegerInstance(this.getLocale()).format(amount);
  }

  private String formatPrice(final BigDecimal price) {
    return NumberFormat.getCurrencyInstance(this.getLocale()).format(price);
  }
}
