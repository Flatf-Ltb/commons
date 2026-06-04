package io.flatf.common.codec.api;

import java.util.function.Function;

/**
 * 编码器
 *
 * @param <T>
 * @param <R>
 * @author yellow013
 */
@FunctionalInterface
public interface Encoder<T, R> extends Function<T, R> {

    /**
     * @param t type
     * @return R
     */
    R encode(T t);

    @Override
    default R apply(T t) {
        return encode(t);
    }

}
