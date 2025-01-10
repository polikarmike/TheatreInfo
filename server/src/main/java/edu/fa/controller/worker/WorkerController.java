package edu.fa.controller;

import edu.fa.model.dto.worker.requests.WorkerCreateRequest;
import edu.fa.model.dto.worker.requests.WorkerUpdateRequest;
import edu.fa.model.dto.worker.responses.WorkerListResponse;
import edu.fa.model.entities.worker.Worker;
import edu.fa.service.roles.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/worker")
public class WorkerController {

    @Autowired
    private WorkerService workerService;

    @GetMapping
    public List<WorkerListResponse> getAllWorkers() {
        return workerService.getAllWorkerBrief();
    }

//    @GetMapping
//    public List<Worker> getAllWorkers() {
//        return workerService.getAllWorkers();
//    }

    @GetMapping("/{id}")
    public Worker getWorker(@PathVariable Long id) {
        return workerService.getWorker(id);
    }

//    @PostMapping
//    public Worker createWorker(
//            @RequestParam("firstName") String firstName,
//            @RequestParam("lastName") String lastName,
//            @RequestParam("biography") String biography,
//            @RequestParam("roleId") Long roleId,
//            @RequestParam(value = "photo", required = false) MultipartFile photoFile) throws IOException {
//
//        return workerService.createWorker(firstName, lastName, biography, roleId, photoFile);
//    }
@PostMapping
public Worker createWorker(@ModelAttribute WorkerCreateRequest workerCreateRequest) throws IOException {
        return workerService.createWorker(workerCreateRequest); }

    @DeleteMapping("/{id}")
    public void deleteWorker(@PathVariable Long id) {
        workerService.deleteWorker(id);
    }

//    @PutMapping("/{id}")
//    public Worker updateWorker(
//            @PathVariable Long id,
//            @RequestParam("firstName") String firstName,
//            @RequestParam("lastName") String lastName,
//            @RequestParam("biography") String biography,
//            @RequestParam("roleId") Long roleId,
//            @RequestParam(value = "photo", required = false) MultipartFile photoFile) throws IOException {
//
//        return workerService.updateWorker(id, firstName, lastName, biography, roleId, photoFile);
//    }

    @PutMapping("/{id}") public Worker updateWorker(@PathVariable Long id,
                                                    @ModelAttribute WorkerUpdateRequest workerUpdateRequest)
            throws IOException {
        workerUpdateRequest.setId(id);
        return workerService.updateWorker(workerUpdateRequest);
    }
}

