package com.sensiblemetrics.api.ws.actuator.indicator;

import com.sensiblemetrics.api.ws.actuator.property.WsGracefulShutdownProperty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.context.SmartLifecycle;
import org.springframework.lang.NonNull;

import javax.annotation.concurrent.ThreadSafe;

import static org.springframework.boot.actuate.health.Health.down;
import static org.springframework.boot.actuate.health.Health.up;

/**
 * Task scheduler {@link HealthIndicator} implementation
 */
@Slf4j
@ThreadSafe
@RequiredArgsConstructor
public class GracefulShutdownHealthIndicator implements SmartLifecycle, HealthIndicator {
    /**
     * Default shutdown {@link Marker} instance
     */
    private static final Marker SHUTDOWN_MARKER = MarkerFactory.getMarker("SERVICE_SHUTDOWN");

    private final WsGracefulShutdownProperty property;

    private volatile Health health = up().build();

    @Override
    public Health health() {
        return this.health;
    }

    @Override
    public void stop(@NonNull final Runnable callback) {
        try {
            this.waitForSettingHealthCheckToDown();
            log.info(SHUTDOWN_MARKER, "set health check to down");
            this.indicateDown();
            this.waitForShutdown();
        } catch (InterruptedException e) {
            log.error(SHUTDOWN_MARKER, "graceful shutdown interrupted", e);
        } finally {
            callback.run();
        }
    }

    private void indicateDown() {
        this.health = down().build();
    }

    private void waitForSettingHealthCheckToDown() throws InterruptedException {
        log.info(SHUTDOWN_MARKER, "shutdown signal received ...");
        Thread.sleep(this.property.getIndicateErrorAfter().toMillis());
    }

    private void waitForShutdown() throws InterruptedException {
        Thread.sleep(this.property.getPhaseOutAfter().toMillis());
        log.info(SHUTDOWN_MARKER, "grace period ended, starting shutdown now");
    }

    @Override
    public void start() {
    }

    @Override
    public void stop() {
    }

    @Override
    public boolean isRunning() {
        return true;
    }

    @Override
    public int getPhase() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isAutoStartup() {
        return true;
    }
}
