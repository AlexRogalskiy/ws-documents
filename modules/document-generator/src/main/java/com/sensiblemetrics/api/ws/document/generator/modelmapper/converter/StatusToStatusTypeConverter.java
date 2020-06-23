package com.sensiblemetrics.api.ws.document.generator.modelmapper.converter;

import com.sensiblemetrics.api.ws.document.generator.enumeration.StatusType;
import com.sensiblemetrics.api.ws.document.generator.generated.Status;
import org.apache.commons.lang3.EnumUtils;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;

/**
 * {@link Status} to {@link StatusType} {@link Converter} implementation
 */
@Component
public class StatusToStatusTypeConverter implements Converter<Status, StatusType> {
    /**
     * Returns converted {@link StatusType} from {@link Status} by input {@link MappingContext}
     *
     * @param context - initial input {@link MappingContext} to convert from
     * @return converted {@link StatusType}
     */
    @Override
    public StatusType convert(final MappingContext<Status, StatusType> context) {
        return this.convertToStatusType(context.getSource());
    }

    /**
     * Returns {@link StatusType} by input {@link Status}
     *
     * @param status initial input {@link Status} to operate by
     * @return new {@link StatusType} enumeration
     */
    private StatusType convertToStatusType(final Status status) {
        return EnumUtils.getEnumIgnoreCase(StatusType.class, status.name());
    }
}
