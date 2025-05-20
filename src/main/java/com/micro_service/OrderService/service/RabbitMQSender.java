package com.micro_service.OrderService.service;

import com.micro_service.OrderService.config.RabbitMQConfig;
import com.micro_service.OrderService.dto.OrderMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendOrderMessage(OrderMessage message) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE,
                RabbitMQConfig.ROUTING_KEY,
                message);
    }
}

