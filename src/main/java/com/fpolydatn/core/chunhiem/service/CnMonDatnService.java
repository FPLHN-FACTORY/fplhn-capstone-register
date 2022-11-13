package com.fpolydatn.core.chunhiem.service;

import com.fpolydatn.core.chunhiem.model.response.CnMonDatnResponse;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author thangncph26123
 */
public interface CnMonDatnService {

    List<CnMonDatnResponse> getAllNhomMonDatn(String coSoId);

    List<CnMonDatnResponse> getAllMonDatnByNhomMon(String coSoId, String nhomMonId);

    List<CnMonDatnResponse> getAllMonDatnByChuyenNganh(String coSoId, String idChuNhiem);
}
