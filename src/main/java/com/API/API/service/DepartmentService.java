package com.API.API.service;

import com.API.API.model.Department;
import com.API.API.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    // Retrieve all departments
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    // Retrieve department by ID
    public Optional<Department> getDepartmentById(Integer id) {
        return departmentRepository.findById(id);
    }

    // Create a new department
    public Department createDepartment(Department department) {
        return departmentRepository.save(department);
    }

    // Update department by ID
    public Department updateDepartment(Integer id, Department updatedDepartment) {
        return departmentRepository.findById(id)
                .map(department -> {
                    department.setDepartmentName(updatedDepartment.getDepartmentName());
                    return departmentRepository.save(department);
                })
                .orElseThrow(() -> new IllegalArgumentException("Department not found with ID: " + id));
    }

    // Delete department by ID
    public void deleteDepartment(Integer id) {
        if (!departmentRepository.existsById(id)) {
            throw new IllegalArgumentException("Department not found with ID: " + id);
        }
        departmentRepository.deleteById(id);
    }
}
