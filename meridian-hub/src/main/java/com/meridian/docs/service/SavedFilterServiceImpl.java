package com.meridian.docs.service;

import com.meridian.docs.SavedFilterRepository;
import com.meridian.docs.domain.SavedFilter;
import com.meridian.docs.query.DocumentReportGateway;
import com.meridian.platform.security.AbstractTenantService;
import com.meridian.platform.security.CurrentUserService;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class SavedFilterServiceImpl extends AbstractTenantService implements SavedFilterService {

    private final SavedFilterRepository savedFilterRepository;
    private final DocumentReportGateway reportGateway;

    public SavedFilterServiceImpl(CurrentUserService currentUserService,
                                  SavedFilterRepository savedFilterRepository,
                                  DocumentReportGateway reportGateway) {
        super(currentUserService);
        this.savedFilterRepository = savedFilterRepository;
        this.reportGateway = reportGateway;
    }

    @Override
    public SavedFilter create(String name, String whereClause) {
        SavedFilter filter = new SavedFilter();
        filter.setTenantId(currentTenantId());
        filter.setName(name);
        filter.setWhereClause(whereClause);
        return savedFilterRepository.save(filter);
    }

    @Override
    public List<Map<String, Object>> run(Long filterId) {
        SavedFilter filter = savedFilterRepository.findById(filterId).orElseThrow();
        return reportGateway.runSavedFilter(currentTenantId(), filter.getWhereClause());
    }
}
