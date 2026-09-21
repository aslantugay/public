package com.acme.platform.security;

import com.acme.platform.domain.User;
import com.acme.platform.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class CurrentUserService {

    private final RequestContext requestContext;
    private final UserRepository userRepository;

    public CurrentUserService(RequestContext requestContext, UserRepository userRepository) {
        this.requestContext = requestContext;
        this.userRepository = userRepository;
    }

    public RequestContext context() {
        return requestContext;
    }

    public Long currentUserId() {
        return requestContext.getUserId();
    }

    public Long currentFirmId() {
        return requestContext.getFirmId();
    }

    public User currentUser() {
        Long id = requestContext.getUserId();
        if (id == null) {
            return null;
        }
        return userRepository.findById(id).orElse(null);
    }
}
