package com.erms.ERMS_Application.Quotation.ItemsTable;


import com.erms.ERMS_Application.Quotation.Form.FormEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemsTableRepository extends JpaRepository <ItemsTableEntity,Long> {

	 List<ItemsTableEntity> findAllByFormEntity(FormEntity formEntity);

}
