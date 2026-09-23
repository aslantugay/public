package com.meridian.iam.web;

import com.meridian.iam.domain.ApiKey;
import com.meridian.iam.service.ApiKeyService;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiKeyController implements ApiKeyApi {

    private final ApiKeyService apiKeyService;

    public ApiKeyController(ApiKeyService apiKeyService) {
        this.apiKeyService = apiKeyService;
    }

    @Override
    public ApiKey create(Map<String, String> body) {
        return apiKeyService.create(body.get("label"));
    }

    @Override
    public List<ApiKey> list(Long tenantId) {
        return apiKeyService.listForTenant(tenantId);
    }
}
