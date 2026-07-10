package com.alok.bankingapp.controller;

import com.alok.bankingapp.dto.TransactionSearchRequest;
import com.alok.bankingapp.dto.UserRequest;
import com.alok.bankingapp.dto.responses.TransactionResponse;
import com.alok.bankingapp.dto.responses.UserResponse;
import com.alok.bankingapp.service.TransactionDemoService;
import com.alok.bankingapp.service.TransactionService;
import com.alok.bankingapp.service.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService service;
    private final TransactionDemoService transService;
    private final TransactionService transactionService;

    public UserController(UserService service, TransactionDemoService transService, TransactionService transactionService) {
        this.service= service;
        this.transService=transService;
        this.transactionService = transactionService;
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

    @GetMapping("{id}/transactions")
    public ResponseEntity<Page<TransactionResponse>> getTransactions(
            @PathVariable long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "15") int size,
            @RequestParam(required = false) LocalDate fromDate,
            @RequestParam(required = false) LocalDate toDate,
            @RequestParam(required = false) BigDecimal minAmount,
            @RequestParam(required = false) BigDecimal maxAmount) {

        TransactionSearchRequest filter = new TransactionSearchRequest();

        filter.setPage(page);
        filter.setSize(size);
        filter.setFromDate(fromDate);
        filter.setFromDate(toDate);
        filter.setMinAmount(minAmount);
        filter.setMaxAmount(maxAmount);

        Page<TransactionResponse> result = transactionService.searchTransactions(userId, filter);
        return ResponseEntity.ok(result);
    }
}
