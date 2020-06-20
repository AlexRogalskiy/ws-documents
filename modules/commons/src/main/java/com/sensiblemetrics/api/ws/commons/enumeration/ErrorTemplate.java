package com.sensiblemetrics.api.ws.commons.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum ErrorTemplate {
    SERVICE_UNAVAILABLE("error-30", "error.service.unavailable"),
    SERVICE_OPERATION_ERROR("error-40", "error.operation.invalid");

    private final String errorCode;
    private final String errorMessage;

    /**
     * Returns {@link ErrorTemplate} by input {@link String} code
     *
     * @param value - initial input {@link String} code
     * @return {@link ErrorTemplate}
     */
    @Nullable
    public static ErrorTemplate findByCode(final String value) {
        return Arrays.stream(values())
                .filter(type -> type.getErrorCode().equalsIgnoreCase(value))
                .findFirst()
                .orElse(null);
    }
}
