package com.stalion73.web;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

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

import com.stalion73.model.Booking;
import com.stalion73.model.modelAssembler.BookingModelAssembler;
import com.stalion73.service.BookingService;


@RestController
@RequestMapping("/bookings")
public class BookingController {

	@Autowired
	private final BookingService bookingService;

	@Autowired
	private final BookingModelAssembler assembler;

	public BookingController(BookingService bookingService, BookingModelAssembler assembler) {
		this.bookingService = bookingService;
		this.assembler = assembler;
	}

	@GetMapping
	public CollectionModel<EntityModel<Booking>> all() {
		List<EntityModel<Booking>> bookings = this.bookingService.findAll().stream().map(assembler::toModel)
				.collect(Collectors.toList());

		return new CollectionModel(bookings, linkTo(methodOn(BookingController.class).all()).withSelfRel());
	}

	@GetMapping("/{id}")
	public EntityModel<Booking> one(@PathVariable Integer id) {
		Booking booking = bookingService.findById((id)).orElseThrow(null);

		return assembler.toModel(booking);
	}

	@PostMapping
	public ResponseEntity<?> create(@RequestBody Booking booking) {
		bookingService.save(booking);
		EntityModel<Booking> entityModel = assembler.toModel(booking);

		return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
	}

	@PutMapping("/{id}")
	ResponseEntity<?> update(@RequestBody Booking newBooking, @PathVariable Integer id) {
		Booking updatedBooking = this.bookingService.findById(id).map(booking -> {
			booking.setBookDate(newBooking.getBookDate());
			booking.setConsumer(newBooking.getConsumer());
			booking.setEmisionDate(newBooking.getEmisionDate());
			booking.setService(newBooking.getServise());
			this.bookingService.save(booking);
			return booking;
		}).orElseGet(() -> {
			newBooking.setId(id);
			this.bookingService.save(newBooking);
			return newBooking;
		});

		EntityModel<Booking> entityModel = assembler.toModel(updatedBooking);

		return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
	}

	@DeleteMapping("/{id}")
	ResponseEntity<?> delete(@PathVariable Integer id) {
		bookingService.deleteById(id);

		return ResponseEntity.noContent().build();
	}

}
