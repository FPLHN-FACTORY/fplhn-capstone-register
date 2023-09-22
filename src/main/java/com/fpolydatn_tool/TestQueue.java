package com.fpolydatn_tool;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class TestQueue {
    private final static String QUEUE_NAME = "hello";
    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             // tạo một kết nối tới cổng rabbit
             Channel channel = connection.createChannel()) {
            // tạo một Queue
//            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            channel.exchangeDeclare("logs", "fanout");
            String message = "Tôi là Huy";
            // truyền mess vào queue
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");

        }
    }
}
