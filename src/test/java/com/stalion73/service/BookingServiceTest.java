package com.stalion73.service;

import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import com.stalion73.model.Booking;
import com.stalion73.model.Consumer;
import com.stalion73.model.Servise;
import com.stalion73.model.Status;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class BookingServiceTest {

	@Autowired
	protected BookingService bookingService;

	private int count;

	@BeforeEach
	void setUp() {
		count = bookingService.findAll().size();
	}

	@Test
	void findAllBookingTest() {
		List<Booking> bookings = (List<Booking>) this.bookingService.findAll();
		Assertions.assertTrue(!bookings.isEmpty() && bookings.size() == count);
	}

	@Test
	void findBookingByIdTest() {
		Optional<Booking> booking = this.bookingService.findById(1);
		Assertions.assertTrue(booking.get().getStatus().equals(Status.IN_PROGRESS));
	}

	@Test
	void findBookingByStatusTest() {
		Collection<Booking> booking = bookingService.findBookingByStatus(Status.IN_PROGRESS);
		Assertions.assertTrue(booking.size() == 3);
	}

	@Test
	void saveBookingTest() {
		Consumer consumer = new Consumer();
		consumer.setId(1);
		Servise servise = new Servise();
		servise.setId(1);
		Booking booking = new Booking();
		booking.setId(5);
		booking.setBookDate(new GregorianCalendar(2021, 07, 07).getTime());
		booking.setEmisionDate(new GregorianCalendar(2021, 06, 06).getTime());
		booking.setStatus(Status.IN_PROGRESS);
		booking.setConsumer(consumer);
		booking.setServise(servise);
		this.bookingService.save(booking);
		Assertions.assertTrue(booking.getBookDate().equals(new GregorianCalendar(2021, 07, 07).getTime())
				&& booking.getEmisionDate().equals(new GregorianCalendar(2021, 06, 06).getTime())
				&& booking.getStatus().equals(Status.IN_PROGRESS));
	}

	@Test
	void deleteBookingByIdTest() {
		Booking booking = this.bookingService.findById(1).get();
		this.bookingService.deleteById(booking.getId());
		List<Booking> bookings = (List<Booking>) this.bookingService.findAll();
		Assertions.assertTrue(bookings.size() == count - 1);
	}

	@Test
	void deleteBookingTest() {
		Booking booking = this.bookingService.findById(2).get();
		this.bookingService.delete(booking);
		List<Booking> bookings = (List<Booking>) this.bookingService.findAll();
		Assertions.assertTrue(bookings.size() == count - 1);
	}

}
