package com.fpolydatn.service;
import com.fpolydatn.infrastructure.command.coso.CreateCoSoCommand;
import com.fpolydatn.infrastructure.command.coso.UpdateCoSoCommand;
import com.fpolydatn.model.entity.CoSoEntity;

import java.util.List;
import java.util.UUID;

/**
 * @author thepvph20110
 */
public interface CoSoService {

    List<CoSoEntity> getListCoSo(int offset, int size);

    CoSoEntity deleteCoSo(UUID id);

    CoSoEntity addNew(CreateCoSoCommand canBo);

    CoSoEntity update(UUID id, UpdateCoSoCommand coSo);

    CoSoEntity findById(UUID id);

}
