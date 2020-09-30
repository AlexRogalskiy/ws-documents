package com.sensiblemetrics.api.ws.logger.handler;

import com.sensiblemetrics.api.ws.logger.property.WsLoggingProperty;
import org.slf4j.MDC;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.sensiblemetrics.api.ws.commons.utils.ServiceUtils.enumerationAsStream;

/**
 * Log headers {@link OncePerRequestFilter} implementation
 */
public class LogHeadersToMdcFilter extends OncePerRequestFilter {
    private final Set<String> headerNames;
    private final String pattern;

    /**
     * Default {@link LogHeadersToMdcFilter} constructor by input {@link WsLoggingProperty.HeadersHandler}
     *
     * @param headersHandler - initial input {@link WsLoggingProperty.HeadersHandler}
     */
    public LogHeadersToMdcFilter(final WsLoggingProperty.HeadersHandler headersHandler) {
        Assert.notNull(headersHandler, "Headers handler property should not be null");

        this.headerNames = headersHandler.getNames();
        this.pattern = headersHandler.getPattern();
    }

    /**
     * {@inheritDoc}
     *
     * @see OncePerRequestFilter
     */
    @Override
    protected void doFilterInternal(@NonNull final HttpServletRequest request,
                                    @NonNull final HttpServletResponse response,
                                    @NonNull final FilterChain filterChain) throws ServletException, IOException {
        try {
            this.addHeaders(this.headerNames, request);
            filterChain.doFilter(request, response);
        } finally {
            this.removeHeaders(this.headerNames);
        }
    }

    /**
     * Removes input {@link List} of {@link String} headers from current {@link MDC} storage
     *
     * @param headerNames - initial input {@link List} of {@link String} headers to remove
     */
    private void removeHeaders(final Set<String> headerNames) {
        headerNames.forEach(MDC::remove);
    }

    /**
     * Updates current {@link MDC} storage by input {@link List} of {@link String} headers and {@link HttpServletRequest}
     *
     * @param headerNames - initial input {@link Set} of {@link String} headers to filter by
     * @param request     - initial input {@link HttpServletRequest} to process by
     */
    private void addHeaders(final Set<String> headerNames,
                            final HttpServletRequest request) {
        if (!CollectionUtils.isEmpty(headerNames)) {
            headerNames.stream()
                    .filter(name -> name.matches(this.pattern))
                    .forEach(name -> {
                        final String headerValue = request.getHeader(name);
                        Optional.ofNullable(headerValue).ifPresent(value -> MDC.put(name, value));
                    });
        } else {
            enumerationAsStream(request.getHeaderNames())
                    .filter(name -> name.matches(this.pattern))
                    .forEach(name -> MDC.put(name, request.getHeader(name)));
        }
    }
}
