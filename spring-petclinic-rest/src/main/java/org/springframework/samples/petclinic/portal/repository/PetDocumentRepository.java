package org.springframework.samples.petclinic.portal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.samples.petclinic.portal.model.PetDocument;

import java.util.List;

public interface PetDocumentRepository extends JpaRepository<PetDocument, Integer> {
    List<PetDocument> findByOwnerAccountId(Integer ownerAccountId);
}
