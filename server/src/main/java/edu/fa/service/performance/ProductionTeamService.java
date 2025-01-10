package edu.fa.service;

import edu.fa.model.ProductionTeam;
import edu.fa.model.Schedule;
import edu.fa.model.entities.worker.Worker;
import edu.fa.model.entities.worker.JobRole;
import edu.fa.repository.ProductionTeamRepository;
import edu.fa.repository.ScheduleRepository;
import edu.fa.repository.WorkerRepository;
import edu.fa.repository.JobRoleRepository;
import edu.fa.service.roles.WorkerService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductionTeamService {

    @Autowired
    private ProductionTeamRepository productionTeamRepository;

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private WorkerService workerService;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private WorkerRepository workerRepository;

    @Autowired
    private JobRoleRepository jobRoleRepository;

    public List<ProductionTeam> getAllProductionTeams() {
        return productionTeamRepository.findAll();
    }

    public ProductionTeam getProductionTeamById(Long id) {
        return productionTeamRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ProductionTeam not found"));
    }

    public ProductionTeam createProductionTeam(Long scheduleId, Long workerId, Long roleId) {
        ProductionTeam productionTeam = new ProductionTeam();
        productionTeam.setSchedule(scheduleService.getScheduleById(scheduleId));
        productionTeam.setWorker(workerService.getWorker(workerId));
        productionTeam.setRole(getJobRoleById(roleId));
        return productionTeamRepository.save(productionTeam);
    }

    public ProductionTeam updateProductionTeam(Long id, ProductionTeam productionTeam, Long scheduleId, Long workerId, Long roleId) {
        ProductionTeam existingProductionTeam = productionTeamRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ProductionTeam not found"));

        return getProductionTeam(scheduleId, workerId, roleId, existingProductionTeam);
    }

    private ProductionTeam getProductionTeam(Long scheduleId, Long workerId, Long roleId, ProductionTeam existingProductionTeam) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new RuntimeException("Schedule not found"));
        Worker worker = workerRepository.findById(workerId)
                .orElseThrow(() -> new RuntimeException("Worker not found"));
        JobRole role = jobRoleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        existingProductionTeam.setSchedule(schedule);
        existingProductionTeam.setWorker(worker);
        existingProductionTeam.setRole(role);

        return productionTeamRepository.save(existingProductionTeam);
    }

    public void deleteProductionTeam(Long id) {
        productionTeamRepository.deleteById(id);
    }

    private JobRole getJobRoleById(Long roleId) {
        return jobRoleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found"));
    }

    public List<ProductionTeam> getProductionTeamsBySchedule(Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new RuntimeException("Schedule not found"));
        return productionTeamRepository.findBySchedule(schedule);
    }

    @Transactional
    public void deleteByScheduleId(Long scheduleId) {
        productionTeamRepository.deleteBySchedule_ScheduleId(scheduleId);
    }

}
