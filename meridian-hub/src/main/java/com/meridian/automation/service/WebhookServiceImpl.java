package com.meridian.automation.service;

import com.meridian.automation.WebhookDeliveryRepository;
import com.meridian.automation.WebhookRepository;
import com.meridian.automation.domain.Webhook;
import com.meridian.automation.domain.WebhookDelivery;
import com.meridian.integration.http.HttpGateway;
import com.meridian.platform.security.AbstractTenantService;
import com.meridian.platform.security.CryptoService;
import com.meridian.platform.security.CurrentUserService;
import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class WebhookServiceImpl extends AbstractTenantService implements WebhookService {

    private final WebhookRepository webhookRepository;
    private final WebhookDeliveryRepository deliveryRepository;
    private final HttpGateway httpGateway;
    private final CryptoService cryptoService;

    public WebhookServiceImpl(CurrentUserService currentUserService,
                              WebhookRepository webhookRepository,
                              WebhookDeliveryRepository deliveryRepository,
                              HttpGateway httpGateway,
                              CryptoService cryptoService) {
        super(currentUserService);
        this.webhookRepository = webhookRepository;
        this.deliveryRepository = deliveryRepository;
        this.httpGateway = httpGateway;
        this.cryptoService = cryptoService;
    }

    @Override
    public Webhook create(String targetUrl, String secret) {
        Webhook webhook = new Webhook();
        webhook.setTenantId(currentTenantId());
        webhook.setTargetUrl(targetUrl);
        if (secret != null) {
            webhook.setSecretEnc(cryptoService.encrypt(secret));
        }
        return webhookRepository.save(webhook);
    }

    @Override
    public WebhookDelivery dispatch(Long webhookId, String event, String payload) {
        Webhook webhook = webhookRepository.findById(webhookId).orElseThrow();
        int status = httpGateway.post(webhook.getTargetUrl(), payload, "application/json");
        WebhookDelivery delivery = new WebhookDelivery(
                webhook.getTenantId(), webhookId, event, payload, String.valueOf(status));
        return deliveryRepository.save(delivery);
    }

    @Override
    public List<WebhookDelivery> recentDeliveries(int limit) {
        return deliveryRepository.findAllByOrderByCreatedAtDesc(PageRequest.of(0, limit));
    }
}
