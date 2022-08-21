package com.fpolydatn.core.admin.service.daotao.service;

import com.fpolydatn.core.admin.service.daotao.model.request.DtFindPhanCongHuongDanRequest;
import com.fpolydatn.core.admin.service.daotao.model.response.DtPhanCongHuongDanGiaoVienResponse;
import com.fpolydatn.core.admin.service.daotao.model.response.DtPhanCongHuongDanMonDatnResponse;
import com.fpolydatn.core.admin.service.daotao.model.response.DtPhanCongHuongDanResponse;
import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.entity.PhanCongHuongDan;

import javax.validation.Valid;
import java.util.List;

/**
 * @author huynq
 */
public interface DtPhanCongHuongDanService {

    PageableObject<DtPhanCongHuongDanResponse> search(String id, final DtFindPhanCongHuongDanRequest req);

    DtPhanCongHuongDanMonDatnResponse getMon(String id);

    PhanCongHuongDan findId(String id);

    Boolean delete(String id);

    List<DtPhanCongHuongDanGiaoVienResponse> getPhanCongGiaoVien(String coSoId, String maMonId);

    PhanCongHuongDan add(@Valid PhanCongHuongDan phanCongHuongDan);

}
