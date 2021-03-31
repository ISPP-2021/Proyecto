package com.stalion73.web;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.mediatype.problem.Problem;



import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;

import javax.validation.Valid;

import com.stalion73.service.BookingService;
import com.stalion73.model.Booking;
import com.stalion73.model.Consumer;
import com.stalion73.model.Status;
import com.stalion73.model.Servise;
import com.stalion73.model.modelAssembler.BookingModelAssembler;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/bookings")
public class BookingController {
    
    @Autowired
    private final BookingService bookingService;

    @Autowired
    private final BookingModelAssembler assembler;

    private final static HttpHeaders headers = new HttpHeaders();


    public  static void setup(){
        headers.setAccessControlAllowOrigin("*");
    }

    public BookingController(BookingService bookingService, BookingModelAssembler assembler){
        this.bookingService = bookingService;
        this.assembler = assembler;
    }

    @GetMapping
    public ResponseEntity<?> all() {
        BookingController.setup();
        List<EntityModel<Booking>> bookings = this.bookingService.findAll().stream()
                        .map(assembler::toModel)
                        .collect(Collectors.toList());
        if(bookings.isEmpty()){
            return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .headers(headers)
                .body(bookings);
        }else{
            return ResponseEntity
                .status(HttpStatus.OK) 
                .headers(headers) 
                .body(bookings);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> one(@PathVariable("id") Integer id) {
        BookingController.setup();
        Optional<Booking> booking = bookingService.findById((id));
        if(booking.isPresent()){
            return ResponseEntity
                .status(HttpStatus.OK)
                .headers(headers)
                .body(assembler.toModel(booking.get()));
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

    @RequestMapping(value = "", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<?> create(@Valid @RequestBody Booking booking,
                                            BindingResult bindingResult, 
                                            UriComponentsBuilder ucBuilder) {
        BookingController.setup();
        BindingErrorsResponse errors = new BindingErrorsResponse();
        if (bindingResult.hasErrors() || (booking == null)) {
            errors.addAllErrors(bindingResult);
            headers.add("errors", errors.toJSON());
            return ResponseEntity
				.status(HttpStatus.BAD_REQUEST)
				.headers(headers)
				.body(Problem.create()
					.withTitle("Validation error")
					.withDetail("The provided consumer was not successfuly validated"));
        }else{
            this.bookingService.save(booking);
            headers.setLocation(ucBuilder.path("/bookings" + booking.getId()).buildAndExpand(booking.getId()).toUri());
            return new ResponseEntity<Booking>(booking, headers, HttpStatus.CREATED);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = "application/json")
	public ResponseEntity<?> update(@PathVariable("id") Integer id, 
                                            @RequestBody @Valid Booking newBooking, 
                                            BindingResult bindingResult){
        BookingController.setup();
		BindingErrorsResponse errors = new BindingErrorsResponse();
		if(bindingResult.hasErrors() || (newBooking == null)){
            errors.addAllErrors(bindingResult);
            headers.add("errors", errors.toJSON());
            return ResponseEntity
				.status(HttpStatus.BAD_REQUEST)
				.headers(headers)
				.body(Problem.create()
					.withTitle("Validation error")
					.withDetail("The provided consumer was not successfuly validated"));
		}else if(!this.bookingService.findById(id).isPresent()){
            return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
                .headers(headers)
                .body(Problem.create()
                    .withTitle("Ineffected ID")
                    .withDetail("The provided ID doesn't exist"));
		}else{
            this.bookingService.update(id, newBooking);
		    return new ResponseEntity<Booking>(newBooking, headers, HttpStatus.NO_CONTENT);
        }
	}


    @DeleteMapping("/{id}/cancel")
    public ResponseEntity<?> cancel(@PathVariable Integer id) {
        BookingController.setup();
        Booking booking = bookingService.findById(id).get();
        if (booking.getStatus() == Status.IN_PROGRESS) {
            booking.setStatus(Status.CANCELLED);
            this.bookingService.save(booking);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .headers(headers)
                    .body(assembler.toModel(booking));
        }else{
            return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED) 
                .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE) 
                .headers(headers)
                .body(Problem.create()
                    .withTitle("Method not allowed") 
                    .withDetail("You can't cancel a booking that is in the " + booking.getStatus() + " status"));
        }
    }

    @PutMapping("/{id}/complete")
    public ResponseEntity<?> complete(@PathVariable Integer id) {
        BookingController.setup();
        Booking booking = this.bookingService.findById(id).get();        
        if (booking.getStatus() == Status.IN_PROGRESS) {
            booking.setStatus(Status.COMPLETED);
            this.bookingService.save(booking);
            return ResponseEntity
                .status(HttpStatus.OK)
                .headers(headers)
                .body(assembler.toModel(booking));
        }else if(booking.getStatus() == Status.COMPLETED){
            return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED) 
                .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE) 
                .headers(headers)
                .body(Problem.create()
                    .withTitle("Method not allowed") 
                    .withDetail("You can't complete a booking that is in the " + booking.getStatus() + " status")); 
        }else{
            return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED) 
                .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE) 
                .headers(headers)
                .body(Problem.create()
                    .withTitle("Method not allowed") 
                    .withDetail("You can't complete a booking that is in the " + booking.getStatus() + " status"));
        }

    }

    @DeleteMapping("/{id}")
	ResponseEntity<?> delete(@PathVariable Integer id) {
        BookingController.setup();
        Optional<Booking> booking = this.bookingService.findById(id);
		if(booking.isPresent()){
            this.bookingService.deleteById(id);
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
