package com.meridian.automation.web;

import com.meridian.automation.domain.Webhook;
import com.meridian.automation.domain.WebhookDelivery;
import com.meridian.automation.service.WebhookService;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebhookController implements WebhookApi {

    private final WebhookService webhookService;

    public WebhookController(WebhookService webhookService) {
        this.webhookService = webhookService;
    }

    @Override
    public Webhook create(Map<String, String> body) {
        return webhookService.create(body.get("targetUrl"), body.get("secret"));
    }

    @Override
    public WebhookDelivery dispatch(Long webhookId, Map<String, String> body) {
        return webhookService.dispatch(webhookId, body.get("event"), body.get("payload"));
    }

    @Override
    public List<WebhookDelivery> recentDeliveries(int limit) {
        return webhookService.recentDeliveries(limit);
    }
}
