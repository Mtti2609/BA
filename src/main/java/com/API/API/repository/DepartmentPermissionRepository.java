package com.API.API.repository;

import com.API.API.model.Department;
import com.API.API.model.Permission;
import com.API.API.model.DepartmentPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepartmentPermissionRepository extends JpaRepository<DepartmentPermission, Integer> {

    // Find by department and permission
    Optional<DepartmentPermission> findByDepartmentAndPermission(Department department, Permission permission);

    // Find all permissions for a department
    List<DepartmentPermission> findAllByDepartment(Department department);

    // Check if a permission exists for a department
    boolean existsByDepartmentAndPermission(Department department, Permission permission);
}
