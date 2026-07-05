package com.alok.bankingapp.service;

import com.alok.bankingapp.dto.TransactionSearchRequest;
import com.alok.bankingapp.dto.responses.TransactionResponse;
import com.alok.bankingapp.entity.Transactions;
import com.alok.bankingapp.repository.TransactionRepository;
import com.alok.bankingapp.repository.specification.TransactionSpecification;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Page<TransactionResponse> searchTransactions(Long userId, TransactionSearchRequest filter) {
        Pageable pageable = PageRequest.of(
                filter.getPage(),
                filter.getSize(),
                Sort.by(Sort.Direction.DESC, "trasanctionDate")
        );

        Specification<Transactions> spec = TransactionSpecification.withFilters(userId, filter);
        Page<Transactions> transactions = transactionRepository.findAll(spec, pageable);
        return transactions.map(this::toResponse);
    }

    private TransactionResponse toResponse(Transactions t) {
        return new TransactionResponse(t.getId(), t.getAmount(), t.getType(), t.getTransactionDate());
    }
}
