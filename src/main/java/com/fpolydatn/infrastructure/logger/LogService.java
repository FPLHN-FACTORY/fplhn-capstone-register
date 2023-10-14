package com.fpolydatn.infrastructure.logger;

import com.fpolydatn.infrastructure.constant.RabbitConstant;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class LogService {

    private final RabbitTemplate amqpTemplate;

    @Autowired
    public LogService(RabbitTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }

    // Gửi log thông điệp vào RabbitMQ
    @Scheduled(fixedDelay = RabbitConstant.RABBIT_FIXED_DELAY, initialDelay = RabbitConstant.RABBIT_INITIAL_DELAY)
    public void sendLogMessage(LoggerObject loggerObject) {
        amqpTemplate.convertAndSend(RabbitConstant.NAME_LOG_TOPIC_EXCHANGE, RabbitConstant.NAME_LOG_ROUTE_KEY_WRITE, loggerObject, messagePostProcessor -> {
            messagePostProcessor.getMessageProperties();
            return messagePostProcessor;
        });
    }
}
