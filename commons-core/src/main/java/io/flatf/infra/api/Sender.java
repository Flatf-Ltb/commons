package io.flatf.infra.api;

public interface Sender<T> extends Transport {

    /**
     * @param msg T msg
     */
    void send(T msg);

}
