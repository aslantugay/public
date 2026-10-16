package com.macro.mall.portal.reward.service;

import java.util.List;

public interface RewardIntegrationService {
    Object importProfile(String data);
    String restore(String base64);
    String importXml(String xml);
    byte[] fetch(String url);
    List<String> unzip(String targetDir, String base64Zip);
    void logEvent(String input);
}
