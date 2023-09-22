package com.fpolydatn.infrastructure.rabbit;

import com.fpolydatn.infrastructure.constant.RabbitConstant;
import com.fpolydatn.infrastructure.logger.LoggerObject;
import com.fpolydatn.util.WriterFileCSV;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class LogConsumer {

    @RabbitListener(queues = RabbitConstant.NAME_LOG_QUEUE)
    public void processLogMessage(LoggerObject object) {
        System.out.println("Connect !!!");
        System.out.println(object.getContent());
        WriterFileCSV writerFileCSV = new WriterFileCSV();
        writerFileCSV.writerFileCSV(object);
    }
}
