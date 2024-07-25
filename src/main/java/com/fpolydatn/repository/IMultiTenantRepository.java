package com.fpolydatn.repository;

import com.fpolydatn.entity.base.IsMultiTenant;

import java.util.List;

public interface IMultiTenantRepository<T extends IsMultiTenant> {

    List<T> findByCoSoId(String coSoId);
}
