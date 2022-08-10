package com.fpolydatn.service;

import com.fpolydatn.model.domain.DotDangKy;

import java.util.List;
import java.util.UUID;

/**
 * @author thepvph20110
 */
public interface DotDangKyService {

    DotDangKy deleteDotDangKy(UUID id);

    List<DotDangKy> getListDotDangKy(UUID id, int offset, int size);

}
