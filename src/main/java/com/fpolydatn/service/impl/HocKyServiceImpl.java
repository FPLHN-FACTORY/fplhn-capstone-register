package com.fpolydatn.service.impl;

import com.fpolydatn.infrastructure.constant.StatusCode;
import com.fpolydatn.infrastructure.exception.RestApiException;
import com.fpolydatn.infrastructure.request.CreateHocKyRequest;
import com.fpolydatn.model.HocKy;
import com.fpolydatn.repository.HocKyRepository;
import com.fpolydatn.service.HocKyService;
import com.fpolydatn.util.FormUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author vinhnv
 */
@Service
public class HocKyServiceImpl implements HocKyService {

    @Autowired
    private HocKyRepository hocKyRepository;

    private FormUtils formUtils = new FormUtils();

    @Override
    public List<HocKy> getList(int offset, int size) {
        return hocKyRepository.getListHocKy(offset, size);
    }

    @Override
    public HocKy addNew(CreateHocKyRequest hocKyRequet) {
        if (ObjectUtils.isEmpty(hocKyRequet.getTenHocKy())) {
            throw new RestApiException(StatusCode.TEN_HOC_KY_EMPTY);
        }
        HocKy hocKy = formUtils.convertToObject(HocKy.class, hocKyRequet);
        return hocKyRepository.save(hocKy);
    }

    @Override
    public String delete(UUID id) {
        HocKy hocKy = hocKyRepository.getById(id);

        if(hocKy == null){
            return "Học kỳ này không tồn tại";
        }

        hocKyRepository.deleteById(id);
        return "Xóa thành công";
    }


}
