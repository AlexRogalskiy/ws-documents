package com.sensiblemetrics.api.ws.webdocs.commons.general.annotation;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.annotation.AliasFor;
import org.springframework.test.annotation.DirtiesContext;

import java.lang.annotation.*;

@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@EnableRestTest
@SpringBootProfileTest
public @interface SpringBootRestTest {
  /**
   * Default active class mode
   *
   * @return {@link DirtiesContext.ClassMode}
   */
  @AliasFor(annotation = SpringBootProfileTest.class, attribute = "activeClassMode")
  DirtiesContext.ClassMode activeClassMode() default DirtiesContext.ClassMode.BEFORE_CLASS;

  /**
   * Default active method mode
   *
   * @return {@link DirtiesContext.MethodMode}
   */
  @AliasFor(annotation = SpringBootProfileTest.class, attribute = "activeMethodMode")
  DirtiesContext.MethodMode activeMethodMode() default DirtiesContext.MethodMode.AFTER_METHOD;

  /**
   * Default active profiles
   *
   * @return {@link String} array of active profiles
   */
  @AliasFor(annotation = SpringBootProfileTest.class, attribute = "activeProfiles")
  String[] activeProfiles() default "test";

  /**
   * Default active classes
   *
   * @return {@link Class} array of active classes
   */
  @AliasFor(annotation = SpringBootProfileTest.class, attribute = "activeClasses")
  Class<?>[] activeClasses() default {};

  /**
   * Default active properties
   *
   * @return {@link String} array of active properties
   */
  @AliasFor(annotation = SpringBootProfileTest.class, attribute = "activeProperties")
  String[] activeProperties() default {};

  /**
   * Default active environment mode
   *
   * @return {@link SpringBootTest.WebEnvironment}
   */
  @AliasFor(annotation = SpringBootProfileTest.class, attribute = "activeEnvironmentMode")
  SpringBootTest.WebEnvironment activeEnvironmentMode() default SpringBootTest.WebEnvironment.MOCK;

  /** Exclude auto-configuration classes */
  @AliasFor(annotation = SpringBootProfileTest.class, attribute = "excludeAutoConfigs")
  Class<?>[] excludeAutoConfigs() default {};

  /** Include auto-configuration classes */
  @AliasFor(annotation = SpringBootProfileTest.class, attribute = "includeAutoConfigs")
  Class<?>[] includeAutoConfigs() default {};

  /**
   * A set of include filters which can be used to add otherwise filtered beans to the application
   * context.
   *
   * @return include filters to apply
   */
  @AliasFor(annotation = SpringBootProfileTest.class, attribute = "includeFilters")
  ComponentScan.Filter[] includeFilters() default {};

  /**
   * A set of exclude filters which can be used to filter beans that would otherwise be added to the
   * application context.
   *
   * @return exclude filters to apply
   */
  @AliasFor(annotation = SpringBootProfileTest.class, attribute = "excludeFilters")
  ComponentScan.Filter[] excludeFilters() default {};
}
