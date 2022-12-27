package com.fpolydatn.core.sinhvien.service;

import com.fpolydatn.core.sinhvien.model.response.SvMonDatnResponse;

import java.util.List;

/**
 * @author SonPT
 */

public interface SvMonDatnService {

    List<SvMonDatnResponse> getAllMonDatnByIdUser(String id, String coSoId, String dotDangKyId);

}
