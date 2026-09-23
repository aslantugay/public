package com.meridian.tenancy.facade;

import com.meridian.iam.UserRepository;
import com.meridian.tenancy.UnitRepository;
import com.meridian.tenancy.domain.Tenant;
import com.meridian.tenancy.dto.TenantDetailView;
import com.meridian.tenancy.service.TenantService;
import org.springframework.stereotype.Component;

@Component
public class TenantFacadeImpl implements TenantFacade {

    private final TenantService tenantService;
    private final UnitRepository unitRepository;
    private final UserRepository userRepository;

    public TenantFacadeImpl(TenantService tenantService,
                            UnitRepository unitRepository,
                            UserRepository userRepository) {
        this.tenantService = tenantService;
        this.unitRepository = unitRepository;
        this.userRepository = userRepository;
    }

    @Override
    public TenantDetailView detail(Long tenantId) {
        Tenant tenant = tenantService.getById(tenantId);
        if (tenant == null) {
            return null;
        }
        TenantDetailView view = new TenantDetailView();
        view.setId(tenant.getId());
        view.setName(tenant.getName());
        view.setTaxNumber(tenant.getTaxNumber());
        view.setBillingEmail(tenant.getBillingEmail());
        view.setPlanTier(tenant.getPlanTier());

        unitRepository.findByTenantId(tenantId).forEach(u ->
                view.getUnits().add(new TenantDetailView.UnitView(
                        u.getId(), u.getName(), u.getCostCenter())));
        userRepository.findByTenantId(tenantId).forEach(m ->
                view.getMembers().add(new TenantDetailView.MemberView(
                        m.getId(), m.getEmail(), m.getDisplayName(), m.getRole(), m.isEnabled())));
        return view;
    }
}
