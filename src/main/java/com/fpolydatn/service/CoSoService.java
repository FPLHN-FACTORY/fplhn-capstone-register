package com.fpolydatn.service;

import com.fpolydatn.infrastructure.command.coso.CreateCoSoCommand;
import com.fpolydatn.infrastructure.command.coso.UpdateCoSoCommand;
import com.fpolydatn.model.entity.CoSoEntity;

import java.util.List;

/**
 * @author thepvph20110
 */
public interface CoSoService {

    List<CoSoEntity> getListCoSo(int offset, int size);

    CoSoEntity deleteCoSo(String id);

    CoSoEntity addNew(CreateCoSoCommand canBo);

    CoSoEntity update(String id, UpdateCoSoCommand coSo);

    CoSoEntity findById(String id);

}
