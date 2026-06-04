package io.flatf.infra.socket.configurator;

import io.flatf.infra.TransportConfig;

public final class SocketConfig implements TransportConfig {

    private final String host;
    private final int port;
    private final long receiveInterval;
    private final int sendQueueSize;

    private SocketConfig(Builder builder) {
        this.host = builder.host;
        this.port = builder.port;
        this.receiveInterval = builder.receiveInterval;
        this.sendQueueSize = builder.sendQueueSize;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public long receiveInterval() {
        return receiveInterval;
    }

    public int sendQueueSize() {
        return sendQueueSize;
    }

    @Override
    public String getConfigInfo() {
        return "SocketConfigurator";
    }

    @Override
    public String connectionInfo() {
        return "";
    }

    public static class Builder {

        private String host = "127.0.0.1";
        private int port;
        private long receiveInterval = 100;
        private int sendQueueSize = 256;

        private Builder() {
        }

        public Builder host(String host) {
            this.host = host;
            return this;
        }

        public Builder port(int port) {
            this.port = port;
            return this;
        }

        public Builder receiveInterval(long receiveInterval) {
            this.receiveInterval = receiveInterval;
            return this;
        }

        public Builder sendQueueSize(int sendQueueSize) {
            this.sendQueueSize = sendQueueSize;
            return this;
        }

        public SocketConfig build() {
            return new SocketConfig(this);
        }
    }

}
