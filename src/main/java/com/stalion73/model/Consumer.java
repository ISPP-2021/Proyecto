package com.stalion73.model;

import java.util.Set;
import java.util.HashSet;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "consumers")
public class Consumer extends Person {


    @OneToMany(orphanRemoval = true, mappedBy = "consumer", fetch = FetchType.LAZY)
    private Set<Booking> bookings;


    public Set<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(Set<Booking> bookings) {
        this.bookings = bookings;
    }

    public void addBooking(Booking booking){
        if(this.bookings == null){
            this.bookings = new HashSet<>();
            this.bookings.add(booking);
            setBookings(bookings);
        }else{
            this.bookings.add(booking);
        }
    }
    
}   
