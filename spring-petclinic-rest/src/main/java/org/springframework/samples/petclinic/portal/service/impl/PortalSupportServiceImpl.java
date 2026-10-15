package org.springframework.samples.petclinic.portal.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.portal.model.SupportMessage;
import org.springframework.samples.petclinic.portal.repository.SupportMessageRepository;
import org.springframework.samples.petclinic.portal.security.PortalContext;
import org.springframework.samples.petclinic.portal.service.PortalSupportService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PortalSupportServiceImpl implements PortalSupportService {

    @Autowired
    private SupportMessageRepository supportMessageRepository;
    @Autowired
    private PortalContext portalContext;

    /** Landing-page support wall — most recent messages across all members. */
    @Override
    public List<SupportMessage> recent() {
        return supportMessageRepository.findTop50ByOrderByCreatedAtDesc();
    }

    @Override
    public SupportMessage post(SupportMessage message) {
        message.setAccountId(portalContext.currentAccountId());
        return supportMessageRepository.save(message);
    }
}
