package org.springframework.samples.petclinic.portal.service;

import org.springframework.samples.petclinic.portal.model.PetDocument;
import org.springframework.samples.petclinic.portal.model.ShareLink;

public interface PortalShareService {

    ShareLink createShare(Integer docId);

    PetDocument resolveShared(String token);
}
