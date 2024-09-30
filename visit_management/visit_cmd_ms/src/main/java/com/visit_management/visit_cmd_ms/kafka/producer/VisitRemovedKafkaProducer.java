package com.visit_management.visit_cmd_ms.kafka.producer;

import com.visit_management.visit_cmd_ms.dto.VisitStatusDto;
import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;


@Service
public class VisitRemovedKafkaProducer implements KafkaProducer<String, Boolean> {

    private static final Logger LOG = LoggerFactory.getLogger(VisitRemovedKafkaProducer.class);

    private KafkaTemplate<String, Boolean> kafkaTemplate;

    public VisitRemovedKafkaProducer(KafkaTemplate<String, Boolean> template) {
        this.kafkaTemplate = template;
    }

//    @Override
//    public void send(String topicName, String key, VisitStatusDto message) {
//        LOG.info("Sending message='{}' to topic='{}'", message, topicName);
//        ListenableFuture<SendResult<String, VisitStatusDto>> kafkaResultFuture =
//                kafkaTemplate.send(topicName, key, message);
//        addCallback(topicName, message, kafkaResultFuture);
//    }
    @Override
    public void send(String topicName, String key, Boolean message) throws ExecutionException, InterruptedException {
        LOG.info("Sending message='{}' to topic='{}'", message, topicName);

               SendResult<String,Boolean> result= kafkaTemplate.send(topicName, key, message).get();
               System.out.println("Kafka Response:::::::::: " + result.getRecordMetadata().topic() +"   "+
                       result.getRecordMetadata().partition() + "   "+
                       result.getRecordMetadata().offset());
//        addCallback(topicName, message, kafkaResultFuture);
    }

    @PreDestroy
    public void close() {
        if (kafkaTemplate != null) {
            LOG.info("Closing kafka producer!");
            kafkaTemplate.destroy();
        }
    }

//    private void addCallback(String topicName, VisitStatusDto message,
//                             ListenableFuture<SendResult<String, VisitStatusDto>> kafkaResultFuture) {
//        kafkaResultFuture.addCallback(new ListenableFutureCallback<>() {
//            @Override
//            public void onFailure(Throwable throwable) {
//                LOG.error("Error while sending message {} to topic {}", message.toString(), topicName, throwable);
//            }
//
//            @Override
//            public void onSuccess(SendResult<String, VisitStatusDto> result) {
//                    RecordMetadata metadata = result.getRecordMetadata();
//                    LOG.debug("Received new metadata. Topic: {}; Partition {}; Offset {}; Timestamp {}, at time {}",
//                            metadata.topic(),
//                            metadata.partition(),
//                            metadata.offset(),
//                            metadata.timestamp(),
//                            System.nanoTime());
//            }
//        });
//    }
}