package com.acme.platform.service.impl;

import com.acme.platform.domain.Firm;
import com.acme.platform.repository.FirmRepository;
import com.acme.platform.security.CurrentUserService;
import com.acme.platform.service.FirmService;
import com.acme.platform.service.support.AbstractTenantService;
import com.acme.platform.web.dto.FirmDetailResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FirmServiceImpl extends AbstractTenantService implements FirmService {

    private final FirmRepository firmRepository;

    public FirmServiceImpl(CurrentUserService currentUserService, FirmRepository firmRepository) {
        super(currentUserService);
        this.firmRepository = firmRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public FirmDetailResponse detail(Long firmId) {
        Firm firm = firmRepository.findById(firmId).orElse(null);
        if (firm == null) {
            return null;
        }

        FirmDetailResponse response = new FirmDetailResponse();
        response.setId(firm.getId());
        response.setName(firm.getName());
        response.setTaxNumber(firm.getTaxNumber());
        response.setBillingEmail(firm.getBillingEmail());
        response.setPlanTier(firm.getPlanTier());

        firm.getUnits().forEach(u ->
                response.getUnits().add(new FirmDetailResponse.UnitView(
                        u.getId(), u.getName(), u.getCostCenter())));
        firm.getMembers().forEach(m ->
                response.getMembers().add(new FirmDetailResponse.MemberView(
                        m.getId(), m.getEmail(), m.getDisplayName(), m.getRole(), m.isEnabled())));
        return response;
    }

    @Override
    public Firm create(Firm firm) {
        return firmRepository.save(firm);
    }

    @Override
    public Firm save(Firm firm) {
        return firmRepository.save(firm);
    }
}
