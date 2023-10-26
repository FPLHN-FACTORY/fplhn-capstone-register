//package com.fpolydatn.infrastructure.rabbit;
//
//import com.fpolydatn.infrastructure.constant.ConfigurationsConstant;
//import com.fpolydatn.infrastructure.constant.RabbitConstant;
//import com.fpolydatn.infrastructure.logger.LoggerObject;
//import com.fpolydatn.util.WriterFileCSV;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.stereotype.Component;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Component
//public class LogConsumer {
//
//    public List<LoggerObject> objects(List<LoggerObject> list) {
//        return list;
//    }
//
//    @RabbitListener(queues = RabbitConstant.NAME_LOG_QUEUE)
//    public void processLogMessage(LoggerObject object) {
//        try {
//            System.out.println("Connect !!!");
//            WriterFileCSV writerFileCSV = new WriterFileCSV();
//            if (object.getStatus() != null && object.getStatus().equals(ConfigurationsConstant.FALSE)) {
//                writerFileCSV.writerFileBugCSV(object);
//            } else {
//                if (object.getTenNhom() == null) {
//                    writerFileCSV.writerFileCSV(object);
//                } else {
//                    writerFileCSV.writerFileCSVLuong(object);
//                }
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//    }
//}
