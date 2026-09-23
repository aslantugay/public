package com.meridian.iam.service;

import com.meridian.iam.domain.User;

public interface AuthService {

    User login(String email, String password);

    String issueResetToken(String email);
}
