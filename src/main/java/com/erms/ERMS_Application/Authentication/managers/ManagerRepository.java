package com.erms.ERMS_Application.Authentication.managers;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ManagerRepository extends JpaRepository<Managers, Long> {

    Optional<Managers> findByEmail(String email);

    @Query("SELECT m.managersId FROM Managers m ORDER BY m.managersId DESC")
    List<String> findUserRoleIdByRole(Pageable pageable);

    Optional<Managers> findByManagersId(String managersId);

    boolean existsByEmail(String email);
}

