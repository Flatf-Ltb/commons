package io.flatf.infra.transport.socket;

import io.flatf.common.lang.Validator;
import io.flatf.common.log4j2.Log4j2LoggerFactory;
import io.flatf.common.thread.Sleep;
import io.flatf.common.thread.Threads;
import io.flatf.infra.transport.TransportComponent;
import io.flatf.infra.transport.api.Receiver;
import io.flatf.infra.transport.socket.configurator.SocketConfig;
import org.slf4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

public class SocketReceiver extends TransportComponent implements Receiver {

    private final SocketConfig configurator;

    private final Consumer<byte[]> callback;

    private Socket socket;

    private final AtomicBoolean isReceiving = new AtomicBoolean(false);
    private final AtomicBoolean isRun = new AtomicBoolean(false);

    protected static final Logger log = Log4j2LoggerFactory.getLogger(SocketReceiver.class);

    /**
     * @param configurator SocketConfigurator
     * @param callback     Consumer<byte[]>
     */
    public SocketReceiver(SocketConfig configurator, Consumer<byte[]> callback) {
        Validator.nonNull(configurator, "configurator");
        Validator.nonNull(callback, "callback");
        this.configurator = configurator;
        this.callback = callback;
        init();
    }

    private void init() {
        try {
            this.socket = new Socket(configurator.getHost(), configurator.getPort());
        } catch (Exception e) {
            log.error("new Socket({}, {}) throw Exception -> {}", configurator.getHost(), configurator.getPort(),
                    e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean isConnected() {
        return socket != null && socket.isConnected() && !socket.isClosed();
    }

    @Override
    public boolean closeIgnoreException() {
        this.isRun.set(false);
        try {
            if (socket != null)
                socket.close();
        } catch (IOException e) {
            log.error("socket.close() throw IOException -> {}", e.getMessage(), e);
        }
        return true;
    }

    @Override
    public String getName() {
        return "SocketReceiver -> " + socket.hashCode();
    }

    @Override
    public void receive() {
        if (!isRun.get())
            isRun.set(true);
        if (!isReceiving.get())
            startReceiveThread();
    }

    private synchronized void startReceiveThread() {
        if (isReceiving.get())
            return;
        isReceiving.set(true);
        Threads.startNewThread(() -> {
            InputStream inputStream;
            try {
                inputStream = socket.getInputStream();
            } catch (IOException e) {
                throw new RuntimeException("Open socket input stream failed", e);
            }
            while (isRun.get()) {
                try {
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
                    log.error(e.getMessage(), e);
                    try {
                        inputStream.close();
                    } catch (IOException ignored) {
                    }
                    closeIgnoreException();
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException ignored) {
                }
            }
        });
    }

    @Override
    public void reconnect() {

    }

    @Override
    public void close() throws IOException {
        closeIgnoreException();
    }

}
