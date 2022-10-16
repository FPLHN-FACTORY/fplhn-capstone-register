package com.fpolydatn.core.daotao.service.impl;

import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.daotao.model.request.DtCreateImportMonDatnRequest;
import com.fpolydatn.core.daotao.model.request.DtCreateMonDatnRequest;
import com.fpolydatn.core.daotao.model.request.DtFindMonDatnRequest;
import com.fpolydatn.core.daotao.model.request.DtUpdateMonDatnRequest;
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
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
import java.util.concurrent.atomic.AtomicInteger;

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

    private HashMap<String, MonDatn> monDatnHashMap = new HashMap<>();

    private HashMap<String, ChuyenNganh> chuyenNganhHashMap = new HashMap<>();

    private HashMap<String, CoSo> coSoHashMap = new HashMap<>();

    @Override
    public PageableObject<DtMonDatnResponse> search(@Valid final DtFindMonDatnRequest req) {
        Pageable pageable = PageRequest.of(req.getPage(), req.getSize());
        Page<DtMonDatnResponse> res = monDatnRepository.findByName(req, pageable);
        return new PageableObject(res);
    }

    @Override
    public Boolean delete(final String id) {
        Optional<MonDatn> monCheck = monDatnRepository.findById(id);
        if (Objects.isNull(monCheck)) {
            throw new RestApiException(Message.MON_DATN_NOT_EXIST);
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
    public MonDatn update(@Valid final DtUpdateMonDatnRequest command) {
        Optional<MonDatn> monDatnCheck = monDatnRepository.findById(command.getId());
        MonDatn monDatnTr = formUtils.convertToObject(MonDatn.class, command);
        MonDatn monDatnInDb = monDatnRepository.findByMaMonUpdate(monDatnTr.getMaMon(), monDatnTr.getId());
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
    public List<String> importExcel(MultipartFile file) {
        // tạo bộ nhớ đệm
        boNhoDem();

        List<MonDatn> list = new ArrayList<>();
        List<String> returnList = new ArrayList<>();

        // check file xlsx
        if(!checkTypeFile(file)) {
            returnList.add("Không đúng định dạng file Excel");
            return returnList;
        }
        List<DtCreateImportMonDatnRequest> listCreate = importExcelList(file);


        listCreate.parallelStream().forEach(creatMonDant -> {
            int i = 0;
            if(monDatnHashMap.containsKey(creatMonDant.getMaMon())) {
                returnList.add(formErrors( "A" + (creatMonDant.getStt()), "Mã đã tồn tại"));
                i++;
            }

            if(i == 0) {
                MonDatn monDatn = new MonDatn();
                monDatn.setMaMon(creatMonDant.getMaMon());
                monDatn.setTenMonDatn(creatMonDant.getTenMonDatn());
                monDatn.setCoSoId(coSoHashMap.get(creatMonDant.getCoSoId()).getId());
                monDatn.setChuyenNganhId(chuyenNganhHashMap.get(creatMonDant.getChuyenNganhId()).getId());
                list.add(monDatn);
            }

        });

        if(list.size() != 0) {
            monDatnRepository.saveAll(list);
        }

        return returnList;
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

                // bắt lỗi dòng
                createMonDatn.setStt(rowNumber);

                Iterator<Cell> cells = row.iterator();

                int cid = 0;
                while (cells.hasNext()) {
                    Cell cell = cells.next();
                    switch (cid) {
                        case 1:
                            createMonDatn.setMaMon(String.valueOf(getCellValue(cell)).trim());
                            break;
                        case 2:
                            createMonDatn.setTenMonDatn(String.valueOf(getCellValue(cell)).trim());
                            break;
                        case 3:
                            createMonDatn.setChuyenNganhId(String.valueOf(getCellValue(cell)).trim());
                            break;
                        case 4:
                            createMonDatn.setCoSoId(String.valueOf(getCellValue(cell)).trim());
                            break;
                        case 5:
                            createMonDatn.setNhomMonDatnId(String.valueOf(getCellValue(cell)).trim());
                            break;
                        default:
                            break;
                    }
                    cid++;
                }
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

    public void boNhoDem() {
        // tạo bộ nhớ đệm Môn đồ án tốt nghiệp
        for (MonDatn x : monDatnRepository.findAll()) {
            monDatnHashMap.put(x.getMaMon(), x);
        }
        // tạo bộ nhớ đêm Chuyên ngành
        for (ChuyenNganh x : chuyenNganhRepository.findAll()) {
            chuyenNganhHashMap.put(x.getTenChuyenNganh(), x);
        }
        // tạo bộ nhớ đệm Cớ sở
        for (CoSo x : coSoRepository.findAll()) {
            coSoHashMap.put(x.getMaCoSo(), x);
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
