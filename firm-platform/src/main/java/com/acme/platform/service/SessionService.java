package com.acme.platform.service;

public interface SessionService {

    String exportContext();

    void importContext(String token);
}
