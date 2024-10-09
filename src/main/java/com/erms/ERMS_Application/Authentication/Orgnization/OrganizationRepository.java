package com.erms.ERMS_Application.Authentication.Orgnization;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> {

    // Query to fetch the latest Organization specifically for organizations
    @Query("SELECT o.organizationId FROM Organization o ORDER BY o.organizationId DESC")
    List<String> findUserRoleIdByRole(Pageable pageable);

    // Check for existing email
    boolean existsByEmail(String email);

    // Find by email
    Optional<Organization> findByEmail(String email);

    Optional<Organization> findByOrganizationId(String organizationId);


}
