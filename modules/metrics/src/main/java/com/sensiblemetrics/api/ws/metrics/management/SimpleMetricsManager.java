package com.sensiblemetrics.api.ws.metrics.management;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * <code>
 * private static int metric = 0;
 *
 * @Autowired private MetricsManager metricsManager;
 * @Autowired private SampleStore sampleStore;
 * @GetMapping("/some/endpoint") public int getMetric() {
 * sampleStore.set(Timer.start(Clock.SYSTEM));
 * int inc = new Random().nextInt(10);
 * metric += inc;
 * metricsManager.trackCounterMetrics("metric.count", inc, "sampleAttr", "Attr1");
 * metricsManager.trackTimerMetrics("metric.transaction", "sampleAttr", "Attr2");
 * return metric;
 * }
 * </code>
 */
@Component
@RequiredArgsConstructor
public class SimpleMetricsManager implements MetricsManager {
  private final MeterRegistry meterRegistry;
  private final SampleStore sampleStore;

  @Override
  public void trackTimerMetrics(final String metricName, final String... tags) {
    final Timer timer = this.meterRegistry.timer(metricName, tags);
    this.sampleStore.get().stop(timer);
  }

  @Override
  public void trackCounterMetrics(
      final String metricName, final double value, final String... tags) {
    this.meterRegistry.counter(metricName, tags).increment(value);
  }
}
