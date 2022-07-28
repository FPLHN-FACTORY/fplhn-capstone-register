package com.fpolydatn.service;

import com.fpolydatn.model.CoSo;

import java.util.List;
import java.util.UUID;

/**
 * @author thepvph20110
 */
public interface CoSoService {
    List<CoSo> getListCoSo(int offset, int size);
    CoSo deleteCoSo(UUID id);
}
