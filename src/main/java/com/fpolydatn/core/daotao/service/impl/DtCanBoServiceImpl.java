package com.fpolydatn.core.daotao.service.impl;

import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.daotao.excel.DtCanBoExportExcel;
import com.fpolydatn.core.daotao.excel.DtCanBoImportExcel;
import com.fpolydatn.core.daotao.excel.DtSinhVienTheoDotExport;
import com.fpolydatn.core.daotao.model.request.DtCreateCanBoRequest;
import com.fpolydatn.core.daotao.model.request.DtFindCanBoRequest;
import com.fpolydatn.core.daotao.model.request.DtUpdateCanBoRequest;
import com.fpolydatn.core.daotao.model.request.FindDtSinhVienTheoDotRequest;
import com.fpolydatn.core.daotao.model.response.DtCanBoResponse;
import com.fpolydatn.core.daotao.model.response.DtSinhVienTheoDotResponse;
import com.fpolydatn.core.daotao.repository.DtCanBoRepository;
import com.fpolydatn.core.daotao.repository.DtCoSoRepository;
import com.fpolydatn.core.daotao.service.DtCanBoService;
import com.fpolydatn.entity.CanBo;
import com.fpolydatn.entity.CoSo;
import com.fpolydatn.infrastructure.constant.Constants;
import com.fpolydatn.infrastructure.constant.Message;
import com.fpolydatn.infrastructure.exception.rest.RestApiException;
import com.fpolydatn.infrastructure.projection.SimpleEntityProj;
import com.fpolydatn.util.FormUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        return dtCanBoRepository.findCanBoByVaiTro(id, vaiTro);
    }


    @Override
    public RestApiException DtCanBoImport(MultipartFile file, String coSoId) throws IOException {
        DtCanBoImportExcel dtCanBoImportExcel = new DtCanBoImportExcel();
        List<CanBo> listCB = dtCanBoImportExcel.importData(file, coSoId);
        Map<String, String> map = new HashMap<>();
        addDataInMap(map);

        for (CanBo canbo : listCB) {
            if (map.get(canbo.getEmailFe()) != null || map.get(canbo.getEmailFpt()) != null) {
                throw new RestApiException(Message.CAN_BO_EXSIT);
            }

            if (canbo.getEmailFpt().isEmpty() || canbo.getEmailFe().isEmpty() ||
                    canbo.getSoDienThoai().isEmpty() || canbo.getTenTaiKhoan().isEmpty() ||
                    canbo.getTenCanBo().isEmpty() || canbo.getVaiTro().toString().isEmpty()) {
                throw new RestApiException(Message.EMPTY);
            }

            if(!canbo.getEmailFpt().matches(Constants.REGEX_EMAIL_FPT)){
                throw new RestApiException(Message.EMAIL_MALFORMED);
            }

            if(!canbo.getEmailFe().matches(Constants.REGEX_EMAIL_FE)){
                throw new RestApiException(Message.EMAIL_MALFORMED);
            }

            if(!canbo.getSoDienThoai().matches(Constants.REGEX_PHONE_NUMBER)){
                throw new RestApiException(Message.NUMBER_FORMAT_EXCEPTION);
            }

            if(canbo.getTenTaiKhoan().contains(" ")){
                throw new RestApiException(Message.NO_SPACES_ALLOW);
            }
        }
        dtCanBoRepository.saveAll(listCB);
        return new RestApiException(Message.SUCCESS) ;
    }

    @Override
    public void MauImportExcel(HttpServletResponse response, String typeExcel) throws IOException {
        DtCanBoExportExcel dtCanBoExportExcel =  new DtCanBoExportExcel();
        response.setContentType("application/octet-stream");

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=MauImportExcel" + "." + typeExcel;
        response.setHeader(headerKey, headerValue);
        dtCanBoExportExcel.exportData(response);
    }

    public void addDataInMap(Map<String, String> mapAll) {
        List<String> listEmailFE = dtCanBoRepository.findAllEmailFe();
        setDataInMap(mapAll, listEmailFE);

        List<String> listEmailFpt = dtCanBoRepository.findAllByEmailFPT();
        setDataInMap(mapAll, listEmailFpt);

    }

    public void setDataInMap(Map<String, String> mapSimple, List<String> listStr) {
        for (String s : listStr) {
            mapSimple.put(s, s);
        }
    }

}

