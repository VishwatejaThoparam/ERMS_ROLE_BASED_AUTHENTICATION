package com.erms.ERMS_Application.Authentication.technician;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TechnicianRepository extends JpaRepository<Technician, Long> {

    Optional<Technician> findByEmail(String email);

    @Query("SELECT t.technicianId FROM Technician t ORDER BY t.technicianId DESC")
    List<String> findUserRoleIdByRole(Pageable pageable);


    boolean existsByEmail(String email);
}
