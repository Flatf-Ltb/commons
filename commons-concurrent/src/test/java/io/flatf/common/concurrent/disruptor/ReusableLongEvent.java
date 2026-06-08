package io.flatf.common.concurrent.disruptor;

import com.lmax.disruptor.EventFactory;

final class ReusableLongEvent implements ReusableEvent {

    static final EventFactory<ReusableLongEvent> FACTORY = ReusableLongEvent::new;

    private long value = -1L;

    long get() {
        return value;
    }

    void set(long value) {
        this.value = value;
    }

    @Override
    public void clear() {
        this.value = -1L;
    }

}
