package com.API.API.repository;

import com.API.API.model.Permission;
import com.API.API.model.DepartmentPermission;
import com.API.API.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Integer> {

    // Tìm danh sách Permission dựa trên Department ID
    @Query("SELECT dp.permission FROM DepartmentPermission dp WHERE dp.department.departmentId = :departmentId")
    List<Permission> findPermissionsByDepartmentId(@Param("departmentId") Integer departmentId);
}
