package edu.fa.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.fa.model.entities.performance.Performance;
import edu.fa.model.entities.performance.PerformanceRole;
import edu.fa.service.performance.PerformanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/performances")
public class PerformanceController {

    @Autowired
    private PerformanceService performanceService;

    @GetMapping
    public List<Performance> getAllPerformances() {
        return performanceService.getAllPerformances();
    }

    @GetMapping("/{id}")
    public Performance getPerformance(@PathVariable Long id) {
        return performanceService.getPerformance(id);
    }

    @PostMapping(consumes = {"multipart/form-data"})
    public Performance createPerformance(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("duration") Integer duration,
            @RequestParam("genreId") Long genreId,
            @RequestPart(value = "poster", required = false) MultipartFile posterFile,
            @RequestParam("roles") String rolesJson) throws IOException {

        List<PerformanceRole> roles = parseRolesJson(rolesJson);
        return performanceService.createPerformance(title, description, duration, genreId, posterFile, roles);
    }

    @DeleteMapping("/{id}")
    public void deletePerformance(@PathVariable Long id) {
        performanceService.deletePerformance(id);
    }

    @PutMapping(value = "/{id}", consumes = {"multipart/form-data"})
    public Performance updatePerformance(
            @PathVariable Long id,
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("duration") Integer duration,
            @RequestParam("genreId") Long genreId,
            @RequestPart(value = "poster", required = false) MultipartFile posterFile,
            @RequestParam("roles") String rolesJson) throws IOException {

        List<PerformanceRole> roles = parseRolesJson(rolesJson);
        return performanceService.updatePerformance(id, title, description, duration, genreId, posterFile, roles);
    }

    private List<PerformanceRole> parseRolesJson(String rolesJson) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(rolesJson, new TypeReference<List<PerformanceRole>>() {});
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при парсинге списка ролей", e);
        }
    }
}

