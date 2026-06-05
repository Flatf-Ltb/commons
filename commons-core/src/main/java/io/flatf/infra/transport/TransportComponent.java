package io.flatf.infra.transport;

import io.flatf.infra.transport.api.Transport;

import static java.lang.System.currentTimeMillis;

public abstract class TransportComponent implements Transport {

    private long startTime;

    private long endTime = -1;

    protected TransportComponent() {
        startTime = currentTimeMillis();
    }

    protected void newStartTime() {
        startTime = currentTimeMillis();
    }

    protected void newEndTime() {
        endTime = currentTimeMillis();
    }

    @Override
    public long getStartTime() {
        return startTime;
    }

    @Override
    public long getEndTime() {
        return endTime;
    }

    @Override
    public long getRunningDuration() {
        if (endTime > -1)
            return endTime - startTime;
        else
            return currentTimeMillis() - startTime;
    }

}
