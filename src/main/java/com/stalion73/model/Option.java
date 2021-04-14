package com.stalion73.model;

import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.Positive;

@Entity
@Table(name = "options")
public class Option extends BaseEntity{

    private boolean automatedAccept;

    private Integer limitAutomated;

    @Range(max=1,min=0)
    private double defaultDeposit;

    //En minutos.
    @Positive
    private int depositTimeLimit;

    public boolean isAutomatedAccept() {
        return automatedAccept;
    }

    public void setAutomatedAccept(boolean automatedAccept) {
        this.automatedAccept = automatedAccept;
    }

    public Integer getLimitAutomated() {
        return limitAutomated;
    }

    public void setLimitAutomated(Integer limitAutomated) {
        this.limitAutomated = limitAutomated;
    }

    public double getDefaultDeposit() {
        return defaultDeposit;
    }

    public void setDefaultDeposit(double defaultDeposit) {
        this.defaultDeposit = defaultDeposit;
    }

    public int getDepositTimeLimit() {
        return depositTimeLimit;
    }

    public void setDepositTimeLimit(int depositTimeLimit) {
        this.depositTimeLimit = depositTimeLimit;
    }

}
