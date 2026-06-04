package io.flatf.foundation.transport.attr;

public final class NetworkAddr {

    private final NetworkProtocol protocol;
    private final String addr;
    private final int port;

    private final String completeInfo;

    private NetworkAddr(NetworkProtocol protocol, String addr, int port) {
        this.protocol = protocol;
        this.addr = addr;
        this.port = port;
        this.completeInfo = protocol.fixAddr(addr) + ":" + port;
    }

    /**
     * @param protocol NetworkProtocol
     * @param port     int
     * @return NetworkAddr
     */
    public static NetworkAddr localhost(NetworkProtocol protocol, int port) {
        return new NetworkAddr(protocol, "127.0.0.1", port);
    }

    /**
     * @param protocol NetworkProtocol
     * @param addr     String
     * @param port     int
     * @return NetworkAddr
     */
    public static NetworkAddr with(NetworkProtocol protocol, String addr, int port) {
        return new NetworkAddr(protocol, addr, port);
    }

    public NetworkProtocol getProtocol() {
        return protocol;
    }

    public String getAddr() {
        return addr;
    }

    public int getPort() {
        return port;
    }

    @Override
    public String toString() {
        return completeInfo;
    }

    public static void main(String[] args) {

        System.out.println(NetworkAddr.localhost(NetworkProtocol.HTTP, 8859));
        System.out.println(NetworkAddr.localhost(NetworkProtocol.WS, 8860));

        System.out.println(NetworkAddr.with(NetworkProtocol.TCP, "23.111.45.12", 8860));
        System.out.println(NetworkAddr.with(NetworkProtocol.WS, "23.111.45.12", 18860));

    }

}
