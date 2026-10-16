package com.helix.patient;

import com.helix.model.Patient;
import com.helix.model.SavedFilter;

import java.util.List;
import java.util.Map;

public interface PatientService {
    List<Patient> search(String name);
    List<Patient> listOrdered(Long tenantId, String sort);
    Patient get(Long id);
    List<Patient> byTenant(Long tenantId);
    SavedFilter saveFilter(SavedFilter f);
    List<Patient> runFilter(Long filterId);
    List<Map<String, Object>> aggregate(String by);
}
