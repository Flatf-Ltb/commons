package io.flatf.common.net;

import java.io.Serial;

public class IpAddressIllegalException extends IllegalArgumentException {

    @Serial
    private static final long serialVersionUID = 3756639631788771826L;

    public IpAddressIllegalException(String ipAddress) {
        super("illegal IP address -> " + ipAddress);
    }

}
