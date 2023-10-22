package com.fpolydatn.util;

import com.fpolydatn.core.daotao.repository.DtChuyenNganhRepository;
import com.fpolydatn.core.daotao.repository.DtDotDangKyRepository;
import com.fpolydatn.entity.ChuyenNganh;
import com.fpolydatn.entity.DotDangKy;
import com.fpolydatn.infrastructure.constant.ConfigurationsConstant;
import com.fpolydatn.infrastructure.constant.Message;
import com.fpolydatn.infrastructure.constant.SessionConstant;
import com.fpolydatn.infrastructure.exception.rest.RestApiException;
import com.fpolydatn.infrastructure.logger.LoggerObject;
import com.fpolydatn.infrastructure.projection.SimpleEntityProj;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
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
    private HttpServletResponse httpServletResponse;

    @Autowired
    private DtDotDangKyRepository dotDangKyRepository;

    @Autowired
    private DtChuyenNganhRepository chuyenNganhRepository;

    public ChuyenNganh findChuyenNganhById(String idChuyenNganh) {
        return chuyenNganhRepository.findById(idChuyenNganh).get();
    }

    public void createFileCSV(String pathFile) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(pathFile, true));
             CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT)) {
            System.out.println("______ File CSV đã được tạo với path: " + pathFile);
            csvPrinter.flush();
            csvPrinter.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createFileCSVLuong(String pathFile) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(pathFile, true));
             CSVPrinter csvPrinter = new CSVPrinter(writer,
                     CSVFormat.DEFAULT)) {
            System.out.println("______ File CSV đã được tạo với path: " + pathFile);
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

    public String tenDotDangKyHienTai() {
        if (session.getAttribute(SessionConstant.TEN_DOT_DANG_KY) == null) {
            return "";
        }
        return session.getAttribute(SessionConstant.TEN_DOT_DANG_KY).toString();
    }

    public void writerFileCSV(String content, String pathFile, String status) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(pathFile, true));
             CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT)) {
            csvPrinter.printRecord(session.getAttribute(SessionConstant.EMAIL) == null ? "" : session.getAttribute(SessionConstant.EMAIL).toString(),
                    httpServletRequest.getRemoteAddr(), new Date().toString(), httpServletRequest.getMethod(), content, switchAuthor(httpServletRequest.getRequestURI()), status);
            System.out.println("================= Nội dung file: " + content);
            csvPrinter.flush();
            csvPrinter.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writerFileBugCSV(LoggerObject bug) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(getPropertiesRead(ConfigurationsConstant.PATH_FILE_TEMPLATE) + "file_bug.csv", true));
             CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT)) {
            csvPrinter.printRecord(
                    // Email người thực hiện
                    bug.getMail()
                    ,
                    // địa chỉ ip
                    bug.getIP(),
                    // thời gian thực hiện
                    new Date().toString(),
                    // method
                    bug.getMethod(),
                    // api
                    bug.getApi(),
                    // nội dung
                    bug.getContent(),
                    // param
                    bug.getParam(),
                    // request body
                    bug.getRequest());
            csvPrinter.flush();
            csvPrinter.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writerFileCSV(LoggerObject loggerObject) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(loggerObject.getPathFile(), true));
             CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT)) {
            writerLoggerObjectIsNotData(loggerObject);
            csvPrinter.printRecord(loggerObject.getMail(),
                    loggerObject.getIP(), loggerObject.getCreateDate(), loggerObject.getMethod(), loggerObject.getContent()
                    , loggerObject.getAuthor(), loggerObject.getStatus(), loggerObject.getIdChuyenNganh(), loggerObject.getTenChuyenNganh());
            System.out.println("======================================================");
            System.out.println("================= Path file: " + loggerObject.getPathFile());
            System.out.println("================= Nội dung file: " + loggerObject.getContent());
            System.out.println("======================================================");
            csvPrinter.flush();
            csvPrinter.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writerFileCSVLuong(LoggerObject loggerObject) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(loggerObject.getPathFile(), true));
             CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT)) {
            writerLoggerObjectIsNotData(loggerObject);
            csvPrinter.printRecord(loggerObject.getMail(),
                    loggerObject.getIP(), loggerObject.getCreateDate(), loggerObject.getMethod(), loggerObject.getContent()
                    , loggerObject.getAuthor(), loggerObject.getStatus(), loggerObject.getTenNhom(), loggerObject.getIdChuyenNganh(), loggerObject.getTenChuyenNganh());
            System.out.println("======================================================");
            System.out.println("================= Path file: " + loggerObject.getPathFile());
            System.out.println("================= Nội dung file: " + loggerObject.getContent());
            System.out.println("======================================================");
            csvPrinter.flush();
            csvPrinter.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public LoggerObject createLoggerObject(String content, String pathFile, String status) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date currentDate = new Date();
        String formattedDate = simpleDateFormat.format(currentDate);

        LoggerObject loggerObject = new LoggerObject();
        loggerObject.setPathFile(pathFile);
        loggerObject.setContent(content);
        loggerObject.setStatus(status);
        loggerObject.setIP(httpServletRequest.getRemoteAddr());
        loggerObject.setMail(session.getAttribute(SessionConstant.EMAIL) == null ? "" : session.getAttribute(SessionConstant.EMAIL).toString());
        loggerObject.setCreateDate(formattedDate);
        loggerObject.setMethod(httpServletRequest.getMethod());
        loggerObject.setAuthor(switchAuthor(httpServletRequest.getRequestURI()));
        return loggerObject;
    }

    public LoggerObject writerLoggerObjectIsNotData(LoggerObject loggerObject) {

        if (loggerObject.getIdChuyenNganh() == null) {
            loggerObject.setIdChuyenNganh(ConfigurationsConstant.KHONG_CO_DU_LIEU);
        }
        if (loggerObject.getTenChuyenNganh() == null) {
            loggerObject.setTenChuyenNganh(ConfigurationsConstant.KHONG_CO_DU_LIEU);
        }
        if (loggerObject.getMail() == null) {
            loggerObject.setMail(ConfigurationsConstant.KHONG_CO_DU_LIEU);
        }
        if (loggerObject.getIP() == null) {
            loggerObject.setIP(ConfigurationsConstant.KHONG_CO_DU_LIEU);
        }
        if (loggerObject.getCreateDate() == null) {
            loggerObject.setCreateDate(ConfigurationsConstant.KHONG_CO_DU_LIEU);
        }
        if (loggerObject.getMethod() == null) {
            loggerObject.setMethod(ConfigurationsConstant.KHONG_CO_DU_LIEU);
        }
        if (loggerObject.getContent() == null) {
            loggerObject.setContent(ConfigurationsConstant.KHONG_CO_DU_LIEU);
        }
        if (loggerObject.getAuthor() == null) {
            loggerObject.setAuthor(ConfigurationsConstant.KHONG_CO_DU_LIEU);
        }
        if (loggerObject.getTenNhom() == null) {
            loggerObject.setTenNhom(ConfigurationsConstant.KHONG_CO_DU_LIEU);
        }
        return loggerObject;
    }

    public void writerAllObjectFileCSV(List<LoggerObject> loggerObject, String pathFile) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(pathFile, true));
             CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT)) {
            for (LoggerObject el : loggerObject) {
                writerLoggerObjectIsNotData(el);
                csvPrinter.printRecord(session.getAttribute(SessionConstant.EMAIL) == null ? "" : session.getAttribute(SessionConstant.EMAIL).toString(),
                        httpServletRequest.getRemoteAddr(), new Date().toString(), httpServletRequest.getMethod(), el.getContent(), switchAuthor(httpServletRequest.getRequestURI()), el.getStatus());
                System.out.println("================= Nội dung file: " + el.getContent());
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

    // Đọc file csv
    public List<LoggerObject> readFileCSV(LoggerObject object) {
        List<LoggerObject> list = new ArrayList<>();
        try (Reader reader = new FileReader(object.getPathFile());
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);) {

            List<CSVRecord> csvRecords = csvParser.getRecords();
            int dem = csvRecords.size();
            for (CSVRecord csvRecord : csvRecords) {
                LoggerObject loggerObject = new LoggerObject();
                loggerObject.setMail(csvRecord.get(0));
                loggerObject.setIP(csvRecord.get(1));
                loggerObject.setCreateDate(csvRecord.get(2));
                loggerObject.setMethod(csvRecord.get(3));
                loggerObject.setContent(csvRecord.get(4));
                loggerObject.setAuthor(csvRecord.get(5));
                loggerObject.setStatus(csvRecord.get(6));
                loggerObject.setIdChuyenNganh(csvRecord.get(7));
                loggerObject.setTenChuyenNganh(csvRecord.get(8));
                if (object.getIdChuyenNganh() != null) {
                    if (!loggerObject.getIdChuyenNganh().equals(object.getIdChuyenNganh())) {
                        break;
                    }
                }
                loggerObject.setStt(dem);
                list.add(loggerObject);
                dem--;
            }
            Collections.reverse(list);
        } catch (FileNotFoundException e) {
            throw new RestApiException(Message.FILE_LOG_NOT_EXISTS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<LoggerObject> readFileCSVByListChuyenNganh(LoggerObject object, List<String> listIdChuyenNganh) {
        List<LoggerObject> list = new ArrayList<>();
        try (Reader reader = new FileReader(object.getPathFile());
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);) {

            List<CSVRecord> csvRecords = csvParser.getRecords();
            int dem = csvRecords.size();
            if (listIdChuyenNganh != null && listIdChuyenNganh.size() > 0) {
                for (CSVRecord csvRecord : csvRecords) {
                    LoggerObject loggerObject = new LoggerObject();
                    loggerObject.setMail(csvRecord.get(0));
                    loggerObject.setIP(csvRecord.get(1));
                    loggerObject.setCreateDate(csvRecord.get(2));
                    loggerObject.setMethod(csvRecord.get(3));
                    loggerObject.setContent(csvRecord.get(4));
                    loggerObject.setAuthor(csvRecord.get(5));
                    loggerObject.setStatus(csvRecord.get(6));
                    if (!listIdChuyenNganh.contains(csvRecord.get(7))) {
                        continue;
                    }
                    loggerObject.setStt(dem);
                    list.add(loggerObject);
                    dem--;
                }
            }
            Collections.reverse(list);
        } catch (FileNotFoundException e) {
            throw new RestApiException(Message.FILE_LOG_NOT_EXISTS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Đọc file csv
    public List<LoggerObject> readFileCSVLuong(LoggerObject object) {
        List<LoggerObject> list = new ArrayList<>();

        try (Reader reader = new FileReader(object.getPathFile());
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);) {

            List<CSVRecord> csvRecords = csvParser.getRecords();
            int dem = 0;
            Collections.reverse(csvRecords);

            for (CSVRecord csvRecord : csvRecords) {

                LoggerObject loggerObject = new LoggerObject();
                loggerObject.setMail(csvRecord.get(0));
                loggerObject.setIP(csvRecord.get(1));
                loggerObject.setCreateDate(csvRecord.get(2));
                loggerObject.setMethod(csvRecord.get(3));
                loggerObject.setContent(csvRecord.get(4));
                loggerObject.setAuthor(csvRecord.get(5));
                loggerObject.setStatus(csvRecord.get(6));
                loggerObject.setTenNhom(csvRecord.get(7));
                loggerObject.setIdChuyenNganh(csvRecord.get(8));
                loggerObject.setTenChuyenNganh(csvRecord.get(9));
                if (object.getTenNhom() != null && object.getTenNhom().trim().length() != 0) {
                    if (!object.getTenNhom().equals(loggerObject.getTenNhom())) {
                        continue;
                    }
                }
                if (object.getIdChuyenNganh() != null) {
                    if (!loggerObject.getIdChuyenNganh().equals(object.getIdChuyenNganh())) {
                        continue;
                    }
                }
                dem++;
                loggerObject.setStt(dem);
                list.add(loggerObject);
            }
        } catch (FileNotFoundException e) {
            throw new RestApiException(Message.FILE_LOG_NOT_EXISTS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<LoggerObject> readFileCSVLuongChuNhiem(LoggerObject object, List<String> listIdChuyenNganh) {
        List<LoggerObject> list = new ArrayList<>();

        try (Reader reader = new FileReader(object.getPathFile());
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);) {

            List<CSVRecord> csvRecords = csvParser.getRecords();
            int dem = 0;
            Collections.reverse(csvRecords);
            if (listIdChuyenNganh != null && listIdChuyenNganh.size() > 0) {
                for (CSVRecord csvRecord : csvRecords) {

                    LoggerObject loggerObject = new LoggerObject();
                    loggerObject.setMail(csvRecord.get(0));
                    loggerObject.setIP(csvRecord.get(1));
                    loggerObject.setCreateDate(csvRecord.get(2));
                    loggerObject.setMethod(csvRecord.get(3));
                    loggerObject.setContent(csvRecord.get(4));
                    loggerObject.setAuthor(csvRecord.get(5));
                    loggerObject.setStatus(csvRecord.get(6));
                    loggerObject.setTenNhom(csvRecord.get(7));
                    loggerObject.setIdChuyenNganh(csvRecord.get(8));
                    loggerObject.setTenChuyenNganh(csvRecord.get(9));
                    if (object.getTenNhom() != null && object.getTenNhom().trim().length() != 0) {
                        if (!object.getTenNhom().equals(loggerObject.getTenNhom())) {
                            continue;
                        }
                    }
                    if (!listIdChuyenNganh.contains(csvRecord.get(8))) {
                        continue;
                    }
                    dem++;
                    loggerObject.setStt(dem);
                    list.add(loggerObject);
                }
            }
        } catch (FileNotFoundException e) {
            throw new RestApiException(Message.FILE_LOG_NOT_EXISTS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public String covertFileName(String fileOld) {
        String chuoiKhongDau = removeDiacritics(fileOld);
        String chuoiChuThuong = chuoiKhongDau.toLowerCase();
        String output = chuoiChuThuong.replaceAll("[^a-zA-Z0-9]", "_");
        String fileNew = output.replace(" ", "_");

        return fileNew;
    }

    public static String removeDiacritics(String text) {
        text = text.replaceAll("[àáạảãâầấậẩẫăằắặẳẵ]", "a")
                .replaceAll("[èéẹẻẽêềếệểễ]", "e")
                .replaceAll("[ìíịỉĩ]", "i")
                .replaceAll("[òóọỏõôồốộổỗơờớợởỡ]", "o")
                .replaceAll("[ùúụủũưừứựửữ]", "u")
                .replaceAll("[ỳýỵỷỹ]", "y")
                .replaceAll("[đ]", "d")
                .replaceAll("[ÀÁẠẢÃÂẦẤẬẨẪĂẰẮẶẲẴ]", "A")
                .replaceAll("[ÈÉẸẺẼÊỀẾỆỂỄ]", "E")
                .replaceAll("[ÌÍỊỈĨ]", "I")
                .replaceAll("[ÒÓỌỎÕÔỒỐỘỔỖƠỜỚỢỞỠ]", "O")
                .replaceAll("[ÙÚỤỦŨƯỪỨỰỬỮ]", "U")
                .replaceAll("[ỲÝỴỶỸ]", "Y")
                .replaceAll("[Đ]", "D");
        return text;
    }

    // trỏ đến đường dẫn folder /cơ sở/đợt đăng ký
    public String getPathFileByDotDangKyAndCoSo(String tenDotDangKy) {
        String path = getSwitchCoSo() + covertFileName(tenDotDangKy) + "/";
        return path;
    }

    // trỏ đến file ghi luông nhóm datn
    public String getPathFileLuongNhomDatn(String tenDotDangKy) {
        return getPathFileByDotDangKyAndCoSo(tenDotDangKy) + getPropertiesRead(ConfigurationsConstant.NAME_FILE_DANG_KY_DATN);
    }

    public String getPathFileLogBug() {
        return getPropertiesRead(ConfigurationsConstant.PATH_FILE_TEMPLATE) + "file_bug.csv";
    }

    public String getPropertiesRead(String constant) {
        PropertiesReader po = new PropertiesReader();
        return po.getPropertyConfig(constant);
    }

    // trỏ đến đường dẫn đến folder /cơ sở
    public String getSwitchCoSo() {
        List<SimpleEntityProj> listCoSo = (List<SimpleEntityProj>) session.getAttribute("listCoSo");

        String idCoSo = session.getAttribute(SessionConstant.CO_SO_ID).toString();
        String coSo = "";
        Iterator<SimpleEntityProj> iterator = listCoSo.stream().filter(el -> el.getId().equals(idCoSo)).iterator();
        if (iterator.hasNext()) {
            coSo = iterator.next().getName();
        }

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

    // tạo mới một folder lưu
    public boolean createFolder(String folderPath) {
        Path path = Paths.get(folderPath);

        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
                System.out.println("================= Tạo mới folder thành công với path: " + path);
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
            createFileCSVLuong(pathFile + getPropertiesRead(ConfigurationsConstant.NAME_FILE_DANG_KY_DATN));
            if (createFolder(pathFile + getPropertiesRead(ConfigurationsConstant.FOLDER_ACTOR_CAN_BO_DAO_TAO))) {
                createFileCSV(pathFile + getPropertiesRead(ConfigurationsConstant.FOLDER_ACTOR_CAN_BO_DAO_TAO) + getPropertiesRead(ConfigurationsConstant.NAME_FILE_GIANG_VIEN_HUONG_DAN));
                createFileCSV(pathFile + getPropertiesRead(ConfigurationsConstant.FOLDER_ACTOR_CAN_BO_DAO_TAO) + getPropertiesRead(ConfigurationsConstant.NAME_FILE_NHOM_DATN));
                createFileCSV(pathFile + getPropertiesRead(ConfigurationsConstant.FOLDER_ACTOR_CAN_BO_DAO_TAO) + getPropertiesRead(ConfigurationsConstant.NAME_FILE_SINH_VIEN));
                createFileCSV(pathFile + getPropertiesRead(ConfigurationsConstant.FOLDER_ACTOR_CAN_BO_DAO_TAO) + getPropertiesRead(ConfigurationsConstant.NAME_FILE_PHAN_CONG_HUONG_DAN));
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

    public String getPathFileActiveWithApi(String tenDotDangKy) {
        String api = httpServletRequest.getRequestURI();
        String folder = "";
        String pathFile = "";
        if (api.startsWith("/api/dao-tao")) {
            folder = getPathFileByDotDangKyAndCoSo(tenDotDangKy) + getPropertiesRead(ConfigurationsConstant.FOLDER_ACTOR_CAN_BO_DAO_TAO);
            if (api.contains("/can-bo")) {
                pathFile = getSwitchCoSo() + getPropertiesRead(ConfigurationsConstant.NAME_FILE_CAN_BO);
            }

            if (api.contains("/chuyen-nganh")) {
                pathFile = getSwitchCoSo() + getPropertiesRead(ConfigurationsConstant.NAME_FILE_CHUYEN_NGANH);
            }

            if (api.contains("/dot-dang-ky")) {
                pathFile = getSwitchCoSo() + getPropertiesRead(ConfigurationsConstant.NAME_FILE_DOT_DANG_KY);
            }

            if (api.contains("/nhom-mon-datn")) {
                pathFile = getSwitchCoSo() + getPropertiesRead(ConfigurationsConstant.NAME_FILE_NHOM_MON_DATN);
            }

            if (api.contains("/giang-vien-huong-dan")) {
                pathFile = folder + getPropertiesRead(ConfigurationsConstant.NAME_FILE_GIANG_VIEN_HUONG_DAN);
            }

            if (api.contains("/hoc-ky")) {
                pathFile = getSwitchCoSo() + getPropertiesRead(ConfigurationsConstant.NAME_FILE_HOC_KY);
            }

            if (api.contains("/mon-datn")) {
                pathFile = getSwitchCoSo() + getPropertiesRead(ConfigurationsConstant.NAME_FILE_MON_DATN);
            }

            if (api.contains("/nhom-datn")) {
                pathFile = folder + getPropertiesRead(ConfigurationsConstant.NAME_FILE_NHOM_DATN);
            }

            if (api.contains("/sinh-vien")) {
                pathFile = folder + getPropertiesRead(ConfigurationsConstant.NAME_FILE_SINH_VIEN);
            }

        }

        if (api.startsWith("/api/giang-vien")) {
            folder = getPathFileByDotDangKyAndCoSo(tenDotDangKy) + getPropertiesRead(ConfigurationsConstant.FOLDER_ACTOR_GIANG_VIEN);
            if (api.contains("/nhom-datn")) {
                pathFile = folder + getPropertiesRead(ConfigurationsConstant.NAME_FILE_NHOM_DATN);
            }
        }

        if (api.startsWith("/api/chu-nhiem")) {
            folder = getPathFileByDotDangKyAndCoSo(tenDotDangKy) + getPropertiesRead(ConfigurationsConstant.FOLDER_ACTOR_CHU_NHIEM);
            if (api.contains("/sinh-vien")) {
                pathFile = folder + getPropertiesRead(ConfigurationsConstant.NAME_FILE_SINH_VIEN);
            }

            if (api.contains("/nhom-datn")) {
                pathFile = folder + getPropertiesRead(ConfigurationsConstant.NAME_FILE_NHOM_DATN);
            }

        }

        if (api.startsWith("/api/sinh-vien")) {
            folder = getPathFileByDotDangKyAndCoSo(tenDotDangKy) + getPropertiesRead(ConfigurationsConstant.FOLDER_ACTOR_SINH_VIEN);
            pathFile = folder + getPropertiesRead(ConfigurationsConstant.NAME_FILE_NHOM_DATN);
        }


        System.out.println("========= pathFile: " + pathFile);
        return pathFile;
    }

//    public File downloadFileLog(String pathFile) throws ZipException {
//        ZipFile zipFile = new ZipFile(pathFile + "download_log.zip");
//        zipFile.addFolder(new File(pathFile));
//        File file = new File(pathFile + "download_log.zip");
//        return file;
//    }
}
