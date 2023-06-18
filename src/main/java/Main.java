import dao.TxtFileReader;
import dao.TxtFileReaderImpl;
import dao.TxtFileWriter;
import dao.TxtFileWriterImpl;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Operation;
import model.Transaction;
import service.OperationStrategy;
import service.OperationStrategyImpl;
import service.ReportService;
import service.ReportServiceImpl;
import service.TransactionService;
import service.TransactionServiceImpl;
import service.operation.OperationHandler;
import service.operation.OrderOperationHandler;
import service.operation.QueryOperationHandler;
import service.operation.UpdateOperationHandler;

public class Main {
    private static final String fromFileName = "src/main/resources/input.txt";
    private static final String toFileName = "src/main/resources/output.txt";
    private static final File toFile = new File(toFileName);
    private static final TxtFileReader txtFileReader = new TxtFileReaderImpl();
    private static final ReportService reportService = new ReportServiceImpl();
    private static final TxtFileWriter txtFileWriter = new TxtFileWriterImpl();

    public static void main(String[] args) {
        Map<Operation, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(Operation.UPDATE, new UpdateOperationHandler());
        operationHandlerMap.put(Operation.QUERY, new QueryOperationHandler());
        operationHandlerMap.put(Operation.ORDER, new OrderOperationHandler());

        List<Transaction> transactions = txtFileReader.readTransactions(fromFileName);

        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlerMap);

        TransactionService transactionService = new TransactionServiceImpl(operationStrategy);
        transactionService.processTransactions(transactions);

        String reportQuery = reportService.reportQuery();

        txtFileWriter.writeReportToTxtFile(reportQuery, toFile);
    }
}
