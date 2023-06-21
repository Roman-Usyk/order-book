package service.operation;

import model.OrderTransaction;
import model.Transaction;
import storage.Storage;

public class OrderOperationHandler implements OperationHandler {
    private static final String BUY = "buy";

    @Override
    public void handle(Transaction transaction) {
        OrderTransaction orderTransaction = (OrderTransaction) transaction;

        switch (orderTransaction.getType()) {
            case BUY:
                buyHandle(orderTransaction.getSize());
                return;
            default:
                sellHandle(orderTransaction.getSize());
        }
    }

    private void buyHandle(int size) {
        Integer keyOfMinAsk = Storage.reportMapAsk.firstEntry().getKey();
        Integer valueOfMinAsk = Storage.reportMapAsk.get(keyOfMinAsk);
        if (valueOfMinAsk < size) {
            throw new RuntimeException("You can't buy " + size
                    + " shares. There are " + valueOfMinAsk + " shares!");
        }
        Storage.reportMapAsk.put(keyOfMinAsk, valueOfMinAsk - size);
    }

    private void sellHandle(int size) {
        Integer keyOfMaxBid = Storage.reportMapBid.lastEntry().getKey();
        Integer valueOfMaxBid = Storage.reportMapBid.get(keyOfMaxBid);
        if (valueOfMaxBid < size) {
            throw new RuntimeException("You can't buy " + size
                    + " shares. There are " + valueOfMaxBid + " shares!");
        }
        Storage.reportMapBid.put(keyOfMaxBid, valueOfMaxBid - size);
    }
}
