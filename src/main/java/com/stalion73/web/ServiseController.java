package com.stalion73.web;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.stalion73.model.Servise;
import com.stalion73.service.ServiseService;

@RestController
@RequestMapping("/servises")
public class ServiseController {
	@Autowired
	private final ServiseService serviseService;

	public ServiseController(ServiseService serviseService) {
		this.serviseService = serviseService;
	}

	@RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Collection<Servise>> all() {
		Collection<Servise> servises = this.serviseService.findAll();
		if (servises.isEmpty()) {
			return new ResponseEntity<Collection<Servise>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Collection<Servise>>(servises, HttpStatus.OK);

	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Servise> one(@PathVariable("id") Integer id) {
		Servise servise = this.serviseService.findById(id).get();
		if (servise == null) {
			return new ResponseEntity<Servise>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Servise>(servise, HttpStatus.OK);
	}

	@RequestMapping(value = "", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Servise> create(@Valid @RequestBody Servise servise, BindingResult bindingResult,
			UriComponentsBuilder ucBuilder) {

		BindingErrorsResponse errors = new BindingErrorsResponse();
		HttpHeaders headers = new HttpHeaders();
		if (bindingResult.hasErrors() || (servise == null)) {
			errors.addAllErrors(bindingResult);
			headers.add("errors", errors.toJSON());
			return new ResponseEntity<Servise>(headers, HttpStatus.BAD_REQUEST);
		}
		this.serviseService.save(servise);
		headers.setLocation(ucBuilder.path("/servises").buildAndExpand(servise.getId()).toUri());
		return new ResponseEntity<Servise>(servise, headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = "application/json")
	public ResponseEntity<Servise> update(@PathVariable("id") Integer id, @RequestBody @Valid Servise newServise,
			BindingResult bindingResult) {
		BindingErrorsResponse errors = new BindingErrorsResponse();
		HttpHeaders headers = new HttpHeaders();
		if (bindingResult.hasErrors() || (newServise == null)) {
			errors.addAllErrors(bindingResult);
			headers.add("errors", errors.toJSON());
			return new ResponseEntity<Servise>(headers, HttpStatus.BAD_REQUEST);
		}
		if (this.serviseService.findById(id).get() == null) {
			return new ResponseEntity<Servise>(HttpStatus.NOT_FOUND);
		}
		Servise updatedServise = this.serviseService.findById(id).map(servise -> {
			String name = newServise.getName() == null ? servise.getName() : newServise.getName();
			servise.setName(name);
			String description = newServise.getDescription() == null ? servise.getDescription()
					: newServise.getDescription();
			servise.setDescription(description);
			Double price = newServise.getPrice() == null ? servise.getPrice() : newServise.getPrice();
			servise.setPrice(price);
			Integer duration = newServise.getDuration() == null ? servise.getDuration() : newServise.getDuration();
			servise.setDuration(duration);
			Integer capacity = newServise.getCapacity() == null ? servise.getCapacity() : newServise.getCapacity();
			servise.setCapacity(capacity);
			Double deposit = newServise.getDeposit() == null ? servise.getDeposit() : newServise.getDeposit();
			servise.setDeposit(deposit);
			Double tax = newServise.getTax() == null ? servise.getTax() : newServise.getTax();
			servise.setTax(tax);
			this.serviseService.save(servise);
			return servise;
		}).orElseGet(() -> {
			newServise.setId(id);
			this.serviseService.save(newServise);
			return newServise;
		});

		return new ResponseEntity<Servise>(updatedServise, HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
		Servise servise = this.serviseService.findById(id).get();
		if (servise == null) {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
		this.serviseService.delete(servise);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

}
