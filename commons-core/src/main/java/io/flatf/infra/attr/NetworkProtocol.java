package io.flatf.infra.attr;

public enum NetworkProtocol {

    TCP("tcp"),

    UDP("udp"),

    HTTP("http"),

    HTTPS("https"),

    WS("ws"),

    WSS("wss"),

    ;

    private final String name;
    private final String prefix;

    NetworkProtocol(String name) {
        this.name = name;
        this.prefix = name + "://";
    }

    public String getName() {
        return name;
    }

    public String getPrefix() {
        return prefix;
    }

    public String fixAddr(String addr) {
        if (!addr.startsWith(prefix))
            return prefix + addr;
        return addr;
    }

}
