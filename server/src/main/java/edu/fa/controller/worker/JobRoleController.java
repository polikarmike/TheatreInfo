package edu.fa.controller;

import edu.fa.model.entities.worker.JobRole;
import edu.fa.service.roles.JobRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/job-roles")
public class JobRoleController {

    @Autowired
    private JobRoleService jobRoleService;

    @GetMapping
    public List<JobRole> getAllRoles() {
        return jobRoleService.getAllRoles();
    }

    @PostMapping
    public JobRole createRole(@RequestBody JobRole jobRole) {
        return jobRoleService.createRole(jobRole);
    }
}
