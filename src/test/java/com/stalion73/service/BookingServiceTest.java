package com.stalion73.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import com.stalion73.model.Booking;
import com.stalion73.model.Business;
import com.stalion73.model.Consumer;
import com.stalion73.model.Option;
import com.stalion73.model.Servise;
import com.stalion73.model.Booking;
import com.stalion73.model.Status;
import com.stalion73.model.Supplier;
import com.stalion73.model.User;
import com.stalion73.service.BookingService;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class BookingServiceTest {

	@Autowired
	protected BookingService bookingService;

	@Test
	void findAllBookingTest() {
		List<Booking> bookings = (List<Booking>) this.bookingService.findAll();
		Assertions.assertTrue(!bookings.isEmpty() && bookings.size() == 2);
	}

	@Test
	void findBookingByIdTest() throws ParseException {
		Optional<Booking> booking = this.bookingService.findById(1);
		Assertions.assertTrue(booking.get().getStatus().equals(Status.IN_PROGRESS));
	}
	
	@Test
	void saveBookingTest() {
		Consumer consumer = new Consumer();
		consumer.setId(1);
		Servise servise = new Servise();
		servise.setId(1);
		Booking booking = new Booking();
		booking.setId(3);
		booking.setBookDate(new GregorianCalendar(2021, 03, 04).getTime());
		booking.setEmisionDate(new GregorianCalendar(2021, 03, 03).getTime());
		booking.setStatus(Status.IN_PROGRESS);
		booking.setConsumer(consumer);
		booking.setServise(servise);
		this.bookingService.save(booking);
		Optional<Booking> bookings = this.bookingService.findById(3);
		Assertions.assertTrue(
				!bookings.isEmpty() && booking.getBookDate().equals(new GregorianCalendar(2021, 03, 04).getTime())
						&& booking.getEmisionDate().equals(new GregorianCalendar(2021, 03, 03).getTime())
						&& booking.getStatus().equals(Status.IN_PROGRESS));
	}

	@Test
	void deleteBookingTest() {
		Booking booking = this.bookingService.findById(1).get();
		this.bookingService.delete(booking);
		List<Booking> bookings = (List<Booking>) this.bookingService.findAll();
		Assertions.assertTrue(bookings.size() == 1);
	}

	@Test
	void deleteBookingByIdTest() {
		Booking booking = this.bookingService.findById(1).get();
		this.bookingService.deleteById(booking.getId());
		List<Booking> bookings = (List<Booking>) this.bookingService.findAll();
		Assertions.assertTrue(bookings.size() == 1);
	}

}
