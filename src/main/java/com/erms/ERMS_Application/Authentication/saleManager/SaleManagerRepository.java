package com.erms.ERMS_Application.Authentication.saleManager;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface SaleManagerRepository extends JpaRepository<SaleManager, Long> {

    Optional<SaleManager> findByEmail(String email);

    @Query("SELECT s.salesManagerId FROM SaleManager s ORDER BY s.salesManagerId DESC")
    List<String> findUserRoleIdByRole(Pageable pageable);

    // Find SaleManager by their unique saleManagerId
    Optional<SaleManager> findBySalesManagerId(String salesManagerId);


    boolean existsByEmail(String email);


}

