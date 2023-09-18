package com.fpolydatn.util;

import com.fpolydatn.core.daotao.repository.DtDotDangKyRepository;
import com.fpolydatn.entity.DotDangKy;
import com.fpolydatn.infrastructure.constant.ConfigurationsConstant;
import com.fpolydatn.infrastructure.constant.SessionConstant;
import com.fpolydatn.infrastructure.logger.LoggerObject;
import com.fpolydatn.infrastructure.projection.SimpleEntityProj;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service
public class WriterFileCSV {

    @Autowired
    private HttpSession session;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private DtDotDangKyRepository dotDangKyRepository;

    public void createFileCSV(String pathFile) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(pathFile, true));
             CSVPrinter csvPrinter = new CSVPrinter(writer,
                     CSVFormat.DEFAULT.withHeader("Mail người thao tác", "Địa chỉ IP", "Ngày thực hiện", "Method", "Nội dung thực hiện", "Actor", "Trạng thái"))) {
            System.out.println("______ File CSV đã được tạo ______");
            csvPrinter.flush();
            csvPrinter.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public DotDangKy findDotDangKyById(String id) {
        return dotDangKyRepository.findById(id).get();
    }

    public String tenDotDangKyHienTai() { return session.getAttribute(SessionConstant.TEN_DOT_DANG_KY).toString(); }

    public void writerFileCSV(String content, String pathFile, String status) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(pathFile, true));
             CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT)) {
            csvPrinter.printRecord(session.getAttribute(SessionConstant.EMAIL) == null ? "" : session.getAttribute(SessionConstant.EMAIL).toString(),
                    httpServletRequest.getRemoteAddr(), new Date().toString(), httpServletRequest.getMethod(), content, switchAuthor(httpServletRequest.getRequestURI()), status);
            System.out.println("Nội dung file CSV: " + content);
            csvPrinter.flush();
            csvPrinter.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writerAllObjectFileCSV(List<LoggerObject> loggerObject, String pathFile) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(pathFile, true));
             CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT)) {
            for (LoggerObject el: loggerObject) {
                csvPrinter.printRecord(session.getAttribute(SessionConstant.EMAIL) == null ? "" : session.getAttribute(SessionConstant.EMAIL).toString(),
                        httpServletRequest.getRemoteAddr(), new Date().toString(), httpServletRequest.getMethod(), el.getContent(), switchAuthor(httpServletRequest.getRequestURI()), el.getStatus());
                System.out.println("Nội dung file CSV: " + el.getContent());
            }
            csvPrinter.flush();
            csvPrinter.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String switchAuthor(String api) {

        if (api.startsWith("/api/dao-tao")) {
            return "Đào tạo";
        }
        if (api.startsWith("/api/chu-nhiem")) {
            return "Chủ nhiệm";
        }
        if (api.startsWith("/api/giang-vien")) {
            return "Giảng viên";
        }
        if (api.startsWith("/api/sinh-vien")) {
            return "Sinh viên";
        }
        return "";
    }

    public List<LoggerObject> readFileCSV(String pathFile) {
        List<LoggerObject> list = new ArrayList<>();
        try (Reader reader = new FileReader(pathFile);
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);) {

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();
            for (CSVRecord csvRecord : csvRecords) {
                LoggerObject loggerObject = new LoggerObject();
                loggerObject.setMail(csvRecord.get(0));
                loggerObject.setIP(csvRecord.get(1));
                loggerObject.setCreateDate(csvRecord.get(2));
                loggerObject.setCreateDate(csvRecord.get(3));
                loggerObject.setContent(csvRecord.get(4));
                list.add(loggerObject);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public String covertFileName(String fileOld) {
        String chuoiKhongDau = Normalizer.normalize(fileOld, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
        String chuoiChuThuong = chuoiKhongDau.toLowerCase();
        String fileNew = chuoiChuThuong.replace(" ", "_");

        return fileNew;
    }

    public String getPathFileByDotDangKyAndCoSo(String tenDotDangKy) {
        List<SimpleEntityProj> listCoSo = (List<SimpleEntityProj>) session.getAttribute("listCoSo");
        String idCoSo = session.getAttribute(SessionConstant.CO_SO_ID).toString();
        String tenCoSo = "";
        Iterator<SimpleEntityProj> iterator = listCoSo.stream().filter(el -> el.getId().equals(idCoSo)).iterator();
        if (iterator.hasNext()) {
            tenCoSo = iterator.next().getName();
        }
        String path = getSwitchCoSo(tenCoSo) + covertFileName(tenDotDangKy) + "/";
        return path;
    }

    public String getPropertiesRead(String constant) {
        PropertiesReader po = new PropertiesReader();
        return po.getPropertyConfig(constant);
    }

    public String getSwitchCoSo(String coSo) {
        if (coSo.equalsIgnoreCase("Hà Nội")) {
            return getPropertiesRead(ConfigurationsConstant.PATH_FILE_CO_SO_HA_NOI);
        }
        if (coSo.equalsIgnoreCase("Hồ Chí Minh")) {
            return getPropertiesRead(ConfigurationsConstant.PATH_FILE_CO_SO_HCM);
        }
        if (coSo.equalsIgnoreCase("Cần Thơ")) {
            return getPropertiesRead(ConfigurationsConstant.PATH_FILE_CO_SO_CAN_THO);
        }
        if (coSo.equalsIgnoreCase("Đà Nẵng")) {
            return getPropertiesRead(ConfigurationsConstant.PATH_FILE_CO_SO_DA_NANG);
        }
        if (coSo.equalsIgnoreCase("Tây Nguyên")) {
            return getPropertiesRead(ConfigurationsConstant.PATH_FILE_CO_SO_TAY_NGUYEN);
        }
        return getPropertiesRead(ConfigurationsConstant.PATH_FILE_CO_SO_KHAC);
    }

    public boolean createFolder(String folderPath) {
        Path path = Paths.get(folderPath);

        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean updateFolder(String oldFolderPath, String newFolderPath) {
        String pathFileOld = getPathFileByDotDangKyAndCoSo(oldFolderPath);

        String pathFileNew = getPathFileByDotDangKyAndCoSo(newFolderPath);

        File oldFolder = new File(pathFileOld);

        File newFolder = new File(pathFileNew);

        if (oldFolder.exists() && !newFolder.exists()) {
            boolean renamed = oldFolder.renameTo(newFolder);
            if (renamed) {
                return true;
            }
        }
        return false;
    }

    public void genFolderLogger(String tenDoDangKy) {
        session.setAttribute(SessionConstant.TEN_DOT_DANG_KY, tenDoDangKy);
        String pathFile = getPathFileByDotDangKyAndCoSo(tenDoDangKy);
        if (createFolder(pathFile)) {
            createFileCSV(pathFile + getPropertiesRead(ConfigurationsConstant.NAME_FILE_DANG_KY_DATN));
            if (createFolder(pathFile + getPropertiesRead(ConfigurationsConstant.FOLDER_ACTOR_CAN_BO_DAO_TAO))) {
                createFileCSV(pathFile + getPropertiesRead(ConfigurationsConstant.FOLDER_ACTOR_CAN_BO_DAO_TAO) + getPropertiesRead(ConfigurationsConstant.NAME_FILE_CAN_BO));
                createFileCSV(pathFile + getPropertiesRead(ConfigurationsConstant.FOLDER_ACTOR_CAN_BO_DAO_TAO) + getPropertiesRead(ConfigurationsConstant.NAME_FILE_CHUYEN_NGANH));
                createFileCSV(pathFile + getPropertiesRead(ConfigurationsConstant.FOLDER_ACTOR_CAN_BO_DAO_TAO) + getPropertiesRead(ConfigurationsConstant.NAME_FILE_DOT_DANG_KY));
                createFileCSV(pathFile + getPropertiesRead(ConfigurationsConstant.FOLDER_ACTOR_CAN_BO_DAO_TAO) + getPropertiesRead(ConfigurationsConstant.NAME_FILE_GIANG_VIEN_HUONG_DAN));
                createFileCSV(pathFile + getPropertiesRead(ConfigurationsConstant.FOLDER_ACTOR_CAN_BO_DAO_TAO) + getPropertiesRead(ConfigurationsConstant.NAME_FILE_HOC_KY));
                createFileCSV(pathFile + getPropertiesRead(ConfigurationsConstant.FOLDER_ACTOR_CAN_BO_DAO_TAO) + getPropertiesRead(ConfigurationsConstant.NAME_FILE_MON_DATN));
                createFileCSV(pathFile + getPropertiesRead(ConfigurationsConstant.FOLDER_ACTOR_CAN_BO_DAO_TAO) + getPropertiesRead(ConfigurationsConstant.NAME_FILE_NHOM_DATN));
                createFileCSV(pathFile + getPropertiesRead(ConfigurationsConstant.FOLDER_ACTOR_CAN_BO_DAO_TAO) + getPropertiesRead(ConfigurationsConstant.NAME_FILE_SINH_VIEN));
            }
            if (createFolder(pathFile + getPropertiesRead(ConfigurationsConstant.FOLDER_ACTOR_CHU_NHIEM))) {
                createFileCSV(pathFile + getPropertiesRead(ConfigurationsConstant.FOLDER_ACTOR_CHU_NHIEM) + getPropertiesRead(ConfigurationsConstant.NAME_FILE_NHOM_DATN));
                createFileCSV(pathFile + getPropertiesRead(ConfigurationsConstant.FOLDER_ACTOR_CHU_NHIEM) + getPropertiesRead(ConfigurationsConstant.NAME_FILE_SINH_VIEN));
            }
            if (createFolder(pathFile + getPropertiesRead(ConfigurationsConstant.FOLDER_ACTOR_GIANG_VIEN))) {
                createFileCSV(pathFile + getPropertiesRead(ConfigurationsConstant.FOLDER_ACTOR_GIANG_VIEN) + getPropertiesRead(ConfigurationsConstant.NAME_FILE_NHOM_DATN));
            }
            if (createFolder(pathFile + getPropertiesRead(ConfigurationsConstant.FOLDER_ACTOR_SINH_VIEN))) {
                createFileCSV(pathFile + getPropertiesRead(ConfigurationsConstant.FOLDER_ACTOR_SINH_VIEN) + getPropertiesRead(ConfigurationsConstant.NAME_FILE_NHOM_DATN));
            }
        }
    }

//    public static String swit
}
