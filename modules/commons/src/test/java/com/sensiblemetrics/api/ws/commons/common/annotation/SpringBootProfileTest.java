package com.sensiblemetrics.api.ws.commons.common.annotation;

import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.OverrideAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.annotation.AliasFor;
import org.springframework.test.annotation.DirtiesContext;

import java.lang.annotation.*;

@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@EnableSpringBootTest
@OverrideAutoConfiguration(enabled = false)
@ImportAutoConfiguration
public @interface SpringBootProfileTest {
  /**
   * Default active class mode
   *
   * @return {@link DirtiesContext.ClassMode}
   */
  @AliasFor(annotation = EnableSpringBootTest.class, attribute = "activeClassMode")
  DirtiesContext.ClassMode activeClassMode() default DirtiesContext.ClassMode.BEFORE_CLASS;

  /**
   * Default active method mode
   *
   * @return {@link DirtiesContext.MethodMode}
   */
  @AliasFor(annotation = EnableSpringBootTest.class, attribute = "activeMethodMode")
  DirtiesContext.MethodMode activeMethodMode() default DirtiesContext.MethodMode.AFTER_METHOD;

  /**
   * Default active profiles
   *
   * @return {@link String} array of active profiles
   */
  @AliasFor(annotation = EnableSpringBootTest.class, attribute = "activeProfiles")
  String[] activeProfiles() default "test";

  /**
   * Default active classes
   *
   * @return {@link Class} array of active classes
   */
  @AliasFor(annotation = EnableSpringBootTest.class, attribute = "activeClasses")
  Class<?>[] activeClasses() default {};

  /**
   * Default active properties
   *
   * @return {@link String} array of active properties
   */
  @AliasFor(annotation = EnableSpringBootTest.class, attribute = "activeProperties")
  String[] activeProperties() default {};

  /**
   * Default active environment mode
   *
   * @return {@link SpringBootTest.WebEnvironment}
   */
  @AliasFor(annotation = EnableSpringBootTest.class, attribute = "activeEnvironmentMode")
  SpringBootTest.WebEnvironment activeEnvironmentMode() default
      SpringBootTest.WebEnvironment.RANDOM_PORT;

  /**
   * Determines if default filtering should be used with {@link
   * SpringBootApplication @SpringBootApplication}. By default no beans are included.
   */
  @AliasFor(annotation = EnableSpringBootTest.class, attribute = "useDefaultFilters")
  boolean useDefaultFilters() default true;

  /**
   * A set of include filters which can be used to add otherwise filtered beans to the application
   * context.
   *
   * @return include filters to apply
   */
  @AliasFor(annotation = EnableSpringBootTest.class, attribute = "includeFilters")
  ComponentScan.Filter[] includeFilters() default {};

  /**
   * A set of exclude filters which can be used to filter beans that would otherwise be added to the
   * application context.
   *
   * @return exclude filters to apply
   */
  @AliasFor(annotation = EnableSpringBootTest.class, attribute = "excludeFilters")
  ComponentScan.Filter[] excludeFilters() default {};

  /** Exclude auto-configuration classes */
  @AliasFor(annotation = ImportAutoConfiguration.class, attribute = "exclude")
  Class<?>[] excludeAutoConfigs() default {};

  /** Include auto-configuration classes */
  @AliasFor(annotation = ImportAutoConfiguration.class, attribute = "classes")
  Class<?>[] includeAutoConfigs() default {};
}
