package com.helix.patient;

import com.helix.common.CommonResult;
import com.helix.model.SavedFilter;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

    private final PatientService service;

    public PatientController(PatientService service) {
        this.service = service;
    }

    @GetMapping("/search")
    public CommonResult search(@RequestParam String name) {
        return CommonResult.success(service.search(name));
    }

    @GetMapping("/list")
    public CommonResult list(@RequestParam Long tenantId,
                             @RequestHeader(value = "X-Sort", defaultValue = "name ASC") String sort) {
        return CommonResult.success(service.listOrdered(tenantId, sort));
    }

    @GetMapping("/{id}")
    public CommonResult get(@PathVariable Long id) {
        return CommonResult.success(service.get(id));
    }

    @GetMapping("/by-tenant")
    public CommonResult byTenant(@RequestParam Long tenantId) {
        return CommonResult.success(service.byTenant(tenantId));
    }

    @PostMapping("/filters")
    public CommonResult saveFilter(@RequestBody SavedFilter f) {
        return CommonResult.success(service.saveFilter(f));
    }

    @GetMapping("/filters/{id}/run")
    public CommonResult runFilter(@PathVariable Long id) {
        return CommonResult.success(service.runFilter(id));
    }

    @GetMapping("/aggregate")
    public CommonResult aggregate(@RequestParam String by) {
        return CommonResult.success(service.aggregate(by));
    }
}
