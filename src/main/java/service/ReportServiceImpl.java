package service;

import service.operation.QueryOperationHandler;

public class ReportServiceImpl implements ReportService {
    private final QueryOperationHandler queryOperationHandler = new QueryOperationHandler();

    @Override
    public String reportQuery() {
        return queryOperationHandler.getQuery();
    }
}
