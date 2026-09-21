package com.acme.platform.repository;

import com.acme.platform.domain.Firm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FirmRepository extends JpaRepository<Firm, Long> {
}
