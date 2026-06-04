package io.flatf.common.http;

import io.flatf.common.http.HttpHeaders.HttpHeader;

public enum HttpHeaderName {

    ACCEPT("Accept"),

    ACCEPT_CHARSET("Accept-Charset"),

    ACCEPT_ENCODING("Accept-Encoding"),

    ACCEPT_LANGUAGE("Accept-Language"),

    ACCEPT_RANGES("Accept-Ranges"),

    AGE("Age"),

    ALLOW("Allow"),

    AUTHORIZATION("Authorization"),

    CACHE_CONTROL("Cache-Control"),

    CONNECTION("Connection"),

    CONTENT_DISPOSITION("Content-Disposition"),

    CONTENT_ENCODING("Content-Encoding"),

    CONTENT_LANGUAGE("Content-Language"),

    CONTENT_LENGTH("Content-Length"),

    CONTENT_LOCATION("Content-Location"),

    CONTENT_MD5("Content-MD5"),

    CONTENT_RANGE("Content-Range"),

    CONTENT_TYPE("Content-Type"),

    COOKIE("Cookie"),

    DATE("Date"),

    ETAG("ETag"),

    EXPIRES("Expires"),

    IF_MODIFIED_SINCE("If-Modified-Since"),

    IF_NONE_MATCH("If-None-Match"),

    KEEP_ALIVE("Keep-Alive"),

    LAST_MODIFIED("Last-Modified"),

    LOCATION("Location"),

    PRAGMA("Pragma"),

    PROXY_AUTHENTICATE("Proxy-Authenticate"),

    PROXY_AUTHORIZATION("Proxy-Authorization"),

    PROXY_CONNECTION("Proxy-Connection"),

    RANGE("Range"),

    REFERER("Referer"),

    REFRESH("Refresh"),

    RETRY_AFTER("Retry-After"),

    SERVER("Server"),

    SET_COOKIE("Set-Cookie"),

    STRICT_TRANSPORT_SECURITY("Strict-Transport-Security"),

    TRAILER("Trailer"),

    TRANSFER_ENCODING("Transfer-Encoding"),

    UPGRADE("Upgrade"),

    USER_AGENT("User-Agent"),

    VARY("Vary"),

    VIA("Via"),

    WARNING("Warning"),

    WWW_AUTHENTICATE("WWW-Authenticate"),

    X_CONTENT_TYPE_OPTIONS("X-Content-Type-Options"),

    X_FORWARDED_FOR("X-Forwarded-For");

    private final String value;

    HttpHeaderName(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

    public HttpHeader set(String value) {
        return new HttpHeader(this, value);
    }

}

