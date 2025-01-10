package edu.fa.service;

import edu.fa.model.Genre;
import edu.fa.model.Performance;
import edu.fa.model.PerformanceRole;
import edu.fa.repository.GenreRepository;
import edu.fa.repository.PerformanceRepository;
import edu.fa.repository.PerformanceRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.IOException;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PerformanceService {

    @Autowired
    private PerformanceRepository performanceRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private PerformanceRoleRepository performanceRoleRepository;

    private static final String UPLOAD_DIR = "D:\\Java\\TheatreInfo\\uploads";

    public List<Performance> getAllPerformances() {
        return performanceRepository.findAll();
    }

    public Performance getPerformance(Long id) {
        return performanceRepository.findById(id).orElseThrow(() -> new RuntimeException("Performance не найден"));
    }

    public Performance createPerformance(String title, String description, Integer duration, Long genreId, MultipartFile posterFile, List<PerformanceRole> roles) throws IOException {
        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        String posterUrl = null;
        if (posterFile != null && !posterFile.isEmpty()) {
            String fileName = "performance_" + System.currentTimeMillis() + ".jpg";
            File file = new File(uploadDir, fileName);
            posterFile.transferTo(file);
            posterUrl = "/uploads/" + fileName;
        }

        Performance performance = new Performance();
        performance.setTitle(title);
        performance.setDescription(description);
        performance.setDuration(duration);
        performance.setPosterUrl(posterUrl);
        Genre genre = genreRepository.findById(genreId).orElseThrow(() -> new RuntimeException("Жанр не найден"));
        performance.setGenre(genre);

        Performance savedPerformance = performanceRepository.save(performance);

        for (PerformanceRole role : roles) {
            role.setPerformance(savedPerformance);
            performanceRoleRepository.save(role);
        }

        return savedPerformance;
    }

    public void deletePerformance(Long id) {
        performanceRepository.deleteById(id);
    }

    public Performance updatePerformance(Long id, String title, String description, Integer duration, Long genreId, MultipartFile posterFile, List<PerformanceRole> roles) throws IOException {
        Performance performance = performanceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Выступление не найдено"));

        performance.setTitle(title);
        performance.setDescription(description);
        performance.setDuration(duration);

        if (posterFile != null && !posterFile.isEmpty()) {
            String fileName = "performance_" + System.currentTimeMillis() + ".jpg";
            File file = new File(UPLOAD_DIR, fileName);
            posterFile.transferTo(file);
            performance.setPosterUrl("/uploads/" + fileName);
        }

        Genre genre = genreRepository.findById(genreId).orElseThrow(() -> new RuntimeException("Жанр не найден"));
        performance.setGenre(genre);

        Performance updatedPerformance = performanceRepository.save(performance);

        performanceRoleRepository.deleteAll(updatedPerformance.getRoles());
        for (PerformanceRole role : roles) {
            role.setPerformance(updatedPerformance);
            performanceRoleRepository.save(role);
        }

        return updatedPerformance;
    }
}
