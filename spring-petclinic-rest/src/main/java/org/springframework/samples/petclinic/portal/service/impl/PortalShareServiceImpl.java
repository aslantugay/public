package org.springframework.samples.petclinic.portal.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.portal.model.PetDocument;
import org.springframework.samples.petclinic.portal.model.ShareLink;
import org.springframework.samples.petclinic.portal.repository.PetDocumentRepository;
import org.springframework.samples.petclinic.portal.repository.ShareLinkRepository;
import org.springframework.samples.petclinic.portal.security.PortalContext;
import org.springframework.samples.petclinic.portal.service.PortalShareService;
import org.springframework.samples.petclinic.portal.util.PortalTokenUtil;
import org.springframework.stereotype.Service;

@Service
public class PortalShareServiceImpl implements PortalShareService {

    @Autowired
    private ShareLinkRepository shareLinkRepository;
    @Autowired
    private PetDocumentRepository documentRepository;
    @Autowired
    private PortalContext portalContext;

    @Override
    public ShareLink createShare(Integer docId) {
        PetDocument doc = documentRepository.findById(docId).orElseThrow();
        ShareLink link = new ShareLink();
        link.setDocId(doc.getId());
        link.setOwnerAccountId(portalContext.currentAccountId());
        link.setToken(PortalTokenUtil.issue(doc.getId(), portalContext.currentAccountId()));
        return shareLinkRepository.save(link);
    }

    @Override
    public PetDocument resolveShared(String token) {
        if (!PortalTokenUtil.verify(token)) {
            throw new RuntimeException("invalid token");
        }
        ShareLink link = shareLinkRepository.findByToken(token);
        if (link == null) {
            throw new RuntimeException("not found");
        }
        return documentRepository.findById(link.getDocId()).orElse(null);
    }
}
