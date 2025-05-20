package com.micro_service.OrderService.repository;

import com.micro_service.OrderService.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<OrderEntity,Long> {
}
