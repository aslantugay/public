package com.meridian.integration.storage;

import java.util.List;

public interface BlobStore {

    byte[] read(String key);

    String store(String suggestedName, byte[] content);

    List<String> extractArchive(String targetDir, byte[] zipBytes);
}
