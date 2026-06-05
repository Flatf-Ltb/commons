package io.flatf.infra.transport.socket.util;

import io.flatf.common.thread.RuntimeInterruptedException;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class MsgBox {

    private final BlockingQueue<byte[]> box = new ArrayBlockingQueue<>(512);

    private static final MsgBox instance = new MsgBox();

    public static MsgBox instance() {
        return instance;
    }

    private MsgBox() {
    }

    public boolean inMsg(byte[] msg) {
        return box.offer(msg);
    }

    public byte[] outMsg() {
        return box.poll();
    }

    public void blockingInMsg(byte[] msg) {
        try {
            box.put(msg);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeInterruptedException("blockingInMsg throw InterruptedException", e);
        }
    }

    public byte[] blockingOutMsg() {
        try {
            return box.take();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeInterruptedException("blockingOutMsg throw InterruptedException", e);
        }
    }

}
