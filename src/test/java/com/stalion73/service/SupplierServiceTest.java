package com.stalion73.service;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import com.stalion73.model.Supplier;
//import com.stalion73.model.User;
import com.stalion73.service.SupplierService;
//import com.stalion73.service.UserService;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class SupplierServiceTest {

	@Autowired
	private SupplierService supplierService;

//	@Autowired
//	private UserService userService;

	@Test
	void findAllSuppliersTest() {
		List<Supplier> suppliers = (List<Supplier>) this.supplierService.findAll();
		Assertions.assertTrue(!suppliers.isEmpty() && suppliers.size() == 2);
	}

	@Test
	void findSupplierByIdTest() {
		Optional<Supplier> supplier = this.supplierService.findById(1);
		Assertions.assertTrue(supplier.get().getName().equals("supplier_name_1")
				&& supplier.get().getLastname().equals("supplier_lastname_1")
				&& supplier.get().getDni().equals("1111111A") && supplier.get().getEmail().equals("random@gmail.com")
				&& supplier.get().getUser().getUsername().equals("josito"));
	}

//	@Test
//	void saveSupplier() {
//		Supplier supplier = new Supplier();
//		supplier.setId(10);
//		supplier.setName("Pablo");
//		supplier.setLastname("Calvo");
//		supplier.setDni("12345678G");
//		supplier.setEmail("pablocalvo@gmail.com");
//		//falta el username
//		this.supplierService.save(supplier);
//		Optional<Supplier> suppliers = this.supplierService.findById(10);
//		Assertions.assertTrue(!suppliers.isEmpty() && suppliers.get().getName().equals("Pablo")
//				&& suppliers.get().getLastname().equals("Calvo") 
//				&& suppliers.get().getDni().equals("12345678G")
//				&& suppliers.get().getEmail().equals("pablocalvo@gmail.com"));
//	}
//	@Test
//	void deleteSupplierTest() {
//		Supplier supplier = this.supplierService.findById(1).get();
//		this.supplierService.delete(supplier);
//		List<Supplier> suppliers = (List<Supplier>) this.supplierService.findAll();
//		Assertions.assertTrue(suppliers.size() == 1);
//
//	}
}