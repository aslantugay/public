package com.acme.platform.integration.storage;

public interface StorageProvider {

    byte[] read(String storageKey);

    String store(String suggestedName, byte[] content);
}
