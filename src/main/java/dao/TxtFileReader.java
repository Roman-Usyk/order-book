package dao;

import java.util.List;
import model.Transaction;

public interface TxtFileReader {
    List<Transaction> readTransactions(String fromFileName);
}
