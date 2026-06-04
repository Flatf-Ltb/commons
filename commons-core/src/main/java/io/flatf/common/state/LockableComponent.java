package io.flatf.common.state;

import java.util.concurrent.atomic.AtomicBoolean;

public abstract class LockableComponent implements Lockable {

    private final AtomicBoolean locked = new AtomicBoolean(false);

    @Override
    public boolean isLocked() {
        return locked.get();
    }

    @Override
    public boolean tryLock() {
        return locked.compareAndSet(false, true);
    }

    @Override
    public void unlock() {
        locked.set(false);
    }

}
