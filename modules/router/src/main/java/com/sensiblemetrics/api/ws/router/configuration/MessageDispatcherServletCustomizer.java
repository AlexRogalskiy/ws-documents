package com.sensiblemetrics.api.ws.router.configuration;

import org.springframework.ws.transport.http.MessageDispatcherServlet;

/** {@link MessageDispatcherServlet} customizer declaration */
@FunctionalInterface
public interface MessageDispatcherServletCustomizer {
  /**
   * Customizes input {@link MessageDispatcherServlet}
   *
   * @param dispatcherServlet - initial input {@link MessageDispatcherServlet} to customize
   */
  void customize(final MessageDispatcherServlet dispatcherServlet);
}
