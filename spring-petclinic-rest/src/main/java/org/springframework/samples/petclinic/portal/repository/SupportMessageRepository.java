package org.springframework.samples.petclinic.portal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.samples.petclinic.portal.model.SupportMessage;

import java.util.List;

public interface SupportMessageRepository extends JpaRepository<SupportMessage, Integer> {
    List<SupportMessage> findTop50ByOrderByCreatedAtDesc();
}
