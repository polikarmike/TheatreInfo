package edu.fa.service;

import edu.fa.model.dto.worker.responses.WorkerListResponse;
import edu.fa.model.entities.worker.Worker;
import edu.fa.model.entities.worker.JobRole;
import edu.fa.repository.WorkerRepository;
import edu.fa.repository.JobRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class WorkerService {

    @Autowired
    private WorkerRepository workerRepository;

    @Autowired
    private JobRoleRepository jobRoleRepository;

    private static final String UPLOAD_DIR = "D:\\Java\\TheatreInfo\\uploads";

    public List<Worker> getAllWorkers() {
        return workerRepository.findAll();
    }

    public Worker getWorker(Long id) {
        return workerRepository.getReferenceById(id);
    }

    public Worker createWorker(String firstName, String lastName, String biography, Long roleId, MultipartFile photoFile) throws IOException {
        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        String photoUrl = null;
        if (photoFile != null && !photoFile.isEmpty()) {
            String fileName = "worker_" + System.currentTimeMillis() + ".jpg";
            File file = new File(uploadDir, fileName);
            photoFile.transferTo(file);
            photoUrl = "/uploads/" + "\\" + fileName;
        }

        Worker worker = new Worker();
        worker.setFirstName(firstName);
        worker.setLastName(lastName);
        worker.setBiography(biography);
        worker.setPhotoUrl(photoUrl);

        JobRole role = jobRoleRepository.findById(roleId).orElse(null);
        worker.setRole(role);

        return workerRepository.save(worker);
    }

    public Worker updateWorker(Long id, String firstName, String lastName, String biography, Long roleId, MultipartFile photoFile) throws IOException {
        Worker worker = workerRepository.findById(id).orElseThrow(() -> new RuntimeException("Сотрудник не найден"));

        worker.setFirstName(firstName);
        worker.setLastName(lastName);
        worker.setBiography(biography);

        if (photoFile != null && !photoFile.isEmpty()) {
            String fileName = "worker_" + System.currentTimeMillis() + ".jpg";
            File file = new File(UPLOAD_DIR, fileName);
            photoFile.transferTo(file);
            worker.setPhotoUrl("/uploads/" + fileName);
        }
        System.out.println(worker.toString());

        JobRole role = jobRoleRepository.findById(roleId).orElseThrow(() -> new RuntimeException("Роль не найдена"));
        worker.setRole(role);

        return workerRepository.save(worker);
    }

    public void deleteWorker(Long id) {
        workerRepository.deleteById(id);
    }




    public List<WorkerListResponse> getAllWorkerBrief() {
        return workerRepository.findAllBrief();
    }
}

