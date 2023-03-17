package com.jpmc.assessment2.parser.util;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static java.util.Optional.empty;
import static java.util.Optional.of;

/**
 * @author debal
 * A functional disjunction implmentation.
 */

public interface Either<L, R> {
    boolean isLeft();
    boolean isRight();
    L leftValue();
    R rightValue();

    default R orElse(R otherValue) {
        return isRight() ? rightValue() : otherValue;
    }

    default R orElseGet(Supplier<R> supplier) {
        return isRight() ? rightValue() : supplier.get();
    }

    default <R1> Either<L, R1> map(Function<R, R1> mapFunc) {
        return isRight() ? right(mapFunc.apply(rightValue())): left(leftValue());
    }

    default <R1> Either<L, R1> flatMap(Function<R, Either<L, R1>> bindFunc) {
        return isRight() ? bindFunc.apply(rightValue()) : left(leftValue());
    }

    default L leftOrElse(L otherValue) {
        return isLeft() ? leftValue() : otherValue;
    }
    default L leftOrElseGet(Supplier<L> leftValueSupplier) {
        return isLeft() ? leftValue() : leftValueSupplier.get();
    }

    default Optional<Either<L, R>> filter(Predicate<R> predFunc) {
        return isRight() && predFunc.test(rightValue()) ? of(right(rightValue())) : empty();
    }

    /**
     * Transform the left value of this disjoint to another type.
     */
    default <L1> Either<L1, R> leftMap(Function<L, L1> mapFunc) {
        return isLeft() ? left(mapFunc.apply(leftValue())): right(rightValue());
    }

    /**
     * Provides a way to produce some side effect while returns the same disjoint.
     */
    default Either<L, R> consumeRight(Consumer<R> consumer){
        if(isRight()) {
            consumer.accept(rightValue());
        }
        return this;
    }

    default Either<L, R> leftPeek(Consumer<L> consumer) {
        if(isLeft()) {
            consumer.accept(leftValue());
        }
        return this;
    }

    default Either<L, R> rightPeek(Consumer<R> consumer) {
        if(isRight()) {
            consumer.accept(rightValue());
        }
        return this;
    }

    static <L1, R1> Either<L1, R1> left(L1 value){
        return new LeftProjection<>(value);
    }

    static <L1, R1> Either<L1, R1> right(R1 value){
        return new RightProjection<>(value);
    }

    /**
     * Provides a way to spit out a single value from the supplied disjoint when the types are same.
     */
    static <K> K orElseLeft(Either<K, K> input) {
        return input.isRight() ? input.rightValue() : input.leftValue();
    }

}

class RightProjection<L, R> implements Either<L, R> {

    private final R value;
    RightProjection(R value){
        this.value = value;
    }

    @Override
    public boolean isLeft() {
        return false;
    }

    @Override
    public boolean isRight() {
        return true;
    }

    @Override
    public L leftValue() {
        throw new NoSuchElementException("Either.leftValue on Right");
    }

    @Override
    public R rightValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else {
            if (o == null || getClass() != o.getClass()) {
                return false;
            } else {
                RightProjection<?, ?> that = (RightProjection<?, ?>) o;
                return Objects.equals(value, that.value);
            }
        }

    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}

class LeftProjection<L, R> implements Either<L, R> {

    private final L value;
    LeftProjection(L value){
        this.value = value;
    }

    @Override
    public boolean isLeft() {
        return true;
    }

    @Override
    public boolean isRight() {
        return false;
    }

    @Override
    public L leftValue() {
        return value;
    }

    @Override
    public R rightValue() {
        throw new NoSuchElementException("Either.rightValue on Left");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else {
            if (o == null || getClass() != o.getClass()) {
                return false;
            } else {
                LeftProjection<?, ?> that = (LeftProjection<?, ?>) o;
                return Objects.equals(value, that.value);
            }
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
