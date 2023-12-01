package com.fpolydatn.core.admin.controller;

import com.fpolydatn.util.WriterFileCSV;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author thangncph26123
 */
@RestController
@RequestMapping("/api/admin/bit-dev")
public class HomeRestController {

    @Autowired
    private WriterFileCSV writerFileCSV;

    @GetMapping("/download-log-500")
    public ResponseEntity<Resource> downloadCsvFileLog500() throws IOException {

        String pathFile = writerFileCSV.getPathFile500();

        File file = new File(pathFile);

        if (!file.exists()) {
            return ResponseEntity.notFound().build();
        }

        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + resource.getFilename());

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    @DeleteMapping("/delete-log-500")
    public ResponseEntity<String> deleteLog500() {
        String logFilePath = writerFileCSV.getPathFile500();

        File fileToDelete = new File(logFilePath);

        if (fileToDelete.exists()) {
            if (fileToDelete.delete()) {
                return ResponseEntity.ok("Tệp log đã được xóa thành công.");
            } else {
                return ResponseEntity.status(500).body("Không thể xóa tệp log.");
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/download-log-bad-request")
    public ResponseEntity<Resource> downloadCsvFileLogBadRequest() throws IOException {

        String pathFile = writerFileCSV.getPathFileBadRequest();

        File file = new File(pathFile);

        if (!file.exists()) {
            return ResponseEntity.notFound().build();
        }

        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + resource.getFilename());

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    @DeleteMapping("/delete-log-bad-request")
    public ResponseEntity<String> deleteLogBadRequest() {
        String logFilePath = writerFileCSV.getPathFileBadRequest();

        File fileToDelete = new File(logFilePath);

        if (fileToDelete.exists()) {
            if (fileToDelete.delete()) {
                return ResponseEntity.ok("Tệp log đã được xóa thành công.");
            } else {
                return ResponseEntity.status(500).body("Không thể xóa tệp log.");
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
