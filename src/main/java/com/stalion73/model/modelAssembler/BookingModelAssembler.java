package com.stalion73.model.modelAssembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.stalion73.model.Booking;
import com.stalion73.web.BookingController;
@Component
public class BookingModelAssembler implements RepresentationModelAssembler<Booking, EntityModel<Booking>> {

	@Override
	public EntityModel<Booking> toModel(Booking booking) {

		// Links incondicionales hacia single-item resource y aggregate root

		EntityModel<Booking> bookingModel = new EntityModel(booking,
				linkTo(methodOn(BookingController.class).one(booking.getId())).withSelfRel(),
				linkTo(methodOn(BookingController.class).all()).withRel("/bookings"));

		return bookingModel;

	}
}
