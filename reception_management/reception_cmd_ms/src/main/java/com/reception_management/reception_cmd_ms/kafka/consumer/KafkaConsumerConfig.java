package com.reception_management.reception_cmd_ms.kafka.consumer;


import com.reception_management.reception_cmd_ms.dto.VisitStatusDto;
import com.reception_management.reception_cmd_ms.kafka.config.KafkaConfigData;
import com.reception_management.reception_cmd_ms.kafka.event.VisitRemovedEvent;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;


import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

    private final KafkaConfigData kafkaConfigData;

    private final KafkaConsumerConfigData kafkaConsumerConfigData;

    public KafkaConsumerConfig(KafkaConfigData configData, KafkaConsumerConfigData consumerConfigData) {
        this.kafkaConfigData = configData;
        this.kafkaConsumerConfigData = consumerConfigData;
    }

    @Bean
    public Map<String, Object> consumerConfigs() {

//        JsonDeserializer<Object> deserializer = new JsonDeserializer<>(Object.class);
//        deserializer.setRemoveTypeHeaders(false);
//        deserializer.addTrustedPackages("*");
//        deserializer.setUseTypeMapperForKey(true);
//
//        deserializer.setUseTypeHeaders(false);


        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConfigData.getBootstrapServers());
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, kafkaConsumerConfigData.getKeyDeserializer());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, kafkaConsumerConfigData.getValueDeserializer());
//        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, deserializer);

        props.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaConsumerConfigData.getConsumerGroupId());
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, kafkaConsumerConfigData.getAutoOffsetReset());
//        props.put(kafkaConfigData.getSchemaRegistryUrlKey(), kafkaConfigData.getSchemaRegistryUrl());
//        props.put(kafkaConsumerConfigData.getSpecificAvroReaderKey(), kafkaConsumerConfigData.getSpecificAvroReader());
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, kafkaConsumerConfigData.getSessionTimeoutMs());
        props.put(ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG, kafkaConsumerConfigData.getHeartbeatIntervalMs());
        props.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, kafkaConsumerConfigData.getMaxPollIntervalMs());
        props.put(ConsumerConfig.MAX_PARTITION_FETCH_BYTES_CONFIG,
                kafkaConsumerConfigData.getMaxPartitionFetchBytesDefault() *
                        kafkaConsumerConfigData.getMaxPartitionFetchBytesBoostFactor());
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, kafkaConsumerConfigData.getMaxPollRecords());

        props.put(JsonDeserializer.TRUSTED_PACKAGES, kafkaConsumerConfigData.getTrustedPackage());
        props.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, false);
////        props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, "com.reception_management.reception_cmd_ms.dto.VisitStatusDto");
//        props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, Object.class);


        return props;
    }

    @Bean
    public ConsumerFactory<String, VisitStatusDto> changeVisitStatusTopicConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs(),new StringDeserializer(),
                new JsonDeserializer<>(VisitStatusDto.class));
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, VisitStatusDto>> changeVisitStatusTopicKafkaListener() {
        ConcurrentKafkaListenerContainerFactory<String, VisitStatusDto> factory = new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(changeVisitStatusTopicConsumerFactory());
        factory.setBatchListener(kafkaConsumerConfigData.getBatchListener());
        factory.setConcurrency(kafkaConsumerConfigData.getConcurrencyLevel());
        factory.setAutoStartup(kafkaConsumerConfigData.getAutoStartup());
        factory.getContainerProperties().setPollTimeout(kafkaConsumerConfigData.getPollTimeoutMs());
        return factory;
    }

    @Bean
    public ConsumerFactory<String, VisitRemovedEvent> visitRemovedTopicConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs(),new StringDeserializer(),
                new JsonDeserializer<>(VisitRemovedEvent.class));
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, VisitRemovedEvent>> visitRemovedTopicKafkaListener() {
        ConcurrentKafkaListenerContainerFactory<String, VisitRemovedEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(visitRemovedTopicConsumerFactory());
        factory.setBatchListener(kafkaConsumerConfigData.getBatchListener());
        factory.setConcurrency(kafkaConsumerConfigData.getConcurrencyLevel());
        factory.setAutoStartup(kafkaConsumerConfigData.getAutoStartup());
        factory.getContainerProperties().setPollTimeout(kafkaConsumerConfigData.getPollTimeoutMs());
        return factory;
    }

    @Bean
    public ConsumerFactory<String, Boolean> receptionRemovedTopicConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs(),new StringDeserializer(),
                new JsonDeserializer<>(Boolean.class));
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, Boolean>> receptionRemovedTopicKafkaListener() {
        ConcurrentKafkaListenerContainerFactory<String, Boolean> factory = new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(receptionRemovedTopicConsumerFactory());
        factory.setBatchListener(kafkaConsumerConfigData.getBatchListener());
        factory.setConcurrency(kafkaConsumerConfigData.getConcurrencyLevel());
        factory.setAutoStartup(kafkaConsumerConfigData.getAutoStartup());
        factory.getContainerProperties().setPollTimeout(kafkaConsumerConfigData.getPollTimeoutMs());
        return factory;
    }
}
