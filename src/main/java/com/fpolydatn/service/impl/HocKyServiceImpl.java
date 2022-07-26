package com.fpolydatn.service.impl;

import com.fpolydatn.infrastructure.request.CreateHocKyRequet;
import com.fpolydatn.model.HocKy;
import com.fpolydatn.repository.HocKyRepository;
import com.fpolydatn.service.HocKyService;
import com.fpolydatn.util.FormUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public HocKy addNew(CreateHocKyRequet hocKyRequet) {
        HocKy hocKy = formUtils.convertToObject(HocKy.class, hocKyRequet);
        return hocKyRepository.save(hocKy);
    }

    @Override
    public String delete(UUID id) {
        Optional<HocKy> hocKy = hocKyRepository.findById(id);

        if(!hocKy.isPresent()){
            return "Học kỳ này không tồn tại";
        }

        hocKyRepository.deleteById(id);
        return "Xóa thành công";
    }
}
