package dao;

import java.io.File;

public interface TxtFileWriter {
    void writeReportToTxtFile(String report, File toFile);
}
