package com.example.rabbitmq.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;

@Component
@RequiredArgsConstructor
@Slf4j
public class MessageConsumer {
    private final RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = "queue")
    public void receiveTopicMessage(Message message) {
        var response = new String(message.getBody());
        System.out.println("Received Topic message: " + response + " time: " + LocalDateTime.now());

        rabbitTemplate.convertAndSend(message.getMessageProperties().getReplyTo(), buildResponseMessage(message));
    }

    private Message buildResponseMessage(Message request) {
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setCorrelationId(request.getMessageProperties().getCorrelationId());
        return new Message(request.getBody(), messageProperties);
    }

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
