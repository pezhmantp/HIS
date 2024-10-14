package com.visit_management.visit_cmd_ms.aggregate;

import com.visit_management.visit_cmd_ms.command.CreateVisitCommand;
import com.visit_management.visit_cmd_ms.command.RemoveVisitCommand;
import com.visit_management.visit_cmd_ms.command.UpdateVisitCommand;
import com.visit_management.visit_core.event.NewVisitCreatedEvent;
import com.visit_management.visit_core.event.VisitRemovedEvent;
import com.visit_management.visit_core.event.VisitUpdatedEvent;
import com.visit_management.visit_core.model.Visit;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import java.sql.Date;

import static org.junit.Assert.*;

public class VisitAggregateTest {
    private FixtureConfiguration<VisitAggregate> fixture;

    public VisitAggregateTest() {
    }
    @Before
    public void setUp() throws Exception {
        this.fixture = new AggregateTestFixture(VisitAggregate.class);
    }
    @Test
    @DisplayName("CreateVisitCommand should publish NewVisitCreatedEvent")
    public void testCreateVisitCommand() {
        Visit visit = new Visit();
        visit.setReceptionId("r1");
        visit.setVisitId("v1");



        this.fixture.given(new NewVisitCreatedEvent(visit.getVisitId(), visit))
                .when(new CreateVisitCommand(visit.getVisitId(), visit))
                .expectEvents(new NewVisitCreatedEvent(visit.getVisitId(), visit));
    }
    @Test
    @DisplayName("UpdateVisitCommand should publish VisitUpdatedEvent")
    public void testUpdateVisitCommand() {
        Visit visit = new Visit();
        visit.setReceptionId("r1");
        visit.setVisitId("v1");

        this.fixture.given(new VisitUpdatedEvent(visit.getVisitId(),visit))
                .when(new UpdateVisitCommand(visit.getVisitId(), visit))
                .expectEvents(new VisitUpdatedEvent(visit.getVisitId(),visit));
    }

}