package com.fpolydatn.infrastructure.constant;

public class RabbitConstant {
    public static final String NAME_LOG_QUEUE = "fpoly_datn_logs_queue";
    public static final String NAME_READ_QUEUE = "fpoly_datn_read_queue";
    public static final int RABBIT_L0G_DEPLAY = 5000;
    public static final String NAME_LOG_TOPIC_EXCHANGE = "fpoly_datn_logs_topic_exchange";
    public static final String NAME_LOG_ROUTE_KEY = "fpoly_datn_logs_key.#";
    public static final String NAME_READ_ROUTE_KEY = "fpoly_datn_read_key.#";
    public static final String NAME_LOG_ROUTE_KEY_WRITE = "fpoly_datn_logs_key.write";
    public static final String NAME_LOG_ROUTE_KEY_READ = "fpoly_datn_read_key.read";
}
