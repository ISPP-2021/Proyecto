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

import com.stalion73.model.Supplier;
import com.stalion73.model.User;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class SupplierServiceTest {

	@Autowired
	private SupplierService supplierService;

//	@Autowired
//	private UserService userService;

	private int count;

	@BeforeEach
	void setUp(){
		count = supplierService.findAll().size();
	}

	@Test
	void findAllSuppliersTest() {
		List<Supplier> suppliers = (List<Supplier>) this.supplierService.findAll();
		Assertions.assertTrue(!suppliers.isEmpty() && suppliers.size() == count);
	}

	@Test
	void findSupplierByIdTest() {
		Optional<Supplier> supplier = this.supplierService.findById(1);
		Assertions.assertTrue(supplier.get().getName().equals("Augusto")
				&& supplier.get().getLastname().equals("Garcia")
				&& supplier.get().getDni().equals("00000000A") && supplier.get().getEmail().equals("cosas@gmail.com")
				&& supplier.get().getUser().getUsername().equals("aug"));
	}

	@Test
	void saveSupplier() {
		User user = new User();
		user.setUsername("rafita");
		Supplier supplier = new Supplier();
		supplier.setId(3);
		supplier.setName("Pablo");
		supplier.setLastname("Calvo");
		supplier.setDni("12345678G");
		supplier.setEmail("pablocalvo@gmail.com");
		supplier.setUser(user);
		this.supplierService.save(supplier);
		Optional<Supplier> suppliers = this.supplierService.findById(3);
		Assertions.assertTrue(suppliers.get().getName().equals("Pablo")
				&& suppliers.get().getLastname().equals("Calvo") 
				&& suppliers.get().getDni().equals("12345678G")
				&& suppliers.get().getEmail().equals("pablocalvo@gmail.com"));
	}
	
	@Test
	void deleteSupplierTest() {
		Supplier supplier = this.supplierService.findById(1).get();
		this.supplierService.delete(supplier);
		List<Supplier> suppliers = (List<Supplier>) this.supplierService.findAll();
		Assertions.assertTrue(suppliers.size() == count - 1);

	}

	@Test
	void deleteSupplierByIdTest() {
		Supplier supplier = this.supplierService.findById(2).get();
		this.supplierService.deleteById(supplier.getId());
		List<Supplier> suppliers = (List<Supplier>) this.supplierService.findAll();
		Assertions.assertTrue(suppliers.size() == count - 1);

	}
}