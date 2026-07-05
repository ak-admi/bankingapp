package com.alok.bankingapp.repository;
 import com.alok.bankingapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

 import java.lang.ScopedValue;

public interface UserRepository extends JpaRepository<User, Long> {

    <T> ScopedValue<T> findByUsername(String username);
}
