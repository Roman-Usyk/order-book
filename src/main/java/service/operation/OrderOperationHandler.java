package service.operation;

import java.util.Map;
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
                buyHandle(orderTransaction.getSize());
                return;
            default:
                sellHandle(orderTransaction.getSize());
        }
    }

    private void buyHandle(int size) {
        int minValueAsk = Storage.reportMapAsk.entrySet()
                .stream()
                .map(Map.Entry::getValue)
                .mapToInt(v -> v).min()
                .orElseThrow();
        Storage.reportMapAsk.put(Storage.reportMapAsk.entrySet()
                .stream()
                .filter(e -> e.getValue() == minValueAsk)
                .findFirst()
                .orElseThrow()
                .getKey(), minValueAsk - size);
    }

    private void sellHandle(int size) {
        int maxValueBid = Storage.reportMapBid.entrySet()
                .stream()
                .map(Map.Entry::getValue)
                .mapToInt(v -> v)
                .max()
                .orElseThrow();
        Storage.reportMapBid.put(Storage.reportMapBid.entrySet()
                .stream()
                .filter(e -> e.getValue() == maxValueBid)
                .findFirst()
                .orElseThrow()
                .getKey(), maxValueBid - size);
    }
}
