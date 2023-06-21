package service.operation;

import java.util.Map;
import java.util.Optional;
import model.QueryTransaction;
import model.Transaction;
import storage.Storage;

public class QueryOperationHandler implements OperationHandler {
    private static final String PUNCTUATION_MARK = ",";
    private static final String BEST_BID = "best_bid";
    private static final String BEST_ASK = "best_ask";
    private static final String NEW_LINE = System.lineSeparator();
    private static final byte PRICE_POSITION = 0;
    private static final byte SIZE_POSITION = 1;
    private static StringBuilder query = new StringBuilder();

    @Override
    public void handle(Transaction transaction) {
        QueryTransaction queryTransaction = (QueryTransaction) transaction;

        switch (queryTransaction.getType()) {
            case BEST_BID:
                configureQuery(Storage.reportMapBid.lastEntry());
                return;
            case BEST_ASK:
                configureQuery(Storage.reportMapBid.firstEntry());
                return;
            default:
                Optional<Integer> sizeFromBids = Storage.reportMapBid.entrySet()
                        .stream().filter(e -> e.getKey() == queryTransaction.getPrice())
                        .findFirst()
                        .map(Map.Entry::getValue);
                Optional<Integer> sizeFromAsks = Storage.reportMapAsk.entrySet()
                        .stream().filter(e -> e.getKey() == queryTransaction.getPrice())
                        .findFirst()
                        .map(Map.Entry::getValue);
                if (sizeFromBids.isPresent()) {
                    query.append(sizeFromBids.get());
                } else if (sizeFromAsks.isPresent()) {
                    query.append(sizeFromAsks.get());
                }
        }
    }

    public void configureQuery(Map.Entry<Integer, Integer> priceAndSize) {
        query.append(priceAndSize.getKey())
                .append(PUNCTUATION_MARK)
                .append(priceAndSize.getValue())
                .append(NEW_LINE);
    }

    public String getQuery() {
        return query.toString().trim();
    }
}
