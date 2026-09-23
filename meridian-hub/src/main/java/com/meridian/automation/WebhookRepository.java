package com.meridian.automation;

import com.meridian.automation.domain.Webhook;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WebhookRepository extends JpaRepository<Webhook, Long> {

    List<Webhook> findByTenantId(Long tenantId);
}
