package com.example.rabbitmq.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class MessageConsumer {

    @RabbitListener(queues = "queue1")
    public void receiveMessage(String message) {
        System.out.println("Received message: " + message + " time: " + new Date());
        // Add your message processing logic here
    }

    @RabbitListener(queues = "queue2")
    public void receiveMessage1(String message) {
        System.out.println("Received message1: " + message + " time: " + new Date());
        // Add your message processing logic here
    }
}
