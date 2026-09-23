package com.meridian.tenancy.web;

import com.meridian.tenancy.domain.Unit;
import com.meridian.tenancy.dto.UnitCreateRequest;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface UnitApi {

    @GetMapping("/api/tenants/{tenantId}/units")
    List<Unit> list(@PathVariable Long tenantId);

    @PostMapping("/api/tenants/{tenantId}/units")
    Unit create(@PathVariable Long tenantId, @RequestBody UnitCreateRequest request);

    @DeleteMapping("/api/units/{unitId}")
    ResponseEntity<Void> delete(@PathVariable Long unitId);
}
