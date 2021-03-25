package com.stalion73.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;


import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;




@Entity
@Table(name = "bookings")
public class Booking extends BaseEntity {

    
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "book")
    private Date bookDate;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "emision")
    private Date emisionDate;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    @JoinColumn(name ="consumer_id")
    @JsonIgnore
    private Consumer consumer;

    @ManyToOne
    @JoinColumn(name ="servise_id")
    @JsonIgnore
    private Servise servise;

    public Date getBookDate() {
        return bookDate;
    }

    public void setBookDate(Date bookDate) {
        this.bookDate = bookDate;
    }

    public Date getEmisionDate() {
        return emisionDate;
    }

    public void setEmisionDate(Date emisionDate) {
        this.emisionDate = emisionDate;
    }

    public Consumer getConsumer() {
        return consumer;
    }

    public void setConsumer(Consumer consumer) {
        this.consumer = consumer;
    }

    public Servise getServise() {
        return servise;
    }

    public void setService(Servise service) {
        this.servise = service;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setServise(Servise servise) {
        this.servise = servise;
    }



    
}
