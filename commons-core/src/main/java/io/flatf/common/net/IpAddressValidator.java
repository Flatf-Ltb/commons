package io.flatf.common.net;

import io.flatf.common.util.RegexValidator;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author yellow013
 */
public final class IpAddressValidator implements Serializable {

    @Serial
    private static final long serialVersionUID = -919201640201914789L;

    private static final int IPV4_SEGMENT_MAX_VALUE = 255;

    private static final int MAX_UNSIGNED_SHORT = 0xffff;

    private static final int BASE_16 = 16;

    private static final String IPV4_REGEX = "^(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})$";

    /**
     * Max number of hex groups (separated by :) in an IPV6 address
     */
    private static final int IPV6_MAX_HEX_GROUPS = 8;

    /**
     * Max hex digits in each IPv6 group
     */
    private static final int IPV6_MAX_HEX_DIGITS_PER_GROUP = 4;

    /**
     * IPv4 RegexValidator
     */
    private static final RegexValidator ipv4Validator = new RegexValidator(IPV4_REGEX);

    /**
     * @param address String
     * @return String
     * @throws IpAddressIllegalException exception
     */
    public static synchronized String assertIpAddress(String address) throws IpAddressIllegalException {
        if (!isIpAddress(address))
            throw new IpAddressIllegalException(address);
        return address;
    }

    /**
     * Checks if the specified string is a valid IP address.
     *
     * @param address the string to validate
     * @return true if the string validates as an IP address
     */
    public static synchronized boolean isIpAddress(String address) {
        return isIPv4(address) || isIPv6(address);
    }

    /**
     * Validates an IPv4 address. Returns true if valid.
     *
     * @param address the IPv4 address to validate
     * @return true if the argument contains a valid IPv4 address
     */
    public static synchronized boolean isIPv4(String address) {
        // verify that address conforms to generic IPv4 format
        String[] groups = ipv4Validator.match(address);

        if (groups == null)
            return false;

        // verify that address subgroups are legal
        for (String ipSegment : groups) {
            if (ipSegment == null || ipSegment.isEmpty())
                return false;

            int ipSegmentInt;
            try {
                ipSegmentInt = Integer.parseInt(ipSegment);
            } catch (NumberFormatException e) {
                return false;
            }

            if (ipSegmentInt > IPV4_SEGMENT_MAX_VALUE)
                return false;

            if (ipSegment.length() > 1 && ipSegment.startsWith("0"))
                return false;
        }
        return true;
    }

    /**
     * Validates an IPv6 address. Returns true if valid.
     *
     * @param address the IPv6 address to validate
     * @return true if the argument contains a valid IPv6 address
     * @since 1.4.1
     */
    public static synchronized boolean isIPv6(String address) {
        String[] parts;
        // remove prefix size. This will appear after the zone id (if any)
        parts = address.split("/", -1);
        if (parts.length > 2) {
            return false; // can only have one prefix specifier
        }
        if (parts.length == 2) {
            if (parts[1].matches("\\d{1,3}")) { // Need to eliminate signs
                int bits = Integer.parseInt(parts[1]); // cannot fail because of RE check
                if (bits < 0 || bits > 128) {
                    return false; // out of range
                }
            } else {
                return false; // not a valid number
            }
        }
        // remove zone-id
        parts = parts[0].split("%", -1);
        if (parts.length > 2) {
            return false;
        } else if (parts.length == 2) {
            // The id syntax is implementation independent, but it presumably cannot allow:
            // whitespace, '/' or '%'
            if (!parts[1].matches("[^\\s/%]+")) {
                return false; // invalid id
            }
        }
        address = parts[0];
        boolean containsCompressedZeroes = address.contains("::");
        if (containsCompressedZeroes && (address.indexOf("::") != address.lastIndexOf("::"))) {
            return false;
        }
        if ((address.startsWith(":") && !address.startsWith("::"))
                || (address.endsWith(":") && !address.endsWith("::"))) {
            return false;
        }
        String[] octets = address.split(":");
        if (containsCompressedZeroes) {
            List<String> octetList = new ArrayList<>(Arrays.asList(octets));
            if (address.endsWith("::")) {
                // String.split() drops ending empty segments
                octetList.add("");
            } else if (address.startsWith("::") && !octetList.isEmpty()) {
                octetList.removeFirst();
            }
            octets = octetList.toArray(new String[0]);
        }

        if (octets.length > IPV6_MAX_HEX_GROUPS)
            return false;

        int validOctets = 0;
        int emptyOctets = 0; // consecutive empty chunks
        for (int index = 0; index < octets.length; index++) {
            String octet = octets[index];
            if (octet.isEmpty()) {
                emptyOctets++;
                if (emptyOctets > 1)
                    return false;

            } else {
                emptyOctets = 0;
                // Is last chunk an IPv4 address?
                if (index == octets.length - 1 && octet.contains(".")) {
                    if (!isIPv4(octet))
                        return false;

                    validOctets += 2;
                    continue;
                }
                if (octet.length() > IPV6_MAX_HEX_DIGITS_PER_GROUP) {
                    return false;
                }
                int octetInt;
                try {
                    octetInt = Integer.parseInt(octet, BASE_16);
                } catch (NumberFormatException e) {
                    return false;
                }
                if (octetInt < 0 || octetInt > MAX_UNSIGNED_SHORT) {
                    return false;
                }
            }
            validOctets++;
        }
        return validOctets <= IPV6_MAX_HEX_GROUPS && (validOctets == IPV6_MAX_HEX_GROUPS || containsCompressedZeroes);
    }

}
