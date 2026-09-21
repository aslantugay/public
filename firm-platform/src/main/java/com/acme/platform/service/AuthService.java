package com.acme.platform.service;

import com.acme.platform.domain.User;

public interface AuthService {

    User login(String email, String password);

    String issueResetToken(String email);
}
