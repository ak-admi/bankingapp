package com.alok.bankingapp.entity;

import com.alok.bankingapp.exception.InsufficientStockException;
import jakarta.persistence.*;

import javax.naming.InsufficientResourcesException;

@Entity
public class Inventory {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int stock;

    @Version
    private Long version;

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

    public void decreamentStock(int quantity) {
        if (this.stock < quantity) {
            throw new InsufficientStockException("Not Enough Stock");
        }
        this.stock -= quantity;
    }
}
