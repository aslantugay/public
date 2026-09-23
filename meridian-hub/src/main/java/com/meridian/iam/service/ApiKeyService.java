package com.meridian.iam.service;

import com.meridian.iam.domain.ApiKey;
import java.util.List;

public interface ApiKeyService {

    ApiKey create(String label);

    List<ApiKey> listForTenant(Long tenantId);
}
