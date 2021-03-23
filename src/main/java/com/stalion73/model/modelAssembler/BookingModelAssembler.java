package com.stalion73.model.modelAssembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.stalion73.web.BookingController;
import com.stalion73.model.Booking;
import com.stalion73.model.Status;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class BookingModelAssembler implements RepresentationModelAssembler<Booking, EntityModel<Booking>> {

  @Override
  public EntityModel<Booking> toModel(Booking booking) {

    // Unconditional links to single-item resource and aggregate root
    EntityModel<Booking> bookingModel = new EntityModel(booking,
        linkTo(methodOn(BookingController.class).one(booking.getId())).withSelfRel(),
        linkTo(methodOn(BookingController.class).all()).withRel("/bookings"));


    // Conditional links based on state of the order
    if (booking.getStatus() == Status.IN_PROGRESS) {
      bookingModel.add(linkTo(methodOn(BookingController.class).cancel(booking.getId())).withRel("cancel"));
      bookingModel.add(linkTo(methodOn(BookingController.class).complete(booking.getId())).withRel("complete"));
    }

    return bookingModel;
  }
}