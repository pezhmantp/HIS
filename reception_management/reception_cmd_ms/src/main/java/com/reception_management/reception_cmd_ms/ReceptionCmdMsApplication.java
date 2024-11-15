package com.reception_management.reception_cmd_ms;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.kafka.config.TopicBuilder;

@SpringBootApplication
@EnableDiscoveryClient
@EnableJpaRepositories({"com.reception_management.reception_core"})
@EntityScan(basePackages = {
		"com.reception_management.reception_core.model",
		"com.reception_management.reception_core.event",
		"com.reception_management.reception_core.queries",
		"com.reception_management.reception_core.responeObj",
		"org.axonframework.eventsourcing.eventstore.jpa",
		"org.axonframework.modelling.saga.repository.jpa",
		"org.axonframework.eventhandling.tokenstore"
})
public class ReceptionCmdMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReceptionCmdMsApplication.class, args);

	}
}
