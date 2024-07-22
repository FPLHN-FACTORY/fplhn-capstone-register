package com.fpolydatn.core.admin;

import com.fpolydatn.entity.CoSo;
import com.fpolydatn.repository.CoSoRepository;
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
public class CoSoService {

    @Autowired
    @Qualifier(CoSoRepository.NAME)
    private CoSoRepository coSoRepository;

    public List<CoSo> getAllCoSos() {
        return coSoRepository.findAll();
    }

    public Optional<CoSo> getCoSoById(String id) {
        return coSoRepository.findById(id);
    }

    public CoSo saveCoSo(CoSo coSo) {
        return coSoRepository.save(coSo);
    }

    public void deleteCoSo(String id) {
        coSoRepository.deleteById(id);
    }
}
