package com.erms.ERMS_Application.Quotation.AddList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/addlist")
public class AddListController {
	
	@Autowired
	private AddListService addListService;
	
	////////////////////Save Addlist///////////////////////////////
	@PostMapping("/save")
	public ResponseEntity<?> saveaddList(@RequestBody AddListEntity addList) {
        try {
            AddListEntity savedAddList = addListService.saveAddList(addList);
            return new ResponseEntity<>(savedAddList, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error saving add list: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
	
	///////////////////////Get AddList By Id////////////////////////////////
	@GetMapping("/getByAddList/{alId}")
	 public ResponseEntity<?> getAddedList(@PathVariable("alId") long alId) {
        try {
            Optional<AddListEntity> addList = addListService.getAddListById(alId);
            if (addList.isPresent()) {
                return new ResponseEntity<>(addList.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Add list not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Error fetching add list: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
	/////////////////////GetAll Parties ///////////////////////////////////////////
	
	@GetMapping("/getAll")
	 public ResponseEntity<?> getAllParties() {
        try {
            List<AddListEntity> allLists = addListService.getAllAddedLists();
            return new ResponseEntity<>(allLists, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error fetching all add lists: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
		
	//////////////////////Partial Update////////////////////////////////////////////
		
	@PatchMapping("/update/{alId}")
	public ResponseEntity<?> updateAddList(@PathVariable long alId, @RequestBody AddListEntity updateList) {
        try {
            AddListEntity updatedAddList = addListService.updateAddedLists(alId, updateList);
            return new ResponseEntity<>(updatedAddList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error updating add list: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
	
	///////////////////////Delete AddParty//////////////////////////////////////
	
	@DeleteMapping("/{alId}")
	 public ResponseEntity<?> deleteById(@PathVariable("alId") long alId) {
        try {
            addListService.deleteByAddListId(alId);
            return new ResponseEntity<>("Add list deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error deleting add list: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
