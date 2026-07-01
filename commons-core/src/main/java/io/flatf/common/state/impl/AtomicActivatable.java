package io.flatf.common.state.impl;

import io.flatf.common.state.api.Activatable;

import javax.annotation.concurrent.ThreadSafe;
import java.util.concurrent.atomic.AtomicBoolean;

@ThreadSafe
public abstract class AtomicActivatable implements Activatable {

    private final AtomicBoolean active = new AtomicBoolean(false);

    @Override
    public boolean isActive() {
        return active.get();
    }

    @Override
    public boolean activate() {
        return active.compareAndSet(false, true);
    }

    @Override
    public boolean deactivate() {
        return active.compareAndSet(true, false);
    }

}
