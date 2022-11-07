package com.fpolydatn.core.daotao.service;

import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.daotao.model.request.DtCreateMonDatnRequest;
import com.fpolydatn.core.daotao.model.request.DtCreateNhomMonDatnRequest;
import com.fpolydatn.core.daotao.model.request.DtFindNhomMonDatnRequest;
import com.fpolydatn.core.daotao.model.request.DtFindPhanMonGiangVienHuongDanRequest;
import com.fpolydatn.core.daotao.model.request.DtUpdateNhomMonDatnRequest;
import com.fpolydatn.core.daotao.model.response.DtFindNhomMonDatnResponse;
import com.fpolydatn.core.daotao.model.response.DtNhomMonDatnResponse;
import com.fpolydatn.core.daotao.model.response.DtPhanMonDatnChoGiangVienHuongDanResponse;
import com.fpolydatn.entity.CanBo;
import com.fpolydatn.entity.ChuyenNganh;
import com.fpolydatn.entity.MonDatn;
import com.fpolydatn.entity.NhomDatn;

import javax.validation.Valid;
import java.util.List;

public interface DtNhomMonDatnService {
    PageableObject<DtNhomMonDatnResponse> search(@Valid final DtFindNhomMonDatnRequest req);

    Boolean delete(final String id);

    Boolean update(@Valid final DtUpdateNhomMonDatnRequest command);

    Boolean add(@Valid final DtCreateNhomMonDatnRequest command);

    MonDatn findById(String id);

    List<DtFindNhomMonDatnResponse> getMonChiTiet(String id, final DtFindNhomMonDatnRequest req);
}
