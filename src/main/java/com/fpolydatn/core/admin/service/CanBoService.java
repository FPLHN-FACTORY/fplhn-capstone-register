package com.fpolydatn.core.admin.service;

import com.fpolydatn.entity.CanBo;
import com.fpolydatn.repository.CanBoRepository;
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
public class CanBoService {

    @Autowired
    @Qualifier(CanBoRepository.NAME)
    private CanBoRepository canBoRepository;

    public List<CanBo> getAllCanBo() {
        return canBoRepository.findAll();
    }

    public List<CanBo> getCanBosByCosoId(String cosoId) {
        return canBoRepository.findByCoSoId(cosoId);
    }

    public Optional<CanBo> getCanBoById(String id) {
        return canBoRepository.findById(id);
    }

    public CanBo saveCanBo(CanBo canBo) {
        return canBoRepository.save(canBo);
    }

    public void deleteCanBo(String id) {
        canBoRepository.deleteById(id);
    }
}
