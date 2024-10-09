package com.erms.ERMS_Application.Quotation.Salesman;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;




@Service
public class SaleService {
	
	@Autowired
	private SaleRepository saleRepo;
	
	public SaleEntity SaveSale(SaleEntity sale) {
		SaleEntity save = saleRepo.save(sale);
		return save;
	}
	
	public Optional<SaleEntity> findById(long sId) {
		Optional<SaleEntity> find = saleRepo.findById(sId);
		return find;
	}
	
	public List<SaleEntity> findAll(){
		List<SaleEntity> All=saleRepo.findAll();
		return All;
	}
	
	///////////////////Partial Update////////////////////////////////
	public SaleEntity updatesale (long sId, SaleEntity sale) {
		SaleEntity update = saleRepo.findById(sId).orElseThrow();
	
	Optional.ofNullable(sale.getFirstName()).ifPresent(update::setFirstName);
	Optional.ofNullable(sale.getLastName()).ifPresent(update::setLastName);
	Optional.ofNullable(sale.getEmailId()).ifPresent(update::setEmailId);
	Optional.ofNullable(sale.getMobileNumber()).ifPresent(update::setMobileNumber);
	Optional.ofNullable(sale.getRole()).ifPresent(update::setRole);
	Optional.ofNullable(sale.getManager()).ifPresent(update::setManager);
	Optional.ofNullable(sale.getOfficialMailId()).ifPresent(update::setOfficialMailId);
	Optional.ofNullable(sale.getReportingManager()).ifPresent(update::setReportingManager);
	return saleRepo.save(update);
	
	}
	
	public void deleteBySaleId(long sId) {
		saleRepo.deleteById(sId);
	}
	
}
