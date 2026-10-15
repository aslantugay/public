package org.springframework.samples.petclinic.portal.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.portal.model.PetDocument;
import org.springframework.samples.petclinic.portal.model.ShareLink;
import org.springframework.samples.petclinic.portal.service.PortalShareService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/portal/share")
public class PortalShareController {

    @Autowired
    private PortalShareService shareService;

    @PostMapping
    public ShareLink create(@RequestParam Integer docId) {
        return shareService.createShare(docId);
    }

    @GetMapping("/shared/{token}")
    public PetDocument shared(@PathVariable String token) {
        return shareService.resolveShared(token);
    }
}
