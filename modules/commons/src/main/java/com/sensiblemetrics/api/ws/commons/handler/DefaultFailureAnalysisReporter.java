package com.sensiblemetrics.api.ws.commons.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.diagnostics.FailureAnalysis;
import org.springframework.boot.diagnostics.FailureAnalysisReporter;
import org.springframework.util.StringUtils;

/**
 * {@link FailureAnalysisReporter} that logs the failure analysis.
 */
@Slf4j
public final class DefaultFailureAnalysisReporter implements FailureAnalysisReporter {

    @Override
    public void report(final FailureAnalysis failureAnalysis) {
        if (log.isDebugEnabled()) {
            log.debug("Application failed to start due to an exception", failureAnalysis.getCause());
        }
        if (log.isErrorEnabled()) {
            log.error(buildMessage(failureAnalysis));
        }
    }

    private String buildMessage(final FailureAnalysis failureAnalysis) {
        final StringBuilder builder = new StringBuilder();
        builder.append(String.format("%n%n"))
                .append(String.format("***************************%n"))
                .append(String.format("WEBDOCS APPLICATION FAILED TO START%n"))
                .append(String.format("***************************%n%n"))
                .append(String.format("Description:%n%n"))
                .append(String.format("%s%n", failureAnalysis.getDescription()));
        if (StringUtils.hasText(failureAnalysis.getAction())) {
            builder.append(String.format("%nAction:%n%n"))
                    .append(String.format("%s%n", failureAnalysis.getAction()));
        }
        return builder.toString();
    }
}
