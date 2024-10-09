package com.erms.ERMS_Application.Quotation.ItemsTable;



import com.erms.ERMS_Application.Quotation.Form.FormEntity;
import com.erms.ERMS_Application.Quotation.Form.FormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;




@RestController 
@RequestMapping("api/itemstable")
public class ItemsTableController {
	
	@Autowired
	private ItemsTableService itemsTableService; 
	
	@Autowired
	private FormService formService;
	
	////////////////////Adding List of items///////////////////////////////

	@PostMapping("/saveitems/{fId}")
	public ResponseEntity<?> saveItems(@PathVariable long fId, @RequestBody List<ItemsTableEntity> addItems) {
	    // Find the FormEntity using the provided FId
	    Optional<FormEntity> formOptional = formService.FindByFormId(fId);

	    if (!formOptional.isPresent()) { // Check if the FormEntity exists
	        return new ResponseEntity<>("FormEntity not found", HttpStatus.NOT_FOUND);
	    }	    
	    FormEntity addForm = formOptional.get();
	    // Set the FormEntity reference for each item in the list
	    for (ItemsTableEntity addItem : addItems) {
	        addItem.setFormEntity(addForm);
	    }	    
	    // Save all items at once
	    List<ItemsTableEntity> savedItems = itemsTableService.saveAllItems(addItems);
	    return new ResponseEntity<>(savedItems, HttpStatus.CREATED);
	}

	////////////////////Get List of Items by AdId ///////////////////////////
	@GetMapping("/getItemsByFormId/{fId}")
	public ResponseEntity<?> getItemsByFormId(@PathVariable long FId) {
	    // Find the FormEntity using the provided FId
	    FormEntity formOptional = formService.FindByFormId(FId).orElse(null);

	    if (formOptional == null) {
	        return new ResponseEntity<>("FormEntity not found", HttpStatus.NOT_FOUND);
	    }

	    // Fetch all items associated with the FormEntity
	    List<ItemsTableEntity> items = itemsTableService.findItemsByForm(formOptional);
	    return new ResponseEntity<>(items, HttpStatus.OK);
	}


	///////////////////// Get All Forms ///////////////////////////////
	@GetMapping("/getAllForms")
	public ResponseEntity<?> getAllItems() {
		try {
			List<ItemsTableEntity> allItems = itemsTableService.findAllItemsTable();
			return new ResponseEntity<>(allItems, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Error fetching all Items: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//////////////////// Partial Update //////////////////////////////
	@PatchMapping("/update/{itId}")
	public ResponseEntity<?> updateItemsTable(@PathVariable long itId, @RequestBody ItemsTableEntity updateItem) {
		try {
			ItemsTableEntity updatedItems = itemsTableService.updateFormDetails(itId, updateItem);
			return new ResponseEntity<>(updatedItems, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Error updating Items: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	///////////////////// Delete Form By Id ///////////////////////////
	@DeleteMapping("/{itId}")
	public ResponseEntity<?> deleteItemById(@PathVariable("itId") long itId) {
		try {
			itemsTableService.deleteByItemsId(itId);
			return new ResponseEntity<>("ItemsTable deleted successfully", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Error deleting Items: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
