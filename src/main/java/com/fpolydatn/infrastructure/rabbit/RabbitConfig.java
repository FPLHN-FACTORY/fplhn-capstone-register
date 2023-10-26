package com.fpolydatn.infrastructure.rabbit;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Value("${rabbit.name.queue}")
    private String nameQueue;

    @Value("${rabbit.topic.exchange}")
    private String exchangeQueue;

    @Value("${rabbit.route.key}")
    private String routeQueue;

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(exchangeQueue);
    }

    @Bean
    public Queue logQueue() {
        return new Queue(nameQueue);
    }

    @Bean
    public Binding binding(Queue logQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(logQueue).to(topicExchange).with(routeQueue);
    }
}
