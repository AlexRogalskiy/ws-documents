package com.sensiblemetrics.api.ws.metrics.configuration;

import io.micrometer.core.instrument.Meter;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Description;
import org.springframework.context.annotation.Role;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import static com.sensiblemetrics.api.ws.commons.utils.ServiceUtils.streamOf;

@Component
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
@Description("SensibleMetrics WebDocs Metrics configurer adapter")
public class WsMetricsConfigurerAdapter {
    /**
     * Returns meter tags {@link Predicate} by input {@link Set} collection of patterns
     *
     * @param patterns - initial input {@link Set} collection of patterns to match by
     * @return meter tags {@link Predicate}
     */
    public Predicate<Meter.Id> createMeterTagPredicate(final Set<String> patterns) {
        return tag -> streamOf(patterns).anyMatch(p -> Pattern.matches(p, tag.getName()));
    }
}
