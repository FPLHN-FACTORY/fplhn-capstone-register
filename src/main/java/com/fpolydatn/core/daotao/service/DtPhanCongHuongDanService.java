package com.fpolydatn.core.daotao.service;

import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.daotao.model.request.DtFindPhanCongHuongDanRequest;
import com.fpolydatn.core.daotao.model.response.DtPhanCongHuongDanGiaoVienResponse;
import com.fpolydatn.core.daotao.model.response.DtPhanCongHuongDanMonDatnResponse;
import com.fpolydatn.core.daotao.model.response.DtPhanCongHuongDanResponse;
import com.fpolydatn.entity.PhanCongHuongDan;

import javax.validation.Valid;
import java.util.List;

/**
 * @author huynq
 */
public interface DtPhanCongHuongDanService {

    PageableObject<DtPhanCongHuongDanResponse> search(String id, final DtFindPhanCongHuongDanRequest req);

    DtPhanCongHuongDanMonDatnResponse getMon(String id);

    PhanCongHuongDan findId(String idGV, String idMon);

    Boolean delete(String idMon, String idGV);

    List<DtPhanCongHuongDanGiaoVienResponse> getPhanCongGiaoVien(String coSoId, String maMonId);

    List<PhanCongHuongDan> addAll(List<PhanCongHuongDan> list);

}
