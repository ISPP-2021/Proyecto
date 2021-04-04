package com.stalion73.web;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.stalion73.service.ConsumerService;
import com.stalion73.model.Consumer;
import com.stalion73.model.User;
import com.stalion73.model.modelAssembler.ConsumerModelAssembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mediatype.problem.Problem;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/consumers")
public class ConsumerController{

	@Autowired
	private final ConsumerService consumerService;

	@Autowired
	private final ConsumerModelAssembler assembler;

	private final static HttpHeaders headers = new HttpHeaders();


    public  static void setup(){
        // headers.setAccessControlAllowOrigin("*");
		// List<HttpMethod> methods = new ArrayList<>();
		//methods.add(HttpMethod.POST);
		//headers.setAccessControlAllowMethods(methods);
   	}
    

    public ConsumerController(ConsumerService consumerService, ConsumerModelAssembler assembler){
		this.consumerService = consumerService;
		this.assembler = assembler;
    }

	@GetMapping
	public ResponseEntity<?> all() {
		ConsumerController.setup();
		List<EntityModel<Consumer>>  consumers = this.consumerService
						.findAll().stream()
						.map(assembler::toModel)
						.collect(Collectors.toList());
		if(consumers.isEmpty()){
			return ResponseEntity
					.status(HttpStatus.NO_CONTENT)
					.headers(headers)
					.body(consumers);
		}else{
			return ResponseEntity
			.status(HttpStatus.OK)
			.headers(headers)
			.body(consumers);
		}	
	}


	@GetMapping("/{id}")
    public ResponseEntity<?> one(@PathVariable Integer id) {
        ConsumerController.setup();
		Optional<Consumer> consumer = consumerService.findById((id));
        
		if(!consumer.isPresent()){
			return ResponseEntity
			.status(HttpStatus.NOT_FOUND)
			.header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
			.headers(headers)
			.body(Problem.create()
				.withTitle("Ineffected ID")
				.withDetail("The provided ID doesn't exist"));
		}else{
			return ResponseEntity
				.status(HttpStatus.OK) 
				.headers(headers) 
				.body(assembler.toModel(consumer.get()));
		}
	  }

	
	@PostMapping
	public ResponseEntity<?> create(@Valid @RequestBody Consumer consumer,
                                            BindingResult bindingResult, 
                                            UriComponentsBuilder ucBuilder) {
		
		ConsumerController.setup();
		BindingErrorsResponse errors = new BindingErrorsResponse();
        if (bindingResult.hasErrors() || (consumer == null)) {
            errors.addAllErrors(bindingResult);
            headers.add("errors", errors.toJSON());
            return ResponseEntity
				.status(HttpStatus.BAD_REQUEST)
				.headers(headers)
				.body(Problem.create()
					.withTitle("Validation error")
					.withDetail("The provided consumer was not successfuly validated"));
        }

        this.consumerService.save(consumer);
		EntityModel<Consumer> entityModel = assembler.toModel(consumer);
    	return ResponseEntity 
			.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) 
			.headers(headers)
			.body(entityModel);
    }
 
		

    
	@PutMapping("/{id}")
	ResponseEntity<?> update(@PathVariable("id") Integer id, @Valid @RequestBody Consumer newConsumer,
																BindingResult bindingResult, 
                                            					UriComponentsBuilder ucBuilder) {
		ConsumerController.setup();
		BindingErrorsResponse errors = new BindingErrorsResponse();
		if (bindingResult.hasErrors() || (newConsumer == null)) {
            errors.addAllErrors(bindingResult);
            headers.add("errors", errors.toJSON());
            return ResponseEntity
				.status(HttpStatus.BAD_REQUEST)
				.headers(headers)
				.body(Problem.create()
					.withTitle("Validation error")
					.withDetail("The provided consumer was not successfuly validated"));

        }

			this.consumerService.update(id, newConsumer);  
			
			newConsumer.setId(id);
			EntityModel<Consumer> entityModel = assembler.toModel(newConsumer);
	
			return ResponseEntity 
					.status(HttpStatus.PARTIAL_CONTENT)
					.headers(headers)
					.body(entityModel);
	}

  
	@DeleteMapping("/{id}")
	ResponseEntity<?> delete(@PathVariable Integer id) {
		ConsumerController.setup();
		Optional<Consumer> consumer = consumerService.findById(id);
		if(consumer.isPresent()){
			consumerService.deleteById(id);
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity
			.status(HttpStatus.NOT_FOUND)
			.header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
			.headers(headers)
			.body(Problem.create()
				.withTitle("Ineffected ID")
				.withDetail("The provided ID doesn't exist"));
	}
  

	// -------------AUGUSTO'S BAD&IMPROVABLE CODE------------

	@GetMapping("/profile")
	public ResponseEntity<?> profile(SecurityContextHolder contextHolder){

		String username = (String) contextHolder.getContext().getAuthentication().getPrincipal();
		Consumer consumer = this.consumerService.findConsumerByUsername(username);
		if(consumer!=null){
		
		return ResponseEntity
				.status(HttpStatus.OK) 
				.headers(headers) 
				.body(consumer);
		}else {
			return ResponseEntity
				.status(HttpStatus.NOT_FOUND)
				.headers(headers).body("Sorry");
		}
	}

	// ---------------AUGUSTO'S BAD&IMPROVABLE CODE---------------

}