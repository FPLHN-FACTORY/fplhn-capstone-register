package com.fpolydatn.core.admin;

import com.fpolydatn.entity.ChuyenNganh;
import com.fpolydatn.repository.ChuyenNganhRepository;
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
public class ChuyenNganhService {

    @Autowired
    @Qualifier(ChuyenNganhRepository.NAME)
    private ChuyenNganhRepository chuyenNganhRepository;

    public List<ChuyenNganh> getAllChuyenNganhs() {
        return chuyenNganhRepository.findAll();
    }

    public Optional<ChuyenNganh> getChuyenNganhById(String id) {
        return chuyenNganhRepository.findById(id);
    }

    public ChuyenNganh saveChuyenNganh(ChuyenNganh chuyenNganh) {
        return chuyenNganhRepository.save(chuyenNganh);
    }

    public void deleteChuyenNganh(String id) {
        chuyenNganhRepository.deleteById(id);
    }

    public List<ChuyenNganh> getChuyenNganhsByCoSoId(String coSoId) {
        return chuyenNganhRepository.findByCoSoId(coSoId);
    }
}
