package com.reception_management.reception_query_ms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories({"com.reception_management.reception_core"})
@EntityScan(basePackages = {
		"com.reception_management.reception_core.model",
		"com.reception_management.reception_core.event",
		"com.reception_management.reception_core.query",
		"com.reception_management.reception_core.responseObj",
		"org.axonframework.eventsourcing.eventstore.jpa",
		"org.axonframework.eventhandling.saga.repository.jpa",
		"org.axonframework.eventhandling.tokenstore"
})
public class ReceptionQueryMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReceptionQueryMsApplication.class, args);
	}

}
