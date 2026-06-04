package io.flatf.infra.attr;

import io.flatf.common.annotation.OnlyOverrideEquals;

@OnlyOverrideEquals
public final class TcpKeepAlive {

    private final KeepAliveOption option;

    private int count;

    private int idle;

    private int interval;

    private TcpKeepAlive(KeepAliveOption option) {
        this.option = option;
    }

    public KeepAliveOption getOption() {
        return option;
    }

    public int getCount() {
        return count;
    }

    public int getIdle() {
        return idle;
    }

    public int getInterval() {
        return interval;
    }

    public TcpKeepAlive setCount(int count) {
        this.count = count;
        return this;
    }

    public TcpKeepAlive setIdle(int idle) {
        this.idle = idle;
        return this;
    }

    public TcpKeepAlive setInterval(int interval) {
        this.interval = interval;
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof TcpKeepAlive o)) {
            return false;
        } else {
            if (option != o.getOption())
                return false;
            if (this.count != o.getCount())
                return false;
            if (this.idle != o.getIdle())
                return false;
            return this.interval == o.getInterval();
        }
    }

    /**
     * @return TcpKeepAlive
     */
    public static TcpKeepAlive enable() {
        return new TcpKeepAlive(KeepAliveOption.Enable);
    }

    /**
     * @return TcpKeepAlive
     */
    public static TcpKeepAlive disable() {
        return new TcpKeepAlive(KeepAliveOption.Disable);
    }

    /**
     * @return TcpKeepAlive
     */
    public static TcpKeepAlive sysDefault() {
        return new TcpKeepAlive(KeepAliveOption.Default);
    }

    /**
     * @author yellow013
     */
    public enum KeepAliveOption {

        Enable(1),

        Disable(0),

        Default(-1),

        ;

        private final int code;

        KeepAliveOption(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }

    }

}
