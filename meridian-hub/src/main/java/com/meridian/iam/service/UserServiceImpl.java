package com.meridian.iam.service;

import com.meridian.iam.UserRepository;
import com.meridian.iam.domain.User;
import com.meridian.iam.dto.UserUpdateRequest;
import com.meridian.platform.security.AbstractTenantService;
import com.meridian.platform.security.CurrentUserService;
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
    public List<User> listForTenant(Long tenantId) {
        return userRepository.findByTenantId(tenantId);
    }

    @Override
    public User get(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    @Override
    public User update(Long userId, UserUpdateRequest request) {
        User user = userRepository.findById(userId).orElseThrow();
        BeanUtils.copyProperties(request, user, "id", "passwordHash", "tenantId");
        return userRepository.save(user);
    }
}
