package com.meridian.automation;

import com.meridian.automation.domain.WebhookDelivery;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WebhookDeliveryRepository extends JpaRepository<WebhookDelivery, Long> {

    List<WebhookDelivery> findAllByOrderByCreatedAtDesc(Pageable pageable);
}
