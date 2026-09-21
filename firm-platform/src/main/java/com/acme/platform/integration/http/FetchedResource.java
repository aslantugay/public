package com.acme.platform.integration.http;

public class FetchedResource {

    private final String contentType;
    private final byte[] body;

    public FetchedResource(String contentType, byte[] body) {
        this.contentType = contentType;
        this.body = body;
    }

    public String getContentType() {
        return contentType;
    }

    public byte[] getBody() {
        return body;
    }

    public int size() {
        return body == null ? 0 : body.length;
    }
}
