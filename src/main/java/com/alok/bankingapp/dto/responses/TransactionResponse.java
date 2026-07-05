package com.alok.bankingapp.dto.responses;

import com.alok.bankingapp.entity.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TransactionResponse {

    private Long Id;
    private BigDecimal amount;
    private TransactionType type;
    private LocalDate transactionDate;

    public Long getId() {
        return Id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public TransactionType getType() {
        return type;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public TransactionResponse(Long id, BigDecimal amount, TransactionType type, LocalDate transactionDate) {

    }

}
