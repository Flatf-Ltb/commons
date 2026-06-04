package io.flatf.foundation.transport.api;

public interface Sender<T> extends Transport {

    /**
     * @param msg T msg
     */
    void send(T msg);

}
