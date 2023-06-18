package dao;

import exception.DataProcessException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class TxtFileWriterImpl implements TxtFileWriter {
    private static final String ERROR_MESSAGE = "Can't write data to file ";

    @Override
    public void writeReportToTxtFile(String report, File toFile) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(toFile))) {
            bufferedWriter.write(report);
        } catch (IOException e) {
            throw new DataProcessException(ERROR_MESSAGE + toFile, e);
        }
    }
}

