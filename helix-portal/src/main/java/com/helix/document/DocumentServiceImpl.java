package com.helix.document;

import com.helix.common.SignUtil;
import com.helix.model.Document;
import com.helix.repo.DocumentRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Service
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository documents;
    private final String baseDir;

    public DocumentServiceImpl(DocumentRepository documents,
                               @Value("${app.storage.base-dir}") String baseDir) {
        this.documents = documents;
        this.baseDir = baseDir;
    }

    @Override
    public List<Document> publicFeed() {
        return documents.findAll();
    }

    @Override
    public Document shareDocument(Long docId) {
        Document d = documents.findById(docId).orElseThrow(() -> new RuntimeException("no doc"));
        d.setShareToken(SignUtil.newToken(docId));
        d.setVisibility("SHARED");
        return documents.save(d);
    }

    @Override
    public Document resolveShared(String token) {
        return documents.findByShareToken(token).orElseThrow(() -> new RuntimeException("invalid token"));
    }

    @Override
    public byte[] download(String path) {
        try {
            File f = new File(baseDir, path);
            return Files.readAllBytes(f.toPath());
        } catch (Exception e) {
            throw new RuntimeException("download failed: " + e.getMessage());
        }
    }

    @Override
    public String upload(MultipartFile file) {
        try {
            File dest = new File(baseDir, file.getOriginalFilename());
            file.transferTo(dest);
            return "stored at " + dest.getPath();
        } catch (Exception e) {
            throw new RuntimeException("upload failed: " + e.getMessage());
        }
    }

    @Override
    public String importXml(String xml) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            org.w3c.dom.Document doc = db.parse(new InputSource(new StringReader(xml)));
            return "root=" + doc.getDocumentElement().getNodeName();
        } catch (Exception e) {
            throw new RuntimeException("xml parse failed: " + e.getMessage());
        }
    }

    @Override
    public String importConfig(byte[] data) {
        try {
            SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser parser = spf.newSAXParser();
            org.xml.sax.helpers.DefaultHandler handler = new org.xml.sax.helpers.DefaultHandler();
            parser.parse(new ByteArrayInputStream(data), handler);
            return "config parsed (" + data.length + " bytes)";
        } catch (Exception e) {
            throw new RuntimeException("config parse failed: " + e.getMessage());
        }
    }

    @Override
    public List<String> extractZip(MultipartFile zip) {
        List<String> out = new ArrayList<>();
        try (ZipInputStream zis = new ZipInputStream(zip.getInputStream())) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                File dest = new File(baseDir, entry.getName());
                try (FileOutputStream fos = new FileOutputStream(dest)) {
                    byte[] buf = new byte[4096];
                    int n;
                    while ((n = zis.read(buf)) != -1) fos.write(buf, 0, n);
                }
                out.add(dest.getPath());
            }
        } catch (Exception e) {
            throw new RuntimeException("extract failed: " + e.getMessage());
        }
        return out;
    }
}
