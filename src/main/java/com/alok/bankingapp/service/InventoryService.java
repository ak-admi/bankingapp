package com.alok.bankingapp.service;

import com.alok.bankingapp.entity.Inventory;
import com.alok.bankingapp.exception.ConcurrencyException;
import com.alok.bankingapp.exception.InsufficientStockException;
import com.alok.bankingapp.exception.InventoryNotFoundException;
import com.alok.bankingapp.repository.InventoryRepository;
import jakarta.persistence.OptimisticLockException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InventoryService {
    private final InventoryRepository repository;

    public InventoryService(InventoryRepository repository) {
        this.repository = repository;
    }

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

    @Transactional
    public void pruchaseItem(Long inventoryId, int quantity) {
        int maxRetries = 3;
        int attempt = 0;
        while (attempt < maxRetries) {
            try {
                Inventory inventory = repository.findById(inventoryId)
                        .orElseThrow(() -> new InventoryNotFoundException("Inventory not Found with Id:" + inventoryId));

                if (inventory.getStock() < quantity) {
                    throw new InsufficientStockException(
                            "Requested " + quantity + " but Only" + inventory.getStock() + "available");
                }
                inventory.decreamentStock(quantity);
                return;
            } catch (OptimisticLockException e) {
                attempt++;
                if (attempt >= maxRetries) {
                    throw new ConcurrencyException("Too Many concurrent updates,please retry");
                }
            }
        }
    }
}
