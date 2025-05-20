package com.micro_service.OrderService.controller;

import com.micro_service.OrderService.UserClient.AuthClient;
import com.micro_service.OrderService.UserClient.InventoryClient;
import com.micro_service.OrderService.dto.OrderMessage;
import com.micro_service.OrderService.entity.OrderEntity;
import com.micro_service.OrderService.repository.CartRepository;
import com.micro_service.OrderService.service.RabbitMQSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PlaceOrder {

    @Autowired
    private CartRepository orderRepo;

    @Autowired
    private AuthClient authClient;

    @Autowired
    private InventoryClient inventoryClient;

    @Autowired
    private RabbitMQSender rabbitMQSender;

    @PostMapping("/place")
    public ResponseEntity<?> placeOrder(@RequestBody OrderEntity order,
                                        @RequestHeader("Authorization") String token) {
        try {
            // Validate token using AuthClient
            ResponseEntity<String> response = authClient.validateToken(token);
            if (!response.getStatusCode().is2xxSuccessful()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
            }

            // Check stock for each product in the order
            for (var product : order.getOrder()) {
                boolean inStock = inventoryClient.isInStock(product.getProductName(), product.getProductQuantity());
                if (!inStock) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body("Product '" + product.getProductName() + "' is out of stock.");
                }
            }

            // Save the order if all products are in stock
            orderRepo.save(order);
            OrderMessage message = new OrderMessage(order.getOrderName(), "Order has been placed successfully.");
            rabbitMQSender.sendOrderMessage(message);

            return ResponseEntity.ok("Order placed and message sent!");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }

}
