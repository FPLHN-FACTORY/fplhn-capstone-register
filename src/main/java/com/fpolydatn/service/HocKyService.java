package com.fpolydatn.service;

import com.fpolydatn.infrastructure.query.hocky.HocKyQuery;
import com.fpolydatn.infrastructure.command.hocky.CreateHocKyCommand;
import com.fpolydatn.infrastructure.command.hocky.UpdateHocKyCommand;
import com.fpolydatn.model.domain.HocKy;
import com.fpolydatn.model.entity.HocKyEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

/**
 * @author HangNT
 */

@Service
@Validated
public interface HocKyService {

    HocKyEntity create(@Valid final CreateHocKyCommand command);

    Boolean delete(final UUID id);

    HocKyEntity update(final UpdateHocKyCommand command);

    HocKyEntity findById(final UUID id);

    long getSizeListHocKy(String tenHocKy);

    List<HocKy> search(final HocKyQuery query);

}
