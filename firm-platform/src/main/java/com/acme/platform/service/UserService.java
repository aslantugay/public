package com.acme.platform.service;

import com.acme.platform.domain.User;
import com.acme.platform.web.dto.UserUpdateRequest;
import java.util.List;

public interface UserService {

    List<User> listForFirm(Long firmId);

    User get(Long userId);

    User update(Long userId, UserUpdateRequest request);
}
