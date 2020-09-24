package com.sensiblemetrics.api.ws.commons.property;

import com.sensiblemetrics.api.ws.commons.exception.EndpointConfigurationException;

import java.util.Optional;

import static com.sensiblemetrics.api.ws.commons.exception.EndpointConfigurationException.throwInvalidConfiguration;

/**
 * Provides configuration information on web service endpoint by name
 */
@FunctionalInterface
public interface EndpointConfigurationProvider {
    /**
     * Returns {@link WsRouteProperty.WsEndpoint} configuration by input {@link String} endpoint name
     *
     * @param endpointName initial input {@link String} endpoint name to fetch by
     * @return {@link WsRouteProperty.WsEndpoint} configuration
     */
    WsRouteProperty.WsEndpoint getEndpoint(final String endpointName);

    /**
     * Returns {@link WsRouteProperty.WsEndpoint} configuration by input {@link String} endpoint name or throw {@link EndpointConfigurationException}
     *
     * @param endpointName initial input {@link String} endpoint name to fetch by
     * @return {@link WsRouteProperty.WsEndpoint} configuration
     * @throws EndpointConfigurationException if endpoint configuration is not available
     */
    default WsRouteProperty.WsEndpoint getOrThrow(final String endpointName) {
        try {
            return Optional.ofNullable(this.getEndpoint(endpointName))
                    .orElseThrow(() -> throwInvalidConfiguration(endpointName));
        } catch (Throwable ex) {
            throw new EndpointConfigurationException(ex);
        }
    }
}
