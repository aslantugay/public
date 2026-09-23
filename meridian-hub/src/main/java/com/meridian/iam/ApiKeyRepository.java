package com.meridian.iam;

import com.meridian.iam.domain.ApiKey;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApiKeyRepository extends JpaRepository<ApiKey, Long> {

    List<ApiKey> findByTenantId(Long tenantId);

    Optional<ApiKey> findBySecret(String secret);
}
