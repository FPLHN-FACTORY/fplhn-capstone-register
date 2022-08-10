package com.fpolydatn.service.impl;

import com.fpolydatn.infrastructure.constant.Message;
import com.fpolydatn.infrastructure.exception.RestApiException;
import com.fpolydatn.model.domain.CoSo;
import com.fpolydatn.model.domain.DotDangKy;
import com.fpolydatn.repository.CoSoRepository;
import com.fpolydatn.repository.DotDangKyRepository;
import com.fpolydatn.service.DotDangKyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * @author thepvph20110
 */

@Service
public class DotDangKyServiceImpl implements DotDangKyService {

    @Autowired
    private DotDangKyRepository dotDangKyRepository;

    @Autowired
    private CoSoRepository coSoRepository;

    @Override
    public DotDangKy deleteDotDangKy(UUID id) {
        Optional<DotDangKy> dotDangKy = dotDangKyRepository.findById(id);
        if (Objects.isNull(dotDangKy)) {
            throw new RestApiException(Message.DOT_DANG_KY_NOT_EXIST);
        }
        dotDangKyRepository.delete(dotDangKy.get());
        return dotDangKy.get();
    }

    @Override
    public List<DotDangKy> getListDotDangKy(UUID coSoId, int offset, int size) {
        Optional<CoSo> coSo = coSoRepository.findById(coSoId);
        if (Objects.isNull(coSo)) {
            throw new RestApiException(Message.CO_SO_NOT_EXIST);
        }
        String id = String.valueOf(coSoId);
        return dotDangKyRepository.getListDotDangKy(id, offset, size);
    }

}
