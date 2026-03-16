package com.alok.bankingapp.service;

import com.alok.bankingapp.entity.Inventory;
import com.alok.bankingapp.exception.ConcurrencyException;
import com.alok.bankingapp.exception.InsufficientStockException;
import com.alok.bankingapp.exception.InventoryNotFoundException;
import com.alok.bankingapp.repository.InventoryRepository;
import jakarta.persistence.OptimisticLockException;
import org.hibernate.StaleObjectStateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InventoryService {
    private final InventoryRepository repository;

    public InventoryService(InventoryRepository repository) {
        this.repository = repository;
    }

    @Autowired
    @Lazy
    private InventoryService self;

    @Transactional
    public void buy(Long id) {
//    Inventory inventory =repository.findById(id)
//            .orElseThrow();
//
//    int stock = inventory.getStock();
//    if(stock<=0){
//        throw new RuntimeException("Out of Stock");
//    }
//    inventory.setStock(stock-1);
        int updatedRows = repository.decreaseStock(id);
        if (updatedRows == 0) {
            throw new RuntimeException("Out of Stock");
        }
    }

    public void pruchaseItem(Long inventoryId, int quantity) {
        int maxRetries = 3;
        int attempt = 0;
        while (attempt < maxRetries) {
            try {
                self.pruchaseItemInternal(inventoryId, quantity);
                return;
            } catch (ObjectOptimisticLockingFailureException | StaleObjectStateException e) {
                attempt++;
                if (attempt >= maxRetries) {
                    throw new ConcurrencyException("Too Many concurrent updates,please retry");
                }

                try {
                    Thread.sleep(50);  // 50ms backoff
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    @Transactional
    protected void pruchaseItemInternal(Long id, int quantity) {
        Inventory inventory = repository.findById(id)
                .orElseThrow(() -> new InventoryNotFoundException(
                        "Inventory not found with id: " + id));

        if (inventory.getStock() < quantity) {
            throw new InsufficientStockException(
                    "Requested " + quantity + " but only " + inventory.getStock() + " available");
        }

        inventory.decreamentStock(quantity);
        System.out.println("✅ Purchase successful, stock now: " + inventory.getStock());

    }

    //this method is to check pessimistic locking
    @Transactional
    public void purchaseItemPessimistic(Long inventoryId, int quantity) {
        System.out.println("[PESSIMISTC] thread" + Thread.currentThread().getName() +
                "attemting to acquire lock ....");

        //this line blocks if another thread hold the lock
        Inventory inventory = repository.findByIdWithLock(inventoryId)
                .orElseThrow(() -> new InventoryNotFoundException(
                        "Inventory not found with id:" + inventoryId
                ));

        System.out.println("[PESSIMISTIC] thread " + Thread.currentThread().getName() +
                "acquired lock. Stock:" + inventory.getStock() +
                ",version:" + inventory.getVersion());

        //bussiness logic
        if (inventory.getStock() < quantity) {
            throw new InsufficientStockException(
                    "Requested " + quantity + " but only " + inventory.getStock() + "available"
            );
        }

        inventory.decreamentStock(quantity);

        System.out.println("✅ [PESSIMISTIC] Thread " + Thread.currentThread().getName() +
                " updated stock to: " + inventory.getStock());

        // Lock is automatically released when transaction commits
    }
}
