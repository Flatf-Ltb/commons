package io.flatf.common.state;

public interface Lockable {

    boolean isLocked();

    boolean tryLock();

    void unlock();

}
