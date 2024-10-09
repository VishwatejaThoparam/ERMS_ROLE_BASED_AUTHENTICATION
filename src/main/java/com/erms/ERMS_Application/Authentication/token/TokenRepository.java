package com.erms.ERMS_Application.Authentication.token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;



@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {

    @Query("SELECT t FROM Token t WHERE (t.admin.id = :userId OR t.managers.id = :userId OR " +
            "t.saleManager.id = :userId OR t.technician.id = :userId OR t.sales.id = :userId) " +
            "AND (t.expired = false AND t.revoked = false)")
    List<Token> findAllValidTokensByUser(Integer userId);

    @Query("SELECT t FROM Token t WHERE t.admin.id = :adminId AND (t.expired = false AND t.revoked = false)")
    List<Token> findAllValidTokensByAdmin(Integer adminId);

    @Query("SELECT t FROM Token t WHERE t.managers.id = :managerId AND (t.expired = false AND t.revoked = false)")
    List<Token> findAllValidTokensByManager(Integer managerId);

    @Query("SELECT t FROM Token t WHERE t.saleManager.id = :saleManagerId AND (t.expired = false AND t.revoked = false)")
    List<Token> findAllValidTokensBySaleManager(Integer saleManagerId);

    @Query("SELECT t FROM Token t WHERE t.technician.id = :technicianId AND (t.expired = false AND t.revoked = false)")
    List<Token> findAllValidTokensByTechnician(Integer technicianId);

    @Query("SELECT t FROM Token t WHERE t.sales.id = :salesId AND (t.expired = false AND t.revoked = false)")
    List<Token> findAllValidTokensBySales(Integer salesId);

    Optional<Token> findByToken(String token);
}

