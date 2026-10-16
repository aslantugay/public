package com.helix.integration;

import com.helix.common.CommonResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Map;

@RestController
@RequestMapping("/api/integration")
public class IntegrationController {

    private final IntegrationService service;

    public IntegrationController(IntegrationService service) {
        this.service = service;
    }

    @GetMapping("/fetch")
    public CommonResult fetch(@RequestParam String url) {
        return CommonResult.success(service.fetch(url));
    }

    @GetMapping("/redirect")
    public RedirectView redirect(@RequestParam String next) {
        return new RedirectView(next);
    }

    @PostMapping("/render")
    public CommonResult render(@RequestBody Map<String, String> body) {
        return CommonResult.success(service.render(body.get("template"), body.get("name")));
    }

    @PostMapping("/evaluate")
    public CommonResult evaluate(@RequestParam String expr) {
        return CommonResult.success(String.valueOf(service.evaluate(expr)));
    }

    @GetMapping("/ldap/search")
    public CommonResult ldap(@RequestParam String user) {
        return CommonResult.success(service.ldapSearch(user));
    }

    @PostMapping("/xpath")
    public CommonResult xpath(@RequestBody Map<String, String> body) {
        return CommonResult.success(service.xpathLookup(body.get("xml"), body.get("q")));
    }

    @GetMapping("/log")
    public CommonResult log(@RequestParam String msg) {
        return CommonResult.success(service.auditLog(msg));
    }

    @PostMapping("/deserialize/java")
    public CommonResult javaDeser(@RequestBody String base64) {
        return CommonResult.success(service.javaDeserialize(base64));
    }

    @PostMapping("/deserialize/jackson")
    public CommonResult jacksonDeser(@RequestBody String json) {
        return CommonResult.success(service.jacksonDeserialize(json));
    }

    @PostMapping("/deserialize/xstream")
    public CommonResult xstreamDeser(@RequestBody String xml) {
        return CommonResult.success(service.xstreamDeserialize(xml));
    }

    @PostMapping("/deserialize/fastjson")
    public CommonResult fastjsonDeser(@RequestBody String json) {
        return CommonResult.success(service.fastjsonDeserialize(json));
    }
}
