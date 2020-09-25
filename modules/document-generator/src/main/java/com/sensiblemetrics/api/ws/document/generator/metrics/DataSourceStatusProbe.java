package com.sensiblemetrics.api.ws.document.generator.metrics;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.Tags;
import io.micrometer.core.instrument.binder.MeterBinder;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DataSourceStatusProbe implements MeterBinder {
    private static final String SELECT_1 = "SELECT 1;";
    private static final int QUERY_TIMEOUT = 1;
    private static final double UP = 1.0;
    private static final double DOWN = 0.0;

    private final String name;
    private final String description;
    private final Iterable<Tag> tags;
    private final DataSource dataSource;

    public DataSourceStatusProbe(final DataSource dataSource) {
        Assert.notNull(dataSource, "dataSource cannot be null");
        this.dataSource = dataSource;
        this.name = "data_source";
        this.description = "DataSource status";
        this.tags = tags(dataSource);
    }

    private boolean status() {
        try (
                final Connection connection = this.dataSource.getConnection();
                final PreparedStatement statement = connection.prepareStatement(SELECT_1)
        ) {
            statement.setQueryTimeout(QUERY_TIMEOUT);
            statement.executeQuery();
            return true;
        } catch (SQLException ignored) {
            return false;
        }
    }

    @Override
    public void bindTo(@NonNull final MeterRegistry meterRegistry) {
        Gauge.builder(this.name, this, value -> value.status() ? UP : DOWN)
                .description(this.description)
                .tags(this.tags)
                .baseUnit("status")
                .register(meterRegistry);
    }

    protected static Iterable<Tag> tags(final DataSource dataSource) {
        Assert.notNull(dataSource, "dataSource cannot be null");

        try {
            return Tags.of(Tag.of("url", dataSource.getConnection().getMetaData().getURL()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
