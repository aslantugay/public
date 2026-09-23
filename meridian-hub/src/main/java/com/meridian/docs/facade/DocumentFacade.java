package com.meridian.docs.facade;

import com.meridian.docs.query.DocumentSearchCriteria;
import java.util.List;
import java.util.Map;

public interface DocumentFacade {

    List<Map<String, Object>> search(DocumentSearchCriteria criteria);
}
