package com.sensiblemetrics.api.ws.commons.annotation;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.ConfigurationCondition;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.lang.NonNull;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import static com.sensiblemetrics.api.ws.commons.utils.ServiceUtils.safeCast;
import static java.lang.String.format;

public class OnMissingBeanCondition extends SpringBootCondition implements ConfigurationCondition {

  @Override
  public ConditionOutcome getMatchOutcome(
      final ConditionContext context, final AnnotatedTypeMetadata metadata) {
    final Map<String, Object> beanAttributes =
        metadata.getAnnotationAttributes(Bean.class.getName());
    return Optional.ofNullable(beanAttributes)
        .map(value -> safeCast(value.getOrDefault("value", value.get("name")), String[].class))
        .filter(ArrayUtils::isNotEmpty)
        .map(beanName -> ArrayUtils.get(beanName, 0))
        .map(this.buildConditionOutCome(context))
        .orElseThrow(
            () ->
                new IllegalStateException(
                    format(
                        "OnMissingBeanCondition can't detect bean name, attributes: {%s}",
                        beanAttributes)));
  }

  @NonNull
  @Override
  public ConfigurationPhase getConfigurationPhase() {
    return ConfigurationPhase.REGISTER_BEAN;
  }

  private Function<String, ConditionOutcome> buildConditionOutCome(final ConditionContext context) {
    return beanName -> {
      final String name = context.getEnvironment().resolveRequiredPlaceholders(beanName);
      return Optional.ofNullable(context.getBeanFactory())
          .filter(factory -> factory.containsBean(name))
          .map(v -> ConditionOutcome.match(beanName + " not found"))
          .orElseGet(() -> ConditionOutcome.noMatch(beanName + " found"));
    };
  }
}
