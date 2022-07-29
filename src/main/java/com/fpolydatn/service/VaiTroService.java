package com.fpolydatn.service;

import com.fpolydatn.infrastructure.request.CreateVaiTroRequest;
import com.fpolydatn.model.VaiTro;

import java.util.List;
import java.util.UUID;

public interface VaiTroService {

    List<VaiTro> getList(int offset, int size);

    VaiTro addNew(CreateVaiTroRequest vaiTroRequest);

    String delete(UUID id);

    VaiTro updateVaiTro(CreateVaiTroRequest request , UUID id);

    VaiTro findByID(UUID id);

}

