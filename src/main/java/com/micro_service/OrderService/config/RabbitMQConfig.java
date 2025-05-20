package com.micro_service.OrderService.config;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.impl.AMQImpl;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String QUEUE = "order_queue";
    public static final String EXCHANGE = "order_exchange";
    public static final String ROUTING_KEY = "order_routing_key";

    @Bean
    public AMQP.Queue queue() {
        return new AMQImpl.Queue(QUEUE);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    public Binding binding(AMQP.Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
    }
}

