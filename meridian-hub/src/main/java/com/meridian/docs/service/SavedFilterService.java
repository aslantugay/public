package com.meridian.docs.service;

import com.meridian.docs.domain.SavedFilter;
import java.util.List;
import java.util.Map;

public interface SavedFilterService {

    SavedFilter create(String name, String whereClause);

    List<Map<String, Object>> run(Long filterId);
}
