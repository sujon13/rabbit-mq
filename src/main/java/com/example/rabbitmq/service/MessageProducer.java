package com.example.rabbitmq.service;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageProducer {

    private final RabbitTemplate rabbitTemplate;

    private final FanoutExchange fanoutExchange1;

    private final FanoutExchange fanoutExchange2;

    public void sendMessage(String exchange, String message) {
        if (exchange.equals("1")) {
            rabbitTemplate.convertAndSend(fanoutExchange1.getName(), "", message);
        } else {
            rabbitTemplate.convertAndSend(fanoutExchange2.getName(), "", message);
        }
    }
}
