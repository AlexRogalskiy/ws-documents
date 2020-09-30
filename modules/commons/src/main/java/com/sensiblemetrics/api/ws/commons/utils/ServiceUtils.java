package com.sensiblemetrics.api.ws.commons.utils;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.beans.FeatureDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static java.util.Objects.isNull;
import static org.apache.commons.lang3.ArrayUtils.EMPTY_OBJECT_ARRAY;

@Slf4j
@UtilityClass
public class ServiceUtils {

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
     * Returns {@link InetAddress} by current {@link String} host name
     *
     * @param hostName - initial input {@link String} host name
     * @return {@link InetAddress}
     */
    @Nullable
    public static InetAddress getHost(final String hostName) {
        try {
            return InetAddress.getByName(hostName);
        } catch (UnknownHostException e) {
            log.error("Cannot extract host name from: {}", hostName, e);
            return null;
        }
    }

    /**
     * Returns IPPattern {@link InetAddress} by current {@link String} host name
     *
     * @param hostName - initial input {@link String} host name
     * @return IPPattern {@link InetAddress}
     */
    @Nullable
    public static byte[] getIpAddress(final String hostName) {
        try {
            return InetAddress.getByName(hostName).getAddress();
        } catch (Exception e) {
            log.error("Cannot extract IP address from: {}", hostName, e);
            return null;
        }
    }

    /**
     * Rethrow input {@link Throwable}
     *
     * @param <E>       type of throwable item
     * @param throwable - initial input {@link Throwable}
     * @throws E type of throwable
     */
    @SuppressWarnings("unchecked")
    public static <E extends Throwable> void doThrow(final Throwable throwable) throws E {
        Assert.notNull(throwable, "Throwable should not be null");
        throw (E) throwable;
    }

    /**
     * Returns {@link Stream} by input {@link Enumeration}
     *
     * @param enumeration - initial input {@link Enumeration} to build stream from
     * @param <T>         type of enumerable element
     * @return {@link Stream} instance
     */
    @NonNull
    public static <T> Stream<T> enumerationAsStream(final Enumeration<T> enumeration) {
        Assert.notNull(enumeration, "Enumeration should not be null");

        return StreamSupport.stream(
                new Spliterators.AbstractSpliterator<T>(Long.MAX_VALUE, Spliterator.ORDERED) {
                    public boolean tryAdvance(final Consumer<? super T> action) {
                        if (enumeration.hasMoreElements()) {
                            action.accept(enumeration.nextElement());
                            return true;
                        }
                        return false;
                    }

                    @Override
                    public void forEachRemaining(final Consumer<? super T> action) {
                        while (enumeration.hasMoreElements()) {
                            action.accept(enumeration.nextElement());
                        }
                    }
                }, false);
    }

    /**
     * Returns non-nullable {@link Stream} of {@code T} by input collection of {@code T} values
     *
     * @param <T>    type of input element to be converted from by operation
     * @param values - initial input collection of {@code T} values
     * @return non-nullable {@link Stream} of {@code T}
     */
    @NonNull
    @SafeVarargs
    public static <T> Stream<T> streamOf(final T... values) {
        return Arrays.stream(Optional.ofNullable(values)
                .orElseGet(() -> cast(EMPTY_OBJECT_ARRAY)));
    }

    /**
     * Returns non-nullable {@link Stream} of {@code T} by input {@link Iterable} collection of {@code T} values
     *
     * @param <T>      type of input element to be converted from by operation
     * @param iterable - initial input {@link Iterable} collection of {@code T} values
     * @param parallel - initial input {@code boolean} parallel flag
     * @return non-nullable {@link Stream} of {@code T}
     */
    @NonNull
    public static <T> Stream<T> streamOf(final Iterable<T> iterable,
                                         final boolean parallel) {
        return StreamSupport.stream(iterable.spliterator(), parallel);
    }

    /**
     * Returns non-nullable {@link Stream} of {@code T} by input {@link Iterator}
     *
     * @param <T>      type of input element to be converted from by operation
     * @param iterator - initial input {@link Iterator}
     * @param parallel - initial input parallel flag
     * @return non-nullable {@link Stream} of {@code T}
     */
    @NonNull
    public static <T> Stream<T> streamOf(final Iterator<T> iterator,
                                         final boolean parallel) {
        Assert.notNull(iterator, "Iterator should not be null");
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterator, 0), parallel);
    }

    /**
     * Returns non-nullable {@link Stream} of {@code T} by input {@link Iterator}
     *
     * @param <T>      type of input element to be converted from by operation
     * @param iterator - initial input {@link Iterator}
     * @return non-nullable {@link Stream} of {@code T}
     */
    @NonNull
    public static <T> Stream<T> streamOf(final Iterator<T> iterator) {
        return streamOf(iterator, false);
    }

    /**
     * Returns flat {@link Stream} of {@code T} items by input array of {@link Optional}s
     *
     * @param <T>       type of configurable optional item
     * @param optionals - initial input array of {@link Optional}s
     * @return {@link Stream} of {@code T} items
     */
    @NonNull
    @SafeVarargs
    public static <T> Stream<T> flatOf(final Optional<T>... optionals) {
        return streamOf(optionals).flatMap(ServiceUtils::streamOpt);
    }

    /**
     * Returns non-nullable {@link Stream} of {@code T} by input {@link Iterable} collection of {@code T} values
     *
     * @param <T>      type of input element to be converted from by operation
     * @param iterable - initial input {@link Iterable} collection of {@code T} values
     * @return non-nullable {@link Stream} of {@code T}
     */
    @NonNull
    public static <T> Stream<T> streamOf(final Iterable<T> iterable) {
        return Optional.ofNullable(iterable).map(ServiceUtils::stream).orElseGet(Stream::empty);
    }

    /**
     * Returns {@link Stream} by transforming input {@link Optional} depending upon whether a value is present
     *
     * @param <T>      type of input element to be converted from by operation
     * @param optional - initial input {@link Optional} of {@code T} values
     * @return {@link Stream}
     */
    @NonNull
    public static <T> Stream<T> streamOpt(final Optional<T> optional) {
        return optional.map(Stream::of).orElseGet(Stream::empty);
    }

    /**
     * Returns non-nullable {@link Stream} of {@code T} by input {@link Iterable} collection of {@code T} values
     *
     * @param <T>      type of input element to be converted from by operation
     * @param iterable - initial input {@link Iterable} collection of {@code T} values
     * @return non-nullable {@link Stream} of {@code T}
     */
    @NonNull
    public static <T> Stream<T> stream(final Iterable<T> iterable) {
        return (iterable instanceof Collection) ? ((Collection<T>) iterable).stream() : StreamSupport.stream(iterable.spliterator(), false);
    }

    /**
     * Returns {@link T} casted object
     *
     * @param <T>    type of input element to be converted from by operation
     * @param object - initial input {@link Object} to cast
     * @return {@link T} casted object
     */
    @SuppressWarnings("unchecked")
    public static <T> T cast(final Object object) {
        return (T) object;
    }

    /**
     * Returns null-safe cast argument by input {@link Class} or throw {@link Exception}
     *
     * @param <T>       type of class instance
     * @param value     - initial input {@link Object} to be casted to {@link Class}
     * @param clazz     - initial input {@link Class} to cast to
     * @param throwable - initial input target {@link Throwable}
     * @return casted {@code T} object
     */
    @NonNull
    public static <T, X extends Throwable> T safeCastOrThrow(final Object value, final Class<? extends T> clazz, final X throwable) throws X {
        return cast(value, clazz).orElseThrow(() -> throwable);
    }

    /**
     * Returns null-safe argument cast by input {@link Class}
     *
     * @param <T>   type of class instance
     * @param value - initial input {@link Object} to be casted to {@link Class}
     * @param clazz - initial input {@link Class} to cast to
     * @return casted {@code T} object
     */
    @NonNull
    public static <T> T safeCast(final Object value, final Class<T> clazz) {
        return safeCastOrThrow(value, clazz, new IllegalArgumentException(String.format("Cannot cast input value: {%s}, to class: {%s}", value, clazz)));
    }

    /**
     * Returns null-safe argument cast by input {@link Class}
     *
     * @param <T>   type of class instance
     * @param value - initial input {@link Object} to be casted to {@link Class}
     * @param clazz - initial input {@link Class} to cast to
     * @return casted {@link T} object wrapped by {@link Optional}
     */
    private static <T> Optional<T> cast(final Object value, final Class<T> clazz) {
        return Optional.ofNullable(clazz).filter(c -> c.isInstance(value)).map(c -> c.cast(value));
    }
}
