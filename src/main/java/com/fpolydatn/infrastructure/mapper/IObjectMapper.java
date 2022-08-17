package com.fpolydatn.infrastructure.mapper;

import com.fpolydatn.api.base.PageableDto;
import com.fpolydatn.infrastructure.query.PageableQuery;

/**
 * @author phongtt35
 */
public interface IObjectMapper {

    <T extends PageableQuery> T map(Class<T> clazz, final PageableDto data);
}
