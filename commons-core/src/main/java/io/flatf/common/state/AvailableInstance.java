package io.flatf.common.state;

import javax.annotation.concurrent.ThreadSafe;
import java.util.concurrent.atomic.AtomicBoolean;

@ThreadSafe
public abstract class AvailableInstance implements Available {

    private final AtomicBoolean enabled = new AtomicBoolean(false);

    @Override
    public boolean isEnabled() {
        return enabled.get();
    }

    @Override
    public boolean enable() {
        return enabled.compareAndSet(false, true);
    }

    @Override
    public boolean disable() {
        return enabled.compareAndSet(true, false);
    }

}
