package service.operation;

import model.Transaction;
import model.UpdateTransaction;
import storage.Storage;

public class UpdateOperationHandler implements OperationHandler {
    private static final String BID = "bid";

    @Override
    public void handle(Transaction transaction) {
        UpdateTransaction updateTransaction = (UpdateTransaction) transaction;
        if (updateTransaction.getType().equals(BID)) {
            Storage.reportMapBid.put(updateTransaction.getPrice(), updateTransaction.getSize());
        } else {
            Storage.reportMapAsk.put(updateTransaction.getPrice(), updateTransaction.getSize());
        }
    }
}
