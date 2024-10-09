package com.erms.ERMS_Application.Quotation.BankDetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/bankdetails")
public class BankDetailsController {
	
	@Autowired
	private BankDetailsService bankDetailsService;
	////////////////////////Saving Bank Details///////////////////////////////////
	
	@PostMapping("/save")
	public ResponseEntity<?> saveBankDetails(@RequestBody BankDetailsEntity bankDetails) {
		try {
			BankDetailsEntity savedDetails = bankDetailsService.saveBankDetails(bankDetails);
			return new ResponseEntity<>(savedDetails, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>("Error saving bank details: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	///////////////////////Get BankDetails By id////////////////////////////////
	@GetMapping("/getdetails/{bdId}")
	public ResponseEntity<?> findBankDetails(@PathVariable("bdId") long bdId) {
		Optional<BankDetailsEntity> bankDetailsOpt = bankDetailsService.getBankDetails(bdId);
		if (bankDetailsOpt.isPresent()) {
			return new ResponseEntity<>(bankDetailsOpt.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Bank details not found for id: " + bdId, HttpStatus.NOT_FOUND);
		}
	}
	
	////////////////////////GetAll BankDetails ////////////////////////////////////////
	@GetMapping("/getAll")
	public ResponseEntity<?> getAllBankdetails() {
		try {
			List<BankDetailsEntity> getAllDetails = bankDetailsService.getAllBankdetails();
			return new ResponseEntity<>(getAllDetails, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Error retrieving all bank details: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	////////////////////Partial Update //////////////////////////////
	@PatchMapping("/update/{bdId}")
	public ResponseEntity<?> updateBankDetails(@PathVariable long bdId, @RequestBody BankDetailsEntity updateBank) {
		try {
			Optional<BankDetailsEntity> existingDetailsOpt = bankDetailsService.getBankDetails(bdId);
			if (existingDetailsOpt.isPresent()) {
				BankDetailsEntity updatedDetails = bankDetailsService.updateBankDetails(bdId, updateBank);
				return new ResponseEntity<>(updatedDetails, HttpStatus.OK);
			} else {
				return new ResponseEntity<>("Bank details not found for id: " + bdId, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>("Error updating bank details: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	///////////////////// Delete Form By Id ///////////////////////////
	@DeleteMapping("/{bdId}")
	public ResponseEntity<?> deleteBankDetailsById(@PathVariable("bdId") long bdId) {
		try {
			Optional<BankDetailsEntity> bankDetailsOpt = bankDetailsService.getBankDetails(bdId);
			if (bankDetailsOpt.isPresent()) {
				bankDetailsService.deleteBankdetailsById(bdId);
				return new ResponseEntity<>("Bank details deleted successfully", HttpStatus.OK);
			} else {
				return new ResponseEntity<>("Bank details not found for id: " + bdId, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>("Error deleting bank details: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
