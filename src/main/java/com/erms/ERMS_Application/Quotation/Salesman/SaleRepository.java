package com.erms.ERMS_Application.Quotation.Salesman;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleRepository extends JpaRepository<SaleEntity,Long>{
	

}
