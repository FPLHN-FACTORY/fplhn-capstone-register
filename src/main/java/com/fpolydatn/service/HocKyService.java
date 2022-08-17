package com.fpolydatn.service;

import com.fpolydatn.infrastructure.query.HocKyQuery;
import com.fpolydatn.infrastructure.command.hocky.CreateHocKyCommand;
import com.fpolydatn.infrastructure.command.hocky.UpdateHocKyCommand;
import com.fpolydatn.model.domain.HocKy;

import java.util.List;
import java.util.UUID;

/**
 * @author HangNT
 */
public interface HocKyService {

    HocKy create(CreateHocKyCommand hocKy);

    Boolean delete(UUID id);

    HocKy update(UUID id, UpdateHocKyCommand hocKy);

    HocKy findById(UUID id);

    long getSizeListHocKy(String tenHocKy);

    List<HocKy> search(HocKyQuery interactor);

}
