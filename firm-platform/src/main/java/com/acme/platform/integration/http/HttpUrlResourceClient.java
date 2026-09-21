package com.acme.platform.integration.http;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class HttpUrlResourceClient implements UrlResourceClient {

    private final int timeoutMs;

    public HttpUrlResourceClient(
            @Value("${app.integration.image-proxy-timeout-ms:4000}") int timeoutMs) {
        this.timeoutMs = timeoutMs;
    }

    @Override
    public FetchedResource fetch(String url) {
        try {
            URL target = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) target.openConnection();
            connection.setConnectTimeout(timeoutMs);
            connection.setReadTimeout(timeoutMs);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestMethod("GET");

            String contentType = connection.getContentType();
            try (InputStream in = connection.getInputStream();
                 ByteArrayOutputStream out = new ByteArrayOutputStream()) {
                byte[] buffer = new byte[8192];
                int read;
                while ((read = in.read(buffer)) != -1) {
                    out.write(buffer, 0, read);
                }
                return new FetchedResource(contentType, out.toByteArray());
            }
        } catch (Exception e) {
            throw new IllegalStateException("Failed to fetch remote resource", e);
        }
    }
}
