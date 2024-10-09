package com.erms.ERMS_Application.Quotation.AddParty;

import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AddPartyService {
	
	@Autowired
	private AddPartyRepository addPartyRepo;
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////AddParty Save/////////////////////////////////////////////////////////////
	public AddPartyEntity addParty(AddPartyEntity addparty) throws MessagingException {
		try{
			AddPartyEntity newParty =addPartyRepo.save(addparty);
			// Check if the save operation failed (newParty is null or not populated correctly)
            if (newParty == null || newParty.getAdId() == 0) {
                throw new MessagingException("Failed to load the party data after saving.");
            }
		return newParty;
		}catch (DataAccessException data) {
        // Catch any data access exceptions that occur during the save operation
			throw new MessagingException("Failed to save party entity due to database error: " + data.getMessage(), data);
		} catch (Exception ex) {
        // Catch any other exceptions that may occur
			throw new MessagingException("An unexpected error occurred while saving the party entity: " + ex.getMessage(), ex);
		}
	}
	
	public Optional<AddPartyEntity> getParty(long AdId){
		Optional<AddPartyEntity> add=addPartyRepo.findById(AdId);
		return add;
	}
	
	
	//////////////////GetAll AddParties////////////////////////
	public List<AddPartyEntity> getAllParties(){
		List<AddPartyEntity> addAll=addPartyRepo.findAll();
		return addAll;
	}
	 
	///////////////////Partial Update////////////////////////////////
	public AddPartyEntity updateAddparty (long AdId, AddPartyEntity addparty) {
		AddPartyEntity updatepartial = addPartyRepo.findById(AdId).orElseThrow();
		
		Optional.ofNullable(addparty.getCustomerName()).ifPresent(updatepartial::setCustomerName);
		Optional.ofNullable(addparty.getMobileNumber()).ifPresent(updatepartial::setMobileNumber);
		Optional.ofNullable(addparty.getBillingAddress()).ifPresent(updatepartial::setBillingAddress);
		Optional.ofNullable(addparty.getState()).ifPresent(updatepartial::setState);
		Optional.ofNullable(addparty.getPincode()).ifPresent(updatepartial::setPincode);
		Optional.ofNullable(addparty.getCity()).ifPresent(updatepartial::setCity);
		Optional.ofNullable(addparty.getShippingAddress()).ifPresent(updatepartial::setShippingAddress);
		Optional.ofNullable(addparty.getShippingCity()).ifPresent(updatepartial::setShippingCity);
		Optional.ofNullable(addparty.getShippingState()).ifPresent(updatepartial::setShippingState);
		Optional.ofNullable(addparty.getPincode()).ifPresent(updatepartial::setShippingPincode);
		Optional.ofNullable(addparty.getGstIn()).ifPresent(updatepartial::setGstIn);
		return addPartyRepo.save(updatepartial);
		
	}

	///////////////////////Delete AddParty//////////////////////////////////////
	public void deleteByPartyId(long AdId) {
		addPartyRepo.deleteById(AdId);
	}
	

}
