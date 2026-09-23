package com.meridian.automation.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

/**
 * A single delivery attempt for a webhook, retained for the ops delivery log
 * shown on the automation dashboard.
 */
@Entity
@Table(name = "webhook_deliveries")
public class WebhookDelivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tenant_id")
    private Long tenantId;

    @Column(name = "webhook_id")
    private Long webhookId;

    @Column(name = "event")
    private String event;

    @Column(name = "payload", length = 4000)
    private String payload;

    @Column(name = "status")
    private String status;

    @Column(name = "created_at")
    private Instant createdAt = Instant.now();

    public WebhookDelivery() {
    }

    public WebhookDelivery(Long tenantId, Long webhookId, String event, String payload, String status) {
        this.tenantId = tenantId;
        this.webhookId = webhookId;
        this.event = event;
        this.payload = payload;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public Long getWebhookId() {
        return webhookId;
    }

    public String getEvent() {
        return event;
    }

    public String getPayload() {
        return payload;
    }

    public String getStatus() {
        return status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}
