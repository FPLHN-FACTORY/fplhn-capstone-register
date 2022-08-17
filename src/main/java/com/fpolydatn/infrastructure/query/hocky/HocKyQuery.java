package com.fpolydatn.infrastructure.query.hocky;

import com.fpolydatn.infrastructure.query.PageableQuery;
import lombok.Getter;
import lombok.Setter;

/**
 * @author phongtt35
 */
@Getter
@Setter
public class HocKyQuery extends PageableQuery {

    private String tenHocKy;
}
