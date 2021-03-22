package com.stalion73.web;

import java.util.List;
import java.util.Collection;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.stalion73.service.SupplierService;
import com.stalion73.service.UserService;
import com.stalion73.model.Supplier;
import com.stalion73.model.User;


import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/suppliers")
public class SupplierController {

    @Autowired
    private final SupplierService supplierService;

    @Autowired
    private final UserService userService;

    public SupplierController(SupplierService supplierService, UserService userService){
        this.supplierService = supplierService;
        this.userService = userService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Collection<Supplier>> all() {
        Collection<Supplier> suppliers = this.supplierService.findAll();
        if (suppliers.isEmpty()) {
            return new ResponseEntity<Collection<Supplier>>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Collection<Supplier>>(suppliers, HttpStatus.OK);

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Supplier> one(@PathVariable("id") Integer id) {
        Supplier supplier = this.supplierService.findById(id).get();
        if (supplier == null) {
            return new ResponseEntity<Supplier>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Supplier>(supplier, HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Supplier> create(@Valid @RequestBody Supplier supplier,
                                            BindingResult bindingResult, 
                                            UriComponentsBuilder ucBuilder) {

        BindingErrorsResponse errors = new BindingErrorsResponse();
        HttpHeaders headers = new HttpHeaders();
        if (bindingResult.hasErrors() || (supplier == null)) {
            errors.addAllErrors(bindingResult);
            headers.add("errors", errors.toJSON());
            return new ResponseEntity<Supplier>(headers, HttpStatus.BAD_REQUEST);
        }
        this.supplierService.save(supplier);
        headers.setLocation(ucBuilder.path("/suppliers").buildAndExpand(supplier.getId()).toUri());
        return new ResponseEntity<Supplier>(supplier, headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = "application/json")
	public ResponseEntity<Supplier> update(@PathVariable("id") Integer id, 
                                            @RequestBody @Valid Supplier newSupplier, 
                                            BindingResult bindingResult){
		BindingErrorsResponse errors = new BindingErrorsResponse();
		HttpHeaders headers = new HttpHeaders();
		if(bindingResult.hasErrors() || (newSupplier == null)){
			errors.addAllErrors(bindingResult);
			headers.add("errors", errors.toJSON());
			return new ResponseEntity<Supplier>(headers, HttpStatus.BAD_REQUEST);
		}
		if(this.supplierService.findById(id).get() == null){
			return new ResponseEntity<Supplier>(HttpStatus.NOT_FOUND);
		}
        // suppliers (id, name, lastName, dni, email, username)
        // we need to be able to find users by username to update that attribute
        // User user = this.userService.findByName(username).get();
        Supplier updatedSupplier = this.supplierService.findById(id)
                    .map(supplier -> {
                            String name = newSupplier.getName() == null ? supplier.getName() : newSupplier.getName();
                            supplier.setName(name);
                            String lastName = newSupplier.getLastname() == null ? supplier.getLastname() : newSupplier.getLastname();
                            supplier.setLastname(lastName);
                            String dni = newSupplier.getDni() == null ? supplier.getDni() : newSupplier.getDni();
                            supplier.setDni(dni);
                            String email = newSupplier.getEmail() == null ? supplier.getEmail() : newSupplier.getEmail();
                            supplier.setEmail(email);
                            this.supplierService.save(supplier);
                            return supplier;
                        }
                    ) 
                    .orElseGet(() -> {
                        newSupplier.setId(id);
                        this.supplierService.save(newSupplier);
                        return newSupplier;
                    });

		return new ResponseEntity<Supplier>(updatedSupplier, HttpStatus.NO_CONTENT);
	}

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<Void> delete(@PathVariable("id") Integer id){
		Supplier supplier = this.supplierService.findById(id).get();
		if(supplier == null){
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
        supplier.setUser(null);
		this.supplierService.delete(supplier);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
    
}
