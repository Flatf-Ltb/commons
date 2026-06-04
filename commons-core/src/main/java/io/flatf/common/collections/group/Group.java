package io.flatf.common.collections.group;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import java.io.Serial;
import java.util.Set;

/**
 * @param <K>
 * @param <V>
 * @author yellow013
 */
@Immutable
public interface Group<K, V> {

    @Nonnull
    V getMember(@Nonnull K k) throws MemberNotExistException;

    @Nonnull
    Set<K> getKeys();

    class MemberNotExistException extends RuntimeException {

        @Serial
        private static final long serialVersionUID = -6229705929334610236L;

        public MemberNotExistException(String msg) {
            super(msg);
        }

    }

}
