package io.flatf.infra.socket;

import io.flatf.common.collections.queue.Queue;
import io.flatf.common.thread.Sleep;
import io.flatf.common.thread.Threads;
import io.flatf.infra.socket.configurator.SocketConfig;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

public final class SocketTransceiver extends BaseTransceiver<String> {

    private final SocketConfig configurator;
    private final Consumer<byte[]> callback;

    private Socket socket;
    private Writer writer;

    private final AtomicBoolean isReceiving = new AtomicBoolean(false);
    private final AtomicBoolean isRun = new AtomicBoolean(false);

    /**
     * @param configurator SocketConfigurator
     * @param callback     Consumer<byte[]>
     */
    public SocketTransceiver(SocketConfig configurator, Consumer<byte[]> callback) {
        super();
        if (configurator == null || callback == null)
            throw new IllegalArgumentException("configurator or callback is null for init ");
        this.configurator = configurator;
        this.callback = callback;
        init();
    }

    private void init() {
        try {
            this.socket = new Socket(configurator.getHost(), configurator.getPort());
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public boolean isConnected() {
        return false;
    }

    @Override
    public boolean closeIgnoreException() {
        this.isReceiving.set(false);
        try {
            if (writer != null) {
                writer.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void receive() {
        if (!isRun.get()) {
            isRun.set(true);
        }
        if (!isReceiving.get()) {
            startReceiveThread();
        }
    }

    private synchronized void startReceiveThread() {
        if (isReceiving.get())
            return;
        Threads.startNewThread(() -> {
            InputStream inputStream = null;
            while (isRun.get()) {
                try {
                    inputStream = socket.getInputStream();
                    int available = inputStream.available();
                    if (available == 0) {
                        Sleep.millis(configurator.receiveInterval());
                        continue;
                    }
                    byte[] bytes = new byte[available];
                    IOUtils.read(inputStream, bytes);
                    callback.accept(bytes);
                } catch (IOException e) {
                    e.printStackTrace();
                    closeIgnoreException();
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        isReceiving.set(true);
    }

    private void processSendQueue(String msg) {
        try {
            if (isRun.get()) {
                if (writer == null) {
                    this.writer = new OutputStreamWriter(socket.getOutputStream());
                }
                writer.write(msg);
                writer.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
            closeIgnoreException();
        }
    }

    @Override
    protected Queue<String> initSendQueue() {
        return null;//RingQueue.withSingleProducer().setName("socket-queue").size(128).process(this::processSendQueue);
    }

    @Override
    public void reconnect() {
        // TODO Auto-generated method stub
    }

    @Override
    public void close() throws IOException {
        closeIgnoreException();
    }

}
