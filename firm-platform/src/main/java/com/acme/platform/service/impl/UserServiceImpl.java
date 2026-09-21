package com.acme.platform.service.impl;

import com.acme.platform.domain.User;
import com.acme.platform.repository.UserRepository;
import com.acme.platform.security.CurrentUserService;
import com.acme.platform.service.UserService;
import com.acme.platform.service.support.AbstractTenantService;
import com.acme.platform.web.dto.UserUpdateRequest;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends AbstractTenantService implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(CurrentUserService currentUserService, UserRepository userRepository) {
        super(currentUserService);
        this.userRepository = userRepository;
    }

    @Override
    public List<User> listForFirm(Long firmId) {
        return userRepository.findByFirmId(firmId);
    }

    @Override
    public User get(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    @Override
    public User update(Long userId, UserUpdateRequest request) {
        User user = userRepository.findById(userId).orElseThrow();
        BeanUtils.copyProperties(request, user, "id", "passwordHash");
        return userRepository.save(user);
    }
}
