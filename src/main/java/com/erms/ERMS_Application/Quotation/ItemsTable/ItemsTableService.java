package com.erms.ERMS_Application.Quotation.ItemsTable;

import com.erms.ERMS_Application.Quotation.Form.FormEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemsTableService {
	
	@Autowired
	private ItemsTableRepository itemsTableRepo;
	
	////////////////List Of Items Saving /////////////////////////////////////////
	public List<ItemsTableEntity> saveAllItems(List<ItemsTableEntity> itemsTable) {
        return itemsTableRepo.saveAll(itemsTable); 
        }

	//////////////////////////get items by FormEntity ///////////////////////////
	public List<ItemsTableEntity> findItemsByForm(FormEntity formEntity) {
	    // Calls the repository method to fetch all items by FormEntity
	    return itemsTableRepo.findAllByFormEntity(formEntity);
	}
	
	//////////////////////Get All ItemsTable/////////////////////////////////
	public List<ItemsTableEntity> findAllItemsTable() {
		List<ItemsTableEntity> getAllItemstable=itemsTableRepo.findAll();
		return getAllItemstable;
	}	
	
	////////////////////////partial update////////////////////////////////////
	public ItemsTableEntity updateFormDetails (long itId, ItemsTableEntity itemsTable) {
		ItemsTableEntity updateItems = itemsTableRepo.findById(itId).orElseThrow();
	
	Optional.ofNullable(itemsTable.getItems()).ifPresent(updateItems::setItems);
	Optional.ofNullable(itemsTable.getHsn()).ifPresent(updateItems::setHsn);
	Optional.ofNullable(itemsTable.getQty()).ifPresent(updateItems::setQty);
	Optional.ofNullable(itemsTable.getPriceItem()).ifPresent(updateItems::setPriceItem);
	Optional.ofNullable(itemsTable.getDiscount()).ifPresent(updateItems::setDiscount);
	Optional.ofNullable(itemsTable.getTax()).ifPresent(updateItems::setTax);
	Optional.ofNullable(itemsTable.getAmount()).ifPresent(updateItems::setAmount);
	return itemsTableRepo.save(updateItems);
	
	}
	
	/////////////////////////////Delete Items in Table/////////////////////////////////////
	public void deleteByItemsId(long itId) {
		itemsTableRepo.deleteById(itId);
		
   
	}

}
