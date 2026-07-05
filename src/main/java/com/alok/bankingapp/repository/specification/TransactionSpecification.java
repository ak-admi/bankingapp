package com.alok.bankingapp.repository.specification;

import com.alok.bankingapp.dto.TransactionSearchRequest;
import com.alok.bankingapp.entity.Transactions;
import jakarta.persistence.criteria.Predicate;
import org.hibernate.Transaction;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;


public class TransactionSpecification {

    public static Specification<Transactions> withFilters(Long accountId, TransactionSearchRequest filter) {
        return (root, query, cb) -> {
            List<Predicate> predicateList = new ArrayList<>();
            predicateList.add(cb.equal(root.get("accountId"), accountId));

            if (filter.getFromDate() != null) {
                predicateList.add(cb.greaterThanOrEqualTo(root.get("transactionDate"), filter.getFromDate()));
            }
            if (filter.getToDate() != null) {
                predicateList.add(cb.lessThanOrEqualTo(root.get("transactionDate"), filter.getToDate()));
            }
            if (filter.getMinAmount() != null) {
                predicateList.add(cb.greaterThanOrEqualTo(root.get("amount"), filter.getMinAmount()));
            }
            if (filter.getMaxAmount() != null) {
                predicateList.add(cb.lessThanOrEqualTo(root.get("amount"), filter.getMaxAmount()));
            }
            if (filter.getType() != null) {
                predicateList.add(cb.equal(root.get("type"), filter.getType()));
            }

            return cb.and(predicateList.toArray(new Predicate[0]));
        };
    }
}
