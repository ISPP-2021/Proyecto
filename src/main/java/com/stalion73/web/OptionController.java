package com.stalion73.web;

import com.stalion73.model.Option;
import com.stalion73.service.OptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.mediatype.problem.Problem;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collection;
import java.util.Optional;

import javax.validation.Valid;

@RestController
@RequestMapping("/options")
public class OptionController {


	@Autowired
	private final OptionService optionService;

	private final static HttpHeaders headers = new HttpHeaders();


    public  static void setup(){
        headers.setAccessControlAllowOrigin("*");
   	}

	public OptionController(OptionService optionService){
		this.optionService = optionService;
	}


	@RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> all() {
        OptionController.setup();
        Collection<Option> options = this.optionService.findAll();
        if (options.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .headers(headers)
                    .body(options);
        }else{
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .headers(headers)
                    .body(options);
        }

    }

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> one(@PathVariable("id") Integer id) {

		OptionController.setup();
		Optional<Option> option = this.optionService.findById(id);
        if (!option.isPresent()) {
            return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
                .headers(headers)
                .body(Problem.create()
						.withTitle("Opción incorrecta")
						.withDetail("La opción no existe."));
        }else{
            return ResponseEntity
                .status(HttpStatus.OK) 
                .headers(headers) 
                .body(option.get());
        }
    }

	@RequestMapping(value = "", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<?> create(@Valid @RequestBody Option option,
										   BindingResult bindingResult,
										   UriComponentsBuilder ucBuilder) {

		OptionController.setup();
		BindingErrorsResponse errors = new BindingErrorsResponse();							
		HttpHeaders headers = new HttpHeaders();
        if (bindingResult.hasErrors() || ( option== null)) {
            errors.addAllErrors(bindingResult);
            headers.add("errors", errors.toJSON());
            return ResponseEntity
				.status(HttpStatus.BAD_REQUEST)
				.headers(headers)
				.body(Problem.create()
						.withTitle("Error de validación")
						.withDetail("La opción no se ha podido validar correctamente."));
        }else{
            this.optionService.save(option);
            headers.setLocation(ucBuilder.path("/business").buildAndExpand(option.getId()).toUri());
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .headers(headers)
                    .body(option);
        }
    }
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = "application/json")
	public ResponseEntity<?> update(@PathVariable("id") Integer id,
										   @RequestBody @Valid Option newOption,
										   BindingResult bindingResult){
		OptionController.setup();
		BindingErrorsResponse errors = new BindingErrorsResponse();                                        
		if(bindingResult.hasErrors() || (newOption == null)){
			errors.addAllErrors(bindingResult);
			headers.add("errors", errors.toJSON());
			return ResponseEntity
				.status(HttpStatus.BAD_REQUEST)
				.headers(headers)
				.body(Problem.create()
						.withTitle("Error de validación")
						.withDetail("La opción no se ha podido validar correctamente."));
		}else if(!this.optionService.findById(id).isPresent()){
			return ResponseEntity
				.status(HttpStatus.NOT_FOUND)
				.header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
				.headers(headers)
				.body(Problem.create()
						.withTitle("Opción incorrecta")
						.withDetail("La opción no existe."));
		}else{
	
			this.optionService.update(id, newOption);
			Option updatedOption = this.optionService.findById(id).get();
			return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
					.headers(headers)
					.body(updatedOption);
		}
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<?> delete(@PathVariable("id") Integer id){
		OptionController.setup();
		Optional<Option> option = this.optionService.findById(id);
		
		if(option.isPresent()){
            this.optionService.delete(option.get());
			return ResponseEntity.noContent().build();

		}else{
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
                    .headers(headers)
                    .body(Problem.create()
							.withTitle("Opción incorrecta")
							.withDetail("La opción no existe."));
        }
	}
  


}