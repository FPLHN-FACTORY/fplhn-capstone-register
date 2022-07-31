package com.fpolydatn.service;

import com.fpolydatn.model.DotDangKy;

import java.util.UUID;

/**
 * @author caodinh
 */
public interface DotDangKyService {

    DotDangKy findByID(UUID uuid);
}
