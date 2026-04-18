package com.alok.bankingapp.service;

import com.alok.bankingapp.entity.Inventory;
import com.alok.bankingapp.entity.Orders;
import com.alok.bankingapp.entity.User;
import com.alok.bankingapp.repository.InventoryRepository;
import com.alok.bankingapp.repository.OrderRepository;
import com.alok.bankingapp.repository.UserRepository;

import java.util.List;

public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final InventoryRepository inventoryRepository;

    public OrderService(OrderRepository orderRepository,
                        UserRepository userRepository,
                        InventoryRepository inventoryRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.inventoryRepository = inventoryRepository;
    }

    public Orders placeOrder(Long userId, Long inventoryId, int quantity) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User Not found"));
        Inventory inventory = inventoryRepository.findById(inventoryId)
                .orElseThrow(() -> new RuntimeException("Invenotry Not found"));

        Orders order = new Orders();
        order.setUser(user);
        order.setInventory(inventory);
        order.setQuantity(quantity);

        return orderRepository.save(order);

    }

    public List<Orders> getOrdersByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User Not Found"));
        return user.getOrders();
    }
}
