package com.erms.ERMS_Application.Authentication.admin;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {

    Optional<Admin> findByEmail(String email);

    // Query to fetch the latest adminId for Admin
    @Query("SELECT a.adminId FROM Admin a ORDER BY a.adminId DESC")
    List<String> findUserRoleIdByRole(Pageable pageable);


    boolean existsByAdminId(String adminId);

    Optional<Admin> findByAdminId(String adminId);

    boolean existsByEmail(String email);
}
