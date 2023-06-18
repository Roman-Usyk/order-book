package dao;

import exception.DataProcessException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import model.Operation;
import model.OrderTransaction;
import model.QueryTransaction;
import model.Transaction;
import model.UpdateTransaction;

public class TxtFileReaderImpl implements TxtFileReader {
    private static final String ERROR_MESSAGE = "Can't read data from file ";
    private static final byte FIRST_COLUMN = 0;
    private static final byte SECOND_COLUMN = 1;
    private static final byte THIRD_COLUMN = 2;
    private static final byte FOURTH_COLUMN = 3;
    private static final String UPDATE = "u";
    private static final String QUERY = "q";

    @Override
    public List<Transaction> readTransactions(String fromFileName) {
        List<String> lines = new ArrayList<>();
        try {
            lines = Files.readAllLines(Path.of(fromFileName));
        } catch (IOException e) {
            throw new DataProcessException(ERROR_MESSAGE + fromFileName, e);
        }
        return lines
                .stream()
                .map(this::parseTransaction)
                .collect(Collectors.toList());
    }

    private Transaction parseTransaction(String line) {
        String[] columns = line.split(",");

        switch (columns[FIRST_COLUMN]) {
            case UPDATE :
                UpdateTransaction updateTransaction = new UpdateTransaction();
                updateTransaction.setOperation(Operation.getByCode(columns[FIRST_COLUMN]));
                updateTransaction.setPrice(Integer.parseInt(columns[SECOND_COLUMN]));
                updateTransaction.setSize(Integer.parseInt(columns[THIRD_COLUMN]));
                updateTransaction.setType(columns[FOURTH_COLUMN]);
                return updateTransaction;
            case QUERY :
                QueryTransaction queryTransaction = new QueryTransaction();
                queryTransaction.setOperation(Operation.getByCode(columns[FIRST_COLUMN]));
                queryTransaction.setType(columns[SECOND_COLUMN]);
                if (columns.length > 2) {
                    queryTransaction.setPrice(Integer.parseInt(columns[THIRD_COLUMN]));
                }
                return queryTransaction;
            default:
                OrderTransaction orderTransaction = new OrderTransaction();
                orderTransaction.setOperation(Operation.getByCode(columns[FIRST_COLUMN]));
                orderTransaction.setType(columns[SECOND_COLUMN]);
                orderTransaction.setSize(Integer.parseInt(columns[THIRD_COLUMN]));
                return orderTransaction;
        }
    }
}
