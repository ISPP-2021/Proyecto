package com.stalion73.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import javax.persistence.Table;



@Entity
@Table(name =" suppliers")
public class Supplier extends Person{


    @OneToMany(orphanRemoval = true, mappedBy = "supplier", fetch = FetchType.LAZY)
    private Set<Business> Business;

    public Set<Business> getBusiness() {
        return Business;
    }

    public void setBusiness(Set<Business> business) {
        Business = business;
    }


}
