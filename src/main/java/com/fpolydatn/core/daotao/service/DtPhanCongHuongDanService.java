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
    // phân công giảng viên hướng dẫn vào môn
    PageableObject<DtPhanCongHuongDanResponse> search(String id, final DtFindPhanCongHuongDanRequest req, int page);

    DtPhanCongHuongDanMonDatnResponse getMon(String id);

    PhanCongHuongDan findById(String idGV, String idMon);

    Boolean delete(String idMon, String idGV);

    PageableObject<DtPhanCongHuongDanResponse> getPhanCongGiaoVien(String coSoId, String maMonId, final DtFindPhanCongHuongDanRequest req);

    List<PhanCongHuongDan> addAll(List<PhanCongHuongDan> list);

    // phân môn cho giảng viên
    PageableObject<DtPhanMonDatnChoGiangVienHuongDanResponse> getPhanMonDatn(String coSoId, String id, final DtFindPhanMonGiangVienHuongDanRequest req);

    DtGiangVienHDResponse getGiangVienHuongDan(String id, String coSoId);

    PageableObject<DtPhanMonDatnChoGiangVienHuongDanResponse> getPhanMonDatnList(String coSoId, String maMonId, final DtFindPhanMonGiangVienHuongDanRequest req);

}
