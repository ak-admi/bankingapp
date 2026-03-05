package com.alok.bankingapp.controller;

import com.alok.bankingapp.service.InventoryService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
