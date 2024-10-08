package com.visit_management.visit_query_ms.handler;

import com.visit_management.visit_core.query.FindVisitByReceptionIdQuery;

public interface VisitQueryHandler {
    String findVisitByReceptionId(FindVisitByReceptionIdQuery query);
}
