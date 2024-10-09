package com.erms.ERMS_Application.Quotation.BankDetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BankDetailsService {

	
	@Autowired
	private BankDetailsRepository bankDetailsRepository; 
	
	///////////////////////Save BankDetails ///////////////////////////////
	public BankDetailsEntity saveBankDetails(BankDetailsEntity bankDetails) {
		BankDetailsEntity BankDetails =bankDetailsRepository.save(bankDetails);
		return BankDetails;
	}
	
	//////////////////////Get BankDetails By Id//////////////////////////////
	public Optional<BankDetailsEntity> getBankDetails (long BdId ){
		Optional<BankDetailsEntity> bankdetails = bankDetailsRepository.findById(BdId);
		return bankdetails;
	}
	
	//////////////////////GetAll BankDetails////////////////////////////////
	public List<BankDetailsEntity> getAllBankdetails(){
		List<BankDetailsEntity> AllBankDetails = bankDetailsRepository.findAll();
		return AllBankDetails;	
	}
	
	public BankDetailsEntity updateBankDetails(long BdId, BankDetailsEntity updateBank) {
        Optional<BankDetailsEntity> existingDetailsOpt = bankDetailsRepository.findById(BdId);
        if (existingDetailsOpt.isPresent()) {
            BankDetailsEntity existingDetails = existingDetailsOpt.get();

            // Update only non-null fields
            Optional.ofNullable(updateBank.getAccountHolderName()).ifPresent(existingDetails::setAccountHolderName);
            Optional.ofNullable(updateBank.getAccountNumber()).ifPresent(existingDetails::setAccountNumber);
            Optional.ofNullable(updateBank.getBankName()).ifPresent(existingDetails::setBankName);
            Optional.ofNullable(updateBank.getBranchname()).ifPresent(existingDetails::setBranchname);
            Optional.ofNullable(updateBank.getIfsccode()).ifPresent(existingDetails::setIfsccode);

            return bankDetailsRepository.save(existingDetails); // Save the updated entity
        } else {
            throw new IllegalArgumentException("Bank details not found for id: " + BdId);
        }
    }
	
	///////////////Delete BankDetails By Id////////////////////////////////////////
	public void deleteBankdetailsById(long BdId) {
		bankDetailsRepository.deleteById(BdId);
		
	}
} 
