package com.stalion73.web;

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

	private final static HttpHeaders headers = new HttpHeaders();

	public  static void setup(){
        headers.setAccessControlAllowOrigin("*");
    }

	public ServiseController(ServiseService serviseService) {
		this.serviseService = serviseService;
	}

	@RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Collection<Servise>> all() {
		ServiseController.setup();
		Collection<Servise> servises = this.serviseService.findAll();
		if (servises.isEmpty()) {
			return new ResponseEntity<Collection<Servise>>(headers, HttpStatus.NOT_FOUND);
		}else{
			return new ResponseEntity<Collection<Servise>>(servises, headers, HttpStatus.OK);
		}

	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> one(@PathVariable("id") Integer id) {
		ServiseController.setup();
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

	@RequestMapping(value = "", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<?> create(@Valid @RequestBody Servise servise, 
												BindingResult bindingResult,
												UriComponentsBuilder ucBuilder) {
		ServiseController.setup();
		BindingErrorsResponse errors = new BindingErrorsResponse();
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
			this.serviseService.save(servise);
			headers.setLocation(ucBuilder.path("/servises" + servise.getId()).buildAndExpand(servise.getId()).toUri());
			return new ResponseEntity<Servise>(servise, headers, HttpStatus.CREATED);
		}
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = "application/json")
	public ResponseEntity<?> update(@PathVariable("id") Integer id, 
												@RequestBody @Valid Servise newServise,
												BindingResult bindingResult) {
		ServiseController.setup();				
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
        ServiseController.setup();
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
