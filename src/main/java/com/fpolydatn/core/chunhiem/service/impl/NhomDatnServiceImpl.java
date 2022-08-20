package com.fpolydatn.core.chunhiem.service.impl;

import com.fpolydatn.core.admin.model.response.HocKyResponse;
import com.fpolydatn.core.chunhiem.model.request.FindNhomDatnRequest;
import com.fpolydatn.core.chunhiem.model.response.NhomDatnResponse;
import com.fpolydatn.core.chunhiem.repository.CnNhomDatnRepository;
import com.fpolydatn.core.chunhiem.service.NhomDatnService;
import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.infrastructure.constant.Message;
import com.fpolydatn.infrastructure.exception.rest.RestApiException;
import com.fpolydatn.util.FormUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author thangncph26123
 */
@Service
public class NhomDatnServiceImpl implements NhomDatnService {

    @Autowired
    private CnNhomDatnRepository cnNhomDatnRepository;

    private FormUtils formUtils = new FormUtils();

    @Override
    public PageableObject<NhomDatnResponse> search(FindNhomDatnRequest request) {
//        if (request.getTrangThai() != null || !request.getTrangThai().isEmpty()) {
//            try {
//                int trangThaiCheck = Integer.parseInt(request.getTrangThai());
//            } catch (Exception e) {
//                throw new RestApiException(Message.NUMBER_FORMAT_EXCEPTION);
//            }
//        }
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        Page<NhomDatnResponse> res = cnNhomDatnRepository.findNhomDatn(request, pageable);
        return new PageableObject(res);
    }

    @Override
    public List<String> listTenChuyenNganh() {
        return cnNhomDatnRepository.listTenChuyenNganh();
    }

    @Override
    public List<String> listTenGvhd() {
        return cnNhomDatnRepository.listTenGvhd();
    }

}
