package com.meridian.tenancy.dto;

import com.meridian.iam.domain.Role;
import java.util.ArrayList;
import java.util.List;

public class TenantDetailView {

    private Long id;
    private String name;
    private String taxNumber;
    private String billingEmail;
    private String planTier;
    private List<UnitView> units = new ArrayList<>();
    private List<MemberView> members = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTaxNumber() {
        return taxNumber;
    }

    public void setTaxNumber(String taxNumber) {
        this.taxNumber = taxNumber;
    }

    public String getBillingEmail() {
        return billingEmail;
    }

    public void setBillingEmail(String billingEmail) {
        this.billingEmail = billingEmail;
    }

    public String getPlanTier() {
        return planTier;
    }

    public void setPlanTier(String planTier) {
        this.planTier = planTier;
    }

    public List<UnitView> getUnits() {
        return units;
    }

    public List<MemberView> getMembers() {
        return members;
    }

    public static class UnitView {
        private final Long id;
        private final String name;
        private final String costCenter;

        public UnitView(Long id, String name, String costCenter) {
            this.id = id;
            this.name = name;
            this.costCenter = costCenter;
        }

        public Long getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getCostCenter() {
            return costCenter;
        }
    }

    public static class MemberView {
        private final Long id;
        private final String email;
        private final String displayName;
        private final Role role;
        private final boolean enabled;

        public MemberView(Long id, String email, String displayName, Role role, boolean enabled) {
            this.id = id;
            this.email = email;
            this.displayName = displayName;
            this.role = role;
            this.enabled = enabled;
        }

        public Long getId() {
            return id;
        }

        public String getEmail() {
            return email;
        }

        public String getDisplayName() {
            return displayName;
        }

        public Role getRole() {
            return role;
        }

        public boolean isEnabled() {
            return enabled;
        }
    }
}
