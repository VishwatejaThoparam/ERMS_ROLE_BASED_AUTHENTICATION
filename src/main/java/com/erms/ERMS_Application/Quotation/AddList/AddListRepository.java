package com.erms.ERMS_Application.Quotation.AddList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddListRepository extends JpaRepository<AddListEntity,Long>{

}
