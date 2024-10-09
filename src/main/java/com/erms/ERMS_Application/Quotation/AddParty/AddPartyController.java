package com.erms.ERMS_Application.Quotation.AddParty;


import jakarta.mail.MessagingException;
import com.erms.ERMS_Application.Quotation.Salesman.SaleEntity;
import com.erms.ERMS_Application.Quotation.Salesman.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/addparty")
public class AddPartyController {
	
	@Autowired
	private AddPartyService addPartyService;
	
	@Autowired
	private SaleRepository saleRepo;
	
	////////////////////Saving AddParty/////////////////////////////////
	

    @PostMapping("/save/{sId}")
    public ResponseEntity<?> saveAddParty(@PathVariable long sId,@RequestBody AddPartyEntity addParty) {
    	 // Fetch the AddPartyEntity by ID
    	SaleEntity sale = saleRepo.findById(sId).orElse(null);
    	
    	if (sale == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body("SaleEntity not found for ID: " + sId);
        }
    	// Associate the AddPartyEntity with the FormEntity
    	addParty.setSaleEntity(sale);
    	try {
            AddPartyEntity savedParty = addPartyService.addParty(addParty);
            return new ResponseEntity<>(savedParty, HttpStatus.CREATED);
        } catch (MessagingException ex) {
            return new ResponseEntity<>("Error sending email: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception ex) {
            return new ResponseEntity<>("Error saving party: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
	
	////////////////////Getparty By Id///////////////////////////////////
    @GetMapping("getparty/{adId}")
    public ResponseEntity<?> getAddedParty(@PathVariable long adId) {
        Optional<AddPartyEntity> party = addPartyService.getParty(adId);
        
        if (party.isPresent()) {
            return ResponseEntity.ok(party.get());  // Return 200 OK with the party details
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body("Party not found for ID: " + adId);  // Return 404 if not found
        }
    }
	
	/////////////////////GetAll Parties ///////////////////////////////////////////
	
	@GetMapping("/getAll")
    public ResponseEntity<?> getAllParties() {
        try {
            List<AddPartyEntity> allParties = addPartyService.getAllParties();
            return new ResponseEntity<>(allParties, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>("Error fetching all parties: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
	
	//////////////////////Partial Update////////////////////////////////////////////
	
	@PatchMapping("/update/{adId}")
    public ResponseEntity<?> updatepartially(@PathVariable long adId, @RequestBody AddPartyEntity updateAdd) {
        try {
            AddPartyEntity updatedParty = addPartyService.updateAddparty(adId, updateAdd);
            return new ResponseEntity<>(updatedParty, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>("Error updating party: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
	
	///////////////////////Delete AddParty//////////////////////////////////////
	

    @DeleteMapping("/{adId}")
    public ResponseEntity<?> deleteByAddPartyId(@PathVariable long adId) {
        try {
            addPartyService.deleteByPartyId(adId);
            return new ResponseEntity<>("Party deleted successfully", HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>("Error deleting party: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
