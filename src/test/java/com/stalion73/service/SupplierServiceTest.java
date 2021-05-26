package com.stalion73.service;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import com.stalion73.model.Business;
import com.stalion73.model.Supplier;
import com.stalion73.model.User;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class SupplierServiceTest {

	@Autowired
	private SupplierService supplierService;

	@Autowired
	private BusinessService businessService;

	private int count;

	@BeforeEach
	void setUp() {
		count = supplierService.findAll().size();
	}

	@Test
	void findAllSuppliersTest() {
		List<Supplier> suppliers = (List<Supplier>) this.supplierService.findAll();
		Assertions.assertTrue(!suppliers.isEmpty() && suppliers.size() == count);
	}

	@Test
	void findSupplierByIdTest() {
		Optional<Supplier> supplier = this.supplierService.findByIndex(1);
		Assertions.assertTrue(supplier.get().getName().equals("Augusto")
				&& supplier.get().getLastname().equals("García") && supplier.get().getDni().equals("00000000A")
				&& supplier.get().getEmail().equals("augusto@gmail.com")
				&& supplier.get().getUser().getUsername().equals("aug"));
	}

	@Test
	void findSupplierByUsernameTest() {
		Optional<Supplier> supplier = this.supplierService.findSupplierByUsername("rodri");
		Assertions.assertTrue(supplier.get().getName().equals("Rodrigo")
				&& supplier.get().getLastname().equals("García") && supplier.get().getDni().equals("45600000A")
				&& supplier.get().getEmail().equals("rodrigo@gmail.com"));
	}

//	@Test
//	void findBookingOnSupplierTest() {
//
//	}

	@Test
	void saveSupplierTest() {
		User user = new User();
		user.setUsername("pablito");
		Supplier supplier = new Supplier();
		supplier.setIndex(4);
		supplier.setName("Pablo");
		supplier.setLastname("Calvo");
		supplier.setDni("12345678G");
		supplier.setEmail("pablocalvo@gmail.com");
		supplier.setUser(user);
		this.supplierService.save(supplier);
		Optional<Supplier> suppliers = this.supplierService.findByIndex(4);
		Assertions.assertTrue(suppliers.get().getName().equals("Pablo") && suppliers.get().getLastname().equals("Calvo")
				&& suppliers.get().getDni().equals("12345678G")
				&& suppliers.get().getEmail().equals("pablocalvo@gmail.com"));
	}

	@Test
	void deleteSupplierByIdTest() {
		Supplier supplier = this.supplierService.findByIndex(1).get();
		this.supplierService.deleteByIndex(supplier.getIndex());
		List<Supplier> suppliers = (List<Supplier>) this.supplierService.findAll();
		Assertions.assertTrue(suppliers.size() == count - 1);
	}

	@Test
	void deleteSupplierTest() {
		Supplier supplier = this.supplierService.findByIndex(2).get();
		this.supplierService.delete(supplier);
		List<Supplier> suppliers = (List<Supplier>) this.supplierService.findAll();
		Assertions.assertTrue(suppliers.size() == count - 1);
	}

	@Test
	void deleteBusinessBySupplierIdTest() {
		Supplier supplier = this.supplierService.findByIndex(1).get();
		this.supplierService.deleteBusinessBySupplierIndex(supplier.getIndex());
		List<Business> business = (List<Business>) this.businessService.findAll();
		Assertions.assertTrue(business.size() == 6);
	}

}