package edu.fa.controller;

import edu.fa.model.entities.performance.PerformanceRole;
import edu.fa.service.performance.PerformanceRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/performance-roles")
public class PerformanceRoleController {

    @Autowired
    private PerformanceRoleService performanceRoleService;

    @GetMapping("/all")
    public List<PerformanceRole> getAllRoles() {
        return performanceRoleService.getAllRoles();
    }

    @GetMapping("/{id}")
    public PerformanceRole getRoleById(@PathVariable Long id) {
        return performanceRoleService.getRoleById(id);
    }

    @GetMapping
    public List<PerformanceRole> getRolesByPerformanceId(@RequestParam(required = false) Long performanceId) {
        if (performanceId == null) {
            return performanceRoleService.getAllRoles();
        }
        return performanceRoleService.getRolesByPerformanceId(performanceId);
    }

    @PostMapping
    public PerformanceRole createRole(@RequestBody PerformanceRole performanceRole) {
        return performanceRoleService.createRole(performanceRole);
    }

    @PutMapping("/{id}")
    public PerformanceRole updateRole(@PathVariable Long id, @RequestBody PerformanceRole performanceRole) {
        return performanceRoleService.updateRole(id, performanceRole);
    }

    @DeleteMapping("/{id}")
    public void deleteRole(@PathVariable Long id) {
        performanceRoleService.deleteRole(id);
    }
}

