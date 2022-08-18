package com.fpolydatn.service;

import com.fpolydatn.infrastructure.command.hocky.CreateHocKyCommand;
import com.fpolydatn.infrastructure.command.hocky.UpdateHocKyCommand;
import com.fpolydatn.infrastructure.query.hocky.HocKyQuery;
import com.fpolydatn.model.domain.HocKy;
import com.fpolydatn.model.entity.HocKyEntity;

import javax.validation.Valid;
import java.util.List;

/**
 * @author HangNT
 */

public interface HocKyService {

    HocKyEntity create(@Valid final CreateHocKyCommand command);

    Boolean delete(final String id);

    HocKy update(@Valid final UpdateHocKyCommand command);

    HocKyEntity findById(final String id);

    long getSizeListHocKy(String tenHocKy);

    List<HocKy> search(final HocKyQuery query);

}
