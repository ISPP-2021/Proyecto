package com.stalion73.repository;

import java.util.Collection;
import java.util.Optional;

import com.stalion73.model.Business;
import com.stalion73.model.BusinessType;

import org.springframework.data.jpa.repository.Modifying;
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

	@Query("SELECT DISTINCT b FROM Business b WHERE b.supplier.index =:supplierIndex")
	Collection<Business> findBusinessBySupplierIndex(@Param("supplierIndex")Integer supplierIndex);

	@Query("SELECT b FROM Business b WHERE b.option.index =:optionIndex")
    Business findBusinessByOptionIndex(@Param("optionIndex")Integer optionIndex);

	@Query("SELECT count(x) FROM Business x")
    Integer tableSize();

	@Query("SELECT b FROM Business b WHERE b.index =:index")
    Optional<Business> findByIndex(@Param("index")Integer index);

	@Modifying
	@Query("delete from Business b where b.index =:index")
	void deleteByIndex(@Param("index") Integer index);

}
