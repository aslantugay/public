package com.meridian.automation.service;

import com.meridian.automation.domain.Webhook;
import com.meridian.automation.domain.WebhookDelivery;
import java.util.List;

public interface WebhookService {

    Webhook create(String targetUrl, String secret);

    WebhookDelivery dispatch(Long webhookId, String event, String payload);

    List<WebhookDelivery> recentDeliveries(int limit);
}
