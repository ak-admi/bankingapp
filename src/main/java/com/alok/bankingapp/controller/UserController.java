package com.alok.bankingapp.controller;

import com.alok.bankingapp.dto.UserRequest;
import com.alok.bankingapp.dto.UserResponse;
import com.alok.bankingapp.service.TransactionDemoService;
import com.alok.bankingapp.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService service;
    private final TransactionDemoService transService;

    public UserController(UserService service,TransactionDemoService transService){
        this.service= service;
        this.transService=transService;
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequest request) {
        UserResponse response = service.create(request);
        return ResponseEntity
                .created(URI.create("/users/"+response.getId()))
        .body(response);
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> findAll(){
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findById(@PathVariable Long id){
        return ResponseEntity.ok(service.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/fail")
    public void createUserAndFail(@RequestBody UserRequest request){
        transService.createUserAndFail(request);
    }

    @PostMapping("/self")
    public void selfInvocation(@RequestBody UserRequest request){
        transService.outerMethod(request);
    }
}
