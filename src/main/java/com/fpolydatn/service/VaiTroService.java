package com.fpolydatn.service;

import com.fpolydatn.infrastructure.request.CreateVaiTroRequest;
import com.fpolydatn.model.VaiTro;

import java.util.List;

public interface VaiTroService {

    List<VaiTro> getList(int offset, int size);

    VaiTro addNew(CreateVaiTroRequest vaiTroRequest);

}

