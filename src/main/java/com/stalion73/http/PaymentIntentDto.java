package com.stalion73.http;

public class PaymentIntentDto {

    public enum Currency{
        usd, eur
    }

    private String description;
    private int amount;
    private Currency currency;
    //private String price;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    /*public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }*/
}
