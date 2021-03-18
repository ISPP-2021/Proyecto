package com.stalion73.web;

import java.util.List;
import java.util.stream.Collectors;

import com.stalion73.service.ConsumerService;
import com.stalion73.model.Consumer;
import com.stalion73.model.modelAssembler.ConsumerModelAssembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/consumers")
public class ConsumerController{

	@Autowired
	private final ConsumerService consumerService;

	@Autowired
	private final ConsumerModelAssembler assembler;
    

    public ConsumerController(ConsumerService consumerService, ConsumerModelAssembler assembler){
		this.consumerService = consumerService;
		this.assembler = assembler;
    }

	
	@GetMapping
	public CollectionModel<EntityModel<Consumer>> all() {
		List<EntityModel<Consumer>>  consumers = this.consumerService
						.findAll().stream()
						.map(assembler::toModel)
						.collect(Collectors.toList());
			
		return new CollectionModel(consumers, linkTo(methodOn(ConsumerController.class).all()).withSelfRel());
	}

	@GetMapping("/{id}")
    public EntityModel<Consumer> one(@PathVariable Integer id) {
        Consumer consumer = consumerService.findById((id))
            				.orElseThrow(null);

        return assembler.toModel(consumer);
	  }

	
	@PostMapping
	public ResponseEntity<?> create(@RequestBody Consumer consumer) {
		consumerService.save(consumer);
		EntityModel<Consumer> entityModel = assembler.toModel(consumer);
 
		return ResponseEntity 
			.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) 
			.body(entityModel);  
	}
    
	@PutMapping("/{id}")
	ResponseEntity<?> update(@RequestBody Consumer newConsumer, @PathVariable Integer id) {
		Consumer updatedConsumer = this.consumerService.findById(id)
					.map(consumer -> {
							consumer.setName(newConsumer.getName());
                            consumer.setLastname(newConsumer.getLastname());
                            consumer.setDni(newConsumer.getDni());
                            consumer.setEmail(newConsumer.getEmail());
							this.consumerService.save(consumer);
							return consumer;
				 		}) 
				  .orElseGet(() -> {
					  newConsumer.setId(id);
					  this.consumerService.save(newConsumer);
					  return newConsumer;
				  });
  
		EntityModel<Consumer> entityModel = assembler.toModel(updatedConsumer);
  
		return ResponseEntity 
				  .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
				  .body(entityModel);
	}
  
	@DeleteMapping("/{id}")
	ResponseEntity<?> delete(@PathVariable Integer id) {
		consumerService.deleteById(id);
	
		return ResponseEntity.noContent().build();
	}
  


}