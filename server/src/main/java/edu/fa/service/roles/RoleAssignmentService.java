package edu.fa.service;

import edu.fa.model.PerformanceRole;
import edu.fa.model.RoleAssignment;
import edu.fa.model.Schedule;
import edu.fa.model.entities.worker.Worker;
import edu.fa.repository.PerformanceRoleRepository;
import edu.fa.repository.RoleAssignmentRepository;
import edu.fa.repository.ScheduleRepository;
import edu.fa.repository.WorkerRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleAssignmentService {

    @Autowired
    private RoleAssignmentRepository roleAssignmentRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private PerformanceRoleRepository performanceRoleRepository;

    @Autowired
    private WorkerRepository workerRepository;

    public List<RoleAssignment> getAllRoleAssignments() {
        return roleAssignmentRepository.findAll();
    }

    public RoleAssignment getRoleAssignmentById(Long id) {
        return roleAssignmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("RoleAssignment not found"));
    }

    public RoleAssignment createRoleAssignment(Long scheduleId, Long roleId, Long workerId) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new RuntimeException("Schedule not found"));
        PerformanceRole role = performanceRoleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found"));
        Worker worker = workerRepository.findById(workerId)
                .orElseThrow(() -> new RuntimeException("Worker not found"));

        RoleAssignment roleAssignment = new RoleAssignment();
        roleAssignment.setSchedule(schedule);
        roleAssignment.setRole(role);
        roleAssignment.setWorker(worker);

        return roleAssignmentRepository.save(roleAssignment);
    }

    public RoleAssignment updateRoleAssignment(Long id, Long scheduleId, Long roleId, Long workerId) {
        RoleAssignment existingRoleAssignment = roleAssignmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("RoleAssignment not found"));

        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new RuntimeException("Schedule not found"));
        PerformanceRole role = performanceRoleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found"));
        Worker worker = workerRepository.findById(workerId)
                .orElseThrow(() -> new RuntimeException("Worker not found"));

        existingRoleAssignment.setSchedule(schedule);
        existingRoleAssignment.setRole(role);
        existingRoleAssignment.setWorker(worker);

        return roleAssignmentRepository.save(existingRoleAssignment);
    }

    public void deleteRoleAssignment(Long id) {
        roleAssignmentRepository.deleteById(id);
    }

    public List<RoleAssignment> getRoleAssignmentsBySchedule(Long scheduleId) {
        return roleAssignmentRepository.findBySchedule_ScheduleId(scheduleId);
    }

    @Transactional
    public void deleteByScheduleId(Long scheduleId) {
        roleAssignmentRepository.deleteBySchedule_ScheduleId(scheduleId);
    }
}

