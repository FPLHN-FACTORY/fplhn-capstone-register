package com.fpolydatn.core.chunhiem.service;

import com.fpolydatn.core.chunhiem.model.request.CnFindDashboardRequest;
import com.fpolydatn.core.chunhiem.model.response.CnDashboardGvhdResponse;
import com.fpolydatn.core.chunhiem.model.response.CnDashboardResponse;
import com.fpolydatn.infrastructure.projection.SimpleEntityProj;

import java.util.List;

/**
 * @author thangncph26123
 */
public interface CnDashboardService {

    CnDashboardResponse dashboard(final CnFindDashboardRequest request);

    List<CnDashboardGvhdResponse> dashboardGvhd(final CnFindDashboardRequest request);

    List<SimpleEntityProj> getAllChuyenNganhByIdChuNhiem(final CnFindDashboardRequest request);

    List<SimpleEntityProj> getAllMonDatnByIdChuNhiem(final CnFindDashboardRequest request);
}
