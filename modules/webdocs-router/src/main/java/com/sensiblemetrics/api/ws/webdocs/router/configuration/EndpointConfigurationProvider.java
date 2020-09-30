package com.sensiblemetrics.api.ws.webdocs.router.configuration;

import com.sensiblemetrics.api.ws.webdocs.commons.exception.EndpointConfigurationException;
import com.sensiblemetrics.api.ws.webdocs.router.property.WsAddressingProperty;

import java.util.Optional;

import static com.sensiblemetrics.api.ws.webdocs.commons.exception.EndpointConfigurationException.throwInvalidConfiguration;

/** Provides configuration information on web service endpoint by name */
@FunctionalInterface
public interface EndpointConfigurationProvider {
  /**
   * Returns {@link WsAddressingProperty.WsEndpoint} configuration by input {@link String} endpoint
   * name
   *
   * @param endpointName initial input {@link String} endpoint name to fetch by
   * @return {@link WsAddressingProperty.WsEndpoint} configuration
   */
  WsAddressingProperty.WsEndpoint getEndpoint(final String endpointName);

  /**
   * Returns {@link WsAddressingProperty.WsEndpoint} configuration by input {@link String} endpoint
   * name or throw {@link EndpointConfigurationException}
   *
   * @param endpointName initial input {@link String} endpoint name to fetch by
   * @return {@link WsAddressingProperty.WsEndpoint} configuration
   * @throws EndpointConfigurationException if endpoint configuration is not available
   */
  default WsAddressingProperty.WsEndpoint getOrThrow(final String endpointName) {
    try {
      return Optional.ofNullable(this.getEndpoint(endpointName))
          .orElseThrow(() -> throwInvalidConfiguration(endpointName));
    } catch (Throwable ex) {
      throw new EndpointConfigurationException(ex);
    }
  }
}
