package service.operation;

import java.util.List;
import model.Transaction;
import model.UpdateTransaction;
import storage.Storage;

public class UpdateOperationHandler implements OperationHandler {
    @Override
    public void handle(Transaction transaction) {
        UpdateTransaction updateTransaction = (UpdateTransaction) transaction;
        Storage.reportMap.put(updateTransaction.getType(),
                List.of(updateTransaction.getPrice(), updateTransaction.getSize()));
    }
}
