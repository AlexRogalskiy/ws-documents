package com.sensiblemetrics.api.ws.commons.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.core.Ordered;
import org.springframework.core.env.Environment;
import org.springframework.lang.NonNull;

import java.util.stream.Stream;

/**
 * Bean Definition Registry Post Processor that looks for placeholders in bean names and resolves
 * them, re-defining those beans with the new names.
 */
@Slf4j
@RequiredArgsConstructor
public class DefaultBeanNamePlaceholderRegistryPostProcessor
    implements BeanDefinitionRegistryPostProcessor, Ordered {
  private final Environment environment;

  @Override
  public void postProcessBeanDefinitionRegistry(@NonNull final BeanDefinitionRegistry registry)
      throws BeansException {
    final DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) registry;
    Stream.of(beanFactory.getBeanDefinitionNames())
        // Look for beans with placeholders name format: '${placeholder}' or
        // '${placeholder:defaultValue}'
        .filter(name -> name.matches("\\$\\{[\\w.-]+(?>:[\\w.-]+)?\\}"))
        .forEach(
            placeholder -> {
              final String actualName = this.environment.resolveRequiredPlaceholders(placeholder);
              final BeanDefinition bd = beanFactory.getBeanDefinition(placeholder);
              beanFactory.removeBeanDefinition(placeholder);
              beanFactory.registerBeanDefinition(actualName, bd);
              log.debug(
                  "Registering new name '{}' for Bean definition with placeholder name: {}",
                  actualName,
                  placeholder);
            });
  }

  @Override
  public void postProcessBeanFactory(@NonNull final ConfigurableListableBeanFactory beanFactory)
      throws BeansException {}

  @Override
  public int getOrder() {
    return Ordered.LOWEST_PRECEDENCE - 1;
  }
}
