package com.alok.bankingapp.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "orders")
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "inventory_id")
    private Inventory inventory;

    public Long getId() {
        return Id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
