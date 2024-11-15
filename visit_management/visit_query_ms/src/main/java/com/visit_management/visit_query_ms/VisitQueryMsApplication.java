package com.visit_management.visit_query_ms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
@EnableJpaRepositories({"com.visit_management.visit_core"})
@EntityScan(basePackages = {
		"com.visit_management.visit_core.model",
		"com.visit_management.visit_core.event",
		"com.visit_management.visit_core.query",
		"org.axonframework.eventsourcing.eventstore.jpa",
		"org.axonframework.eventhandling.saga.repository.jpa",
		"org.axonframework.eventhandling.tokenstore"
})
public class VisitQueryMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(VisitQueryMsApplication.class, args);
	}

}
