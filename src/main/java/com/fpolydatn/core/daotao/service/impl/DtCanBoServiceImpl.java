package com.fpolydatn.core.daotao.service.impl;

import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.daotao.model.request.DtCreateCanBoRequest;
import com.fpolydatn.core.daotao.model.request.DtFindCanBoRequest;
import com.fpolydatn.core.daotao.model.request.DtUpdateCanBoRequest;
import com.fpolydatn.core.daotao.model.response.DtCanBoResponse;
import com.fpolydatn.core.daotao.repository.DtCanBoRepository;
import com.fpolydatn.core.daotao.repository.DtCoSoRepository;
import com.fpolydatn.core.daotao.service.DtCanBoService;
import com.fpolydatn.entity.CanBo;
import com.fpolydatn.entity.CoSo;
import com.fpolydatn.infrastructure.constant.Message;
import com.fpolydatn.infrastructure.constant.VaiTro;
import com.fpolydatn.infrastructure.exception.rest.RestApiException;
import com.fpolydatn.infrastructure.projection.SimpleEntityProj;
import com.fpolydatn.util.FormUtils;
import org.apache.poi.xssf.usermodel.XSSFRow;
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
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author nguyenvv4
 */
@Service
@Validated
public class DtCanBoServiceImpl implements DtCanBoService {

    @Autowired
    private DtCanBoRepository dtCanBoRepository;

    @Autowired
    private DtCoSoRepository dtCoSoRepository;

    private FormUtils formUtils = new FormUtils();

    @Override
    public PageableObject<DtCanBoResponse> search(final DtFindCanBoRequest canBoRequest) {
        Pageable pageable = PageRequest.of(canBoRequest.getPage(), canBoRequest.getSize());
        Page<DtCanBoResponse> res = dtCanBoRepository.findAllByTenCanBo(canBoRequest, pageable);
        return new PageableObject(res);
    }

    @Override
    public CanBo create(@Valid final DtCreateCanBoRequest canBoRequest) {
        CanBo canBo = formUtils.convertToObject(CanBo.class, canBoRequest);
        return dtCanBoRepository.save(canBo);
    }

    @Override
    public CanBo createcb(CanBo canBo) {
        return dtCanBoRepository.save(canBo);
    }


    @Override
    public Boolean delete(String id) {
        Optional<CanBo> canBoCheck = dtCanBoRepository.findById(id);
        if (Objects.isNull(canBoCheck)) {
            throw new RestApiException(Message.CAN_BO_NOT_EXIST);
        }
        dtCanBoRepository.delete(canBoCheck.get());
        return true;
    }

    @Override
    public CanBo findById(String id) {
        Optional<CanBo> canBo = dtCanBoRepository.findById(id);
        if (Objects.isNull(canBo)) {
            throw new RestApiException(Message.CAN_BO_NOT_EXIST);
        }
        return canBo.get();
    }

    @Override
    public CanBo update(DtUpdateCanBoRequest canBoRequest) {
        Optional<CanBo> canBo = dtCanBoRepository.findById(canBoRequest.getId());
        if (Objects.isNull(canBo)) {
            throw new RestApiException(Message.CAN_BO_NOT_EXIST);
        }
        Optional<CoSo> coSo = dtCoSoRepository.findById(canBoRequest.getCoSoId());
        if (Objects.isNull(coSo)) {
            throw new RestApiException(Message.CO_SO_NOT_EXIST);
        }
        CanBo updateCanBo = formUtils.convertToObject(CanBo.class, canBoRequest);
        return dtCanBoRepository.save(updateCanBo);
    }

    @Override
    public List<SimpleEntityProj> findAllSimpleEntityByTenCanBo(String id, int vaiTro) {
        return dtCanBoRepository.findAllSimpleEntityByTenCanBo(id, vaiTro);
    }

    @Override
    public Optional<CanBo> findByEmailFe(String emailFe) {
        return dtCanBoRepository.findByEmailFe(emailFe);
    }

    @Override
    public List<CanBo> importData(MultipartFile reapExcelDataFile, String id) {
        List<CanBo> listCanBo;
        try {
            listCanBo = new ArrayList<>();
            XSSFWorkbook workbook = new XSSFWorkbook(reapExcelDataFile.getInputStream());
            XSSFSheet worksheet = workbook.getSheetAt(0);

            for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {
                CanBo canBo = new CanBo();
                XSSFRow row = worksheet.getRow(i);
                canBo.setEmailFe(row.getCell(0).getStringCellValue());
                canBo.setEmailFpt(row.getCell(1).getStringCellValue());
                canBo.setSoDienThoai(row.getCell(2).getStringCellValue());
                canBo.setTenCanBo(row.getCell(3).getStringCellValue());
                canBo.setTenTaiKhoan(row.getCell(4).getStringCellValue());
                canBo.setVaiTro(VaiTro.valueOf(row.getCell(5).getStringCellValue()));
                canBo.setCoSoId(id);
                if (!findByEmailFe(canBo.getEmailFe()).isPresent()) {
                    listCanBo.add(canBo);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return listCanBo;
    }

}

