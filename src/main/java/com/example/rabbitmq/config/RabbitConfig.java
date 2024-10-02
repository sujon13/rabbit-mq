package com.example.rabbitmq.config;


import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    @Value("${spring.rabbitmq.host}")
    private String host;

    @Value("${spring.rabbitmq.port}")
    private int port;

    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.password}")
    private String password;


    @Bean
    public FanoutExchange fanoutExchange1() {
        return new FanoutExchange("fanoutExchange1");
    }

    @Bean
    public FanoutExchange fanoutExchange2() {
        return new FanoutExchange("fanoutExchange2");
    }

    @Bean
    public TopicExchange topicExchange1() {
        return new TopicExchange("topicExchange1");
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(host);
        connectionFactory.setPort(port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        return connectionFactory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        //rabbitTemplate.setExchange(fanoutExchange().getName());
        return rabbitTemplate;
    }

    @Bean
    public Queue queue() {
        return new Queue("queue");
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
    public Binding binding(TopicExchange topicExchange1, Queue queue) {
        return BindingBuilder.bind(queue).to(topicExchange1).with("route.*");
    }

    @Bean
    public Binding queue1ToFanout1(FanoutExchange fanoutExchange1, Queue queue1) {
        return BindingBuilder.bind(queue1).to(fanoutExchange1);
    }

//    @Bean
//    public Binding queue2ToFanout1(FanoutExchange fanoutExchange1, Queue queue2) {
//        return BindingBuilder.bind(queue2).to(fanoutExchange1);
//    }

}
