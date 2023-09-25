package com.fpolydatn.infrastructure.rabbit;

import com.fpolydatn.infrastructure.constant.RabbitConstant;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(RabbitConstant.NAME_LOG_TOPIC_EXCHANGE);
    }

    @Bean
    public Queue logQueue() {
        return new Queue(RabbitConstant.NAME_LOG_QUEUE);
    }

    @Bean
    public Binding binding(Queue logQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(logQueue).to(topicExchange).with(RabbitConstant.NAME_LOG_ROUTE_KEY);
    }
}
