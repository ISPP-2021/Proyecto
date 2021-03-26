package com.stalion73.web;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;

import javax.validation.Valid;

import com.stalion73.service.BusinessService;
import com.stalion73.model.Business;
import com.stalion73.model.Supplier;
import com.stalion73.model.BusinessType;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/business")
public class BusinessController {
    
    @Autowired
    private final BusinessService businessService;

    private final static HttpHeaders headers = new HttpHeaders();


    public  static void setup(){
        headers.setAccessControlAllowOrigin("*");
    }

    public BusinessController(BusinessService businessService){
        this.businessService = businessService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Collection<Business>> all() {
        Collection<Business> businesses = this.businessService.findAll();
        if (businesses.isEmpty()) {
            return new ResponseEntity<Collection<Business>>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Collection<Business>>(businesses, headers, HttpStatus.OK);

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Business> one(@PathVariable("id") Integer id) {
        Business business = this.businessService.findById(id).get();
        if (business == null) {
            return new ResponseEntity<Business>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Business>(business, headers, HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Business> create(@Valid @RequestBody Business business,
                                            BindingResult bindingResult, 
                                            UriComponentsBuilder ucBuilder) {

        BindingErrorsResponse errors = new BindingErrorsResponse();
        HttpHeaders headers = new HttpHeaders();
        if (bindingResult.hasErrors() || (business == null)) {
            errors.addAllErrors(bindingResult);
            headers.add("errors", errors.toJSON());
            return new ResponseEntity<Business>(headers, HttpStatus.BAD_REQUEST);
        }
        this.businessService.save(business);
        headers.setLocation(ucBuilder.path("/business").buildAndExpand(business.getId()).toUri());
        return new ResponseEntity<Business>(business, headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = "application/json")
	public ResponseEntity<Business> update(@PathVariable("id") Integer id, 
                                            @RequestBody @Valid Business newBusiness, 
                                            BindingResult bindingResult){
		BindingErrorsResponse errors = new BindingErrorsResponse();
		HttpHeaders headers = new HttpHeaders();
		if(bindingResult.hasErrors() || (newBusiness == null)){
			errors.addAllErrors(bindingResult);
			headers.add("errors", errors.toJSON());
			return new ResponseEntity<Business>(headers, HttpStatus.BAD_REQUEST);
		}
		if(this.businessService.findById(id).get() == null){
			return new ResponseEntity<Business>(HttpStatus.NOT_FOUND);
		}
        // business(name, address, businessType, automatedAccept, Supplier, Servises)
        Business updatedBusiness = this.businessService.findById(id)
                    .map(business -> {
                            String name = newBusiness.getName()== null ? business.getName() : newBusiness.getName();
                            business.setName(name);
                            String address = newBusiness.getAddress() == null ? business.getAddress() : newBusiness.getAddress();
                            business.setAddress(address);
                            BusinessType type = newBusiness.getBusinessType() == null ? business.getBusinessType() : newBusiness.getBusinessType();
                            business.setBusinessType(type);
                            Boolean automatedAccept = newBusiness.getAutomatedAccept() == null ? business.getAutomatedAccept() : newBusiness.getAutomatedAccept();
                            business.setAutomatedAccept(automatedAccept);
                            Supplier supplier = newBusiness.getSupplier() == null ? business.getSupplier() : newBusiness.getSupplier();
                            business.setSupplier(supplier);
                            business.setAutomatedAccept(automatedAccept);
                            this.businessService.save(business);
                            return business;
                        }
                    ) 
                    .orElseGet(() -> {
                        newBusiness.setId(id);
                        this.businessService.save(newBusiness);
                        return newBusiness;
                    });

		return new ResponseEntity<Business>(updatedBusiness, headers, HttpStatus.NO_CONTENT);
	}

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<Void> delete(@PathVariable("id") Integer id){
		Business business = this.businessService.findById(id).get();
		if(business == null){
			return new ResponseEntity<Void>(headers, HttpStatus.NOT_FOUND);
		}
        business.setSupplier(null);
        business.setServices(null);
		this.businessService.delete(business);
		return new ResponseEntity<Void>(headers, HttpStatus.NO_CONTENT);
	}

}
