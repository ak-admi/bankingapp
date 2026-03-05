package com.alok.bankingapp.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class Inventory {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private int stock;

    public Inventory(){}

    public Inventory(int stock){
        this.stock=stock;
    }

    public Long getId(){
        return id;
    }

    public int getStock(){
        return stock;
    }

    public void setStock(int stock){
        this.stock = stock;
    }
}
