package com.sensiblemetrics.api.ws.actuator.indicator;

import lombok.Value;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;

import java.util.concurrent.atomic.AtomicReference;

public class StatefulHealthIndicator extends AbstractHealthIndicator {
  private final AtomicReference<HealthState> state =
      new AtomicReference<>(new HealthState(HealthStateStatus.WARNING, System.currentTimeMillis()));

  @Override
  protected void doHealthCheck(final Health.Builder builder) {
    final HealthState healthState = this.state.get();
    switch (healthState.status) {
      case OK:
        builder.up().withDetail("lastChanged", healthState.lastChanged);
        break;
      case WARNING:
        builder.unknown().withDetail("lastChanged", healthState.lastChanged);
        break;
      case CRITICAL:
        builder.down().withDetail("lastChanged", healthState.lastChanged);
        break;
      default:
        builder.unknown();
        break;
    }
  }

  public void enterCriticalState() {
    this.updateState(HealthStateStatus.CRITICAL);
  }

  public void enterOkState() {
    this.updateState(HealthStateStatus.OK);
  }

  private void updateState(final HealthStateStatus stateStatus) {
    HealthState oldState;
    HealthState newState;
    do {
      oldState = this.state.get();
      newState = new HealthState(stateStatus, System.currentTimeMillis());
    } while (!this.state.compareAndSet(oldState, newState));
  }

  @Value
  private static class HealthState {
    HealthStateStatus status;
    long lastChanged;
  }

  private enum HealthStateStatus {
    OK,
    WARNING,
    CRITICAL
  }
}
