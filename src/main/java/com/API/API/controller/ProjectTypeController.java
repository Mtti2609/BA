package com.API.API.controller;

import com.API.API.model.ProjectType;
import com.API.API.service.ProjectTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/project-types")
public class ProjectTypeController {

    @Autowired
    private ProjectTypeService projectTypeService;

    // GET: /api/project-types
    @GetMapping
    public List<ProjectType> getAllProjectTypes() {
        return projectTypeService.getAllProjectTypes();
    }

    // GET: /api/project-types/{id}
    @GetMapping("/{id}")
    public ResponseEntity<ProjectType> getProjectTypeById(@PathVariable Integer id) {
        Optional<ProjectType> projectType = projectTypeService.getProjectTypeById(id);
        return projectType.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST: /api/project-types
    @PostMapping
    public ProjectType createProjectType(@RequestBody ProjectType projectType) {
        return projectTypeService.createProjectType(projectType);
    }

    // PUT: /api/project-types/{id}
    @PutMapping("/{id}")
    public ResponseEntity<ProjectType> updateProjectType(@PathVariable Integer id, @RequestBody ProjectType projectType) {
        try {
            ProjectType updatedProjectType = projectTypeService.updateProjectType(id, projectType);
            return ResponseEntity.ok(updatedProjectType);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE: /api/project-types/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProjectType(@PathVariable Integer id) {
        projectTypeService.deleteProjectType(id);
        return ResponseEntity.noContent().build();
    }
}
