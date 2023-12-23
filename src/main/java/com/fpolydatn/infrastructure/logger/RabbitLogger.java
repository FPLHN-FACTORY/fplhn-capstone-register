package com.fpolydatn.infrastructure.logger;

import com.fpolydatn.core.common.log.IDatnLogger;
import com.fpolydatn.core.common.log.LogObject;
import com.google.gson.Gson;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public class RabbitLogger implements IDatnLogger {

    private final RabbitTemplate amqpTemplate;

    @Value("${rabbit.route.key}")
    private String routeQueue;

    @Value("${rabbit.topic.exchange}")
    private String exchangeQueue;

    @Autowired
    public RabbitLogger(RabbitTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }

    @Override
    public void log(LogObject data) {
        Gson gson = new Gson();
        String message = gson.toJson(data);
        amqpTemplate.convertAndSend(exchangeQueue, routeQueue, message, messagePostProcessor -> {
            messagePostProcessor.getMessageProperties();
            return messagePostProcessor;
        });
    }

    @Override
    public void log(String content, String status) {

    }
}
