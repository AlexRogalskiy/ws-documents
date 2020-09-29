package com.sensiblemetrics.api.ws.metrics.management;

import org.springframework.stereotype.Component;

import static io.micrometer.core.instrument.Timer.Sample;

@Component
public class SampleStore {
  private static final InheritableThreadLocal<Sample> threadLocal = new InheritableThreadLocal<>();

  public void set(final Sample sample) {
    threadLocal.set(sample);
  }

  public Sample get() {
    return threadLocal.get();
  }
}
