package com.fpolydatn.service;

import com.fpolydatn.model.entity.DotDangKyEntity;

import java.util.List;
import java.util.UUID;

/**
 * @author thepvph20110
 */
public interface DotDangKyService {

    DotDangKyEntity deleteDotDangKy(UUID id);

    List<DotDangKyEntity> getListDotDangKy(UUID id, int offset, int size);

}
