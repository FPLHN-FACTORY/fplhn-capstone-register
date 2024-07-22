package com.fpolydatn.core.admin;

import com.fpolydatn.entity.NhomDatn;
import com.fpolydatn.repository.NhomDatnRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author PhongTT35
 * @description N/A
 **/
@Service
public class NhomDatnService {

    @Autowired
    @Qualifier(NhomDatnRepository.NAME)
    private NhomDatnRepository nhomDatnRepository;

    public Page<NhomDatn> getAllNhomDatns(Pageable pageable) {
        return nhomDatnRepository.findAll(pageable);
    }

    public Optional<NhomDatn> getNhomDatnById(String id) {
        return nhomDatnRepository.findById(id);
    }

    public NhomDatn saveNhomDatn(NhomDatn nhomDatn) {
        return nhomDatnRepository.save(nhomDatn);
    }

    public void deleteNhomDatn(String id) {
        nhomDatnRepository.deleteById(id);
    }

    public Page<NhomDatn> getNhomDatnsByCoSoIdAndDotDangKyId(String coSoId,
                                                             String dotDangKyId, Pageable pageable) {
        return nhomDatnRepository.findByCoSoIdAndDotDangKyId(coSoId, dotDangKyId, pageable);
    }
}
