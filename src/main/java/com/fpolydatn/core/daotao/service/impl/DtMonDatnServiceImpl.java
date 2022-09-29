package com.fpolydatn.core.daotao.service.impl;

import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.daotao.model.request.DtCreateMonDatnRequest;
import com.fpolydatn.core.daotao.model.request.DtFindMonDatnRequest;
import com.fpolydatn.core.daotao.model.request.DtUpdateMonDatnRequest;
import com.fpolydatn.core.daotao.model.response.DtMonDatnResponse;
import com.fpolydatn.core.daotao.repository.DtChuyenNganhRepository;
import com.fpolydatn.core.daotao.repository.DtCoSoRepository;
import com.fpolydatn.core.daotao.repository.DtMonDatnRepository;
import com.fpolydatn.core.daotao.service.DtMonDatnService;
import com.fpolydatn.entity.MonDatn;
import com.fpolydatn.infrastructure.constant.Message;
import com.fpolydatn.infrastructure.exception.rest.RestApiException;
import com.fpolydatn.util.FormUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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

    public List<?> convertExcelToList(MultipartFile is) {
        List<MonDatn> list = new ArrayList<>();

        try {
            XSSFWorkbook workbook = new XSSFWorkbook(is.getInputStream());
            XSSFSheet sheet = workbook.getSheetAt(0);
            int rowNumber = 0;
            Iterator<Row> iterator = sheet.iterator();

            while (iterator.hasNext()) {
                Row row = iterator.next();

                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }

                Iterator<Cell> cells = row.iterator();

                int cid = 0;
                MonDatn monDatn = new MonDatn();
                while (cells.hasNext()) {
                    Cell cell = cells.next();

                    switch (cid) {
                        case 1:
                            String ma = cell.getStringCellValue();
                            monDatn.setMaMon(ma);
                            break;
                        case 2:
                            String ten = cell.getStringCellValue();
                            monDatn.setTenMonDatn(ten);
                            break;
                        case 3:
                            String chuyenNganh = cell.getStringCellValue().trim();
                            monDatn.setChuyenNganhId(this.chuyenNganhRepository.findName(chuyenNganh).getId());
                            break;
                        case 4:
                            String coSo = cell.getStringCellValue().trim();
                            monDatn.setCoSoId(this.coSoRepository.findName(coSo).getId());
                            break;
                        default:
                            break;
                    }
                    cid++;

                }
                list.add(monDatn);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        ;
        return list;
    }

    @Override
    public Boolean importExcel(MultipartFile file) {
        List<MonDatn> list = (List<MonDatn>) convertExcelToList(file);
        if (list == null) {
            return false;
        }
        monDatnRepository.saveAll(list);
        return true;
    }

}
