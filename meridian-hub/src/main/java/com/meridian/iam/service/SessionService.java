package com.meridian.iam.service;

public interface SessionService {

    String exportContext();

    void importContext(String token);
}
