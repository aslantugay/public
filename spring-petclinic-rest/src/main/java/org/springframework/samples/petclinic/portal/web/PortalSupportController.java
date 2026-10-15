package org.springframework.samples.petclinic.portal.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.portal.model.SupportMessage;
import org.springframework.samples.petclinic.portal.service.PortalSupportService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/portal/support")
public class PortalSupportController {

    @Autowired
    private PortalSupportService supportService;

    /** Landing-page activity wall shown to every signed-in member. */
    @GetMapping("/recent")
    public List<SupportMessage> recent() {
        return supportService.recent();
    }

    @PostMapping
    public SupportMessage post(@RequestBody SupportMessage message) {
        return supportService.post(message);
    }
}
