package com.helix.document;

import com.helix.model.Document;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface DocumentService {
    List<Document> publicFeed();
    Document shareDocument(Long docId);
    Document resolveShared(String token);
    byte[] download(String path);
    String upload(MultipartFile file);
    String importXml(String xml);
    String importConfig(byte[] data);
    List<String> extractZip(MultipartFile zip);
}
