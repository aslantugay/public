package com.acme.platform.service;

import com.acme.platform.domain.Firm;
import com.acme.platform.web.dto.FirmDetailResponse;

public interface FirmService {

    FirmDetailResponse detail(Long firmId);

    Firm create(Firm firm);

    Firm save(Firm firm);
}
