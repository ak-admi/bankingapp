package com.alok.bankingapp.service;

import com.alok.bankingapp.dto.HelloResponse;
import org.springframework.stereotype.Service;

@Service
public class HelloService {
    public HelloResponse generateHello() {
        return new HelloResponse(
                "Backend engineering starts.",
                "Alok",
                "ACTIVE"
        );
    }
}
