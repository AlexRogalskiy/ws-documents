package com.sensiblemetrics.api.ws.commons.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;

@Getter
@RequiredArgsConstructor
public enum ErrorTemplateType {
    SERVICE_UNAVAILABLE("error-30", "error.service.unavailable"),
    SERVICE_OPERATION_ERROR("error-40", "error.operation.invalid"),
    BAD_REQUEST("error-50", "error.request.invalid"),
    DOCUMENT_PROCESSING_ERROR("error-50", "error.document.processing.invalid"),
    INVALID_ENDPOINT_CONFIGURATION("error-60", "error.endpoint.configuration.invalid");

    /**
     * Default {@link String} error code
     */
    private final String errorCode;
    /**
     * Default {@link String} error messsage
     */
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

    /**
     * Returns {@link List} of {@link ErrorTemplateType}s
     *
     * @return {@link List} of {@link ErrorTemplateType}s
     */
    @NonNull
    public static List<ErrorTemplateType> buildErrorTemplateList() {
        return Collections.unmodifiableList(asList(ErrorTemplateType.values()));
    }

    @Override
    public String toString() {
        return this.errorCode;
    }
}
