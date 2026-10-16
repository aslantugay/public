package com.helix.document;

import com.helix.common.CommonResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/documents")
public class DocumentController {

    private final DocumentService service;

    public DocumentController(DocumentService service) {
        this.service = service;
    }

    @GetMapping("/public-feed")
    public CommonResult feed() {
        return CommonResult.success(service.publicFeed());
    }

    @PostMapping("/{id}/share")
    public CommonResult share(@PathVariable Long id) {
        return CommonResult.success(service.shareDocument(id));
    }

    @GetMapping("/shared/{token}")
    public CommonResult shared(@PathVariable String token) {
        return CommonResult.success(service.resolveShared(token));
    }

    @GetMapping("/download")
    public CommonResult download(@RequestParam String path) {
        byte[] data = service.download(path);
        return CommonResult.success(Map.of("size", data.length, "content", new String(data)));
    }

    @PostMapping("/upload")
    public CommonResult upload(@RequestParam("file") MultipartFile file) {
        return CommonResult.success(service.upload(file));
    }

    @PostMapping("/import-xml")
    public CommonResult importXml(@RequestBody String xml) {
        return CommonResult.success(service.importXml(xml));
    }

    @PostMapping("/import-config")
    public CommonResult importConfig(@RequestBody byte[] data) {
        return CommonResult.success(service.importConfig(data));
    }

    @PostMapping("/extract-zip")
    public CommonResult extractZip(@RequestParam("file") MultipartFile file) {
        return CommonResult.success(service.extractZip(file));
    }
}
