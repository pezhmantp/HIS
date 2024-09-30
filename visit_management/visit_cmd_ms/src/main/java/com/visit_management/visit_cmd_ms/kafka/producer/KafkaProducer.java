package com.visit_management.visit_cmd_ms.kafka.producer;

import java.util.concurrent.ExecutionException;

public interface KafkaProducer<K,V> {
    void send(String topicName, K key, V message) throws ExecutionException, InterruptedException;
}
