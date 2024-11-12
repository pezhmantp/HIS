package com.pharmacy_management.pharmacy_cmd_ms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.pharmacy_management.pharmacy_core")
@EntityScan(basePackages = {
		"com.pharmacy_management.pharmacy_core.model",
		"com.pharmacy_management.pharmacy_core.event",
//		"com.pharmacy_management.pharmacy_core.query"
		"org.axonframework.eventsourcing.eventstore.jpa",
		"org.axonframework.eventhandling.saga.repository.jpa",
		"org.axonframework.eventhandling.tokenstore"
})
public class PharmacyCmdMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(PharmacyCmdMsApplication.class, args);
	}

}
