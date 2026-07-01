package io.flatf.common.state.api;

public interface Lockable {

    boolean isLocked();

    boolean tryLock();

    void unlock();

}
