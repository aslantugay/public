package com.acme.platform.web;

import com.acme.platform.domain.Unit;
import com.acme.platform.service.UnitService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UnitController {

    private final UnitService unitService;

    public UnitController(UnitService unitService) {
        this.unitService = unitService;
    }

    @GetMapping("/api/firms/{firmId}/units")
    public List<Unit> list(@PathVariable Long firmId) {
        return unitService.listForFirm(firmId);
    }

    @PostMapping("/api/firms/{firmId}/units")
    public Unit create(@PathVariable Long firmId, @RequestBody Unit unit) {
        return unitService.create(firmId, unit);
    }

    @DeleteMapping("/api/units/{unitId}")
    public ResponseEntity<Void> delete(@PathVariable Long unitId) {
        unitService.delete(unitId);
        return ResponseEntity.noContent().build();
    }
}
