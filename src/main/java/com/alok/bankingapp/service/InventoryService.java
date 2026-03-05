package com.alok.bankingapp.service;

import com.alok.bankingapp.entity.Inventory;
import com.alok.bankingapp.repository.InventoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InventoryService {
private final InventoryRepository repository;
public InventoryService(InventoryRepository repository){
    this.repository = repository;
}

@Transactional
    public void buy(Long id){
    Inventory inventory =repository.findById(id)
            .orElseThrow();

    int stock = inventory.getStock();
    if(stock<=0){
        throw new RuntimeException("Out of Stock");
    }
    inventory.setStock(stock-1);
}
}
