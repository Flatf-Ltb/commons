package io.flatf.common.concurrent.agrona;

import org.agrona.concurrent.Agent;
import org.agrona.concurrent.MessageHandler;
import org.agrona.concurrent.UnsafeBuffer;
import org.agrona.concurrent.ringbuffer.ManyToOneRingBuffer;
import org.agrona.concurrent.ringbuffer.OneToOneRingBuffer;
import org.agrona.concurrent.ringbuffer.RingBuffer;
import org.agrona.concurrent.ringbuffer.RingBufferDescriptor;

import javax.annotation.Nonnull;
import java.nio.ByteBuffer;
import java.util.Objects;

import static java.nio.ByteBuffer.allocate;

public class UnsafeRingBuffer {

    private final RingBuffer ringBuffer;

    private final MessageHandler handler;

    private UnsafeRingBuffer(@Nonnull RingBuffer ringBuffer,
                             @Nonnull MessageHandler handler) {
        this.ringBuffer = Objects.requireNonNull(ringBuffer, "ringBuffer");
        this.handler = Objects.requireNonNull(handler, "handler");
    }

    public int read() {
        return ringBuffer.read(handler);
    }

    public Agent allocateSender() {
        throw new UnsupportedOperationException("allocateSender requires a message source and is not implemented");
    }


    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {

        int size = 8192;
        ByteBuffer buffer = null;

        public Builder setSize(int size) {
            this.size = size;
            return this;
        }

        public Builder setBuffer(ByteBuffer buffer) {
            this.buffer = buffer;
            return this;
        }

        public UnsafeRingBuffer withManyToOne(final MessageHandler handler) {
            if (buffer == null)
                buffer = allocate(size + RingBufferDescriptor.TRAILER_LENGTH);
            return new UnsafeRingBuffer(
                    new ManyToOneRingBuffer(new UnsafeBuffer(buffer)), handler);
        }

        public UnsafeRingBuffer withOneToOne(final MessageHandler handler) {
            if (buffer == null)
                buffer = allocate(size + RingBufferDescriptor.TRAILER_LENGTH);
            return new UnsafeRingBuffer(
                    new OneToOneRingBuffer(new UnsafeBuffer(buffer)), handler);
        }

    }

}
