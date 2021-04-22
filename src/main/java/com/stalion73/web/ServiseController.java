package com.stalion73.web;

import com.stalion73.model.Servise;
import com.stalion73.model.Business;
import com.stalion73.model.Supplier;
import com.stalion73.service.ServiseService;
import com.stalion73.service.BusinessService;
import com.stalion73.service.SupplierService;

import java.util.Collection;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.mediatype.problem.Problem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.security.core.context.SecurityContextHolder;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/servises")
public class ServiseController {

	@Autowired
	private final ServiseService serviseService;

	@Autowired
	private final BusinessService businessService;

	@Autowired
	private final SupplierService supplierService;

	private final static HttpHeaders headers = new HttpHeaders();

	public ServiseController(ServiseService serviseService, BusinessService businessService,
				SupplierService supplierService) {
		this.serviseService = serviseService;
		this.businessService = businessService;
		this.supplierService = supplierService;
	}

	@RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Collection<Servise>> all() {
		Collection<Servise> servises = this.serviseService.findAll();
		if (servises.isEmpty()) {
			return new ResponseEntity<Collection<Servise>>(headers, HttpStatus.NOT_FOUND);
		}else{
			return new ResponseEntity<Collection<Servise>>(servises, headers, HttpStatus.OK);
		}

	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> one(@PathVariable("id") Integer id) {
		Optional<Servise> servise = this.serviseService.findById(id);
		if (!servise.isPresent()) {
			return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
                .headers(headers)
                .body(Problem.create()
                    .withTitle("Ineffected ID")
                    .withDetail("The provided ID doesn't exist"));
		}
		return new ResponseEntity<Servise>(servise.get(), headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<?> create(@PathVariable("id") Integer id,
									@Valid @RequestBody Servise servise, 
									BindingResult bindingResult,
									UriComponentsBuilder ucBuilder) {
		BindingErrorsResponse errors = new BindingErrorsResponse();
		String authority = SecurityContextHolder.getContext().getAuthentication().getAuthorities().iterator().next()
										.getAuthority();
		if(authority.equals("owner")){
			Supplier supplier = this.supplierService.findSupplierByUsername((String)SecurityContextHolder.getContext()
												.getAuthentication().getPrincipal()).get();
			if (bindingResult.hasErrors() || (servise == null)) {
				errors.addAllErrors(bindingResult);
				headers.add("errors", errors.toJSON());
				return ResponseEntity
					.status(HttpStatus.BAD_REQUEST)
					.headers(headers)
					.body(Problem.create()
						.withTitle("Validation error")
						.withDetail("The provided servise was not successfuly validated"));
			}else{
				Optional<Business> b = this.businessService.findById(id);
				if(b.isPresent()){
					Business business = b.get();
					if(business.getSupplier().getId().equals(supplier.getId())){
						servise.setBussiness(business);
						business.addServise(servise);
						this.businessService.save(business);
						this.serviseService.save(servise);
						headers.setLocation(ucBuilder.path("/servises/" + servise.getId()).buildAndExpand(servise.getId()).toUri());
						return new ResponseEntity<Servise>(servise, headers, HttpStatus.CREATED);
					}else{
						return ResponseEntity
						.status(HttpStatus.FORBIDDEN) 
						.header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE) 
						.headers(headers)
						.body(Problem.create()
							.withTitle("Not owned") 
							.withDetail("The request servise is not up to your provided credentials."));
					}
				}
				return ResponseEntity
                .status(HttpStatus.BAD_REQUEST) 
                .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE) 
                .headers(headers)
                .body(Problem.create()
                    .withTitle("non-existent") 
                    .withDetail("The request business not exist."));
			}
		}
		return ResponseEntity
                .status(HttpStatus.FORBIDDEN) 
                .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE) 
                .headers(headers)
                .body(Problem.create()
                    .withTitle("You shall not pass") 
                    .withDetail("The request wasn't expecting the provied credentials."));
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = "application/json")
	public ResponseEntity<?> update(@PathVariable("id") Integer id, 
												@RequestBody @Valid Servise newServise,
												BindingResult bindingResult) {
		BindingErrorsResponse errors = new BindingErrorsResponse();
		if (bindingResult.hasErrors() || (newServise == null)) {
			errors.addAllErrors(bindingResult);
            headers.add("errors", errors.toJSON());
            return ResponseEntity
				.status(HttpStatus.BAD_REQUEST)
				.headers(headers)
				.body(Problem.create()
					.withTitle("Validation error")
					.withDetail("The provided servise was not successfuly validated"));
		}else if(!this.serviseService.findById(id).isPresent()){
			return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .headers(headers)
                .body(Problem.create()
                    .withTitle("Ineffected ID")
                    .withDetail("The provided ID doesn't exist"));
		}else{
			this.serviseService.update(id, newServise);
			newServise.setId(id);	
			return ResponseEntity 
					.status(HttpStatus.PARTIAL_CONTENT)
					.headers(headers)
					.body(newServise);
		}

	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        Optional<Servise> servise = this.serviseService.findById(id);
		if(servise.isPresent()){
            this.serviseService.deleteById(id);
			return ResponseEntity.noContent().build();
		}else{
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
                    .headers(headers)
                    .body(Problem.create()
                        .withTitle("Ineffected ID")
                        .withDetail("The provided ID doesn't exist"));
        }
	}

}
