package com.erms.ERMS_Application.Quotation.Form;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FormRepository extends JpaRepository<FormEntity, Long> {
    Optional<FormEntity> findTopByQuotationNumberContainingOrderByFIdDesc(String financialYear);
}


