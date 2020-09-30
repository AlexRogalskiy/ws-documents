package com.sensiblemetrics.api.ws.webdocs.commons.general.annotation;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.filter.TypeExcludeFilters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.lang.annotation.*;

@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
@EnableBootstrapTest
@DirtiesContext
@ActiveProfiles
@SpringBootTest
@TypeExcludeFilters(SpringBootTypeExcludeFilter.class)
public @interface EnableSpringBootTest {
  /**
   * Default active class mode
   *
   * @return {@link DirtiesContext.ClassMode}
   */
  @AliasFor(annotation = DirtiesContext.class, attribute = "classMode")
  DirtiesContext.ClassMode activeClassMode() default DirtiesContext.ClassMode.BEFORE_CLASS;

  /**
   * Default active method mode
   *
   * @return {@link DirtiesContext.MethodMode}
   */
  @AliasFor(annotation = DirtiesContext.class, attribute = "methodMode")
  DirtiesContext.MethodMode activeMethodMode() default DirtiesContext.MethodMode.AFTER_METHOD;

  /**
   * Default active profiles
   *
   * @return {@link String} array of active profiles
   */
  @AliasFor(annotation = ActiveProfiles.class, attribute = "profiles")
  String[] activeProfiles() default "test";

  /**
   * Default active classes
   *
   * @return {@link Class} array of active classes
   */
  @AliasFor(annotation = SpringBootTest.class, attribute = "classes")
  Class<?>[] activeClasses() default {};

  /**
   * Default active properties
   *
   * @return {@link String} array of active properties
   */
  @AliasFor(annotation = SpringBootTest.class, attribute = "properties")
  String[] activeProperties() default {};

  /**
   * Default active environment mode
   *
   * @return {@link SpringBootTest.WebEnvironment}
   */
  @AliasFor(annotation = SpringBootTest.class, attribute = "webEnvironment")
  SpringBootTest.WebEnvironment activeEnvironmentMode() default
      SpringBootTest.WebEnvironment.RANDOM_PORT;

  /**
   * Determines if default filtering should be used with {@link
   * SpringBootApplication @SpringBootApplication}. By default {@link Component} beans are included.
   */
  boolean useDefaultFilters() default true;

  /**
   * A set of include filters which can be used to add otherwise filtered beans to the application
   * context.
   *
   * @return include filters to apply
   */
  ComponentScan.Filter[] includeFilters() default {};

  /**
   * A set of exclude filters which can be used to filter beans that would otherwise be added to the
   * application context.
   *
   * @return exclude filters to apply
   */
  ComponentScan.Filter[] excludeFilters() default {};
}
