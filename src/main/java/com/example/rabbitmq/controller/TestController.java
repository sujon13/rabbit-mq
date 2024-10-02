package com.example.rabbitmq.controller;

import com.example.rabbitmq.service.MessageProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api")
public class TestController {
    private final MessageProducer messageProducer;

    @GetMapping("/send")
    public String sendMessage(@RequestParam String message, @RequestParam String exchange) {
        messageProducer.sendMessage(exchange, message);
        return "Message sent: " + message;
    }
}
