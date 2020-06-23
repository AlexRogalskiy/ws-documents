package com.sensiblemetrics.api.ws.document.generator.modelmapper.converter;

import com.sensiblemetrics.api.ws.document.generator.enumeration.StatusType;
import com.sensiblemetrics.api.ws.document.generator.generated.Status;
import org.apache.commons.lang3.EnumUtils;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;

/**
 * {@link StatusType} to {@link Status} {@link Converter} implementation
 */
@Component
public class StatusTypeToStatusConverter implements Converter<StatusType, Status> {
    /**
     * Returns converted {@link Status} from {@link StatusType} by input {@link MappingContext}
     *
     * @param context - initial input {@link MappingContext} to convert from
     * @return converted {@link Status}
     */
    @Override
    public Status convert(final MappingContext<StatusType, Status> context) {
        return this.convertToStatus(context.getSource());
    }

    /**
     * Returns {@link Status} by input {@link StatusType}
     *
     * @param statusType initial input {@link StatusType} to operate by
     * @return new {@link Status} enumeration
     */
    private Status convertToStatus(final StatusType statusType) {
        return EnumUtils.getEnumIgnoreCase(Status.class, statusType.name());
    }
}
