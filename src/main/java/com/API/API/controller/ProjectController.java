package com.API.API.controller;

import com.API.API.model.Project;
import com.API.API.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    // GET: /api/projects
    @GetMapping
    public ResponseEntity<List<Project>> getAllProjects() {
        List<Project> projects = projectService.getAllProjects();
        return ResponseEntity.ok(projects);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Project> getProjectById(@PathVariable Integer id) {
        Optional<Project> project = projectService.getProjectById(id);
        return project.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // POST: /api/projects
    @PostMapping
    public ResponseEntity<Project> createProject(@RequestBody Project project) {
        try {
            // Gọi service để tạo dự án
            Project savedProject = projectService.createProject(project);
            return ResponseEntity.ok(savedProject);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(null);
        }
    }



    // PUT: /api/projects/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Project> updateProject(@PathVariable Integer id, @RequestBody Project project) {
        try {
            Project updatedProject = projectService.updateProject(id, project);
            return ResponseEntity.ok(updatedProject);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE: /api/projects/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Integer id) {
        projectService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }
}
