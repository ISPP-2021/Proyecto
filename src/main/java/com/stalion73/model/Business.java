package com.stalion73.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "business")
public class Business extends BaseEntity{

    private String name;
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private BusinessType businessType;
    
    @Column(name = "automated")
    private Boolean automatedAccept;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "supplier_id", referencedColumnName = "id")
    private Supplier supplier;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "business", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Servise> servises;

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
    
}
