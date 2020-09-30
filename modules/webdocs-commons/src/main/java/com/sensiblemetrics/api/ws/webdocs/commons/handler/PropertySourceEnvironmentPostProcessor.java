package com.sensiblemetrics.api.ws.webdocs.commons.handler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;

import static java.lang.String.format;

public class PropertySourceEnvironmentPostProcessor implements EnvironmentPostProcessor {
  /** {@link YamlPropertySourceLoader} instance */
  private final YamlPropertySourceLoader loader = new YamlPropertySourceLoader();

  @Override
  public void postProcessEnvironment(
      final ConfigurableEnvironment environment, final SpringApplication application) {
    final Resource path = new ClassPathResource("config.yaml");
    final PropertySource<?> propertySource = this.loadYaml(path);
    environment.getPropertySources().addLast(propertySource);
  }

  private PropertySource<?> loadYaml(final Resource path) {
    if (!path.exists()) {
      throw new IllegalArgumentException(format("Resource {%s} does not exist", path));
    }
    try {
      return this.loader.load("webdocs-resource", path).get(0);
    } catch (IOException ex) {
      throw new IllegalStateException(
          format("Failed to load yaml configuration from {%s}", path), ex);
    }
  }
}
