package com.fpolydatn.core.daotao.service;


import com.fpolydatn.infrastructure.projection.SimpleEntityProj;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author thepvph20110
 */
public interface DtChuyenNganhService {

    List<SimpleEntityProj>  findAllSimpleEntityByCoSo( String coSoId);

}
