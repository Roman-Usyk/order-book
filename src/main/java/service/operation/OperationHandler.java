package service.operation;

import model.Transaction;

public interface OperationHandler {
    void handle(Transaction transaction);
}
