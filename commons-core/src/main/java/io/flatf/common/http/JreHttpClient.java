package io.flatf.common.http;

import io.flatf.common.constant.MimeTypeConst;
import io.flatf.common.log4j2.Log4j2LoggerFactory;
import org.slf4j.Logger;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;

public abstract class JreHttpClient {

    private static final Logger log = Log4j2LoggerFactory.getLogger(JreHttpClient.class);

    private static final HttpClient HC = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .connectTimeout(Duration.ofSeconds(15))
            .followRedirects(HttpClient.Redirect.NORMAL)
            .build();

    private JreHttpClient() {
    }

    public static HttpResponse<String> doGet(@Nonnull String uri, PathParam... params)
            throws IOException, InterruptedException {
        return doGet(uri, PathParamSet.with(params));
    }

    public static HttpResponse<String> doGet(@Nonnull String uri, PathParamSet params)
            throws IOException, InterruptedException {
        return doGet(params.toFullUri(uri));
    }

    public static HttpResponse<String> doGet(@Nonnull URI uri)
            throws IOException, InterruptedException {
        return doGet(HttpRequest.newBuilder().uri(uri).GET()
                .headers(HttpHeaderName.CONTENT_TYPE.value(), MimeTypeConst.APPLICATION_JSON_UTF8)
                .build());
    }

    public static HttpResponse<String> doGet(@Nonnull HttpRequest request)
            throws IOException, InterruptedException {
        return HC.send(
                // GET request
                request, BodyHandlers.ofString());
    }

    public static HttpResponse<String> doPost(@Nonnull URI uri, String body)
            throws IOException, InterruptedException {
        return HC.send(
                // POST request
                HttpRequest.newBuilder().uri(uri)
                        .POST(HttpRequest.BodyPublishers.ofString(body))
                        .header(HttpHeaderName.CONTENT_TYPE.value(), MimeTypeConst.APPLICATION_JSON_UTF8)
                        .build(),
                BodyHandlers.ofString());
    }


    public static HttpResponse<String> doPut(@Nonnull URI uri, String body)
            throws IOException, InterruptedException {
        return HC.send(
                // PUT request
                HttpRequest.newBuilder().uri(uri)
                        .PUT(HttpRequest.BodyPublishers.ofString(body))
                        .header(HttpHeaderName.CONTENT_TYPE.value(), MimeTypeConst.APPLICATION_JSON_UTF8)
                        .build(),
                BodyHandlers.ofString());
    }


    public static HttpResponse<String> doDelete(URI uri)
            throws IOException, InterruptedException {
        return HC.send(
                // DELETE request
                HttpRequest.newBuilder().uri(uri).DELETE().build(),
                BodyHandlers.ofString());
    }


}
