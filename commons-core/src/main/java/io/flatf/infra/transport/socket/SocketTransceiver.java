package io.flatf.infra.transport.socket;

import io.flatf.common.collections.queue.Queue;
import io.flatf.common.collections.queue.Queue.QueueType;
import io.flatf.common.log4j2.Log4j2LoggerFactory;
import io.flatf.common.thread.Sleep;
import io.flatf.common.thread.Threads;
import io.flatf.infra.transport.socket.configurator.SocketConfig;
import org.slf4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

public final class SocketTransceiver extends BaseTransceiver<String> {

    private static final Logger log = Log4j2LoggerFactory.getLogger(SocketTransceiver.class);

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
            this.isRun.set(true);
        } catch (IOException e) {
            throw new RuntimeException("Create socket failed: " + configurator.getHost() + ":" + configurator.getPort(),
                    e);
        }
    }

    @Override
    public boolean isConnected() {
        return socket != null && socket.isConnected() && !socket.isClosed();
    }

    @Override
    public boolean closeIgnoreException() {
        this.isRun.set(false);
        this.isReceiving.set(false);
        try {
            if (writer != null) {
                writer.close();
                writer = null;
            }
            if (socket != null) {
                socket.close();
                socket = null;
            }
        } catch (IOException e) {
            log.warn("Close socket transceiver failed: {}", e.getMessage(), e);
        }
        return true;
    }

    @Override
    public String getName() {
        return socket == null ? "SocketTransceiver" : "SocketTransceiver -> " + socket.hashCode();
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
        isReceiving.set(true);
        Threads.startNewThread(() -> {
            InputStream inputStream = null;
            while (isRun.get()) {
                try {
                    if (inputStream == null) {
                        inputStream = socket.getInputStream();
                    }
                    byte[] bytes = new byte[8192];
                    int read = inputStream.read(bytes);
                    if (read < 0) {
                        closeIgnoreException();
                        break;
                    }
                    if (read == 0) {
                        Sleep.millis(configurator.receiveInterval());
                    } else {
                        byte[] received = new byte[read];
                        System.arraycopy(bytes, 0, received, 0, read);
                        callback.accept(received);
                    }
                } catch (IOException e) {
                    log.warn("Receive socket data failed: {}", e.getMessage(), e);
                    closeIgnoreException();
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    log.warn("Close socket input stream failed: {}", e.getMessage(), e);
                }
            }
        });
    }

    private void processSendQueue(String msg) {
        try {
            if (isRun.get() && isConnected()) {
                if (writer == null) {
                    this.writer = new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8);
                }
                writer.write(msg);
                writer.flush();
            }
        } catch (IOException e) {
            log.warn("Send socket data failed: {}", e.getMessage(), e);
            closeIgnoreException();
        }
    }

    @Override
    protected Queue<String> initSendQueue() {
        return new Queue<>() {
            @Override
            public boolean enqueue(String msg) {
                processSendQueue(msg);
                return true;
            }

            @Override
            public String getQueueName() {
                return "socket-transceiver-direct-queue";
            }

            @Override
            public boolean isEmpty() {
                return true;
            }

            @Override
            public QueueType getQueueType() {
                return QueueType.SPSC;
            }
        };
    }

    @Override
    public void reconnect() {
        closeIgnoreException();
        init();
    }

    @Override
    public void close() throws IOException {
        closeIgnoreException();
    }

}
