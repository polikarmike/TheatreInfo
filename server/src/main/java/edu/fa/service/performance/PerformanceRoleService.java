package edu.fa.service;

import edu.fa.model.PerformanceRole;
import edu.fa.repository.PerformanceRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PerformanceRoleService {

    @Autowired
    private PerformanceRoleRepository performanceRoleRepository;

    public List<PerformanceRole> getAllRoles() {
        return performanceRoleRepository.findAll();
    }

    public PerformanceRole getRoleById(Long roleId) {
        return performanceRoleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found"));
    }

    public PerformanceRole createRole(PerformanceRole performanceRole) {
        return performanceRoleRepository.save(performanceRole);
    }

    public PerformanceRole updateRole(Long roleId, PerformanceRole performanceRole) {
        PerformanceRole existingRole = performanceRoleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found"));
        existingRole.setRoleName(performanceRole.getRoleName());
        existingRole.setPerformance(performanceRole.getPerformance());
        return performanceRoleRepository.save(existingRole);
    }

    public void deleteRole(Long roleId) {
        performanceRoleRepository.deleteById(roleId);
    }

    public List<PerformanceRole> getRolesByPerformanceId(Long performanceId) {
        return performanceRoleRepository.findByPerformance_PerformanceId(performanceId);
    }

}

