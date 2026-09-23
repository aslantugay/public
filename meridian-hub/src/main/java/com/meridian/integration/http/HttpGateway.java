package com.meridian.integration.http;

public interface HttpGateway {

    FetchedResource get(String url);

    int post(String url, String body, String contentType);
}
