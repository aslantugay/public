package com.helix.patient;

import com.helix.model.Patient;
import com.helix.model.SavedFilter;
import com.helix.repo.PatientQueryDao;
import com.helix.repo.PatientRepository;
import com.helix.repo.SavedFilterRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patients;
    private final PatientQueryDao dao;
    private final SavedFilterRepository filters;

    public PatientServiceImpl(PatientRepository patients, PatientQueryDao dao, SavedFilterRepository filters) {
        this.patients = patients;
        this.dao = dao;
        this.filters = filters;
    }

    @Override
    public List<Patient> search(String name) {
        return dao.searchByName(name);
    }

    @Override
    public List<Patient> listOrdered(Long tenantId, String sort) {
        return dao.listOrdered(tenantId, sort);
    }

    @Override
    public Patient get(Long id) {
        return patients.findById(id).orElseThrow(() -> new RuntimeException("not found"));
    }

    @Override
    public List<Patient> byTenant(Long tenantId) {
        return patients.findByTenantId(tenantId);
    }

    @Override
    public SavedFilter saveFilter(SavedFilter f) {
        return filters.save(f);
    }

    @Override
    public List<Patient> runFilter(Long filterId) {
        SavedFilter f = filters.findById(filterId).orElseThrow(() -> new RuntimeException("no filter"));
        return dao.execFilter(f.getWhereClause());
    }

    @Override
    public List<Map<String, Object>> aggregate(String by) {
        return dao.aggregateByColumn(by);
    }
}
