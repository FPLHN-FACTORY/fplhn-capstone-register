package com.fpolydatn.core.chunhiem.model.response;

import com.fpolydatn.core.common.base.PageableObject;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author thangncph26123
 */
@Getter
@Setter
public class CnApiResponse<T> {

    PageableObject<T> data;

    List<Integer> list;
}
