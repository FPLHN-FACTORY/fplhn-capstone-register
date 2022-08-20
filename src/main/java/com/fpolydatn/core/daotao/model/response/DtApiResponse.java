package com.fpolydatn.core.daotao.model.response;

import com.fpolydatn.core.common.base.PageableObject;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author Vinhnv
 */
@Getter
@Setter
public class DtApiResponse<T> {

    private PageableObject<T> data;

    private List<Integer> list;
}
