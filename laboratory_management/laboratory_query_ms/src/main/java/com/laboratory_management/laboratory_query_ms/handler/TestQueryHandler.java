package com.laboratory_management.laboratory_query_ms.handler;

import com.laboratory_management.laboratory_core.query.FindTestsByVisitIdQuery;
import com.laboratory_management.laboratory_core.response.TestResponse;
import com.laboratory_management.laboratory_core.response.TestResponses;

import java.util.List;

public interface TestQueryHandler {
    TestResponses findTestsByVisitId(FindTestsByVisitIdQuery event);
}
