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
    public ResponseEntity<List<EntityModel<Booking>>> all() {
        BookingController.setup();
        List<EntityModel<Booking>> bookings = this.bookingService.findAll().stream()
                        .map(assembler::toModel)
                        .collect(Collectors.toList());

        return ResponseEntity
                .status(HttpStatus.OK) 
                .headers(headers) 
                .body(bookings);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Booking>> one(@PathVariable Integer id) {
        BookingController.setup();
        Booking booking = bookingService.findById((id))
            				.orElseThrow(null);

        return ResponseEntity
                .status(HttpStatus.OK)
                .headers(headers)
                .body(assembler.toModel(booking));
    }

    @RequestMapping(value = "", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Booking> create(@Valid @RequestBody Booking booking,
                                            BindingResult bindingResult, 
                                            UriComponentsBuilder ucBuilder) {
        BookingController.setup();
        BindingErrorsResponse errors = new BindingErrorsResponse();
        HttpHeaders headers = new HttpHeaders();
        if (bindingResult.hasErrors() || (booking == null)) {
            errors.addAllErrors(bindingResult);
            headers.add("errors", errors.toJSON());
            return new ResponseEntity<Booking>(headers, HttpStatus.BAD_REQUEST);
        }
        this.bookingService.save(booking);
        headers.setLocation(ucBuilder.path("/bookings").buildAndExpand(booking.getId()).toUri());
        return new ResponseEntity<Booking>(booking, headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = "application/json")
	public ResponseEntity<Booking> update(@PathVariable("id") Integer id, 
                                            @RequestBody @Valid Booking newBooking, 
                                            BindingResult bindingResult){
        BookingController.setup();
		BindingErrorsResponse errors = new BindingErrorsResponse();
		HttpHeaders headers = new HttpHeaders();
		if(bindingResult.hasErrors() || (newBooking == null)){
			errors.addAllErrors(bindingResult);
			headers.add("errors", errors.toJSON());
			return new ResponseEntity<Booking>(headers, HttpStatus.BAD_REQUEST);
		}
		if(this.bookingService.findById(id).get() == null){
			return new ResponseEntity<Booking>(HttpStatus.NOT_FOUND);
		}
        // bookings(bookDate, emisionDate, status, consumer, servise)
        Booking updatedBooking= this.bookingService.findById(id)
                    .map(booking -> {
                            Date bookDate = newBooking.getBookDate()== null ? booking.getBookDate() : newBooking.getBookDate();
                            booking.setBookDate(bookDate);
                            Date emisionDate = newBooking.getEmisionDate() == null ? booking.getEmisionDate() : newBooking.getEmisionDate();
                            booking.setEmisionDate(emisionDate);
                            Status status = newBooking.getStatus() == null ? booking.getStatus() : newBooking.getStatus();
                            booking.setStatus(status);
                            Servise servise = newBooking.getServise() == null ? booking.getServise() : newBooking.getServise();
                            booking.setServise(servise);
                            this.bookingService.save(booking);
                            return booking;
                        }
                    ) 
                    .orElseGet(() -> {
                        newBooking.setId(id);
                        this.bookingService.save(newBooking);
                        return newBooking;
                    });

		return new ResponseEntity<Booking>(updatedBooking, HttpStatus.NO_CONTENT);
	}


    @DeleteMapping("/{id}/cancel")
    public ResponseEntity<?> cancel(@PathVariable Integer id) {
        BookingController.setup();
        Booking booking = bookingService.findById(id).get();
        if (booking.getStatus() == Status.IN_PROGRESS) {
            booking.setStatus(Status.CANCELLED);
            this.bookingService.save(booking);
            return ResponseEntity.ok(assembler.toModel(booking));
        }

        return ResponseEntity
        .status(HttpStatus.METHOD_NOT_ALLOWED) 
        .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE) 
        .body(Problem.create()
            .withTitle("Method not allowed") 
            .withDetail("You can't cancel a booking that is in the " + booking.getStatus() + " status"));
    }

    @PutMapping("/{id}/complete")
    public ResponseEntity<?> complete(@PathVariable Integer id) {
        BookingController.setup();
        Booking booking = this.bookingService.findById(id).get();        
        if (booking.getStatus() == Status.IN_PROGRESS) {
            booking.setStatus(Status.COMPLETED);
            this.bookingService.save(booking);
            return ResponseEntity.ok(assembler.toModel(booking));
        }

        return new ResponseEntity<Booking>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{id}")
	ResponseEntity<?> delete(@PathVariable Integer id) {
        BookingController.setup();
		this.bookingService.deleteById(id);
	
		return ResponseEntity.noContent().build();
	}

}
