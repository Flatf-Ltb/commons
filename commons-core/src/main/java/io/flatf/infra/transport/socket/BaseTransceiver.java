package io.flatf.infra.transport.socket;

import io.flatf.common.annotation.AbstractFunction;
import io.flatf.common.collections.queue.Queue;
import io.flatf.infra.transport.TransportComponent;
import io.flatf.infra.transport.api.Sender;

import java.util.Objects;

public abstract class BaseTransceiver<T> extends TransportComponent implements Transceiver<T> {

    private final Sender<T> sender;

    protected BaseTransceiver() {
        this.sender = new InnerSender(initSendQueue());
    }

    private class InnerSender extends TransportComponent implements Sender<T> {

        private final Queue<T> queue;

        private InnerSender(Queue<T> queue) {
            this.queue = Objects.requireNonNull(queue, "queue");
        }

        @Override
        public void send(T msg) {
            queue.enqueue(msg);
        }

        @Override
        public String getName() {
            return BaseTransceiver.this.getName() + "-sender";
        }

        @Override
        public boolean isConnected() {
            return BaseTransceiver.this.isConnected();
        }

        @Override
        public boolean closeIgnoreException() {
            return BaseTransceiver.this.closeIgnoreException();
        }

    }

    @Override
    public Sender<T> getSender() {
        return sender;
    }

    @Override
    public boolean startSend() {
        try {
            //queue.start();
            return true;
        } catch (Exception e) {
            throw new RuntimeException("start queue exception : " + e.getMessage(), e);
        }
    }

    @AbstractFunction
    protected abstract Queue<T> initSendQueue();

}
