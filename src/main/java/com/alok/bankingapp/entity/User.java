package com.alok.bankingapp.entity;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;

    protected User() {}
    public User(String name, String email){
        this.name=name;
        this.email =email;
    }

    public Long getId() {return id;}
    public String getName() {return name;}
    public String getEmail() {return email;}

    public void updateName(String name){
        this.name=name;
    }
}
