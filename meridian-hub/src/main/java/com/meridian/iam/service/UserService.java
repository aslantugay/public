package com.meridian.iam.service;

import com.meridian.iam.domain.User;
import com.meridian.iam.dto.UserUpdateRequest;
import java.util.List;

public interface UserService {

    List<User> listForTenant(Long tenantId);

    User get(Long userId);

    User update(Long userId, UserUpdateRequest request);
}
