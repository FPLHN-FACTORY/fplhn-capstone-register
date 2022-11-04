package com.fpolydatn.core.daotao.service.impl;

import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.daotao.model.request.DtCreateImportMonDatnRequest;
import com.fpolydatn.core.daotao.model.request.DtCreateMonDatnRequest;
import com.fpolydatn.core.daotao.model.request.DtFindMonDatnRequest;
import com.fpolydatn.core.daotao.model.request.DtUpdateMonDatnRequest;
import com.fpolydatn.core.daotao.model.response.DtMessMonDatnReponse;
import com.fpolydatn.core.daotao.model.response.DtMonDatnResponse;
import com.fpolydatn.core.daotao.repository.DtChuyenNganhRepository;
import com.fpolydatn.core.daotao.repository.DtCoSoRepository;
import com.fpolydatn.core.daotao.repository.DtMonDatnRepository;
import com.fpolydatn.core.daotao.service.DtMonDatnService;
import com.fpolydatn.entity.ChuyenNganh;
import com.fpolydatn.entity.CoSo;
import com.fpolydatn.entity.MonDatn;
import com.fpolydatn.infrastructure.constant.Message;
import com.fpolydatn.infrastructure.exception.rest.RestApiException;
import com.fpolydatn.util.FormUtils;
import com.github.pjfanning.xlsx.StreamingReader;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * @author hungpv
 */
@Service
@Validated
public class DtMonDatnServiceImpl implements DtMonDatnService {
    @Autowired
    private DtMonDatnRepository monDatnRepository;

    @Autowired
    private DtChuyenNganhRepository chuyenNganhRepository;

    @Autowired
    private DtCoSoRepository coSoRepository;

    private FormUtils formUtils = new FormUtils();

    private HashMap<String, MonDatn> monDatnHashMap;

    private HashMap<String, ChuyenNganh> chuyenNganhHashMap;

    private HashMap<String, MonDatn> nhomMonDatnHashMap;

    private List<String> returnList = new ArrayList<>();

    @Override
    public PageableObject<DtMonDatnResponse> search(@Valid final DtFindMonDatnRequest req) {
        Pageable pageable = PageRequest.of(req.getPage(), req.getSize());
        Page<DtMonDatnResponse> res = monDatnRepository.findByName(req, pageable);
        return new PageableObject(res);
    }

    @Override
    public Boolean delete(final String id, String coSoId, String dotDangKyId) {
        Optional<MonDatn> monCheck = monDatnRepository.findById(id);
        List<MonDatn> checkIdMon = monDatnRepository.findByIdMon(id, coSoId, dotDangKyId);
        if (Objects.isNull(monCheck)) {
            throw new RestApiException(Message.MON_DATN_NOT_EXIST);
        }
        if (checkIdMon.size() != 0) {
            throw new RestApiException(Message.KHONG_DUOC_XOA_MON);
        }
        monDatnRepository.delete(monCheck.get());
        return true;
    }

    @Override
    public MonDatn findById(String id) {
        Optional<MonDatn> monCheck = monDatnRepository.findById(id);
        if (Objects.isNull(monCheck)) {
            throw new RestApiException(Message.MON_DATN_NOT_EXIST);
        }
        return monCheck.get();
    }

    @Override
    public List<MonDatn> findByTenNhomMon() {
        return monDatnRepository.findByTenNhomMon();
    }

    @Override
    public MonDatn update(@Valid final DtUpdateMonDatnRequest command) {
        Optional<MonDatn> monDatnCheck = monDatnRepository.findById(command.getId());
        MonDatn monDatnTr = formUtils.convertToObject(MonDatn.class, command);
        String monDatnInDb = monDatnRepository.findByMaMonUpdate(monDatnTr.getMaMon(), monDatnTr.getId());
        if (!monDatnCheck.isPresent()) {
            throw new RestApiException(Message.MON_DATN_NOT_EXIST);
        }
        if (!Objects.isNull(monDatnInDb)) {
            throw new RestApiException(Message.MON_DATN_ALREADY_EXIST);
        }
        MonDatn monDatn = monDatnCheck.get();
        monDatn.setMaMon(command.getMaMon());
        monDatn.setTenMonDatn(command.getTenMonDatn());
        return monDatnRepository.save(monDatn);
    }

    @Override
    public Boolean add(@Valid final DtCreateMonDatnRequest command) {
        MonDatn monDatn = formUtils.convertToObject(MonDatn.class, command);
        MonDatn monDatnInDb = monDatnRepository.findByMaMon(monDatn.getMaMon());
        if (!Objects.isNull(monDatnInDb)) {
            throw new RestApiException(Message.MON_DATN_ALREADY_EXIST);
        } else {
            monDatnRepository.save(monDatn);
            return true;
        }
    }

    @Override
    public DtMessMonDatnReponse importExcel(MultipartFile file, String coSoId) {
        monDatnHashMap = new HashMap<>();
        chuyenNganhHashMap = new HashMap<>();
        nhomMonDatnHashMap = new HashMap<>();
        returnList.clear();
        // tạo bộ nhớ đệm
        boNhoDem(coSoId);

        // check file xlsx
        if (!checkTypeFile(file)) {
            returnList.add("file không đúng định dạng XLSX");
            return new DtMessMonDatnReponse(false, returnList);
        }

        List<DtCreateImportMonDatnRequest> importMonDatnRequests = importExcelList(file);

        // đưa ra môn
        List<MonDatn> list = checkImport(importMonDatnRequests, coSoId);


        // import in database
        if (list.size() != 0 && list.size() == importMonDatnRequests.size()) {
            monDatnRepository.saveAll(list);
        }
        if(returnList.size() ==0) {
            return new DtMessMonDatnReponse(true, null);
        }

        // trả về thông báo
        return new DtMessMonDatnReponse(false, returnList);
    }

    private List<MonDatn> checkImport(List<DtCreateImportMonDatnRequest> list, String coSoId) {
        List<MonDatn> traVe = new ArrayList<>();

        list.parallelStream().forEach(createMonDant -> {
            int check = 0;
            // check mã môn
            if (monDatnHashMap.containsKey(createMonDant.getMaMon())) {
                returnList.add(formErrors("B" + (createMonDant.getStt() + 1), "Mã đã tồn tại"));
                check ++;
            } else if (createMonDant.getMaMon().trim().length() == 0) {
                returnList.add(formErrors("B" + (createMonDant.getStt() + 1), "Mã không được để trống"));
                check ++;
            } else if (createMonDant.getMaMon().trim().length() > 10) {
                returnList.add(formErrors("B" + (createMonDant.getStt() + 1), "Mã không được quá 10 ký tự"));
                check ++;
            } else if (createMonDant.getMaMon().indexOf(" ") > 0) {
                returnList.add(formErrors("B" + (createMonDant.getStt() + 1), "Không được để khoảng trắng"));
                check ++;
            }

            // check tên môn
            if (createMonDant.getTenMonDatn().trim().length() == 0) {
                returnList.add(formErrors("C" + (createMonDant.getStt() + 1), "Tên môn không được để trống"));
                check++;
            } else if (createMonDant.getTenMonDatn().trim().length() > 100) {
                returnList.add(formErrors("C" + (createMonDant.getStt() + 1), "Tên môn không được quá 100 ký tự"));
                check++;
            }

            // check mã chuyên ngành
            if (createMonDant.getChuyenNganhId().trim().length() == 0) {
                returnList.add(formErrors("D" + (createMonDant.getStt() + 1), "Tên chuyên ngành không được để trống"));
                check++;
            } else if (!chuyenNganhHashMap.containsKey(createMonDant.getChuyenNganhId())) {
                returnList.add(formErrors("D" + (createMonDant.getStt() + 1), "Không tìm thấy tên chuyên ngành"));
                check++;
            } else if (createMonDant.getChuyenNganhId().trim().length() > 36) {
                returnList.add(formErrors("D" + (createMonDant.getStt() + 1), "Tên chuyên ngành không được quá 36 ký tự"));
                check++;
            }

            // check mã Nhóm môn
            if (createMonDant.getNhomMonDatnId().trim().length() == 0) {
                returnList.add(formErrors("F" + (createMonDant.getStt() + 1), "Mã nhóm môn không được để trống"));
                check++;
            } else if (createMonDant.getNhomMonDatnId().trim().length() > 10) {
                returnList.add( formErrors("F" + (createMonDant.getStt() + 1), "Mã nhóm không được quá 10 ký tự"));
                check++;
            } else if(!nhomMonDatnHashMap.containsKey(createMonDant.getNhomMonDatnId()) && !createMonDant.getMaMon().equals(createMonDant.getNhomMonDatnId())) {
                returnList.add( formErrors("F" + (createMonDant.getStt() + 1), "Không tìm thấy mã nhóm môn"));
                check++;
            }

            if (check==0) {
                MonDatn monDatn = new MonDatn();
                monDatn.setMaMon(createMonDant.getMaMon());
                monDatn.setTenMonDatn(createMonDant.getTenMonDatn());
                monDatn.setCoSoId(coSoId);
                monDatn.setChuyenNganhId(chuyenNganhHashMap.get(createMonDant.getChuyenNganhId()).getId());
                monDatn.setNhomMonDatnId(nhomMonDatnHashMap.get(createMonDant.getNhomMonDatnId()).getId());
                monDatnHashMap.put(monDatn.getMaMon(), monDatn);
                traVe.add(monDatn);
            }

        });
        return traVe;
    }

    public String formErrors(String vtr, String err) {
        return "Lỗi tại vị trí " + vtr + " . Lỗi -> " + err;
    }

    public List<DtCreateImportMonDatnRequest> importExcelList(MultipartFile file) {
        List<DtCreateImportMonDatnRequest> list = new ArrayList<>();

        try {
            Workbook workbook = StreamingReader.builder()
                    .bufferSize(4096)
                    .rowCacheSize(50)
                    .open(file.getInputStream());
            Sheet sheet = workbook.getSheetAt(0);
            int rowNumber = 0;
            Iterator<Row> iterator = sheet.iterator();

            while (iterator.hasNext()) {
                Row row = iterator.next();

                DtCreateImportMonDatnRequest createMonDatn = new DtCreateImportMonDatnRequest();
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }

                createMonDatn.setStt(row.getRowNum());
                createMonDatn.setMaMon(String.valueOf(getCellValue(row.getCell(1))).trim());
                createMonDatn.setTenMonDatn(String.valueOf(getCellValue(row.getCell(2))).trim());
                createMonDatn.setChuyenNganhId(String.valueOf(getCellValue(row.getCell(3))).trim());
                createMonDatn.setNhomMonDatnId(String.valueOf(getCellValue(row.getCell(4))).trim());

                list.add(createMonDatn);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public boolean checkTypeFile(MultipartFile file) {
        String filename = file.getResource().getFilename();
        String extension = filename.substring(filename.lastIndexOf(".") + 1, filename.length());

        String excel = "xlsx";
        if (!extension.equals(excel)) {
            return false;
        }
        return true;
    }

    public void boNhoDem(String coSoId) {
        // tạo bộ nhớ đệm Môn đồ án tốt nghiệp
        for (MonDatn x : monDatnRepository.getListMonDatnByIdCoSo(coSoId)) {
            monDatnHashMap.put(x.getMaMon(), x);
        }
        // tạo bộ nhớ đêm Chuyên ngành
        for (ChuyenNganh x : chuyenNganhRepository.getAllByIdCoSo(coSoId)) {
            chuyenNganhHashMap.put(x.getTenChuyenNganh(), x);
        }

        // tạo bộ nhó đệm nhóm môn
        for (MonDatn e: monDatnRepository.getListNhomMonDatn(coSoId)) {
            nhomMonDatnHashMap.put(e.getMaMon(), e);
        }

    }

    private static Object getCellValue(Cell cell) {
        try {
            switch (cell.getCellType()) {
                case NUMERIC -> {
                    return cell.getNumericCellValue();
                }
                case BOOLEAN -> {
                    return cell.getBooleanCellValue();
                }
                default -> {
                    return cell.getStringCellValue();
                }

            }
        } catch (Exception e) {
            return "";
        }
    }

}
