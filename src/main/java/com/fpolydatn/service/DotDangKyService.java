package com.fpolydatn.service;

import com.fpolydatn.model.DotDangKy;
import java.util.List;
import java.util.UUID;

public interface DotDangKyService {

    DotDangKy deleteDotDangKy(UUID id);

    List<DotDangKy> getListDotDangKy(UUID coSoId,int offset,int size);

    DotDangKy findByID(UUID id);


}
