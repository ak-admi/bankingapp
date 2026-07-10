package com.alok.bankingapp.entity;

import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotBlank;
import lombok.Setter;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)  // NOT NULL constraint at DB level
    private String name;

    @Column(unique = true, nullable = false)
    private String username;

    @Setter
    @Column(nullable = false)
    private String password;

    @Setter
    @Enumerated(EnumType.STRING)
    private Role role; // e.g., USER, ADMIN

    @Setter
    @Column(nullable = true, unique = true)  // NOT NULL + UNIQUE constraint
    private String email;

    public User() {
    }
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

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Orders> orders = new ArrayList<>();

    public List<Orders> getOrders() {
        return orders;
    }

    public String getUsername() {
        return this.username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    public String getPassword() {
        return this.password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Role getRole() {
        return this.role;
    }

    public void setUsername(@NotBlank(message = "Username is required") String username) {
        this.username = username;
    }

    public void setName(@NotBlank String name) {
        this.name = name;
    }
}
