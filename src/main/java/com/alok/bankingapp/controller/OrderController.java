package com.alok.bankingapp.controller;

import com.alok.bankingapp.entity.Orders;
import com.alok.bankingapp.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public Orders placeOrder(@RequestParam Long userId,
                             @RequestParam Long inventoryId,
                             @RequestParam int quantity) {
        return orderService.placeOrder(userId, inventoryId, quantity);
    }

    @GetMapping("/user/{userId}")
    public List<Orders> getOrdersByUser(@PathVariable Long userId) {
        return orderService.getOrdersByUser(userId);
    }
}
