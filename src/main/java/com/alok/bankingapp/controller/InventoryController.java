package com.alok.bankingapp.controller;

import com.alok.bankingapp.service.InventoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventory")
public class InventoryController {
    private final InventoryService service;

    public InventoryController(InventoryService service){
        this.service=service;
    }

    @PostMapping("/{id}/buy")
    public void buy(@PathVariable Long id){
        service.buy(id);
    }

    @PostMapping("/{id}/decrement")
    public ResponseEntity<String> purchase(@PathVariable Long id, @RequestParam int quantity) {
        service.pruchaseItem(id, quantity);
        return ResponseEntity.ok("Purchase Successful");
    }

}
