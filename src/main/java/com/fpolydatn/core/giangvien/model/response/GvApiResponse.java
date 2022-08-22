package com.fpolydatn.core.giangvien.model.response;

import com.fpolydatn.core.common.base.PageableObject;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author thangdt
 */
@Getter
@Setter
public class GvApiResponse {

    private PageableObject<GvNhomDatnResponse> listNhomDatn;
    private List<Integer> listPage;

    public GvApiResponse(PageableObject<GvNhomDatnResponse> listNhomDatn, List<Integer> listPage) {
        this.listNhomDatn = listNhomDatn;
        this.listPage = listPage;
    }
}
