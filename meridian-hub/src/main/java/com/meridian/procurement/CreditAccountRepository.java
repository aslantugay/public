package com.meridian.procurement;

import com.meridian.procurement.domain.CreditAccount;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditAccountRepository extends JpaRepository<CreditAccount, Long> {

    Optional<CreditAccount> findByTenantId(Long tenantId);
}
