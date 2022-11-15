package com.fpolydatn.core.daotao.service;

import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.daotao.model.request.DtFindPhanCongHuongDanRequest;
import com.fpolydatn.core.daotao.model.request.DtFindPhanMonGiangVienHuongDanRequest;
import com.fpolydatn.core.daotao.model.response.DtGiangVienHDResponse;
import com.fpolydatn.core.daotao.model.response.DtPhanCongHuongDanMonDatnResponse;
import com.fpolydatn.core.daotao.model.response.DtPhanCongHuongDanResponse;
import com.fpolydatn.core.daotao.model.response.DtPhanMonDatnChoGiangVienHuongDanResponse;
import com.fpolydatn.entity.PhanCongHuongDan;

import java.util.List;

/**
 * @author huynq
 */
public interface DtPhanCongHuongDanService {
    PageableObject<DtPhanCongHuongDanResponse> search(String id, final DtFindPhanCongHuongDanRequest req);

    DtPhanCongHuongDanMonDatnResponse getMon(String id);

    PhanCongHuongDan findId(String idGV, String idMon);

    Boolean delete(String idMon, String idGV);

    List<DtPhanCongHuongDanResponse> getPhanCongGiaoVien(String coSoId, String maMonId);

    List<PhanCongHuongDan> addAll(List<PhanCongHuongDan> list);

    PageableObject<DtPhanMonDatnChoGiangVienHuongDanResponse> getPhanMonDatn(String id, final DtFindPhanMonGiangVienHuongDanRequest req);

    DtGiangVienHDResponse getGiangVienHuongDan(String id, String coSoId);

    List<DtPhanMonDatnChoGiangVienHuongDanResponse> getPhanMonDatnList(String coSoId, String maMonId);

}
