package com.example.transactional.utils;

import java.net.http.HttpClient.Version;
import java.net.http.HttpConnectTimeoutException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class HttpClient {
    java.net.http.HttpClient client;
    static final Long CONNECTION_TIMEOUT=60000L;

    public HttpResponse<String> send(HttpRequest request) throws HttpConnectTimeoutException {
        log.debug("Http Call : Begin Action SYNC and uri {}", request.uri().toString());
        try {
            var httpResponse = client().send(request, BodyHandlers.ofString());
            log.debug("HttpClient.send : End Action SYNC with result status code {}",
                    httpResponse.statusCode());
            return httpResponse;
        } catch (Exception e) {
            log.error("HttpClient.send : Error message", e);
            throw new HttpConnectTimeoutException("HttpClient.send : Connection close");
        }
    }

    public java.net.http.HttpClient client() {
        if (this.client == null) {
            client = java.net.http.HttpClient.newBuilder()
                    .connectTimeout(Duration.ofMillis(CONNECTION_TIMEOUT))
                    .version(Version.HTTP_2)
                    .build();
        }

        return client;
    }

}
