package com.reception_management.reception_cmd_ms.kafka.producer;

import com.reception_management.reception_cmd_ms.kafka.config.KafkaConfigData;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig<K,V> {

    private final KafkaConfigData kafkaConfigData;

    private final KafkaProducerConfigData kafkaProducerConfigData;

    public KafkaProducerConfig(KafkaConfigData configData, KafkaProducerConfigData producerConfigData) {
        this.kafkaConfigData = configData;
        this.kafkaProducerConfigData = producerConfigData;
    }

    @Bean
    public Map<String, Object> producerConfig() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConfigData.getBootstrapServers());
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, kafkaProducerConfigData.getKeySerializerClass());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, kafkaProducerConfigData.getValueSerializerClass());
        props.put(ProducerConfig.ACKS_CONFIG, kafkaProducerConfigData.getAcks());
//        props.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, kafkaProducerConfigData.getRequestTimeoutMs());
//        props.put(ProducerConfig.RETRIES_CONFIG, kafkaProducerConfigData.getRetryCount());

        return props;
    }

    @Bean
    public ProducerFactory<K, V> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfig());
    }

    @Bean
    public KafkaTemplate<K, V> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}
