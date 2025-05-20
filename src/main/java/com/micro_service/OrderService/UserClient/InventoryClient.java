package com.micro_service.OrderService.UserClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "INVENTORY-SERVICE")
public interface InventoryClient {

    @GetMapping("/api/inventory/check-stock")
    boolean isInStock(@RequestParam String productCode, @RequestParam int requiredQuantity);
}

