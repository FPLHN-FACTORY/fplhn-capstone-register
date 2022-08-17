package com.fpolydatn.infrastructure.mapper;

import com.fpolydatn.api.base.PageableRequest;
import com.fpolydatn.infrastructure.query.PageableQuery;

/**
 * @author phongtt35
 */
public interface FpolyDatnMapper {

    <T extends PageableQuery> T map(Class<T> clazz, final PageableRequest data);
}
