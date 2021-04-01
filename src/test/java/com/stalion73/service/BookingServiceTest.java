package com.stalion73.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import com.stalion73.model.Booking;
import com.stalion73.model.Booking;
import com.stalion73.model.Status;
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

//	@Test
//	void findBookingByIdTest() throws ParseException {
//		Optional<Booking> booking = this.bookingService.findById(1);
////		Date d1 = new Date(2021, 01, 27);
////		Date d2 = new Date(2022, 01, 27);
//		SimpleDateFormat objSDF = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss"); 
//		Date dt_1 = objSDF.parse("27-01-2021 22:00:00"); 
//		Date dt_2 = objSDF.parse("27-01-2022 22:00:00");
//		Assertions.assertTrue(booking.get().getBookDate().equals(dt_1) && booking.get().getEmisionDate().equals(dt_2)
//				&& booking.get().getStatus().equals(Status.IN_PROGRESS));
//	}

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
