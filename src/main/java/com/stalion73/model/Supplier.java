package com.stalion73.model;

import java.util.Date;
import java.util.Set;
import java.util.HashSet;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;


@Entity
@Table(name =" suppliers")
public class Supplier extends Person{
    
 

    @OneToMany(orphanRemoval = true, mappedBy = "supplier", fetch = FetchType.LAZY)
    private Set<Business> business;

    @Enumerated(EnumType.STRING)
    private SubscriptionType subscription;

    private Date expiration;

    public Set<Business> getBusiness() {
        return business;
    }

    public void setBusiness(Set<Business> business) {
        this.business = business;
    }

    public void addBusiness(Business bus){
        if(this.business == null){
            this.business = new HashSet<>();
            this.business.add(bus);
            setBusiness(business);
        }else{
            this.business.add(bus);
        }
    }

    public SubscriptionType getSubscription() {
        return subscription;
    }

    public void setSubscription(SubscriptionType subscription) {
        this.subscription = subscription;
    }

    public Date getExpiration(){ return this.expiration; }

    public void setExpiration(Date expiration) { this.expiration = expiration; }
}
