package com.meridian.integration.storage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class LocalBlobStore implements BlobStore {

    private final String baseDir;

    public LocalBlobStore(@Value("${app.storage.base-dir}") String baseDir) {
        this.baseDir = baseDir;
    }

    @Override
    public byte[] read(String key) {
        File file = new File(baseDir, key);
        try {
            return Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            throw new IllegalStateException("Unable to read " + key, e);
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

    @Override
    public List<String> extractArchive(String targetDir, byte[] zipBytes) {
        List<String> written = new ArrayList<>();
        File root = new File(baseDir, targetDir);
        try (ZipInputStream zis = new ZipInputStream(new java.io.ByteArrayInputStream(zipBytes))) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                if (entry.isDirectory()) {
                    continue;
                }
                File out = new File(root, entry.getName());
                Files.createDirectories(out.getParentFile().toPath());
                try (FileOutputStream fos = new FileOutputStream(out)) {
                    byte[] buf = new byte[8192];
                    int n;
                    while ((n = zis.read(buf)) != -1) {
                        fos.write(buf, 0, n);
                    }
                }
                written.add(out.getPath());
            }
        } catch (IOException e) {
            throw new IllegalStateException("Archive extraction failed", e);
        }
        return written;
    }
}
