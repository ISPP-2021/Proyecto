package com.stalion73.service;

import com.stalion73.repository.BookingRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Collection;
import java.util.Optional;
import java.util.Date;

import org.springframework.transaction.annotation.Transactional;

import com.stalion73.model.Booking;
import com.stalion73.model.Servise;
import com.stalion73.model.Status;

@Service
public class BookingService {

	BookingRepository bookingRepository;

	@Autowired
	public BookingService(BookingRepository bookingRepository) {
		this.bookingRepository = bookingRepository;
	}

	@Transactional(readOnly = true)
	public Collection<Booking> findAll() {
		return bookingRepository.findAll();
	}

	@Transactional(readOnly = true)
	public Optional<Booking> findById(Integer id) {
		return bookingRepository.findById(id);
	}

	@Transactional
	public void save(Booking booking) {
		bookingRepository.save(booking);
	}

	@Transactional
	public void update(Integer id, Booking newBooking) {
		Booking updatedBooking = this.bookingRepository.findById(id).map(booking -> {
			Date bookDate = newBooking.getBookDate() == null ? booking.getBookDate() : newBooking.getBookDate();
			booking.setBookDate(bookDate);
			Date emisionDate = newBooking.getEmisionDate() == null ? booking.getEmisionDate()
					: newBooking.getEmisionDate();
			booking.setEmisionDate(emisionDate);
			Status status = newBooking.getStatus() == null ? booking.getStatus() : newBooking.getStatus();
			booking.setStatus(status);
			Servise servise = newBooking.getServise() == null ? booking.getServise() : newBooking.getServise();
			booking.setServise(servise);
			this.bookingRepository.save(booking);
			return booking;
		}).orElseGet(() -> {
			return null;
		});
		this.bookingRepository.save(updatedBooking);
	}

	@Transactional
	public void deleteById(Integer id) {
		bookingRepository.deleteById(id);
	}

	@Transactional
	public void delete(Booking booking) {
		bookingRepository.delete(booking);
	}

	@Transactional
	public Collection<Booking> findBookingByStatus(Status status) {
		return bookingRepository.findBookingByStatus(status);
	}

}
