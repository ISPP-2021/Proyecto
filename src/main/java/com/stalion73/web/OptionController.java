package com.stalion73.web;

import com.stalion73.model.Business;
import com.stalion73.model.Option;
import com.stalion73.service.OptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;

@RestController
@RequestMapping("/options")
public class OptionController {


	@Autowired
	private final OptionService optionService;

	public OptionController(OptionService optionService){
		this.optionService = optionService;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Option> one(@PathVariable("id") Integer id) {
		Option option = this.optionService.findById(id).get();
		if (option == null) {
			return new ResponseEntity<Option>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Option>(option, HttpStatus.OK);
	}

	@RequestMapping(value = "", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Option> create(@Valid @RequestBody Option option,
										   BindingResult bindingResult,
										   UriComponentsBuilder ucBuilder) {

		BindingErrorsResponse errors = new BindingErrorsResponse();
		HttpHeaders headers = new HttpHeaders();
		if (bindingResult.hasErrors() || (option == null)) {
			errors.addAllErrors(bindingResult);
			headers.add("errors", errors.toJSON());
			return new ResponseEntity<Option>(headers, HttpStatus.BAD_REQUEST);
		}
		this.optionService.save(option);
		headers.setLocation(ucBuilder.path("/option").buildAndExpand(option.getId()).toUri());
		return new ResponseEntity<Option>(option, headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = "application/json")
	public ResponseEntity<Option> update(@PathVariable("id") Integer id,
										   @RequestBody @Valid Option newOption,
										   BindingResult bindingResult){
		BindingErrorsResponse errors = new BindingErrorsResponse();
		HttpHeaders headers = new HttpHeaders();
		if(bindingResult.hasErrors() || (newOption == null)){
			errors.addAllErrors(bindingResult);
			headers.add("errors", errors.toJSON());
			return new ResponseEntity<Option>(headers, HttpStatus.BAD_REQUEST);
		}
		if(this.optionService.findById(id).get() == null){
			return new ResponseEntity<Option>(HttpStatus.NOT_FOUND);
		}
		// option(name, address, optionType, automatedAccept, Supplier, Servises)
		Option updatedOption = this.optionService.findById(id)
				.map(option -> {
							double defaultDeposit = newOption.getDefaultDeposit();
							option.setDefaultDeposit(defaultDeposit);
							int depositTimeLimit = newOption.getDepositTimeLimit();
							option.setDepositTimeLimit(depositTimeLimit);
							Integer limitAutomated = newOption.getLimitAutomated() == null ? option.getLimitAutomated() : newOption.getLimitAutomated();
							option.setLimitAutomated(limitAutomated);
							Boolean automatedAccept = newOption.isAutomatedAccept();
							option.setAutomatedAccept(automatedAccept);
							option.setAutomatedAccept(automatedAccept);
							this.optionService.save(option);
							return option;
						}
				)
				.orElseGet(() -> {
					newOption.setId(id);
					this.optionService.save(newOption);
					return newOption;
				});

		return new ResponseEntity<Option>(updatedOption, HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<Void> delete(@PathVariable("id") Integer id){
		Option option = this.optionService.findById(id).get();
		if(option == null){
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
		this.optionService.delete(option);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
  


}