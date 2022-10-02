package com.fpolydatn.core.daotao.model.response;

import com.fpolydatn.core.common.base.PageableObject;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class DtApiResponse<T> {

    PageableObject<T> data;

    List<Integer> list;

}
