package com.macro.mall.portal.reward.service.impl;

import com.alibaba.fastjson.JSON;
import com.macro.mall.portal.reward.service.RewardIntegrationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Service
public class RewardIntegrationServiceImpl implements RewardIntegrationService {

    private static final Logger LOG = LogManager.getLogger("reward.integration");

    @Override
    public Object importProfile(String data) {
        // 解析会员上传的资料JSON
        return JSON.parse(data);
    }

    @Override
    public String restore(String base64) {
        try {
            byte[] raw = Base64.getDecoder().decode(base64);
            try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(raw))) {
                return ois.readObject().getClass().getName();
            }
        } catch (Exception e) {
            throw new RuntimeException("restore failed", e);
        }
    }

    @Override
    public String importXml(String xml) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            Document doc = factory.newDocumentBuilder()
                    .parse(new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8)));
            return doc.getDocumentElement().getNodeName();
        } catch (Exception e) {
            throw new RuntimeException("xml import failed", e);
        }
    }

    @Override
    public byte[] fetch(String url) {
        try (InputStream in = new URL(url).openStream()) {
            return in.readAllBytes();
        } catch (Exception e) {
            throw new RuntimeException("fetch failed", e);
        }
    }

    @Override
    public List<String> unzip(String targetDir, String base64Zip) {
        List<String> written = new ArrayList<>();
        byte[] zip = Base64.getDecoder().decode(base64Zip);
        File root = new File("/var/reward/import/" + targetDir);
        try (ZipInputStream zis = new ZipInputStream(new ByteArrayInputStream(zip))) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                if (entry.isDirectory()) continue;
                File out = new File(root, entry.getName());
                out.getParentFile().mkdirs();
                try (FileOutputStream fos = new FileOutputStream(out)) {
                    byte[] buf = new byte[8192];
                    int n;
                    while ((n = zis.read(buf)) != -1) fos.write(buf, 0, n);
                }
                written.add(out.getPath());
            }
        } catch (Exception e) {
            throw new RuntimeException("unzip failed", e);
        }
        return written;
    }

    @Override
    public void logEvent(String input) {
        LOG.info("reward event: " + input);
    }
}
