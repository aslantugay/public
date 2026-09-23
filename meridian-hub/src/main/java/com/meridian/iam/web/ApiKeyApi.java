package com.meridian.iam.web;

import com.meridian.iam.domain.ApiKey;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface ApiKeyApi {

    @PostMapping("/api/apikeys")
    ApiKey create(@RequestBody Map<String, String> body);

    @GetMapping("/api/tenants/{tenantId}/apikeys")
    List<ApiKey> list(@PathVariable Long tenantId);
}
