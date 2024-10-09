package com.erms.ERMS_Application.Quotation.Form;


import com.erms.ERMS_Application.Quotation.AddParty.AddPartyEntity;
import com.erms.ERMS_Application.Quotation.AddParty.AddPartyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("api/form")
public class FormController {

    @Autowired
    private FormService formService;
    
    @Autowired
    private AddPartyRepository addPartyRepo;

    /////////////////////// Form Saving ///////////////////////////////    
    @PostMapping("/save/{addPartyId}/{sId}")
    public ResponseEntity<?> createForm(@PathVariable long addPartyId,
                                        @PathVariable long sId,
                                        @RequestBody FormEntity form) {
        AddPartyEntity addParty = addPartyRepo.findById(addPartyId).orElse(null);

        if (addParty == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body("AddPartyEntity not found for ID: " + addPartyId);
        }

        // Create form with dynamic quotation number
        FormEntity createdForm = formService.createForm(addParty, form, sId);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdForm);
    }

    

    ///////////////////// Get By Form Id ///////////////////////////////
    @GetMapping("/getById/{fId}")
    public ResponseEntity<?> getById(@PathVariable("fId") long fId) {
        try {
            Optional<FormEntity> formdetails = formService.FindByFormId(fId);
            if (formdetails.isPresent()) {
                return new ResponseEntity<>(formdetails.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Form not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Error fetching form: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    ///////////////////// Get All Forms ///////////////////////////////
    @GetMapping("/getAllForms")
    public ResponseEntity<?> getAllForms() {
        try {
            List<FormEntity> allForms = formService.getAllForms();
            return new ResponseEntity<>(allForms, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error fetching all forms: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //////////////////// Partial Update //////////////////////////////
    @PatchMapping("/update/{fId}")
    public ResponseEntity<?> updateformDetail(@PathVariable long fId, @RequestBody FormEntity updateform) {
        try {
            FormEntity updatedForm = formService.updateFormDetails(fId, updateform);
            return new ResponseEntity<>(updatedForm, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error updating form: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    ///////////////////// Delete Form By Id ///////////////////////////
    @DeleteMapping("/{fId}")
    public ResponseEntity<?> deleteFormById(@PathVariable("fId") long fId) {
        try {
        	Optional<FormEntity> form = formService.FindByFormId(fId);
            if (form.isPresent()) {
            	formService.deleteByFormId(fId);
                return new ResponseEntity<>("Form deleted successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Form not found for ID: " + fId, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Error deleting form: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
