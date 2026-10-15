package org.springframework.samples.petclinic.portal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.samples.petclinic.portal.model.CreditCoupon;

public interface CreditCouponRepository extends JpaRepository<CreditCoupon, Integer> {
    CreditCoupon findByCode(String code);
}
