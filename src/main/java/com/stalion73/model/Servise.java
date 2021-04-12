package com.stalion73.model;

import java.util.Set;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.stalion73.model.transformer.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "servises")
public class Servise extends BaseEntity {

	@NotBlank
	@Length(max = 50)
	private String name;
	@NotBlank
	@Length(max = 255)
	private String description;
	@Positive
	private Double price;
	@PositiveOrZero
	private Integer duration;
	@PositiveOrZero
	private Integer capacity;
	@PositiveOrZero
	private Double deposit; //fianza
	private Double tax; //lo que cobramos al negocio (impuesto)

    @ManyToOne()
    @JoinColumn(name ="business_id")
    @JsonSerialize(using = BusinessSerializer.class)
	@JsonDeserialize(using = BusinessDeserializer.class)
    private Business business;

	@OneToMany(orphanRemoval = true, mappedBy = "servise", fetch = FetchType.LAZY)
	private Set<Booking> bookings;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public Integer getCapacity() {
		return capacity;
	}

	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}

	public Double getDeposit() {
		return deposit;
	}

	public void setDeposit(Double deposit) {
		this.deposit = deposit;
	}

	public Double getTax() {
		return tax;
	}

	public void setTax(Double tax) {
		this.tax = tax;
	}

	public Business getBusiness() {
		return business;
	}

	public void setBussiness(Business business) {
		this.business = business;
	}

	public Set<Booking> getBookings() {
		return bookings;
	}

	public void setBookings(Set<Booking> bookings) {
		this.bookings = bookings;
	}
}
