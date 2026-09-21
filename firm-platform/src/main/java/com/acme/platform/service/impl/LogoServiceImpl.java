package com.acme.platform.service.impl;

import com.acme.platform.integration.http.FetchedResource;
import com.acme.platform.integration.http.UrlResourceClient;
import com.acme.platform.integration.storage.StorageProvider;
import com.acme.platform.service.LogoService;
import org.springframework.stereotype.Service;

@Service
public class LogoServiceImpl implements LogoService {

    private final UrlResourceClient urlResourceClient;
    private final StorageProvider storageProvider;

    public LogoServiceImpl(UrlResourceClient urlResourceClient, StorageProvider storageProvider) {
        this.urlResourceClient = urlResourceClient;
        this.storageProvider = storageProvider;
    }

    @Override
    public String importFromUrl(Long firmId, String url) {
        FetchedResource resource = urlResourceClient.fetch(url);
        String key = "firms/" + firmId + "/logo";
        return storageProvider.store(key, resource.getBody());
    }
}
