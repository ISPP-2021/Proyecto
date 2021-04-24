package com.stalion73.service;

import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
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
		Assertions.assertThat(suppliers.isEmpty()).isFalse();
		Assertions.assertThat(suppliers.size()).isEqualTo(count);
	}

	@Test
	void findSupplierByIdTest() {
		Optional<Supplier> supplier = this.supplierService.findById(1);
		Assertions.assertThat(supplier.get().getName()).isEqualTo("Augusto");
		Assertions.assertThat(supplier.get().getLastname()).isEqualTo("Garcia");
		Assertions.assertThat(supplier.get().getDni()).isEqualTo("00000000A");
		Assertions.assertThat(supplier.get().getEmail()).isEqualTo("cosas4@gmail.com");
		Assertions.assertThat(supplier.get().getUser().getUsername()).isEqualTo("aug");
	}

	@Test
	void findSupplierByUsernameTest() {
		Optional<Supplier> supplier = this.supplierService.findSupplierByUsername("rodri");
		Assertions.assertThat(supplier.get().getName()).isEqualTo("Rodrigo");
		Assertions.assertThat(supplier.get().getLastname()).isEqualTo("Garcia");
		Assertions.assertThat(supplier.get().getDni()).isEqualTo("45600000A");
		Assertions.assertThat(supplier.get().getEmail()).isEqualTo("cosas5@gmail.com");
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
		supplier.setId(4);
		supplier.setName("Pablo");
		supplier.setLastname("Calvo");
		supplier.setDni("12345678G");
		supplier.setEmail("pablocalvo@gmail.com");
		supplier.setUser(user);
		this.supplierService.save(supplier);
		Optional<Supplier> suppliers = this.supplierService.findById(4);
		Assertions.assertThat(suppliers.get().getName()).isEqualTo("Pablo");
		Assertions.assertThat(suppliers.get().getLastname()).isEqualTo("Calvo");
		Assertions.assertThat(suppliers.get().getDni()).isEqualTo("12345678G");
		Assertions.assertThat(suppliers.get().getEmail()).isEqualTo("pablocalvo@gmail.com");
	}

	@Test
	void deleteSupplierByIdTest() {
		Supplier supplier = this.supplierService.findById(1).get();
		this.supplierService.deleteById(supplier.getId());
		List<Supplier> suppliers = (List<Supplier>) this.supplierService.findAll();
		Assertions.assertThat(suppliers.size()).isEqualTo(count - 1);
	}

	@Test
	void deleteSupplierTest() {
		Supplier supplier = this.supplierService.findById(2).get();
		this.supplierService.delete(supplier);
		List<Supplier> suppliers = (List<Supplier>) this.supplierService.findAll();
		Assertions.assertThat(suppliers.size()).isEqualTo(count - 1);
	}

	@Test
	void deleteBusinessBySupplierIdTest() {
		Supplier supplier = this.supplierService.findById(2).get();
		this.supplierService.deleteBusinessBySupplierId(supplier.getId());
		List<Business> business = (List<Business>) this.businessService.findAll();
		Assertions.assertThat(business.size()).isEqualTo(2);
	}

}
