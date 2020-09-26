package com.sensiblemetrics.api.ws.metrics.management;

public interface MetricsManager {
    /**
     * To monitor counts & time
     */
    void trackTimerMetrics(final String metricName,
                           final String... tags);

    /**
     * To monitor incremental count
     */
    void trackCounterMetrics(final String metricName,
                             final double increment,
                             final String... tags);
}
