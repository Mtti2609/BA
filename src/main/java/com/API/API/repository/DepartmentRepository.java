package com.API.API.repository;

import com.API.API.model.Department;
import com.API.API.model.DepartmentPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer> {


}
