package service.operation;

import java.util.List;
import java.util.Map;
import model.QueryTransaction;
import model.Transaction;
import storage.Storage;

public class QueryOperationHandler implements OperationHandler {
    private static final String PUNCTUATION_MARK = ",";
    private static final String BEST_BID = "best_bid";
    private static final String BEST_ASK = "best_ask";
    private static final String NEW_LINE = System.lineSeparator();
    private static final String BID = "bid";
    private static final String ASK = "ask";
    private static final byte SIZE_POSITION = 0;
    private static final byte PRICE_POSITION = 1;

    private static StringBuilder query = new StringBuilder();

    @Override
    public void handle(Transaction transaction) {
        QueryTransaction queryTransaction = (QueryTransaction) transaction;

        switch (queryTransaction.getType()) {
            case BEST_BID:
                configureQuery(Storage.reportMap.entrySet()
                        .stream()
                        .filter(e -> e.getKey().equals(BID))
                        .map(Map.Entry::getValue)
                        .findFirst()
                        .get());
                return;
            case BEST_ASK:
                configureQuery(Storage.reportMap.entrySet()
                        .stream()
                        .filter(e -> e.getKey().equals(ASK))
                        .map(Map.Entry::getValue)
                        .findFirst()
                        .get());
                return;
            default:
                configureQuery(List.of(Storage.reportMap.entrySet()
                        .stream()
                        .filter(e -> e.getValue().get(SIZE_POSITION) == queryTransaction.getPrice())
                        .findFirst()
                        .map(e -> e.getValue().get(PRICE_POSITION))
                        .get()));
        }
    }

    public StringBuilder configureQuery(List<Integer> value) {
        for (int i = 0; i < value.size(); i++) {
            if (i < value.size() - PRICE_POSITION) {
                query.append(value.get(i)).append(PUNCTUATION_MARK);
            } else {
                query.append(value.get(i)).append(NEW_LINE);
            }
        }
        return query;
    }

    public String getQuery() {
        return query.toString();
    }
}
