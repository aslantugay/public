package org.springframework.samples.petclinic.portal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.samples.petclinic.portal.model.PortalAccount;

import java.util.List;

public interface PortalAccountRepository extends JpaRepository<PortalAccount, Integer> {
    PortalAccount findByUsername(String username);

    List<PortalAccount> findTop100ByOrderByIdAsc();
}
