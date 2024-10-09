package com.erms.ERMS_Application.Quotation.AddList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AddListService {
	
	@Autowired
	private AddListRepository addListRepo;
	
	
	////////////////////Save AddList//////////////////////////////////
	public AddListEntity saveAddList(AddListEntity AddList) {
		AddListEntity addedList=addListRepo.save(AddList);
		return addedList;
		
	}
	
	/////////////////////Get By AddList Id//////////////////////////////
	public Optional<AddListEntity> getAddListById (long AlId) {
		Optional<AddListEntity> getAddedList =addListRepo.findById(AlId);
		return getAddedList;
		
	}
	
	//////////////////////GetAll AddList//////////////////////////////////
	public List<AddListEntity> getAllAddedLists(){
		List<AddListEntity> GetAllAddLists=addListRepo.findAll();
		return GetAllAddLists;
	}
	
	///////////////////Partial update////////////////////////////////////
	public AddListEntity updateAddedLists(long AlId,AddListEntity AddLists) {
		AddListEntity updateAddList=addListRepo.findById(AlId).orElseThrow();
		
		Optional.ofNullable(AddLists.getCategory()).ifPresent(updateAddList::setCategory);
		Optional.ofNullable(AddLists.getName()).ifPresent(updateAddList::setName);
		Optional.ofNullable(AddLists.getPrice()).ifPresent(updateAddList::setPrice);
		Optional.ofNullable(AddLists.getGstIn()).ifPresent(updateAddList::setGstIn);
		Optional.ofNullable(AddLists.getStock()).ifPresent(updateAddList::setStock);
		return addListRepo.save(updateAddList);	
	}
	
	//////////////////////////Delete By AddListId//////////////////////////////
	public void deleteByAddListId(long AlId) {
		addListRepo.deleteById(AlId);
		
	}
	
}
