package edu.fa.service;

import edu.fa.model.entities.worker.JobRole;
import edu.fa.repository.JobRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobRoleService {

    @Autowired
    private JobRoleRepository jobRoleRepository;

    public List<JobRole> getAllRoles() {
        return jobRoleRepository.findAll();
    }

    public JobRole createRole(JobRole jobRole) {
        return jobRoleRepository.save(jobRole);
    }
}

