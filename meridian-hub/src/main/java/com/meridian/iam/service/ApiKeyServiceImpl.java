package com.meridian.iam.service;

import com.meridian.iam.ApiKeyRepository;
import com.meridian.iam.domain.ApiKey;
import com.meridian.platform.security.AbstractTenantService;
import com.meridian.platform.security.CurrentUserService;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class ApiKeyServiceImpl extends AbstractTenantService implements ApiKeyService {

    private final ApiKeyRepository apiKeyRepository;

    public ApiKeyServiceImpl(CurrentUserService currentUserService, ApiKeyRepository apiKeyRepository) {
        super(currentUserService);
        this.apiKeyRepository = apiKeyRepository;
    }

    @Override
    public ApiKey create(String label) {
        ApiKey key = new ApiKey();
        key.setTenantId(currentTenantId());
        key.setOwnerUserId(currentUserId());
        key.setLabel(label);
        key.setSecret("mk_live_" + UUID.randomUUID().toString().replace("-", ""));
        return apiKeyRepository.save(key);
    }

    @Override
    public List<ApiKey> listForTenant(Long tenantId) {
        return apiKeyRepository.findByTenantId(tenantId);
    }
}
