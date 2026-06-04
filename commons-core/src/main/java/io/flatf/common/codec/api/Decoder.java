package io.flatf.common.codec.api;

import java.util.function.Function;

/**
 * 解码器
 *
 * @param <T>
 * @param <R>
 * @author yellow013
 */
@FunctionalInterface
public interface Decoder<T, R> extends Function<T, R> {

    /**
     * @param t type
     * @return R
     */
    R decode(T t);

    @Override
    default R apply(T t) {
        return decode(t);
    }

}
