package com.API.API.repository;

import com.API.API.model.Permission;
import com.API.API.model.User;
import com.API.API.model.UserPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserPermissionRepository extends JpaRepository<UserPermission, Integer> {

    Optional<UserPermission> findByUserAndPermission(User user, Permission permission);

    @Transactional
    @Modifying
    @Query("DELETE FROM UserPermission up WHERE up.user.userId = :userId")
    void deleteByUserId(@Param("userId") Integer userId);

    // Nếu bạn cần tìm danh sách quyền của user
    List<UserPermission> findByUser(User user);


    boolean existsByUserAndPermission(User user, Permission permission);

    @Query("SELECT up.permission FROM UserPermission up WHERE up.user.userId = :userId")
    List<Permission> findPermissionsByUserId(Integer userId);
}
