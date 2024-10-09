package com.erms.ERMS_Application.Quotation.Salesman;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;



@RestController
@RequestMapping("api/sale")

public class SaleController {

	@Autowired
	SaleService saleService;
	
	///////////////////////////Saving Sales //////////////////////////////////


    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody SaleEntity sales) {
        try {
        	SaleEntity saved= saleService.SaveSale(sales);
            return new ResponseEntity<>(saved, HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<>("Error saving sale: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
	
	////////////////////Getparty By Id///////////////////////////////////
    @GetMapping("getsale/{sId}")
    public ResponseEntity<?> getAddedParty(@PathVariable long sId) {
        Optional<SaleEntity> save = saleService.findById(sId);
        
        if (save.isPresent()) {
            return ResponseEntity.ok(save.get());  // Return 200 OK with the party details
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body("Sale not found for ID: " + sId);  // Return 404 if not found
        }
    }
	
	/////////////////////GetAll Parties ///////////////////////////////////////////
	
	@GetMapping("/getAll")
    public ResponseEntity<?> findAll() {
        try {
            List<SaleEntity> all = saleService.findAll();
            return new ResponseEntity<>(all, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>("Error fetching all parties: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
	
	//////////////////////Partial Update////////////////////////////////////////////
	
	@PatchMapping("/update/{sId}")
    public ResponseEntity<?> updatepartially(@PathVariable long sId, @RequestBody SaleEntity update) {
        try {
            SaleEntity updated = saleService.updatesale(sId, update);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>("Error updating sale: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
	
	///////////////////////Delete AddParty//////////////////////////////////////
	

    @DeleteMapping("/{sId}")
    public ResponseEntity<?> deleteById(@PathVariable long sId) {
        try {
        	saleService.deleteBySaleId(sId);
            return new ResponseEntity<>("Sale deleted successfully", HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>("Error deleting party: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


	
	
}
