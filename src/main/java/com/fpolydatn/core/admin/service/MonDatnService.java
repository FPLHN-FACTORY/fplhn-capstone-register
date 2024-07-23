package com.fpolydatn.core.admin.service;

import com.fpolydatn.entity.MonDatn;
import com.fpolydatn.repository.MonDatnRepository;
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
public class MonDatnService {

    @Autowired
    @Qualifier(MonDatnRepository.NAME)
    private MonDatnRepository monDatnRepository;

    public List<MonDatn> getAllMonDatns() {
        return monDatnRepository.findAll();
    }

    public Optional<MonDatn> getMonDatnById(String id) {
        return monDatnRepository.findById(id);
    }

    public MonDatn saveMonDatn(MonDatn monDatn) {
        return monDatnRepository.save(monDatn);
    }

    public void deleteMonDatn(String id) {
        monDatnRepository.deleteById(id);
    }

    public List<MonDatn> getMonDatnsByCoSoId(String coSoId) {
        return monDatnRepository.findByCoSoId(coSoId);
    }
}