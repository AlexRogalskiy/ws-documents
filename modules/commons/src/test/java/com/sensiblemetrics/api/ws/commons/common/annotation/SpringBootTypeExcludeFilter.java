package com.sensiblemetrics.api.ws.commons.common.annotation;

import org.springframework.boot.context.TypeExcludeFilter;
import org.springframework.boot.test.autoconfigure.filter.StandardAnnotationCustomizableTypeExcludeFilter;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * {@link TypeExcludeFilter} for {@link EnableSpringBootTest} annotation
 */
class SpringBootTypeExcludeFilter extends StandardAnnotationCustomizableTypeExcludeFilter<EnableSpringBootTest> {
    /**
     * Default active class attribute name
     */
    private static final String ACTIVE_CLASSES_ATTRIBUTE_NAME = "activeClasses";

    private static final Class<?>[] NO_COMPONENTS = {};
    private static final Set<Class<?>> DEFAULT_INCLUDES;

    static {
        final Set<Class<?>> includes = new LinkedHashSet<>();
        includes.add(Component.class);
        DEFAULT_INCLUDES = Collections.unmodifiableSet(includes);
    }

    private final Class<?>[] components;

    SpringBootTypeExcludeFilter(final Class<?> testClass) {
        super(testClass);
        this.components = getAnnotation().getValue(ACTIVE_CLASSES_ATTRIBUTE_NAME, Class[].class).orElse(NO_COMPONENTS);
    }

    @Override
    protected Set<Class<?>> getDefaultIncludes() {
        return DEFAULT_INCLUDES;
    }

    @Override
    protected Set<Class<?>> getComponentIncludes() {
        return new LinkedHashSet<>(Arrays.asList(this.components));
    }
}
