package com.fpolydatn.core.daotao.service;

import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.daotao.model.request.FindPhanCongHuongDanRequest;
import com.fpolydatn.core.daotao.model.response.PhanCongHuongDanGiaoVienResponse;
import com.fpolydatn.core.daotao.model.response.PhanCongHuongDanMonDatnResponse;
import com.fpolydatn.core.daotao.model.response.PhanCongHuongDanResponse;
import com.fpolydatn.entity.PhanCongHuongDan;

import java.util.List;

/**
 * @author huynq
 */
public interface DtPhanCongHuongDanService {

    PageableObject<PhanCongHuongDanResponse> search(String id, final FindPhanCongHuongDanRequest req);

    PhanCongHuongDanMonDatnResponse getMon(String id);

    PhanCongHuongDan findId(String id);

    Boolean delete(String id);

    List<PhanCongHuongDanGiaoVienResponse> getPhanCongGiaoVien(String coSoId, String maMonId);

    PhanCongHuongDan add(PhanCongHuongDan phanCongHuongDan);

}
