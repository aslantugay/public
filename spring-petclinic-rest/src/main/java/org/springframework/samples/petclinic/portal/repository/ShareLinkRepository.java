package org.springframework.samples.petclinic.portal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.samples.petclinic.portal.model.ShareLink;

public interface ShareLinkRepository extends JpaRepository<ShareLink, Integer> {
    ShareLink findByToken(String token);
}
