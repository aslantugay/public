package com.meridian.automation.web;

import com.meridian.automation.domain.Webhook;
import com.meridian.automation.domain.WebhookDelivery;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/api/webhooks")
public interface WebhookApi {

    @PostMapping
    Webhook create(@RequestBody Map<String, String> body);

    @PostMapping("/{webhookId}/dispatch")
    WebhookDelivery dispatch(@PathVariable Long webhookId, @RequestBody Map<String, String> body);

    @GetMapping("/deliveries/recent")
    List<WebhookDelivery> recentDeliveries(@RequestParam(defaultValue = "50") int limit);
}
