package com.erms.ERMS_Application.Quotation.BankDetails;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankDetailsRepository extends JpaRepository<BankDetailsEntity,Long>{

}
