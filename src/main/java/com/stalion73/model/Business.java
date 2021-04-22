package com.stalion73.model;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "business")
public class Business extends BaseEntity {

	@NotBlank
	@Length(max = 50)
	private String name;
	@NotBlank
	@Length(max = 100)
	private String address;

	@Enumerated(EnumType.STRING)
	@Column(name = "type")
	private BusinessType businessType;

	@Column(name= "opentime")
	private LocalTime openTime;
	
	@Column(name= "closetime")
    private LocalTime closeTime;

	@Column(name = "automated")
	private Boolean automatedAccept;

	@ManyToOne()
    @JoinColumn(name ="supplier_id")
    @JsonIgnore
    private Supplier supplier;

	@OneToMany(orphanRemoval = true, mappedBy = "business", fetch = FetchType.LAZY)
	private Set<Servise> servises;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "option_id", referencedColumnName = "id")
	private Option option;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public BusinessType getBusinessType() {
		return businessType;
	}

	public void setBusinessType(BusinessType businessType) {
		this.businessType = businessType;
	}

	public Boolean getAutomatedAccept() {
		return automatedAccept;
	}

	public void setAutomatedAccept(Boolean automatedAccept) {
		this.automatedAccept = automatedAccept;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	public Set<Servise> getServices() {
		return servises;
	}

	public void setServices(Set<Servise> servises) {
		this.servises = servises;
	}

	public Option getOption() {
		return option;
	}

	public void setOption(Option option) {
		this.option = option;
	}

	public void addServise(Servise servise){
        if(this.servises == null){
            this.servises = new HashSet<>();
            this.servises.add(servise);
            setServices(servises);
        }else{
            this.servises.add(servise);
        }
    }

	public void addServises(Set<Servise> servises){
        if(this.servises == null){
            this.servises = new HashSet<>();
            this.servises.addAll(servises);
            setServices(servises);
        }else{
            this.servises.addAll(servises);
        }
    }

	public LocalTime getOpenTime() {
        return this.openTime;    
    }

    public void setOpenTime(LocalTime time){
        this.openTime = time;
    }

    public LocalTime getCloseTime() {
        return this.closeTime;    
    }

    public void setCloseTime(LocalTime time){
        this.closeTime = time;
    }

}
