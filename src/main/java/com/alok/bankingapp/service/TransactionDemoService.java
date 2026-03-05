package com.alok.bankingapp.service;

import com.alok.bankingapp.dto.UserRequest;
import com.alok.bankingapp.entity.User;
import com.alok.bankingapp.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionDemoService {
    private final UserRepository repository;

    public TransactionDemoService(UserRepository repository){
        this.repository=repository;
    }

    @Transactional
    public void createUserAndFail(UserRequest request){
        User user = new User(request.getName(), request.getEmail());

        repository.save(user);

        System.out.println("User Saved, now throwing error....");

        throw new RuntimeException(("Intentional Failure"));
    }
}
