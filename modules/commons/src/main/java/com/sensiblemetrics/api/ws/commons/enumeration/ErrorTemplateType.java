package com.sensiblemetrics.api.ws.commons.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum ErrorTemplateType {
    SERVICE_UNAVAILABLE("error-30", "error.service.unavailable"),
    SERVICE_OPERATION_ERROR("error-40", "error.operation.invalid"),
    BAD_REQUEST("error-50", "error.request.invalid"),
    DOCUMENT_PROCESSING_ERROR("error-50", "error.document.processing.invalid"),
    INVALID_ENDPOINT_CONFIGURATION("error-60", "error.endpoint.configuration.invalid");

    private final String errorCode;
    private final String errorMessage;

    /**
     * Returns {@link ErrorTemplateType} by input {@link String} code
     *
     * @param value - initial input {@link String} code
     * @return {@link ErrorTemplateType}
     */
    @Nullable
    public static ErrorTemplateType findByCode(final String value) {
        return Arrays.stream(values())
                .filter(type -> type.getErrorCode().equalsIgnoreCase(value))
                .findFirst()
                .orElse(null);
    }
}
