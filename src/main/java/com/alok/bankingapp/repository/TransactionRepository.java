package com.alok.bankingapp.repository;

import com.alok.bankingapp.entity.Transactions;
import org.hibernate.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TransactionRepository extends JpaRepository<Transactions, Long>, JpaSpecificationExecutor<Transactions> {
}
