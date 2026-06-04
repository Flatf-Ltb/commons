package io.flatf.common.net;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

public final class NetworkProperties {

    public static String getLocalMacAddress() {
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = networkInterfaces.nextElement();
                byte[] mac = networkInterface.getHardwareAddress();
                if (mac != null) {
                    StringBuilder builder = new StringBuilder();
                    for (int i = 0; i < mac.length; i++) {
                        if (i != 0)
                            builder.append('-');
                        // (mac[i] & 0xFF) 将byte转换为正整数
                        String hex = Integer.toHexString(mac[i] & 0xFF);
                        if (hex.length() == 1)
                            builder.append('0');
                        builder.append(hex);
                    }
                    return builder.toString();
                }
            }
        } catch (SocketException ignored) {
        }
        return "";
    }

    public static void main(String[] args) throws UnknownHostException {
        System.out.println(getLocalMacAddress());
    }

}
