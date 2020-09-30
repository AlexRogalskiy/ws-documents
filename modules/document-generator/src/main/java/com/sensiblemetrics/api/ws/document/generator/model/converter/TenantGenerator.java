package com.sensiblemetrics.api.ws.document.generator.model.converter;

import org.hibernate.Session;
import org.hibernate.tuple.ValueGenerator;

import java.util.Optional;

/**
 * {@link String} {@link ValueGenerator} implementation
 */
public class TenantGenerator implements ValueGenerator<String> {
    /**
     * {@inheritDoc}
     *
     * @see ValueGenerator
     */
    @Override
    public String generateValue(final Session session, final Object owner) {
        return Optional.ofNullable(session.getTenantIdentifier())
                .orElse(Thread.currentThread().getThreadGroup().getName());
    }
}
