package com.fpolydatn.infrastructure.logger;

import com.fpolydatn.core.common.log.IDatnLogger;
import com.fpolydatn.core.common.log.LogObject;
import com.fpolydatn.core.common.log.LogObjectBuilder;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;

import static com.fpolydatn.util.StringUtil.removeDiacritics;

public class CsvLogger implements IDatnLogger {

    private final LogObjectBuilder builder;
    private final HttpServletRequest request;

    @Value("${log.csv.root}")
    private String logRoot;

    @Autowired
    public CsvLogger(LogObjectBuilder builder
        , HttpServletRequest request
    ) {
        this.builder = builder;
        this.request = request;
    }

    @Override
    public synchronized void log(LogObject data) {
        String filePath = getPath(data);
        write(filePath, data);
    }

    @Override
    public void log(String content, String status) {
        LogObject data = builder.build(content, status);
        log(data);
    }

    private void write(String filePath, LogObject value) {
        Path path = Paths.get(filePath);
        try (BufferedWriter writer = Files.newBufferedWriter(path);
             CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT)) {
                csvPrinter.printRecord(value.getMail(),
                        value.getIp(), value.getCreateDate(),
                        value.getHttpMethod(), value.getContent(),
                        value.getRole(), value.getLogStatus()
                );
            csvPrinter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getPath(LogObject data) {
        String folderCoSo = normalize(data.getCoSo());
        String folderDotDangKy = normalize(data.getTenDotDangKy());
        String folderRole = normalize(data.getRole());
        String folderFunction = normalize(data.getApi());

        Path path = Path.of(logRoot, folderCoSo, folderDotDangKy, folderRole, folderFunction);
        return path.toString() + ".csv";
    }

    private String normalize(String name) {
        return removeDiacritics(name)
                .toLowerCase(Locale.ROOT)
                .replaceAll("[^a-z0-9]", "_");
    }
}
