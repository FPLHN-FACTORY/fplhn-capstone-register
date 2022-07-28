package com.fpolydatn.service;
import com.fpolydatn.infrastructure.request.CreateCoSoRequest;
import com.fpolydatn.model.CoSo;
import java.util.List;
import java.util.UUID;

public interface CoSoService {
    CoSo findById(UUID id);
    List<CoSo> getList(int offset, int size);
    CoSo addNew(CreateCoSoRequest coSo);
}
