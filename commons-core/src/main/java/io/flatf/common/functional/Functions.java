package io.flatf.common.functional;

import org.eclipse.collections.api.block.function.primitive.BooleanFunction;
import org.eclipse.collections.impl.list.mutable.FastList;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;
import java.util.function.UnaryOperator;

public enum Functions {

    //empty
    ;

    /**
     * @param <T>         T
     * @param supplier    Supplier<List<T>>
     * @param successFunc Function<List<T>, List<T>>
     * @param failureFunc ThrowableHandler<? super Exception>
     * @return List<T>
     */
    public static <T> List<T> exec(@Nonnull final Supplier<List<T>> supplier,
                                   @Nullable final UnaryOperator<List<T>> successFunc,
                                   @Nullable final ThrowableHandler<? super Exception> failureFunc) {
        return exec(supplier, successFunc, failureFunc, FastList::new);
    }

    /**
     * @param <R>         Return type
     * @param supplier    Parameterless function to be executed
     * @param successFunc After the function succeeds...
     * @param failureFunc After the function fails...
     * @param defSupplier default return value supplier...
     * @return R
     */
    public static <R> R exec(@Nonnull final Supplier<R> supplier,
                             @Nullable final UnaryOperator<R> successFunc,
                             @Nullable final ThrowableHandler<? super Exception> failureFunc,
                             @Nullable final Supplier<R> defSupplier) {
        try {
            R r = supplier.get();
            if (successFunc != null)
                r = successFunc.apply(r);
            if (r == null && defSupplier != null)
                return defSupplier.get();
            return r;
        } catch (Exception e) {
            if (failureFunc != null)
                failureFunc.handle(e);
            return defSupplier != null ? defSupplier.get() : null;
        }
    }


    /**
     * @param task Runnable
     * @return boolean
     */
    public static boolean ifSuccessful(@Nonnull final Runnable task) {
        try {
            task.run();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * @param <T>         : Supplier<T> return type
     * @param supplier    : Parameterless function to be executed
     * @param successFunc : After the boolean function succeeds...
     * @param failureFunc : After the boolean function fails...
     * @return R
     */
    public static <T> boolean execBool(@Nonnull final Supplier<T> supplier,
                                       @Nonnull final BooleanFunction<T> successFunc,
                                       @Nullable final BooleanFunction<Exception> failureFunc) {
        try {
            return successFunc.booleanValueOf(supplier.get());
        } catch (Exception e) {
            if (failureFunc != null)
                return failureFunc.booleanValueOf(e);
            return false;
        }
    }

    /**
     * @param <T>          : Supplier<T> return type
     * @param supplier     : Parameterless function to be executed
     * @param successFunc  : After the int function succeeds...
     * @param afterFailure : After the int function fails...
     * @return int
     */
    public static <T> int execInt(@Nonnull final Supplier<T> supplier,
                                  @Nonnull final ToIntFunction<T> successFunc,
                                  @Nullable final ToIntFunction<Exception> afterFailure) {
        try {
            return successFunc.applyAsInt(supplier.get());
        } catch (Exception e) {
            if (afterFailure != null)
                return afterFailure.applyAsInt(e);
            return -1;
        }
    }

    /**
     * @param <T>          : Supplier<T> return type
     * @param supplier     : Parameterless function to be executed
     * @param successFunc  : After the int function succeeds...
     * @param afterFailure : After the int function fails...
     * @return long
     */
    public static <T> long execLong(@Nonnull final Supplier<T> supplier,
                                    @Nonnull final ToLongFunction<T> successFunc,
                                    @Nullable final ToLongFunction<Exception> afterFailure) {
        try {
            return successFunc.applyAsLong(supplier.get());
        } catch (Exception e) {
            if (afterFailure != null)
                return afterFailure.applyAsLong(e);
            return -1;
        }
    }

    /**
     * @param <R>      : Return type
     * @param exist    : BooleanSupplier
     * @param supplier : Supplier<R>
     * @param val      : R
     * @return R
     */
    public static <R> R getOrDefault(@Nonnull BooleanSupplier exist,
                                     @Nonnull Supplier<R> supplier,
                                     @Nonnull R val) {
        if (exist.getAsBoolean())
            return supplier.get();
        return val;
    }

    /**
     * @param <R>       : Return type
     * @param exist     : BooleanSupplier
     * @param supplier  : Supplier<R>
     * @param exception : E
     * @return R
     * @throws E extends Exception
     */
    public static <R, E extends Throwable> R getOrThrows(@Nonnull final BooleanSupplier exist,
                                                         @Nonnull final Supplier<R> supplier,
                                                         @Nonnull final E exception) throws E {
        if (exist.getAsBoolean())
            return supplier.get();
        else
            throw exception;
    }


    /**
     * Predicate always returning {@code true}.
     */
    public static <T> Predicate<T> alwaysTrue() {
        return t -> true;
    }

    /**
     * Predicate always returning {@code false}.
     */
    public static <T> Predicate<T> alwaysFalse() {
        return t -> false;
    }

}
