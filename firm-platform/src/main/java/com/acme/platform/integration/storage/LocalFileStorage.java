package com.acme.platform.integration.storage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class LocalFileStorage implements StorageProvider {

    private final String baseDir;

    public LocalFileStorage(@Value("${app.storage.base-dir}") String baseDir) {
        this.baseDir = baseDir;
    }

    @Override
    public byte[] read(String storageKey) {
        File file = new File(baseDir, storageKey);
        try {
            return Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            throw new IllegalStateException("Unable to read " + storageKey, e);
        }
    }

    @Override
    public String store(String suggestedName, byte[] content) {
        File file = new File(baseDir, suggestedName);
        try {
            Files.createDirectories(file.getParentFile().toPath());
            Files.write(file.toPath(), content);
            return suggestedName;
        } catch (IOException e) {
            throw new IllegalStateException("Unable to store " + suggestedName, e);
        }
    }
}
