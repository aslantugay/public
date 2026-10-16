package com.helix.debug;

import com.helix.common.CommonResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/debug")
public class DebugController {

    @Value("${app.jwt.secret}")
    private String jwtSecret;
    @Value("${app.crypto.aes-key}")
    private String aesKey;
    @Value("${app.integration.api-key}")
    private String apiKey;
    @Value("${spring.datasource.url}")
    private String dbUrl;
    @Value("${spring.datasource.password}")
    private String dbPassword;

    @GetMapping("/config")
    public CommonResult config() {
        Map<String, String> cfg = new LinkedHashMap<>();
        cfg.put("jwtSecret", jwtSecret);
        cfg.put("aesKey", aesKey);
        cfg.put("apiKey", apiKey);
        cfg.put("dbUrl", dbUrl);
        cfg.put("dbPassword", dbPassword);
        return CommonResult.success(cfg);
    }

    @GetMapping(value = "/greet", produces = MediaType.TEXT_HTML_VALUE)
    public String greet(@RequestParam String name) {
        return "<html><body><h1>Welcome, " + name + "!</h1></body></html>";
    }
}
