package com.fpolydatn.core.giangvien.service.impl;

import com.fpolydatn.core.giangvien.repository.GvMonDatnRepository;
import com.fpolydatn.core.giangvien.service.GvMonDatnService;
import com.fpolydatn.infrastructure.projection.SimpleEntityProj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author SonPT
 */

@Service
public class GvMonDatnServiceImpl implements GvMonDatnService {

    @Autowired
    private GvMonDatnRepository gvMonDatnRepository;

    @Override
    public List<SimpleEntityProj> findAllSimpleEntityByCoSoAndGvhd(String idGvhd, String coSoId) {
        return gvMonDatnRepository.findAllSimpleEntityByCoSoAndGvhd(idGvhd, coSoId);
    }

}
