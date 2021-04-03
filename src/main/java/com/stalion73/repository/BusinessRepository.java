package com.stalion73.repository;

import java.util.Collection;

import com.stalion73.model.Business;
import com.stalion73.model.BusinessType;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface BusinessRepository extends CrudRepository<Business, Integer> {
	Collection<Business> findAll();

	@Query("SELECT DISTINCT business FROM Business business WHERE business.name LIKE :name")
	Collection<Business> findBusinessByName(@Param("name") String name);

	@Query("SELECT DISTINCT business FROM Business business WHERE business.address LIKE :address")
	Collection<Business> findBusinessByAddress(@Param("address") String address);

	@Query("SELECT DISTINCT business FROM Business business WHERE business.businessType LIKE :businessType")
	Collection<Business> findBusinessByType(@Param("businessType") BusinessType businessType);

	@Query("SELECT b FROM Business b WHERE b.supplier.id =  :supplierId")
	Business findBusinessBySupplierId(@Param("supplierId")Integer supplierId);

	@Query("SELECT b FROM Option b WHERE b.options.id =  :optionId")
	Business findBusinessByOptionId(@Param("optionId")Integer optionId);

}
