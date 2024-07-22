package com.fpolydatn.core.admin;

import com.fpolydatn.entity.DotDangKy;
import com.fpolydatn.repository.DotDangKyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author PhongTT35
 * @description N/A
 **/
@Service
public class DotDangKyService {

    @Autowired
    @Qualifier(DotDangKyRepository.NAME)
    private DotDangKyRepository dotDangKyRepository;

    public List<DotDangKy> getAllDotDangKys() {
        return dotDangKyRepository.findAll();
    }

    public Optional<DotDangKy> getDotDangKyById(String id) {
        return dotDangKyRepository.findById(id);
    }

    public DotDangKy saveDotDangKy(DotDangKy dotDangKy) {
        return dotDangKyRepository.save(dotDangKy);
    }

    public void deleteDotDangKy(String id) {
        dotDangKyRepository.deleteById(id);
    }

    public List<DotDangKy> getDotDangKysByCoSoId(String coSoId) {
        return dotDangKyRepository.findByCoSoId(coSoId);
    }
}
