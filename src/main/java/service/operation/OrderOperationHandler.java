package service.operation;

import java.util.List;
import model.OrderTransaction;
import model.Transaction;
import storage.Storage;

public class OrderOperationHandler implements OperationHandler {
    private static final String BUY = "buy";
    private static final String ASK = "ask";
    private static final String BID = "bid";
    private static final byte PRICE_POSITION = 0;
    private static final byte SIZE_POSITION = 1;

    @Override
    public void handle(Transaction transaction) {
        OrderTransaction orderTransaction = (OrderTransaction) transaction;

        switch (orderTransaction.getType()) {
            case BUY:
                Storage.reportMap.put(ASK,
                        List.of(Storage.reportMap.get(ASK).get(PRICE_POSITION),
                                Storage.reportMap.get(ASK)
                                        .get(SIZE_POSITION) - orderTransaction.getSize()));
                return;
            default:
                Storage.reportMap.put(BID,
                        List.of(Storage.reportMap.get(BID).get(PRICE_POSITION),
                                Storage.reportMap.get(BID)
                                        .get(SIZE_POSITION) - orderTransaction.getSize()));
        }
    }
}
