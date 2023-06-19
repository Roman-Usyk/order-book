package service.operation;

import java.util.List;
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
    private static final String BID = "bid";
    private static final String ASK = "ask";
    private static final byte PRICE_POSITION = 0;
    private static final byte SIZE_POSITION = 1;

    private static StringBuilder query = new StringBuilder();

    @Override
    public void handle(Transaction transaction) {
        QueryTransaction queryTransaction = (QueryTransaction) transaction;

        switch (queryTransaction.getType()) {
            case BEST_BID:
                configureQuery(getPriceAndSizeFromBids());
                return;
            case BEST_ASK:
                configureQuery(getPriceAndSizeFromAsks());
                return;
            default:
                Optional<Integer> sizeFromBids = Storage.reportMapBid.entrySet()
                        .stream().filter(e -> e.getKey() == queryTransaction.getPrice()).findFirst().map(Map.Entry::getValue);
                Optional<Integer> sizeFromAsks = Storage.reportMapAsk.entrySet()
                        .stream().filter(e -> e.getKey() == queryTransaction.getPrice()).findFirst().map(Map.Entry::getValue);
                if (sizeFromBids.isPresent()) {
                    query.append(sizeFromBids.get());
                } else if (sizeFromAsks.isPresent()) {
                    query.append(sizeFromAsks.get());
                }
        }
    }

    private int[] getPriceAndSizeFromBids() {
        int bestBid = Storage.reportMapBid.entrySet()
                .stream()
                .map(Map.Entry::getValue)
                .mapToInt(v -> v)
                .max().orElseThrow();
        int keyBestBid = Storage.reportMapBid.entrySet()
                .stream().filter(e -> e.getValue() == bestBid).findFirst().get().getKey();
        int[] bid = new int[] {keyBestBid, bestBid};
        return bid;
    }

    private int[] getPriceAndSizeFromAsks() {
        int bestAsk = Storage.reportMapAsk.entrySet()
                .stream()
                .map(Map.Entry::getValue)
                .mapToInt(v -> v).min()
                .orElseThrow();
        int keyBestAsk = Storage.reportMapAsk.entrySet()
                .stream().filter(e -> e.getValue() == bestAsk).findFirst().get().getKey();
        int[] ask = new int[] {keyBestAsk, bestAsk};
        return ask;
    }

    public void configureQuery(int[] priceAndSize) {
        query.append(priceAndSize[PRICE_POSITION])
                .append(PUNCTUATION_MARK)
                .append(priceAndSize[SIZE_POSITION])
                .append(NEW_LINE);
    }

    public String getQuery() {
        return query.toString().trim();
    }
}
