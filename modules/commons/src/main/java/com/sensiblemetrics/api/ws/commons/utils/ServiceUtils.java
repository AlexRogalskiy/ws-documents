package com.sensiblemetrics.api.ws.commons.utils;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.lang.NonNull;

import java.beans.FeatureDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.function.BiConsumer;

import static java.util.Objects.isNull;

@Slf4j
@UtilityClass
public class ServiceUtils {

    /**
     * Returns {@link Resource} by input {@link String} pattern
     *
     * @param pattern initial input {@link String} to fetch resources by
     * @return new {@link Resource} instance by first pattern matching
     */
    @NonNull
    public static Resource findInClasspath(final String pattern) {
        final PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
            final Resource[] resources = resolver.getResources(pattern);
            return Arrays.stream(resources).findFirst().orElseThrow(FileNotFoundException::new);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Copies properties from source {@code T} to target {@code T} by input {@link Iterable} collection of properties
     *
     * @param source        - initial input source {@code T}
     * @param target        - initial input target {@code T}
     * @param propsToUpdate - initial {@link Iterable} collection of {@link String} properties to copy
     */
    public static <T> T copyProperties(final T source, final T target, final Collection<String> propsToUpdate) {
        final BeanWrapper sourceWrap = PropertyAccessorFactory.forBeanPropertyAccess(source);
        final BeanWrapper targetWrap = PropertyAccessorFactory.forBeanPropertyAccess(target);
        propsToUpdate.forEach(property -> targetWrap.setPropertyValue(property, sourceWrap.getPropertyValue(property)));
        return target;
    }

    /**
     * Copies properties from source {@code T} to target {@code T}
     *
     * @param source - initial input source {@code T}
     * @param target - initial input target {@code T}
     */
    public static <T> T copyNonNullProperties(final T source, final T target) {
        BeanUtils.copyProperties(source, target, getNullPropertyNames(source));
        return target;
    }

    /**
     * Returns array of nullable {@link String}s properties by input {@code T} source
     *
     * @param source - initial input source {@code T}
     * @return {@link String} array of null properties
     */
    private static <T> String[] getNullPropertyNames(final T source) {
        final BeanWrapper sourceWrapper = PropertyAccessorFactory.forBeanPropertyAccess(source);
        return Arrays.stream(BeanUtils.getPropertyDescriptors(source.getClass()))
                .map(FeatureDescriptor::getName)
                .filter(name -> isNull(sourceWrapper.getPropertyValue(name)))
                .toArray(String[]::new);
    }

    /**
     * Default {@link BiConsumer} completable action operator
     */
    public static final BiConsumer<? super Object, ? super Throwable> DEFAULT_COMPLETABLE_LOG_ACTION = (response, error) -> {
        try {
            if (Objects.nonNull(error)) {
                log.info("Canceled completable future request [response={}, error={}]", response, error.getMessage());
            } else {
                log.info("Received completable future response [from={}]", response);
            }
        } catch (RuntimeException e) {
            log.error("ERROR: cannot process completable future request callback", e);
        }
    };
}
