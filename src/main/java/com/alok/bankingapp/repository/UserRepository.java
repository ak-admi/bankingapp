package com.alok.bankingapp.repository;
 import com.alok.bankingapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

 import java.lang.ScopedValue;
 import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
