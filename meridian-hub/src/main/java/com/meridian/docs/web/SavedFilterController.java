package com.meridian.docs.web;

import com.meridian.docs.domain.SavedFilter;
import com.meridian.docs.dto.SavedFilterRequest;
import com.meridian.docs.service.SavedFilterService;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SavedFilterController implements SavedFilterApi {

    private final SavedFilterService savedFilterService;

    public SavedFilterController(SavedFilterService savedFilterService) {
        this.savedFilterService = savedFilterService;
    }

    @Override
    public SavedFilter create(SavedFilterRequest request) {
        return savedFilterService.create(request.getName(), request.getWhereClause());
    }

    @Override
    public List<Map<String, Object>> run(Long filterId) {
        return savedFilterService.run(filterId);
    }
}
