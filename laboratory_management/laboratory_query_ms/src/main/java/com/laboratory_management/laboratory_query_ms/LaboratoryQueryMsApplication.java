package com.laboratory_management.laboratory_query_ms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories({"com.laboratory_management.laboratory_core"})
@EntityScan(basePackages = {
		"com.laboratory_management.laboratory_core.model",
		"com.laboratory_management.laboratory_core.event",
		"org.axonframework.eventsourcing.eventstore.jpa",
		"org.axonframework.eventhandling.saga.repository.jpa",
		"org.axonframework.eventhandling.tokenstore"
})
public class LaboratoryQueryMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(LaboratoryQueryMsApplication.class, args);
	}

}
