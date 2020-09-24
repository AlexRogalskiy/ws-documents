package com.sensiblemetrics.api.ws.commons.indicator;

import com.google.common.collect.ImmutableMap;
import com.sensiblemetrics.api.ws.commons.property.WsApiStatusProperty;
import com.sensiblemetrics.api.ws.commons.property.WsInfoServiceProperty;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.boot.actuate.health.Status;
import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.boot.actuate.metrics.MetricsEndpoint;
import org.springframework.context.annotation.Description;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.sensiblemetrics.api.ws.commons.property.WsInfoServiceProperty.InfoMetrics.LOGBACK_EVENTS_METRIC;
import static com.sensiblemetrics.api.ws.commons.property.WsInfoServiceProperty.InfoMetrics.PROCESS_UPTIME_METRIC;
import static org.apache.commons.lang3.StringUtils.EMPTY;

/**
 * {@link InfoContributor} service implementation
 */
@Service
@RequiredArgsConstructor
@Description("SensibleMetrics Commons Web Service Api Status info configuration service")
public class WsApiStatusInfoContributor implements InfoContributor {
    /**
     * Default {@link List} collection of {@link String} level tags
     */
    public static final List<String> LOGBACK_ERROR_LEVEL_TAG = Collections.singletonList("level:error");

    /**
     * {@link MetricsEndpoint} instance
     */
    private final MetricsEndpoint metricsEndpoint;
    private final HealthEndpoint healthEndpoint;
    private final WsInfoServiceProperty infoServiceProperty;
    private final WsApiStatusProperty apiStatusProperty;

    @Override
    public void contribute(final Info.Builder builder) {
        builder.withDetails(this.getInfoDetails());
    }

    private Map<String, Object> getInfoDetails() {
        return ImmutableMap.<String, Object>builder()
                .put(this.infoServiceProperty.getParameterNames().getServerName(), this.apiStatusProperty.getNode().getName())
                .put(this.infoServiceProperty.getParameterNames().getBuildNumberName(), this.apiStatusProperty.getBuild().getVersion())
                .put(this.infoServiceProperty.getParameterNames().getServerUptimeName(), this.getUptimeMetric())
                .put(this.infoServiceProperty.getParameterNames().getStateName(), this.getApplicationStateMetric())
                .put(this.infoServiceProperty.getParameterNames().getStartedStatusName(), this.getApplicationStartedStatusMetric())
                .put(this.infoServiceProperty.getParameterNames().getErrorsCounterName(), this.getErrorCounterMetric())
                .put(this.infoServiceProperty.getParameterNames().getErrorDescriptionName(), EMPTY)
                .build();
    }

    private String getUptimeMetric() {
        final MetricsEndpoint.MetricResponse uptimeMetricResponse = this.metricsEndpoint.metric(PROCESS_UPTIME_METRIC, null);
        final long uptimeMilliseconds = (long) (uptimeMetricResponse.getMeasurements().get(0).getValue() * 1000);
        return DurationFormatUtils.formatDuration(uptimeMilliseconds, this.infoServiceProperty.getSettings().getDurationFormat());
    }

    private boolean getApplicationStartedStatusMetric() {
        final MetricsEndpoint.MetricResponse uptimeMetric = this.metricsEndpoint.metric(PROCESS_UPTIME_METRIC, null);
        return uptimeMetric.getMeasurements().get(0).getValue().longValue() > 0;
    }

    private long getErrorCounterMetric() {
        final MetricsEndpoint.MetricResponse errorMetric = this.metricsEndpoint.metric(LOGBACK_EVENTS_METRIC, LOGBACK_ERROR_LEVEL_TAG);
        return errorMetric.getMeasurements().get(0).getValue().longValue();
    }

    private ApplicationStateType getApplicationStateMetric() {
        return Objects.equals(this.healthEndpoint.health().getStatus(), Status.UP) ? ApplicationStateType.OK : ApplicationStateType.DOWN;
    }
}
