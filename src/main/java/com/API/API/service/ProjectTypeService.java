package com.API.API.service;

import com.API.API.model.ProjectType;
import com.API.API.repository.ProjectTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectTypeService {

    @Autowired
    private ProjectTypeRepository projectTypeRepository;

    public List<ProjectType> getAllProjectTypes() {
        return projectTypeRepository.findAll();
    }

    public Optional<ProjectType> getProjectTypeById(Integer id) {
        return projectTypeRepository.findById(id);
    }

    public ProjectType createProjectType(ProjectType projectType) {
        return projectTypeRepository.save(projectType);
    }

    public ProjectType updateProjectType(Integer id, ProjectType updatedProjectType) {
        return projectTypeRepository.findById(id)
                .map(projectType -> {
                    projectType.setTypeName(updatedProjectType.getTypeName());
                    return projectTypeRepository.save(projectType);
                })
                .orElseThrow(() -> new RuntimeException("Project Type not found"));
    }

    public void deleteProjectType(Integer id) {
        projectTypeRepository.deleteById(id);
    }
}
