package com.meridian.dataio.web;

import com.meridian.tenancy.domain.Tenant;
import java.util.List;
import java.util.Map;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/import")
public interface ImportApi {

    @PostMapping(value = "/tenant", consumes = MediaType.APPLICATION_XML_VALUE)
    Tenant importXml(@RequestBody String xml);

    @PostMapping(value = "/tenant/legacy", consumes = MediaType.APPLICATION_XML_VALUE)
    Tenant importLegacy(@RequestBody String archive);

    @PostMapping("/attachments")
    List<String> importAttachments(@RequestBody Map<String, String> body);
}
