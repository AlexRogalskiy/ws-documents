package com.sensiblemetrics.api.ws.actuator.indicator;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * Task scheduler {@link AbstractHealthIndicator} implementation
 */
@RequiredArgsConstructor
public class WsTaskSchedulerHealthIndicator extends AbstractHealthIndicator {
    private final ThreadPoolTaskScheduler taskScheduler;

    /**
     * {@inheritDoc}
     *
     * @see AbstractHealthIndicator
     */
    @Override
    protected void doHealthCheck(final Health.Builder builder) {
        final int poolSize = this.taskScheduler.getPoolSize();
        final int active = this.taskScheduler.getActiveCount();
        final int free = poolSize - active;

        builder
                .withDetail("active", active)
                .withDetail("poolsize", poolSize);
        if (poolSize > 0 && free <= 1) {
            builder.down();
        } else {
            builder.up();
        }
    }
}
