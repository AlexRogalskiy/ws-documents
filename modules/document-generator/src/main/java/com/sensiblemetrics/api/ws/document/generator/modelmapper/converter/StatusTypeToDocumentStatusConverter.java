package com.sensiblemetrics.api.ws.document.generator.modelmapper.converter;

import com.sensiblemetrics.api.ws.document.generator.enumeration.StatusType;
import com.sensiblemetrics.api.ws.document.generator.generated.DocumentStatus;
import org.apache.commons.lang3.EnumUtils;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;

/**
 * {@link StatusType} to {@link DocumentStatus} {@link Converter} implementation
 */
@Component
public class StatusTypeToDocumentStatusConverter implements Converter<StatusType, DocumentStatus> {
    /**
     * Returns converted {@link DocumentStatus} from {@link StatusType} by input {@link MappingContext}
     *
     * @param context - initial input {@link MappingContext} to convert from
     * @return converted {@link DocumentStatus}
     */
    @Override
    public DocumentStatus convert(final MappingContext<StatusType, DocumentStatus> context) {
        return this.convertToStatus(context.getSource());
    }

    /**
     * Returns {@link DocumentStatus} by input {@link StatusType}
     *
     * @param statusType initial input {@link StatusType} to operate by
     * @return new {@link DocumentStatus} enumeration
     */
    private DocumentStatus convertToStatus(final StatusType statusType) {
        return EnumUtils.getEnumIgnoreCase(DocumentStatus.class, statusType.name());
    }
}
