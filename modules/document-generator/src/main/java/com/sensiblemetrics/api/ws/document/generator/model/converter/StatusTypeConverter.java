package com.sensiblemetrics.api.ws.document.generator.model.converter;

import com.sensiblemetrics.api.ws.document.generator.enumeration.StatusType;
import org.apache.commons.lang3.EnumUtils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Optional;

@Converter(autoApply = true)
public class StatusTypeConverter implements AttributeConverter<StatusType, String> {

    @Override
    public String convertToDatabaseColumn(final StatusType statusType) {
        return Optional.ofNullable(statusType).map(Enum::name).orElse(null);
    }

    @Override
    public StatusType convertToEntityAttribute(final String value) {
        return EnumUtils.getEnum(StatusType.class, value);
    }
}
