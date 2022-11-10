package com.fpolydatn.core.giangvien.service.impl;

import com.fpolydatn.core.giangvien.repository.GvSinhVienRepository;
import com.fpolydatn.core.giangvien.service.GvSinhVienService;
import com.fpolydatn.core.sinhvien.model.request.SvFindSinhVienRequest;
import com.fpolydatn.core.sinhvien.model.response.SvSinhVienTheoNhomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author SonPT
 */
@Service
public class GvSinhVienServiceImpl implements GvSinhVienService {

    @Autowired
    private GvSinhVienRepository svSinhVienRepository;

    @Override
    public List<SvSinhVienTheoNhomResponse> findByNhomId(final SvFindSinhVienRequest request) {
        return svSinhVienRepository.findByNhomId(request);
    }


}
