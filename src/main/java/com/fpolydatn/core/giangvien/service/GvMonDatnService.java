package com.fpolydatn.core.giangvien.service;

import com.fpolydatn.infrastructure.projection.SimpleEntityProj;

import java.util.List;

/**
 * @author SonPT
 */
public interface GvMonDatnService {

    List<SimpleEntityProj> findAllSimpleEntityByCoSoAndGvhd(String idGvhd, String coSoId);

}
