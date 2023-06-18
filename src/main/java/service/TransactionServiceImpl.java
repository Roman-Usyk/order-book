package service;

import java.util.List;
import model.Transaction;

public class TransactionServiceImpl implements TransactionService {
    private OperationStrategy operationStrategy;

    public TransactionServiceImpl(OperationStrategy operationStrategy) {
        this.operationStrategy = operationStrategy;
    }

    @Override
    public void processTransactions(List<Transaction> transactions) {
        transactions.forEach(t -> operationStrategy.get(t.getOperation()).handle(t));
    }
}
