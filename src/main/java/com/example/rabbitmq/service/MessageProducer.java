package com.example.rabbitmq.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class MessageProducer {

    private final RabbitTemplate rabbitTemplate;

    private final FanoutExchange fanoutExchange1;

    private final FanoutExchange fanoutExchange2;

    private final TopicExchange topicExchange1;

    private Message buildMessage(String message) {
        MessageProperties messageProperties = new MessageProperties();
        return new Message(message.getBytes(), messageProperties);
    }

    private void handleResponse(Message response) {
        if (response == null || response.getBody() == null) {
            log.error("response has empty body");
        } else {
            log.info("ack received at : " + LocalDateTime.now());
            log.debug("response -> " + new String(response.getBody()));
        }
    }

    public void sendMessage(String exchange, String message) {
        Message requestMessage = buildMessage(message);

        switch (exchange) {
            case "topic1", "topic3" ->  {
                char lastChar = exchange.charAt(exchange.length() - 1);
                rabbitTemplate.send(topicExchange1.getName(), "route." + lastChar, requestMessage);
                //handleResponse(response);
            }
            //case "topic3" -> rabbitTemplate.convertAndSend(topicExchange1.getName(), "route.3", message);
            case "fanout1" -> {
                System.out.println("Fanout message sent at " + LocalDateTime.now());
                rabbitTemplate.convertAndSend(fanoutExchange1.getName(), "", message);
            }
            default -> rabbitTemplate.convertAndSend(fanoutExchange2.getName(), "", message);
        }
    }
}
