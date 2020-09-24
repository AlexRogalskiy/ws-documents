package com.sensiblemetrics.api.ws.commons.helper;

import com.google.common.base.Objects;
import com.pivovarit.function.ThrowingConsumer;
import com.pivovarit.function.ThrowingFunction;
import com.pivovarit.function.ThrowingRunnable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.lang.reflect.Executable;
import java.util.Optional;
import java.util.function.*;
import java.util.stream.Stream;

import static com.sensiblemetrics.api.ws.commons.utils.ServiceUtils.doThrow;

/**
 * Optional consumer implementation
 *
 * @param <T> type of configurable entity
 */
@Slf4j
public class OptionalConsumer<T> {
    /**
     * Default empty {@link OptionalConsumer} instance
     */
    private static final OptionalConsumer<?> EMPTY = new OptionalConsumer<>(null);

    @NonNull
    private final Optional<T> optional;

    /**
     * Default optional consumer constructor by input {@link Optional} value
     *
     * @param optional - initial input {@link Optional} value
     */
    private OptionalConsumer(@Nullable final Optional<T> optional) {
        this.optional = Optional.ofNullable(optional).orElseGet(Optional::empty);
    }

    /**
     * Returns {@link OptionalConsumer} by input {@code T} value
     *
     * @param <T>      type of configurable entity
     * @param optional - initial input {@link Optional} value
     * @return {@link OptionalConsumer}
     */
    @NonNull
    public static <T> OptionalConsumer<T> of(@Nullable final Optional<T> optional) {
        return new OptionalConsumer<>(optional);
    }

    /**
     * Returns {@link OptionalConsumer} by input {@code T} value
     *
     * @param <T>   type of configurable entity
     * @param value - initial input {@code T} value
     * @return {@link OptionalConsumer}
     */
    @NonNull
    public static <T> OptionalConsumer<T> of(@Nullable final T value) {
        return new OptionalConsumer<>(Optional.ofNullable(value));
    }

    /**
     * Applies a mapping operation to the argument only when {@code test}
     * returns {@code true} for that argument.
     *
     * @param test   Test determining if mapping is applied; never {@code null}
     * @param ifTrue Mapping to apply when the test is true; never {@code null}
     * @return OperatorType appling {@code ifTrue} when {@code test} is true
     * and otherwise returning the argument.
     */
    public static <T> UnaryOperator<T> applyIf(final Predicate<T> test, final UnaryOperator<T> ifTrue) {
        Assert.notNull(test, "Predicate to test should not be null");
        Assert.notNull(ifTrue, "Unary operator should not be null");

        return t -> {
            if (test.test(t)) {
                return ifTrue.apply(t);
            }
            return t;
        };
    }

    /**
     * Convert a consumer to a function that consumes the argument and returns
     * {@link Optional#empty()}.
     *
     * @return A function that consumes the argument and returns the empty option.
     */
    public static <T, R> Function<T, Optional<R>> functionise(final Consumer<T> consumer) {
        Assert.notNull(consumer, "Consumer should not be null");

        return a -> {
            consumer.accept(a);
            return Optional.empty();
        };
    }

    /**
     * Returns {@link OptionalConsumer} by input {@link Predicate} operator
     *
     * @param predicate - initial input {@link Predicate} to operate by
     * @return {@link OptionalConsumer}
     */
    @NonNull
    public OptionalConsumer<T> filter(@NonNull final Predicate<T> predicate) {
        Assert.notNull(predicate, "Predicate should not be null");
        return of(this.optional.filter(predicate));
    }

    /**
     * Returns {@link OptionalConsumer} by input {@link Function} operator
     *
     * @param <U>    type of configurable entity
     * @param mapper - initial input {@link Function} to operate by
     * @return {@link OptionalConsumer}
     */
    @NonNull
    public <U> OptionalConsumer<U> map(@NonNull final Function<? super T, ? extends U> mapper) {
        Assert.notNull(mapper, "Function should not be null");
        return of(this.optional.map(mapper));
    }

    /**
     * Returns {@link OptionalConsumer} by input {@link ThrowingConsumer} operator
     *
     * @param <X>      type of the exception to be thrown
     * @param consumer - initial input {@link ThrowingConsumer} to operate by
     * @return {@link OptionalConsumer}
     */
    @NonNull
    public <X extends Exception> OptionalConsumer<T> ifPresent(@NonNull final ThrowingConsumer<T, X> consumer) {
        Assert.notNull(consumer, "Consumer should not be null");
        this.optional.ifPresent(consumer.uncheck());
        return this;
    }

    /**
     * Returns {@link OptionalConsumer} by input {@link ThrowingRunnable} operator
     *
     * @param <X>      type of the exception to be thrown
     * @param executor - initial input {@link ThrowingRunnable} to operate by
     * @return {@link OptionalConsumer}
     */
    @NonNull
    public <X extends Exception> OptionalConsumer<T> ifNotPresent(@NonNull final ThrowingRunnable<X> executor) {
        Assert.notNull(executor, "Executor should not be null");
        if (!this.optional.isPresent()) {
            executor.unchecked().run();
        }
        return this;
    }

    /**
     * Returns a {@link Stream} wrapper for the {@code T} value. The resulting stream contains either the value is present
     * or it is empty if the value is empty.
     *
     * @return {@link Stream} wrapper for the {@code T} value
     */
    @NonNull
    public Stream<T> stream() {
        return this.optional.map(Stream::of).orElseGet(Stream::empty);
    }

    /**
     * Return the contained {@code T} value, if present, otherwise throw an {@code X} exception to be created by the provided {@link Supplier}
     *
     * @param <X>               type of the exception to be thrown
     * @param exceptionSupplier - initial input {@link Supplier} which will return the exception to be thrown, must not be {@literal null}
     * @return the present {@code T} value
     * @throws X if there is no {@code T} value present
     */
    @NonNull
    public <X extends Throwable> T getValueOrElseThrow(final Supplier<? extends X> exceptionSupplier) throws X {
        Assert.notNull(exceptionSupplier, "Supplier should not be null");
        return this.optional.orElseThrow(exceptionSupplier);
    }

    /**
     * Return the {@code T} value if present, otherwise return {@code T} other
     *
     * @param other - initial input {@code T} value to be returned if there is no value present, may be null
     * @return {@code T} value, if present, otherwise {@code T} other
     */
    @Nullable
    public T getValueOrElse(final T other) {
        return this.optional.orElse(other);
    }

    /**
     * Return the {@code T} value if present, otherwise invoke {@code T} other and return the result of that invocation.
     *
     * @param otherSupplier - initial input {@link Supplier} whose result is returned if no value is present, must not be {@literal null}.
     * @return {@code T} value if present otherwise the result of {@link Supplier}
     * @throws NullPointerException if {@code T} value is not present and {@code T} other is null
     */
    @Nullable
    public T getValueOrElseGet(final Supplier<T> otherSupplier) {
        Assert.notNull(otherSupplier, "Supplier should not be null");
        return this.optional.orElseGet(otherSupplier);
    }

    /**
     * Returns binary result whether current {@link OptionalConsumer} is present and not equal to input {@link OptionalConsumer}
     *
     * @param <E>   type of configurable entity
     * @param other - initial input {@link OptionalConsumer} to compare by
     * @return true - if current {@link OptionalConsumer} is present and not equal to input {@link OptionalConsumer}, false - otherwise
     */
    public <E> boolean isPresentAndNotSame(final OptionalConsumer<E> other) {
        Assert.notNull(other, "Optional consumer should not be null");
        return this.optional.isPresent() && !this.hasValue(other.optional);
    }

    /**
     * Returns binary result whether current {@link OptionalConsumer} is present and equal to input {@link OptionalConsumer}
     *
     * @param <E>   type of configurable entity
     * @param other - initial input {@link OptionalConsumer} to compare by
     * @return true - if current {@link OptionalConsumer} is present and equal to input {@link OptionalConsumer}, false - otherwise
     */
    public <E> boolean isPresentAndSame(final OptionalConsumer<E> other) {
        Assert.notNull(other, "Optional consumer should not be null");
        return this.optional.isPresent() && this.hasValue(other.optional);
    }

    /**
     * Returns mapped {@link Optional} by input {@link Class} type
     *
     * @param <E>  type of configurable entity
     * @param type - initial input {@link Class} type to map by
     * @return mapped {@link Optional} by input {@link Class} type
     */
    @Nullable
    public <E> Optional<E> mapBy(final Class<E> type) {
        return this.optional.filter(type::isInstance).map(type::cast);
    }

    /**
     * Returns compound {@link ThrowingFunction} if current {@link Optional} is present
     *
     * @return compound {@link ThrowingFunction} if current {@link Optional} is present
     */
    @NonNull
    public <X extends Exception> ThrowingFunction<Consumer<T>, ThrowingFunction<Runnable, OptionalConsumer<T>, X>, X> ifPresent() {
        return OptionalConsumer.curry(this::ifPresent);
    }

    /**
     * Returns compound {@link ThrowingFunction} if not current {@link Optional} is not present
     *
     * @return compound {@link ThrowingFunction} if not current {@link Optional} is not present
     */
    @NonNull
    public <X extends Exception> ThrowingFunction<Runnable, ThrowingFunction<Consumer<T>, OptionalConsumer<T>, X>, X> ifNotPresent() {
        return OptionalConsumer.curry(this::ifNotPresent);
    }

    /**
     * Returns processed {@link OptionalConsumer} by input {@link Supplier} operator
     *
     * @param supplier - initial input {@link Supplier} to operate by
     * @return processed {@link OptionalConsumer}
     */
    @NonNull
    public OptionalConsumer<T> or(final Supplier<? extends OptionalConsumer<T>> supplier) {
        Assert.notNull(supplier, "Function supplier should not be null");
        return this.optional.isPresent() ? this : supplier.get();
    }

    /**
     * Returns processed {@link OptionalConsumer} by input {@link Function} operator
     *
     * @param mapper - initial input {@link Function} to operate by
     * @return processed {@link OptionalConsumer}
     */
    @Nullable
    public <R> OptionalConsumer<R> flatMap(final Function<? super T, ? extends OptionalConsumer<R>> mapper) {
        Assert.notNull(mapper, "Function mapper should not be null");
        return this.optional.isPresent() ? mapper.apply(this.optional.get()) : OptionalConsumer.empty();
    }

    /**
     * Returns empty {@link OptionalConsumer}
     *
     * @param <E> type of configurable entity
     * @return empty {@link OptionalConsumer}
     */
    public static <E> OptionalConsumer<E> empty() {
        return new OptionalConsumer<>(null);
    }

    /**
     * Returns compound {@link Function} by input {@link BiFunction} operator
     *
     * @param <X>      type of first consumed entity
     * @param <Y>      type of last consumed entity
     * @param <R>      type of last result entity
     * @param <E>      type of throwing entity
     * @param function - initial input {@link BiFunction} to operate by
     * @return compound {@link Function}
     */
    private static <X, Y, R, E extends Exception> ThrowingFunction<X, ThrowingFunction<Y, R, E>, E> curry(final BiFunction<X, Y, R> function) {
        return (final X x) -> (final Y y) -> function.apply(x, y);
    }

    /**
     * Returns {@link BiFunction} operator by input compound {@link ThrowingFunction} operator
     *
     * @param <X>      type of first consumed entity
     * @param <Y>      type of last consumed entity
     * @param <R>      type of last result entity
     * @param <E>      type of throwing entity
     * @param function - initial input {@link ThrowingFunction} to operate by
     * @return {@link BiFunction} operator
     */
    private static <X, Y, R, E extends Exception> BiFunction<X, Y, R> uncurry(final ThrowingFunction<X, ThrowingFunction<Y, R, E>, E> function) {
        return (final X a, final Y b) -> function.uncheck().apply(a).uncheck().apply(b);
    }

    /**
     * Returns binary result whether current {@link Optional} equal to input {@link Optional}
     *
     * @param <E>   type of configurable entity
     * @param other - initial input {@link Optional} to compare by
     * @return true - if current {@link Optional} equal to input {@link Optional}, false - otherwise
     */
    private <E> boolean hasValue(final Optional<E> other) {
        return this.optional.filter(current -> Objects.equal(current, other)).isPresent();
    }

    /**
     * Returns current {@link OptionalConsumer} by operating on input {@link Consumer} and {@link Runnable} operators
     *
     * @param present    - initial input {@link Consumer} to operate by if value is present
     * @param notPresent - initial input {@link Runnable} to operate by if value is not present
     * @return current {@link OptionalConsumer}
     */
    private OptionalConsumer<T> ifPresent(final Consumer<T> present,
                                          final Runnable notPresent) {
        if (this.optional.isPresent()) {
            present.accept(this.optional.get());
        } else {
            notPresent.run();
        }
        return this;
    }

    /**
     * Returns current {@link OptionalConsumer} by operating on input {@link Runnable} and {@link Consumer} operators
     *
     * @param present    - initial input {@link Runnable} to operate by if value is not present
     * @param notPresent - initial input {@link Consumer} to operate by if value is present
     * @return current {@link OptionalConsumer}
     */
    private OptionalConsumer<T> ifNotPresent(final Runnable notPresent,
                                             final Consumer<T> present) {
        if (!this.optional.isPresent()) {
            notPresent.run();
        } else {
            present.accept(this.optional.get());
        }
        return this;
    }

    /**
     * Invokes the given {@link Consumer} if the {@link Optional} is present or the {@link Executable} if not
     *
     * @param optional - initial input {@link Optional} value
     * @param consumer - initial input {@link Consumer} operator
     * @param runnable - initial input {@link Executable} operator
     */
    public static <T> void ifPresentOrElse(final Optional<T> optional,
                                           final Consumer<T> consumer,
                                           final Runnable runnable) {
        Assert.notNull(optional, "Optional should not be null");
        Assert.notNull(consumer, "Consumer should not be null");
        Assert.notNull(runnable, "Runnable should not be null");

        if (optional.isPresent()) {
            optional.ifPresent(consumer);
        } else {
            try {
                runnable.run();
            } catch (Exception e) {
                log.error("Cannot process runnable task", e);
                doThrow(e);
            }
        }
    }
}
