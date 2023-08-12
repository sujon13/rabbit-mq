package com.example.rabbitmq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Bean
    public FanoutExchange fanoutExchange1() {
        return new FanoutExchange("fanoutExchange1");
    }

    @Bean
    public FanoutExchange fanoutExchange2() {
        return new FanoutExchange("fanoutExchange2");
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        return connectionFactory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        //rabbitTemplate.setExchange(fanoutExchange().getName());
        return rabbitTemplate;
    }

    @Bean
    public Queue queue1() {
        return new Queue("queue1");
    }

    @Bean
    public Queue queue2() {
        return new Queue("queue2");
    }

    @Bean
    public Binding binding1(FanoutExchange fanoutExchange1, Queue queue1) {
        return BindingBuilder.bind(queue1).to(fanoutExchange1);
    }

    @Bean
    public Binding binding2(FanoutExchange fanoutExchange2, Queue queue2) {
        return BindingBuilder.bind(queue2).to(fanoutExchange2);
    }
}
